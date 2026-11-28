package org.firstinspires.ftc.teamcode.drive.logic.stage5;

import org.firstinspires.ftc.teamcode.drive.logic.config.TuningConfig;
import org.firstinspires.ftc.teamcode.drive.logic.stage2.MemoryStage;
import org.firstinspires.ftc.teamcode.drive.logic.stage3.ZonesStage;
import org.firstinspires.ftc.teamcode.drive.logic.stage4.SelectionStage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * STAGE 5 - Drive & Decision: re-evaluates the target every decision tick.
 *
 * - Validate: drop targets that were collected, went stale, or were taken.
 * - Replan: a new fresh ball, or a ball much closer than the current target.
 * - Prioritize: next ball, then the shooting spot, then the next zone.
 * - Stagnation: no progress for the stall time skips that ball for a while.
 *
 * It returns a Decision. Building the actual path is done elsewhere.
 */
public class DriveStage {
    public static final int TARGET_NONE = 0;
    public static final int TARGET_BALL = 1;
    public static final int TARGET_SHOOT = 2;
    public static final int TARGET_ZONE = 3;

    /** Where to drive. Build a new path only when newPath is true. */
    public static class Decision {
        public int type = TARGET_NONE;
        public double x, y;
        public boolean newPath;
    }

    private final SelectionStage selection;
    private final ZonesStage zones;
    private final MemoryStage memory;

    private final List<double[]> route = new ArrayList<>();  // {x, y, color, state}
    private Decision current = new Decision();
    private double lastTick = -1e9;
    private double lastBallTime = Double.NaN;

    // stall tracking
    private boolean tracking = false;
    private double trackX, trackY, bestDist, lastProgress;

    public DriveStage(SelectionStage selection, ZonesStage zones, MemoryStage memory) {
        this.selection = selection;
        this.zones = zones;
        this.memory = memory;
    }

    /** Call after the robot shoots. Also set held back to 0 in your own code. */
    public void clearRoute() {
        route.clear();
        tracking = false;
    }

    /**
     * Call as often as you like. It only decides once per decision tick.
     * balls are {x, y, color, state} from MemoryStage.getFiltered().
     * rival is {x, y} or null. heading is in radians.
     */
    public Decision update(double now, double rx, double ry, double heading,
                           double[][] balls, int held, double[] rival) {
        if (now - lastTick < TuningConfig.DECISION_TICK_SECONDS) {
            current.newPath = false;
            return current;
        }
        lastTick = now;
        if (Double.isNaN(lastBallTime)) lastBallTime = now;

        List<double[]> live = new ArrayList<>(Arrays.asList(balls));
        boolean replan = false;

        // 0. A rival on top of a ball means it takes the ball: forget it.
        if (rival != null) {
            for (Iterator<double[]> it = live.iterator(); it.hasNext();) {
                double[] b = it.next();
                if (Math.hypot(b[0] - rival[0], b[1] - rival[1]) < TuningConfig.RIVAL_TAKE_RADIUS_IN) {
                    memory.remove(b[0], b[1]);
                    it.remove();
                }
            }
        }

        // 1. Validate the route: collected, no longer fresh, or lost to the rival.
        for (Iterator<double[]> it = route.iterator(); it.hasNext();) {
            double[] r = it.next();
            if (Math.hypot(r[0] - rx, r[1] - ry) < TuningConfig.PICKUP_RADIUS_IN) {
                memory.remove(r[0], r[1]);               // collected: gone, and not a zone
                removeNear(live, r[0], r[1]);
                it.remove();
                tracking = false;
                continue;
            }
            double[] seen = findFresh(live, r[0], r[1]);
            boolean rivalAhead = rival != null
                    && Math.hypot(rival[0] - r[0], rival[1] - r[1])
                    < Math.hypot(rx - r[0], ry - r[1]) - TuningConfig.RIVAL_MARGIN_IN;
            if (seen == null || rivalAhead) {
                it.remove();
                replan = true;
                tracking = false;
            } else {
                r[0] = seen[0];                          // follow the newest position
                r[1] = seen[1];
            }
        }

        // 2. Stagnation: no progress on the first ball, so skip it for a while.
        if (!route.isEmpty() && stalled(route.get(0), rx, ry, now)) {
            selection.skip(route.get(0)[0], route.get(0)[1], now);
            route.remove(0);
            replan = true;
            tracking = false;
        }

        // 3. Replan triggers: a new fresh ball, or a much closer one.
        List<double[]> usable = selection.usable(live, rx, ry, rival, now);
        if (!replan && held < TuningConfig.INTAKE_CAP) {
            double targetDist = route.isEmpty() ? Double.MAX_VALUE
                    : Math.hypot(route.get(0)[0] - rx, route.get(0)[1] - ry);
            boolean extra = false, closer = false;
            for (double[] u : usable) {
                if (inRoute(u)) continue;
                extra = true;
                if (Math.hypot(u[0] - rx, u[1] - ry) < TuningConfig.CLOSER_RATIO * targetDist) {
                    closer = true;
                }
            }
            if ((extra && route.size() + held < TuningConfig.INTAKE_CAP) || closer) {
                replan = true;
            }
        }

        // 4. Replan.
        if (replan) {
            List<double[]> picked = selection.select(live, rx, ry, heading, rival, held, now);
            route.clear();
            route.addAll(picked);
            tracking = false;
        }

        // 5. Choose the target: next ball, then the shooting spot, then a zone.
        Decision d = new Decision();
        if (!route.isEmpty()) {
            d.type = TARGET_BALL;
            d.x = route.get(0)[0];
            d.y = route.get(0)[1];
            lastBallTime = now;
        } else if (held >= TuningConfig.INTAKE_CAP
                || (held > 0 && now - lastBallTime > TuningConfig.IDLE_SHOOT_SECONDS)) {
            d.type = TARGET_SHOOT;
            d.x = TuningConfig.SHOOT_X_IN;
            d.y = TuningConfig.SHOOT_Y_IN;
        } else {
            double[] z = zones.getBestZone(rx, ry);
            if (z != null && Math.hypot(z[0] - rx, z[1] - ry) < TuningConfig.ZONE_ARRIVE_RADIUS_IN
                    && usable.isEmpty()) {
                zones.markEmpty(z);                      // arrived and saw nothing
                z = zones.getBestZone(rx, ry);
            }
            if (z != null) {
                d.type = TARGET_ZONE;
                d.x = z[0];
                d.y = z[1];
            }
        }

        d.newPath = replan || d.type != current.type
                || Math.hypot(d.x - current.x, d.y - current.y) > TuningConfig.TARGET_MATCH_RADIUS_IN;
        current = d;
        return current;
    }

    // ---- helpers ----

    private double[] findFresh(List<double[]> live, double x, double y) {
        double[] best = null;
        double bestDist = TuningConfig.TARGET_MATCH_RADIUS_IN;
        for (double[] b : live) {
            if ((int) b[3] != MemoryStage.STATE_FRESH) continue;
            double dist = Math.hypot(b[0] - x, b[1] - y);
            if (dist < bestDist) {
                bestDist = dist;
                best = b;
            }
        }
        return best;
    }

    private void removeNear(List<double[]> live, double x, double y) {
        for (Iterator<double[]> it = live.iterator(); it.hasNext();) {
            double[] b = it.next();
            if (Math.hypot(b[0] - x, b[1] - y) < TuningConfig.TARGET_MATCH_RADIUS_IN) {
                it.remove();
                return;
            }
        }
    }

    private boolean inRoute(double[] ball) {
        for (double[] r : route) {
            if (Math.hypot(r[0] - ball[0], r[1] - ball[1]) < TuningConfig.TARGET_MATCH_RADIUS_IN) {
                return true;
            }
        }
        return false;
    }

    private boolean stalled(double[] target, double rx, double ry, double now) {
        double d = Math.hypot(target[0] - rx, target[1] - ry);
        if (!tracking || Math.hypot(target[0] - trackX, target[1] - trackY)
                > TuningConfig.TARGET_MATCH_RADIUS_IN) {
            tracking = true;
            trackX = target[0];
            trackY = target[1];
            bestDist = d;
            lastProgress = now;
            return false;
        }
        trackX = target[0];
        trackY = target[1];
        if (d < bestDist - TuningConfig.PROGRESS_MIN_IN) {
            bestDist = d;
            lastProgress = now;
        }
        return now - lastProgress > TuningConfig.STALL_SECONDS;
    }
}