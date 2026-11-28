package org.firstinspires.ftc.teamcode.drive.logic.stage1;

/**
 * STAGE 1 — Sense: Converts visual data into field coordinates.
 * 
 * Each Limelight frame provides a bearing and size for every detected ball.
 * To account for latency, the robot references its historical pose at the moment 
 * the frame was captured, rather than its current position.
 * 
 * Since game elements reside on the floor, the combination of distance and 
 * bearing allows for precise calculation of field positions. Detections near 
 * existing known balls update their state, while unrecognized detections 
 * initialize new ball entries.
 */
public class SenseStage {
}
