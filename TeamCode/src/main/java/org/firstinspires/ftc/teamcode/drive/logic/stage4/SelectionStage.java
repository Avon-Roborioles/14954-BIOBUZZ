package org.firstinspires.ftc.teamcode.drive.logic.stage4;

import org.firstinspires.ftc.teamcode.drive.logic.config.TuningConfig;
import org.firstinspires.ftc.teamcode.drive.logic.stage2.MemoryStage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * STAGE 4 - Selection: filter, cluster, score, fill the intake, order with 2-opt.
 * The route always ends at the shooting spot from TuningConfig.
 */
public class SelectionStage {
    // {x, y, skip-until time}
    private final List<double[]> skipped = new ArrayList<>();

    private static class Cluster {
        final List<double[]> members = new ArrayList<>();
        double score;
    }

    /** Ignore the ball near (x, y) for the skip time. */
    public void skip(double x, double y, double now) {
        skipped.add(new double[]{x, y, now + TuningConfig.SKIP_SECONDS});
    }

    private boolean isSkipped(double x, double y, double now) {
        for (double[] s : skipped) {
            if (s[2] > now && Math.hypot(s[0] - x, s[1] - y) < TuningConfig.SKIP_MATCH_RADIUS_IN) {
                return true;
            }
        }
        return false;
    }

    /**
     * Fresh, not skipped, and not closer to the rival than to this robot.
     * balls are {x, y, color, state}. rival is {x, y} or null.
     */
    public List<double[]> usable(List<double[]> balls, double rx, double ry,
                                 double[] rival, double now) {
        for (Iterator<double[]> it = skipped.iterator(); it.hasNext();) {
            if (it.next()[2] <= now) it.remove();
        }
        List<double[]> out = new ArrayList<>();
        for (double[] b : balls) {
            if ((int) b[3] != MemoryStage.STATE_FRESH) continue;
            if (isSkipped(b[0], b[1], now)) continue;
            if (rival != null) {
                double mine = Math.hypot(b[0] - rx, b[1] - ry);
                double theirs = Math.hypot(b[0] - rival[0], b[1] - rival[1]);
                if (theirs < mine) continue;
            }
            out.add(b);
        }
        return out;
    }

    /** Returns the balls to collect, in drive order. Empty if there is nothing to do. */
    public List<double[]> select(List<double[]> balls, double rx, double ry, double heading,
                                 double[] rival, int held, double now) {
        int room = TuningConfig.INTAKE_CAP - held;
        List<double[]> chosen = new ArrayList<>();
        if (room <= 0) return chosen;

        List<double[]> u = usable(balls, rx, ry, rival, now);
        if (u.isEmpty()) return chosen;

        List<Cluster> clusters = cluster(u, rx, ry);
        Collections.sort(clusters, (a, b) -> Double.compare(b.score, a.score));

        for (Cluster c : clusters) {
            Collections.sort(c.members, (a, b) -> Double.compare(
                    Math.hypot(a[0] - rx, a[1] - ry), Math.hypot(b[0] - rx, b[1] - ry)));
            for (double[] m : c.members) {
                if (chosen.size() >= room) break;
                chosen.add(m);
            }
            if (chosen.size() >= room) break;
        }
        return twoOpt(chosen, rx, ry, heading);
    }

    // ---- clustering (single link) ----

    private List<Cluster> cluster(List<double[]> pts, double rx, double ry) {
        int n = pts.size();
        int[] label = new int[n];
        Arrays.fill(label, -1);
        List<Cluster> clusters = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (label[i] != -1) continue;
            Cluster c = new Cluster();
            List<Integer> stack = new ArrayList<>();
            stack.add(i);
            label[i] = clusters.size();
            while (!stack.isEmpty()) {
                int a = stack.remove(stack.size() - 1);
                c.members.add(pts.get(a));
                for (int b = 0; b < n; b++) {
                    if (label[b] == -1 && Math.hypot(pts.get(a)[0] - pts.get(b)[0],
                            pts.get(a)[1] - pts.get(b)[1]) <= TuningConfig.CLUSTER_LINK_IN) {
                        label[b] = clusters.size();
                        stack.add(b);
                    }
                }
            }
            double cx = 0, cy = 0;
            for (double[] m : c.members) {
                cx += m[0];
                cy += m[1];
            }
            cx /= c.members.size();
            cy /= c.members.size();
            c.score = c.members.size()
                    / (Math.hypot(cx - rx, cy - ry) + TuningConfig.CLUSTER_SCORE_OFFSET_IN);
            clusters.add(c);
        }
        return clusters;
    }

    // ---- 2-opt ----

    private List<double[]> twoOpt(List<double[]> route, double rx, double ry, double heading) {
        List<double[]> best = new ArrayList<>(route);
        double bestCost = cost(best, rx, ry, heading);
        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 0; i < best.size() - 1; i++) {
                for (int j = i + 1; j < best.size(); j++) {
                    List<double[]> cand = new ArrayList<>(best);
                    Collections.reverse(cand.subList(i, j + 1));
                    double c = cost(cand, rx, ry, heading);
                    if (c < bestCost - 1e-9) {
                        best = cand;
                        bestCost = c;
                        improved = true;
                    }
                }
            }
        }
        return best;
    }

    /** Distance plus turn cost per radian, robot -> balls -> shooting spot. */
    private double cost(List<double[]> r, double rx, double ry, double heading) {
        double cost = 0, px = rx, py = ry, h = heading;
        for (int k = 0; k <= r.size(); k++) {
            double tx = k < r.size() ? r.get(k)[0] : TuningConfig.SHOOT_X_IN;
            double ty = k < r.size() ? r.get(k)[1] : TuningConfig.SHOOT_Y_IN;
            double d = Math.hypot(tx - px, ty - py);
            if (d > 1e-6) {
                double leg = Math.atan2(ty - py, tx - px);
                cost += d + TuningConfig.TURN_COST_PER_RAD * Math.abs(angleDiff(leg, h));
                h = leg;
            }
            px = tx;
            py = ty;
        }
        return cost;
    }

    static double angleDiff(double a, double b) {
        double d = a - b;
        while (d > Math.PI) d -= 2 * Math.PI;
        while (d < -Math.PI) d += 2 * Math.PI;
        return d;
    }
}