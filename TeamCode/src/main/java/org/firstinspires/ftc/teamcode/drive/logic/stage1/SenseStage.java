package org.firstinspires.ftc.teamcode.drive.logic.stage1;

import org.firstinspires.ftc.teamcode.drive.logic.config.TuningConfig;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * STAGE 1 - Sense: converts Limelight detections into field coordinates.
 *
 * To account for latency, each detection is placed using the robot pose at the
 * moment the frame was captured, not the current pose. Balls lie on the floor,
 * so the camera ray from each detection is intersected with the floor plane.
 *
 * Merging detections with known balls happens in MemoryStage, because that
 * stage owns the list of balls.
 *
 * Assumptions: heading is in radians, counter-clockwise, 0 along +x. Limelight
 * tx is positive to the right and ty is positive upward. The camera must be
 * above the ball center and tilted so the ball is below its view center.
 */
public class SenseStage {
    // Color codes
    public static final int YELLOW = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;

    // {time, x, y, heading}
    private final ArrayDeque<double[]> history = new ArrayDeque<>();

    /** Call every loop with the current pose and clock. */
    public void recordPose(double time, double x, double y, double heading) {
        history.addLast(new double[]{time, x, y, heading});
        while (!history.isEmpty()
                && time - history.peekFirst()[0] > TuningConfig.POSE_HISTORY_SECONDS) {
            history.removeFirst();
        }
    }

    /** Pose {x, y, heading} at a past time, blended between samples. Null if there is no history. */
    public double[] poseAt(double time) {
        if (history.isEmpty()) return null;
        double[] prev = null;
        for (double[] s : history) {
            if (s[0] >= time) {
                if (prev == null) return new double[]{s[1], s[2], s[3]};  // older than all samples
                double span = s[0] - prev[0];
                double f = span > 1e-9 ? (time - prev[0]) / span : 1.0;
                return new double[]{
                        prev[1] + f * (s[1] - prev[1]),
                        prev[2] + f * (s[2] - prev[2]),
                        prev[3] + f * wrap(s[3] - prev[3])};
            }
            prev = s;
        }
        return new double[]{prev[1], prev[2], prev[3]};                  // newer than all samples
    }

    /**
     * detections: {txDegrees, tyDegrees, color}, one per ball in the frame.
     * captureTime: when the frame was captured (now minus camera latency).
     * Returns {x, y, color, time} in field inches, ready for MemoryStage.addAll().
     */
    public double[][] process(double[][] detections, double captureTime) {
        double[] pose = poseAt(captureTime);
        if (pose == null) return new double[0][];

        double h = pose[2];
        double camX = pose[0] + TuningConfig.CAM_FORWARD_OFFSET_IN * Math.cos(h);
        double camY = pose[1] + TuningConfig.CAM_FORWARD_OFFSET_IN * Math.sin(h);
        double pitch = Math.toRadians(TuningConfig.CAM_PITCH_DEG);
        double dh = TuningConfig.BALL_CENTER_HEIGHT_IN - TuningConfig.CAM_HEIGHT_IN;

        List<double[]> out = new ArrayList<>();
        for (double[] d : detections) {
            double tanTx = Math.tan(Math.toRadians(d[0]));
            double tanTy = Math.tan(Math.toRadians(d[1]));

            // Ray direction in robot axes: forward, right, up (camera pitched by 'pitch').
            double up = Math.sin(pitch) + tanTy * Math.cos(pitch);
            double fwd = Math.cos(pitch) - tanTy * Math.sin(pitch);
            if (up >= -1e-6) continue;           // ray never reaches the floor

            double t = dh / up;                  // both negative, so t is positive
            double forward = t * fwd;
            double right = t * tanTx;
            if (forward <= 0) continue;
            if (Math.hypot(forward, right) > TuningConfig.MAX_DETECT_RANGE_IN) continue;

            // The right-hand side of the heading is (sin h, -cos h).
            double x = camX + forward * Math.cos(h) + right * Math.sin(h);
            double y = camY + forward * Math.sin(h) - right * Math.cos(h);
            out.add(new double[]{x, y, d[2], captureTime});
        }
        return out.toArray(new double[0][]);
    }

    /** Fake field-space balls for testing without a robot. Ages: 0.05, 0.2, 0.6, 2.0, 3.0 s. */
    public double[][] getDummyCoords(double now) {
        return new double[][]{
                { 24.0,  36.5, YELLOW, now - 0.05},
                { 30.2,  60.0, YELLOW, now - 0.20},
                { 48.7,  96.3, BLUE,   now - 0.60},
                { 72.0,  72.8, YELLOW, now - 2.00},
                { 90.4,  40.1, RED,    now - 3.00}
        };
    }

    private static double wrap(double a) {
        while (a > Math.PI) a -= 2 * Math.PI;
        while (a < -Math.PI) a += 2 * Math.PI;
        return a;
    }
}