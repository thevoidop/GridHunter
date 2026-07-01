package com.gridhunter.model;

public class Player {
    private int row, col;
    private int score;

    public Player(int startRow, int startCol) {
        this.row = startRow;
        this.col = startCol;
        this.score = 0;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getScore() {
        return score;
    }

    public void move(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
        this.score++; // 1 point per move survived
    }

    public void resetScore() {
        this.score = 0;
    }
}