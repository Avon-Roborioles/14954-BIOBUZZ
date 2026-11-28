package org.firstinspires.ftc.teamcode.drive.logic.stage3;

import org.firstinspires.ftc.teamcode.drive.logic.config.TuningConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * STAGE 3 - Zones: coarse, long-term estimates of where balls are likely to be.
 *
 * - Update: when a ball expires, the nearest zone within the zone radius moves
 *   toward it and gains weight. If no zone is near, a new one is created.
 * - Navigate: with no fresh balls in view, go to the zone with the highest
 *   weight / (distance + offset).
 * - Validate: if the robot arrives and sees nothing, the zone's weight is halved.
 *   Below the minimum weight, the zone is removed.
 */
public class ZonesStage {
    // {x, y, weight}
    private final List<double[]> zones = new ArrayList<>();

    /** Pass in MemoryStage.getExpired(). Call once per loop. */
    public void update(double[][] expired) {
        for (double[] b : expired) {
            addPoint(b[0], b[1]);
        }
    }

    private void addPoint(double x, double y) {
        double[] best = null;
        double bestDist = TuningConfig.ZONE_RADIUS_IN;
        for (double[] z : zones) {
            double d = Math.hypot(z[0] - x, z[1] - y);
            if (d < bestDist) {
                bestDist = d;
                best = z;
            }
        }
        if (best != null) {
            best[0] += TuningConfig.ZONE_NUDGE_FRACTION * (x - best[0]);
            best[1] += TuningConfig.ZONE_NUDGE_FRACTION * (y - best[1]);
            best[2] = Math.min(TuningConfig.ZONE_MAX_WEIGHT, best[2] + TuningConfig.ZONE_NUDGE_WEIGHT);
        } else if (zones.size() < TuningConfig.MAX_ZONES) {
            zones.add(new double[]{x, y, TuningConfig.ZONE_START_WEIGHT});
        }
        // if full and no zone is close, ignore the point
    }

    /** Best zone {x, y, weight} for the robot position, or null if there are none. */
    public double[] getBestZone(double robotX, double robotY) {
        double[] best = null;
        double bestScore = -1;
        for (double[] z : zones) {
            double d = Math.hypot(z[0] - robotX, z[1] - robotY);
            double score = z[2] / (d + TuningConfig.ZONE_SCORE_OFFSET_IN);
            if (score > bestScore) {
                bestScore = score;
                best = z;
            }
        }
        return best;
    }

    /** The robot arrived and saw nothing. Pass the same array getBestZone() returned. */
    public void markEmpty(double[] zone) {
        zone[2] *= TuningConfig.ZONE_EMPTY_FACTOR;
        if (zone[2] < TuningConfig.ZONE_MIN_WEIGHT) {
            zones.remove(zone);
        }
    }

    /** Copies of all zones, so other stages cannot change the real data. */
    public double[][] getZones() {
        double[][] copy = new double[zones.size()][];
        for (int i = 0; i < zones.size(); i++) {
            copy[i] = zones.get(i).clone();
        }
        return copy;
    }
}