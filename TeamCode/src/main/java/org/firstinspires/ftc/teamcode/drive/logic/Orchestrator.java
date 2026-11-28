package org.firstinspires.ftc.teamcode.drive.logic;

import org.firstinspires.ftc.teamcode.drive.logic.stage1.SenseStage;
import org.firstinspires.ftc.teamcode.drive.logic.stage2.MemoryStage;
import org.firstinspires.ftc.teamcode.drive.logic.stage3.ZonesStage;
import org.firstinspires.ftc.teamcode.drive.logic.stage4.SelectionStage;
import org.firstinspires.ftc.teamcode.drive.logic.stage5.DriveStage;
import org.firstinspires.ftc.teamcode.drive.logic.stage6.PathStage;

/**
 * The Orchestrator serves as the primary execution loop, coordinating the robot's
 * logic by sequencing multiple processing stages on a single shared state.
 * 
 * Each stage is executed once per tick in a predefined order, ensuring a 
 * consistent data flow from perception to action. The loop repeats continuously 
 * until the system is shut down.
 */
public class Orchestrator {
    // Initialize the robot logic
    SenseStage sensor;
    MemoryStage memory;
    ZonesStage zones;
    SelectionStage selection;
    DriveStage drive;
    PathStage path;
    public void init() {
        sensor = new SenseStage();
        memory = new MemoryStage();
        // DUMMY DATA
        memory.add(new double[]{ 24.0,  36.5, 0, 9.95});  // fresh (0.05 s old)
        memory.add(new double[]{ 30.2,  60.0, 0, 9.80});  // fresh (0.20 s old)
        memory.add(new double[]{ 48.7,  96.3, 1, 9.40});  // stale (0.60 s old)
        memory.add(new double[]{ 72.0,  72.8, 0, 8.00});  // stale (2.00 s old)
        memory.add(new double[]{ 90.4,  40.1, 2, 7.00});  // expired (3.00 s old)

        zones = new ZonesStage();
        selection = new SelectionStage();
        drive = new DriveStage(selection, zones, memory);
        path = new PathStage();
    }

    public void loop() {
        // TODO: GRAB VALUES FROM SENSORS (UPDATE THESE VARIABLES)
        double now = 10.0;               // Current time in seconds
        double poseX = 10.0;             // Robot x, inches
        double poseY = 10.0;             // Robot y, inches
        double poseHeading = 0.0;        // Radians, 0 = facing +x
        double[] rivalOrNull = null;     // Or new double[]{100.0, 100.0}
        int held = 0;                    // Pull from intake
        double latencySeconds = 0.05;    // TODO: Limelight capture + pipeline latency
        double[][] detections = new double[0][];  // TODO: {txDeg, tyDeg, color} per ball, empty if no new frame

        // Stage 1: remember where the robot was, then turn new detections into field balls
        sensor.recordPose(now, poseX, poseY, poseHeading);
        if (detections.length > 0) {
            double captureTime = now - latencySeconds;
            memory.addAll(sensor.process(detections, captureTime));
        }

        // Stage 2 and 3: age the balls, then turn expired ones into zones
        memory.update(now);
        zones.update(memory.getExpired());

        // Stage 4 and 5: choose balls and decide where to drive
        DriveStage.Decision d = drive.update(now, poseX, poseY, poseHeading,
                memory.getFiltered(), held, rivalOrNull);

        // Stage 6: pathing comes later
        if (d.newPath && d.type != DriveStage.TARGET_NONE) {
            // build the path to (d.x, d.y) here, later
        }
    }

}
