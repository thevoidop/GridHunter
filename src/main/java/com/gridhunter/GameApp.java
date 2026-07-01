package com.gridhunter;

import com.gridhunter.algorithm.BFS;
import com.gridhunter.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameApp {
    public static final int ROWS = 15;
    public static final int COLS = 15;
    public static final int OBSTACLE_COUNT = 30;

    private Grid grid;
    private Player player;
    private List<Enemy> enemies;
    private boolean gameOver;
    private final DifficultyManager difficulty;
    private final Random rand = new Random();

    public GameApp() {
        difficulty = new DifficultyManager();
        init();
    }

    public void init() {
        grid = new Grid(ROWS, COLS);
        grid.placeObstacles(OBSTACLE_COUNT);

        player = new Player(0, 0);
        grid.getCell(0, 0).setType(Cell.Type.PLAYER);

        enemies = new ArrayList<>();
        spawnEnemy();

        gameOver = false;
        difficulty.reset();
    }

    private void spawnEnemy() {
        int[][] candidates = {
                { ROWS - 1, COLS - 1 }, { ROWS - 1, 0 }, { 0, COLS - 1 }, { ROWS - 1, COLS / 2 }
        };

        for (int[] pos : candidates) {
            int r = pos[0], c = pos[1];
            if (grid.getCell(r, c).getType() == Cell.Type.EMPTY) {
                Enemy e = new Enemy(r, c);
                enemies.add(e);
                grid.getCell(r, c).setType(Cell.Type.ENEMY);
                return;
            }
        }

        for (int attempt = 0; attempt < 50; attempt++) {
            int r = rand.nextInt(ROWS);
            int c = rand.nextInt(COLS);
            if (Math.abs(r - player.getRow()) + Math.abs(c - player.getCol()) > 8
                    && grid.getCell(r, c).getType() == Cell.Type.EMPTY) {
                Enemy e = new Enemy(r, c);
                enemies.add(e);
                grid.getCell(r, c).setType(Cell.Type.ENEMY);
                return;
            }
        }
    }

    public void movePlayer(int dRow, int dCol) {
        if (gameOver)
            return;

        int newRow = player.getRow() + dRow;
        int newCol = player.getCol() + dCol;

        if (!grid.isWalkable(newRow, newCol))
            return;

        grid.getCell(player.getRow(), player.getCol()).setType(Cell.Type.EMPTY);
        player.move(newRow, newCol);
        grid.getCell(newRow, newCol).setType(Cell.Type.PLAYER);
        SoundEngine.playMove();

        difficulty.update(player.getScore());

        while (enemies.size() < difficulty.getRequiredEnemyCount()) {
            spawnEnemy();
            SoundEngine.playNewEnemy();
        }

        for (Enemy e : enemies) {
            int dist = Math.abs(e.getRow() - player.getRow())
                    + Math.abs(e.getCol() - player.getCol());
            if (dist <= 2) {
                SoundEngine.playWarning();
                break;
            }
        }

        checkCollision();
    }

    public void moveEnemies() {
        if (gameOver)
            return;

        for (Enemy enemy : enemies) {
            int[] next = BFS.nextStep(grid,
                    enemy.getRow(), enemy.getCol(),
                    player.getRow(), player.getCol());
            if (next == null)
                continue;

            grid.getCell(enemy.getRow(), enemy.getCol()).setType(Cell.Type.EMPTY);
            enemy.moveTo(next[0], next[1]);
            grid.getCell(next[0], next[1]).setType(Cell.Type.ENEMY);
        }

        SoundEngine.playEnemyMove();
        checkCollision();
    }

    private void checkCollision() {
        for (Enemy enemy : enemies) {
            if (enemy.getRow() == player.getRow()
                    && enemy.getCol() == player.getCol()) {
                gameOver = true;
                SoundEngine.playGameOver();
                return;
            }
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public DifficultyManager getDifficulty() {
        return difficulty;
    }

    public void restart() {
        init();
    }
}