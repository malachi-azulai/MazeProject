package org.example;

import java.util.ArrayList;
import java.util.List;

public class MazeSolver {

    public List<int[]> solve(boolean[][] grid, int rows, int cols) {
        // בדיקת גבולות בסיסית ושההתחלה/סוף אינם חסומים
        if (grid == null || !grid[0][0] || !grid[rows - 1][cols - 1]) {
            return null;
        }

        List<int[]> path = new ArrayList<>();
        boolean[][] visited = new boolean[rows][cols];

        if (findPathDFS(0, 0, rows, cols, grid, visited, path)) {
            return path;
        }
        return null;
    }

    private boolean findPathDFS(int r, int c, int rows, int cols, boolean[][] grid, boolean[][] visited, List<int[]> path) {
        if (r < 0 || r >= rows || c < 0 || c >= cols || !grid[r][c] || visited[r][c]) {
            return false;
        }

        visited[r][c] = true;
        path.add(new int[]{r, c});

        // הגעה ליעד
        if (r == rows - 1 && c == cols - 1) {
            return true;
        }

        // סריקה ב-4 כיוונים: מטה, ימינה, מעלה, שמאלה
        if (findPathDFS(r + 1, c, rows, cols, grid, visited, path) ||
                findPathDFS(r, c + 1, rows, cols, grid, visited, path) ||
                findPathDFS(r - 1, c, rows, cols, grid, visited, path) ||
                findPathDFS(r, c - 1, rows, cols, grid, visited, path)) {
            return true;
        }

        // נסיגה לאחור (Backtrack)
        path.remove(path.size() - 1);
        return false;
    }
}


