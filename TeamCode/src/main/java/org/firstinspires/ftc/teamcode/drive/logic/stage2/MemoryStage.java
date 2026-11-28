package org.firstinspires.ftc.teamcode.drive.logic.stage2;

import org.firstinspires.ftc.teamcode.drive.logic.config.TuningConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * STAGE 2 - Memory: manages how old each sighting is.
 *
 * - Under 0.5 s: fresh. Safe to target.
 * - 0.5 to 2.5 s: stale. Kept on the map, never targeted.
 * - 2.5 s or more: expired. Deleted here and reported once through getExpired()
 *   so ZonesStage can remember the area.
 *
 * This stage also merges new detections into known balls and lets other stages
 * remove balls that were collected or taken.
 */
public class MemoryStage {
    public static final int STATE_FRESH = 0;
    public static final int STATE_STALE = 1;

    private final List<double[]> balls = new ArrayList<>();     // {x, y, color, time}
    private final List<double[]> filtered = new ArrayList<>();  // {x, y, color, state}
    private final List<double[]> expired = new ArrayList<>();   // {x, y, color, time}

    /**
     * Adds a detection {x, y, color, time}. If a known ball is within the merge
     * radius, that ball is updated (when the detection is newer). Otherwise a
     * new ball is created.
     */
    public void add(double[] ball) {
        double[] near = nearest(ball[0], ball[1]);
        if (near != null) {
            if (ball[3] >= near[3]) {
                near[0] = ball[0];
                near[1] = ball[1];
                near[2] = ball[2];
                near[3] = ball[3];
            }
        } else {
            balls.add(new double[]{ball[0], ball[1], ball[2], ball[3]});
        }
    }

    public void addAll(double[][] detections) {
        for (double[] d : detections) add(d);
    }

    /** Removes the known ball nearest to (x, y), if one is within the merge radius. */
    public boolean remove(double x, double y) {
        double[] near = nearest(x, y);
        return near != null && balls.remove(near);
    }

    /** Call once per loop, then read getFiltered() and getExpired(). */
    public void update(double now) {
        filtered.clear();
        expired.clear();
        for (Iterator<double[]> it = balls.iterator(); it.hasNext();) {
            double[] b = it.next();
            double age = now - b[3];
            if (age >= TuningConfig.EXPIRE_SECONDS) {
                expired.add(b);
                it.remove();
            } else {
                int state = age < TuningConfig.FRESH_SECONDS ? STATE_FRESH : STATE_STALE;
                filtered.add(new double[]{b[0], b[1], b[2], state});
            }
        }
    }

    /** Fresh and stale balls as {x, y, color, state}. */
    public double[][] getFiltered() {
        return filtered.toArray(new double[0][]);
    }

    /** Balls that expired in the last update() as {x, y, color, time}. Valid until the next update(). */
    public double[][] getExpired() {
        return expired.toArray(new double[0][]);
    }

    private double[] nearest(double x, double y) {
        double[] best = null;
        double bestDist = TuningConfig.MERGE_RADIUS_IN;
        for (double[] b : balls) {
            double d = Math.hypot(b[0] - x, b[1] - y);
            if (d < bestDist) {
                bestDist = d;
                best = b;
            }
        }
        return best;
    }
}