package cn.elysia.algo.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author wangchen
 * @since 24/12/07
 */
@SuppressWarnings("all")
public class L688KnightProbabilityInChessboard {

    public static void main(String[] args) {
        L688KnightProbabilityInChessboard root = new L688KnightProbabilityInChessboard();
        Solution solution = root.new Solution();
        Solution2 solution2 = root.new Solution2();

        double probability = solution.knightProbability(8, 30, 5, 6);
        double probability2 = solution2.knightProbability(8, 30, 5, 6);

        System.out.println("probability = " + probability + " probability2 = " + probability2);
    }

    class Solution {
        final int[] dxArr = {-2, -1, 1, 2, -2, -1, 1, 2};
        final int[] dyArr = {-1, -2, -2, -1, 1, 2, 2, 1};

        public double knightProbability(int n, int k, int row, int column) {
            if (k == 0) return 1d;
            return dfs(n, k, row, column, new HashMap<>());
        }

        public double dfs(int n, int step, int row, int col, Map<Visited, Double> visited) {
            if (row < 0 || row >= n || col < 0 || col >= n) {
                return 0;
            }

            Visited v = new Visited(row, col, step);
            if (visited.containsKey(v)) {
                return visited.get(v);
            }

            if (step <= 0) {
                return 1;
            }

            double pos = 0d;
            for (int i = 0; i < 8; i++) {
                int dx = dxArr[i];
                int dy = dyArr[i];
                pos += dfs(n, step - 1, row + dx, col + dy, visited) / 8;
            }
            visited.put(v, pos);
            return pos;
        }

        class Visited {
            private final int row;
            private final int col;
            private final int step;

            public Visited(int row, int col, int step) {
                this.row = row;
                this.col = col;
                this.step = step;
            }

            public boolean equals(Object v) {
                if (!(v instanceof Visited)) {
                    return false;
                }
                Visited v1 = (Visited) v;
                return row == v1.row && col == v1.col && step == v1.step;
            }

            public int hashCode() {
                return Objects.hash(row, col, step);
            }
        }
    }

    class Solution2 {
        final int[] dxArr = {-2, -1, 1, 2, -2, -1, 1, 2};
        final int[] dyArr = {-1, -2, -2, -1, 1, 2, 2, 1};

        public double knightProbability(int n, int k, int row, int column) {
            double[][][] dp = new double[k+1][n][n];

            for (int step = 0; step <= k; step++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (step == 0) {
                            dp[step][i][j] = 1;
                        } else {
                            for (int d = 0; d < 8; d++) {
                                int x = i + dxArr[d];
                                int y = j + dyArr[d];
                                if (x >= 0 && x < n && y >= 0 && y < n) {
                                    dp[step][i][j] += dp[step - 1][x][y] / 8;
                                }
                            }
                        }
                    }
                }
            }

            return dp[k][row][column];
        }
    }

}
