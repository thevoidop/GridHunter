package com.gridhunter.model;

public class Cell {
    public enum Type {
        EMPTY, PLAYER, ENEMY, OBSTACLE
    }

    private int row, col;
    private Type type;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.type = Type.EMPTY;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isWalkable() {
        return type != Type.OBSTACLE;
    }
}