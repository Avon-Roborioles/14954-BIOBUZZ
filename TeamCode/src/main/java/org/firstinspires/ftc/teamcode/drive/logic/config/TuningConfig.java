package org.firstinspires.ftc.teamcode.drive.logic.config;

/**
 * TuningConfig centralizes all simulator and robot tuning parameters.
 *
 * All values start as simulator guesses or placeholders. Retune them on the robot.
 * Units: inches, seconds, radians (unless the name says DEG).
 */
public class TuningConfig {
    private TuningConfig() {}

    // ---------- Stage 1: Sense ----------
    public static final double CAM_HEIGHT_IN = 10.0;          // TODO measure: lens height above floor
    public static final double CAM_PITCH_DEG = -25.0;         // TODO measure: negative = tilted down
    public static final double CAM_FORWARD_OFFSET_IN = 0.0;   // TODO measure: lens ahead of robot center
    public static final double BALL_CENTER_HEIGHT_IN = 2.5;   // TODO measure: half the ball diameter
    public static final double MAX_DETECT_RANGE_IN = 120.0;   // ignore detections farther than this
    public static final double POSE_HISTORY_SECONDS = 1.0;    // how long to keep past poses

    // ---------- Stage 2: Memory ----------
    public static final double FRESH_SECONDS = 0.5;           // under this: fresh
    public static final double EXPIRE_SECONDS = 2.5;          // at or over this: expired
    public static final double MERGE_RADIUS_IN = 10.0;        // detections this close = same ball

    // ---------- Stage 3: Zones ----------
    public static final int MAX_ZONES = 6;
    public static final double ZONE_RADIUS_IN = 30.0;
    public static final double ZONE_NUDGE_FRACTION = 0.2;     // zone moves this far toward a new point
    public static final double ZONE_START_WEIGHT = 1.0;
    public static final double ZONE_NUDGE_WEIGHT = 0.4;
    public static final double ZONE_MAX_WEIGHT = 3.0;
    public static final double ZONE_MIN_WEIGHT = 0.15;        // below this, the zone is removed
    public static final double ZONE_EMPTY_FACTOR = 0.5;       // weight multiplier when a visit finds nothing
    public static final double ZONE_SCORE_OFFSET_IN = 20.0;
    public static final double ZONE_ARRIVE_RADIUS_IN = 12.0;

    // ---------- Stage 4: Selection ----------
    public static final int INTAKE_CAP = 4;
    public static final double CLUSTER_LINK_IN = 24.0;
    public static final double CLUSTER_SCORE_OFFSET_IN = 12.0;
    public static final double TURN_COST_PER_RAD = 8.0;       // inches of cost per radian turned
    public static final double SKIP_SECONDS = 4.0;
    public static final double SKIP_MATCH_RADIUS_IN = 10.0;
    public static final double SHOOT_X_IN = 72.0;             // TODO set your real shooting spot
    public static final double SHOOT_Y_IN = 12.0;

    // ---------- Stage 5: Drive ----------
    public static final double DECISION_TICK_SECONDS = 0.3;
    public static final double CLOSER_RATIO = 0.7;            // replan if a ball is under 0.7x the target distance
    public static final double TARGET_MATCH_RADIUS_IN = 10.0; // same target / same ball
    public static final double IDLE_SHOOT_SECONDS = 2.2;
    public static final double STALL_SECONDS = 1.2;
    public static final double PROGRESS_MIN_IN = 1.0;         // closer by this much counts as progress
    public static final double PICKUP_RADIUS_IN = 6.0;        // TODO replace with a real intake signal
    public static final double RIVAL_MARGIN_IN = 10.0;        // rival must be this much closer to drop a ball
    public static final double RIVAL_TAKE_RADIUS_IN = 5.0;    // rival this close to a ball = it takes it
}