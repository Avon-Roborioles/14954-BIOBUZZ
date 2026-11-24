package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import java.util.TreeMap;

public class RampExtension {
    // Maps: shooter x (distance) -> {lowAngleDeg, lowServo, highAngleDeg, highServo}
    private final TreeMap<Integer, double[]> angleMap = new TreeMap<>();

    private Servo rampServo;
    private Limelight3A limelight;

    public void init(HardwareMap hwMap) { // Pass hardwareMap in from the OpMode
        fillAngleMap();

        // Use the passed hwMap instance
        rampServo = hwMap.get(Servo.class, "servo");
        limelight = hwMap.get(Limelight3A.class, "limelight");

        limelight.setPollRateHz(100);
        limelight.start();
    }

    public void update() {
        // 1. Get distance from Limelight
        int distanceX = getShooterX();

        // 2. Find the correct servo setting based on distance
        // Only update if we have a valid distance (greater than 0)
        if (distanceX > 0) {
            double targetPosition = getServoPosition(distanceX);
            rampServo.setPosition(targetPosition);
        }
    }

    private void fillAngleMap() {
        // Key: Distance (x), Value: {lowAngle, lowServo, highAngle, highServo}
        angleMap.put(76,  new double[]{92.9,  0.587, 96.1,  0.565});
        angleMap.put(80,  new double[]{97.4,  0.557, 99.5,  0.544});
        angleMap.put(84,  new double[]{101.6, 0.530, 102.6, 0.524});
        angleMap.put(88,  new double[]{105.6, 0.505, 105.4, 0.506});
        angleMap.put(92,  new double[]{109.1, 0.482, 108.0, 0.489});
        angleMap.put(96,  new double[]{112.3, 0.462, 110.2, 0.475});
        angleMap.put(100, new double[]{115.0, 0.444, 112.1, 0.462});
        angleMap.put(104, new double[]{117.4, 0.429, 113.7, 0.452});
        angleMap.put(108, new double[]{119.3, 0.416, 114.9, 0.444});
        angleMap.put(112, new double[]{121.0, 0.406, 115.8, 0.439});
        angleMap.put(116, new double[]{122.2, 0.397, 116.2, 0.436});
        angleMap.put(120, new double[]{123.2, 0.391, 115.9, 0.438});
        angleMap.put(124, new double[]{123.8, 0.387, Double.NaN, Double.NaN});
    }

    private int getShooterX() {
        // 1. Get the latest result from Limelight
        LLResult result = limelight.getLatestResult();

        // 2. Check if the result is valid and contains a target
        if (result == null || !result.isValid()) {
            return 0; // Return 0 if no target is visible
        }

        // 3. Extract the vertical offset (ty) from the result
        double targetOffsetAngle_Vertical = result.getTy();

        // How many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = 25.0;

        // Distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = 20.0;

        // Distance from the target to the floor
        double goalHeightInches = 60.0;

        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = Math.toRadians(angleToGoalDegrees);

        // Calculate distance using trigonometry
        double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);

        return (int) distanceFromLimelightToGoalInches;
    }

    private double getServoPosition(int targetX) {
        // Find the closest distance keys in the map
        Integer floor = angleMap.floorKey(targetX);
        Integer ceiling = angleMap.ceilingKey(targetX);

        if (floor == null && ceiling == null) return 0.5; // Default safety
        if (floor == null) return angleMap.get(ceiling)[1];
        if (ceiling == null) return angleMap.get(floor)[1];

        // Pick the closest one
        int closestKey = (Math.abs(targetX - floor) <= Math.abs(ceiling - targetX)) ? floor : ceiling;

        // Return the 'lowServo' value (index 1) from the array
        return angleMap.get(closestKey)[1];
    }
}