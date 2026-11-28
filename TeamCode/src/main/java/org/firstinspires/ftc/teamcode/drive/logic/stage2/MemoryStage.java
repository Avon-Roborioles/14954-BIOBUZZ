package org.firstinspires.ftc.teamcode.drive.logic.stage2;

/**
 * STAGE 2 — Memory: Manages the temporal reliability of detected objects.
 * 
 * This stage filters detections based on age to ensure the robot plans using 
 * accurate data. 
 * - Sightings within the last 0.5 seconds are considered "fresh" and suitable 
 *   for active targeting.
 * - Sightings between 0.5 and 2.5 seconds are marked as "stale." They remain on 
 *    the map for situational awareness but are not actively targeted, as 
 *   other robots may have moved them.
 * - After 2.5 seconds, sightings are dropped from the active map. However, 
 *   their last known position is used to update long-term zone memory.
 */
public class MemoryStage {
}
