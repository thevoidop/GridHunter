package com.gridhunter.model;

public class Grid {
    private final int rows;
    private final int cols;
    private final Cell[][] cells;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        cells = new Cell[rows][cols];

        // Initialize all cells as EMPTY
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                cells[r][c] = new Cell(r, c);
    }

    public Cell getCell(int row, int col) {
        if (isInBounds(row, col))
            return cells[row][col];
        return null;
    }

    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public boolean isWalkable(int row, int col) {
        return isInBounds(row, col) && cells[row][col].isWalkable();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    // Place obstacles randomly (avoid corners for player/enemy spawn)
    public void placeObstacles(int count) {
        java.util.Random rand = new java.util.Random();
        int placed = 0;
        while (placed < count) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            // Keep corners free for spawning
            if ((r == 0 && c == 0) || (r == rows - 1 && c == cols - 1))
                continue;
            if (cells[r][c].getType() == Cell.Type.EMPTY) {
                cells[r][c].setType(Cell.Type.OBSTACLE);
                placed++;
            }
        }
    }
}