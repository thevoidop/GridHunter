package com.gridhunter.algorithm;

import com.gridhunter.model.Grid;
import java.util.*;

public class BFS {

    public static int[] nextStep(Grid grid, int startRow, int startCol,
            int targetRow, int targetCol) {
        int rows = grid.getRows();
        int cols = grid.getCols();

        boolean[][] visited = new boolean[rows][cols];
        int[][][] parent = new int[rows][cols][2];

        for (int[][] row : parent)
            for (int[] cell : row)
                Arrays.fill(cell, -1);

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { startRow, startCol });
        visited[startRow][startCol] = true;

        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1];

            if (r == targetRow && c == targetCol) {
                return tracePath(parent, startRow, startCol, targetRow, targetCol);
            }

            for (int[] dir : directions) {
                int nr = r + dir[0];
                int nc = c + dir[1];
                if (grid.isWalkable(nr, nc) && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    parent[nr][nc] = new int[] { r, c };
                    queue.add(new int[] { nr, nc });
                }
            }
        }

        return null; // no path found
    }

    private static int[] tracePath(int[][][] parent, int startRow, int startCol,
            int targetRow, int targetCol) {
        List<int[]> path = new ArrayList<>();
        int r = targetRow, c = targetCol;

        while (!(r == startRow && c == startCol)) {
            path.add(new int[] { r, c });
            int[] p = parent[r][c];
            r = p[0];
            c = p[1];
        }

        Collections.reverse(path);
        // First element = the immediate next step for the enemy
        return path.isEmpty() ? null : path.get(0);
    }
}