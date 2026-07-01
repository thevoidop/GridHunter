package com.gridhunter.ui;

import com.gridhunter.GameApp;
import com.gridhunter.model.Cell;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GameBoard extends Canvas {
    public static final int CELL_SIZE = 40;

    private final GameApp game;
    private double animPulse = 0;

    public GameBoard(GameApp game) {
        super(GameApp.COLS * CELL_SIZE, GameApp.ROWS * CELL_SIZE);
        this.game = game;
    }

    public void render() {
        animPulse += 0.15;
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        drawBackground(gc);

        for (int r = 0; r < GameApp.ROWS; r++) {
            for (int c = 0; c < GameApp.COLS; c++) {
                Cell cell = game.getGrid().getCell(r, c);
                double x = c * CELL_SIZE;
                double y = r * CELL_SIZE;

                switch (cell.getType()) {
                    case EMPTY -> drawEmptyCell(gc, x, y);
                    case OBSTACLE -> drawObstacle(gc, x, y);
                    case PLAYER -> drawPlayer(gc, x, y);
                    case ENEMY -> drawEnemy(gc, x, y);
                }
            }
        }

        if (game.isGameOver())
            drawGameOver(gc);
    }

    // Dark grid background
    private void drawBackground(GraphicsContext gc) {
        gc.setFill(Color.web("#0d0d1a"));
        gc.fillRect(0, 0, getWidth(), getHeight());

        gc.setStroke(Color.web("#1a1a33", 0.8));
        gc.setLineWidth(0.5);
        for (int r = 0; r <= GameApp.ROWS; r++) {
            gc.strokeLine(0, r * CELL_SIZE, getWidth(), r * CELL_SIZE);
        }
        for (int c = 0; c <= GameApp.COLS; c++) {
            gc.strokeLine(c * CELL_SIZE, 0, c * CELL_SIZE, getHeight());
        }
    }

    private void drawEmptyCell(GraphicsContext gc, double x, double y) {
        // Subtle inner glow on empty cells
        gc.setFill(Color.web("#111128", 0.5));
        gc.fillRect(x + 1, y + 1, CELL_SIZE - 2, CELL_SIZE - 2);
    }

    // Wall / rock obstacle
    private void drawObstacle(GraphicsContext gc, double x, double y) {
        double pad = 3;
        // Stone block
        gc.setFill(Color.web("#3a3a5c"));
        gc.fillRoundRect(x + pad, y + pad, CELL_SIZE - pad * 2, CELL_SIZE - pad * 2, 6, 6);

        // Highlight top edge (3D effect)
        gc.setFill(Color.web("#5a5a8a", 0.6));
        gc.fillRoundRect(x + pad, y + pad, CELL_SIZE - pad * 2, 5, 3, 3);

        // Crack lines
        gc.setStroke(Color.web("#22223a", 0.9));
        gc.setLineWidth(1);
        gc.strokeLine(x + 10, y + 12, x + 18, y + 22);
        gc.strokeLine(x + 22, y + 10, x + 28, y + 18);
    }

    // Player — blue hero character
    private void drawPlayer(GraphicsContext gc, double x, double y) {
        double cx = x + CELL_SIZE / 2.0;
        double cy = y + CELL_SIZE / 2.0;

        // Breathing pulse effect
        double pulse = 1.0 + 0.04 * Math.sin(animPulse);

        // Glow aura
        gc.setFill(Color.web("#00d4ff", 0.2));
        gc.fillOval(cx - 16 * pulse, cy - 16 * pulse, 32 * pulse, 32 * pulse);

        // Body (rounded rectangle)
        gc.setFill(Color.web("#0077aa"));
        gc.fillRoundRect(x + 11, y + 18, 18, 16, 5, 5);

        // Head
        gc.setFill(Color.web("#00bfff"));
        gc.fillOval(cx - 8, y + 6, 16, 16);

        // Eyes (white + dark pupil)
        gc.setFill(Color.WHITE);
        gc.fillOval(cx - 5, y + 10, 4, 4);
        gc.fillOval(cx + 1, y + 10, 4, 4);
        gc.setFill(Color.web("#003355"));
        gc.fillOval(cx - 4, y + 11, 2, 2);
        gc.fillOval(cx + 2, y + 11, 2, 2);

        // Legs
        gc.setFill(Color.web("#005588"));
        gc.fillRoundRect(x + 11, y + 32, 7, 6, 3, 3);
        gc.fillRoundRect(x + 22, y + 32, 7, 6, 3, 3);
    }

    // Enemy — red monster character
    private void drawEnemy(GraphicsContext gc, double x, double y) {
        double cx = x + CELL_SIZE / 2.0;

        // Menacing pulse (faster than player)
        double pulse = 1.0 + 0.06 * Math.sin(animPulse * 1.5);

        // Red glow
        gc.setFill(Color.web("#ff0000", 0.18));
        gc.fillOval(cx - 17 * pulse, x - 1 + 17 * (1 - pulse), 34 * pulse, 34 * pulse);

        // Body (slightly wider than player, more menacing)
        gc.setFill(Color.web("#8b0000"));
        gc.fillRoundRect(x + 9, y + 19, 22, 14, 4, 4);

        // Head (rounder, more monstrous)
        gc.setFill(Color.web("#cc2200"));
        gc.fillOval(cx - 10, y + 5, 20, 18);

        // Horns
        gc.setFill(Color.web("#ff6600"));
        double[] hornLX = { cx - 9, cx - 5, cx - 12 };
        double[] hornLY = { y + 8, y + 5, y + 2 };
        gc.fillPolygon(hornLX, hornLY, 3);
        double[] hornRX = { cx + 9, cx + 5, cx + 12 };
        double[] hornRY = { y + 8, y + 5, y + 2 };
        gc.fillPolygon(hornRX, hornRY, 3);

        // Glowing yellow eyes
        gc.setFill(Color.web("#ffff00"));
        gc.fillOval(cx - 6, y + 10, 5, 4);
        gc.fillOval(cx + 1, y + 10, 5, 4);
        gc.setFill(Color.web("#440000"));
        gc.fillOval(cx - 5, y + 11, 3, 2);
        gc.fillOval(cx + 2, y + 11, 3, 2);

        // Teeth
        gc.setFill(Color.WHITE);
        gc.fillRect(cx - 4, y + 19, 3, 4);
        gc.fillRect(cx, y + 19, 3, 4);
        gc.fillRect(cx + 4, y + 19, 3, 4);

        // Legs
        gc.setFill(Color.web("#660000"));
        gc.fillRoundRect(x + 10, y + 31, 7, 6, 3, 3);
        gc.fillRoundRect(x + 23, y + 31, 7, 6, 3, 3);
    }

    private void drawGameOver(GraphicsContext gc) {
        // Dark overlay
        gc.setFill(Color.rgb(0, 0, 0, 0.75));
        gc.fillRect(0, 0, getWidth(), getHeight());

        // Game Over text
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.web("#ff4444"));
        gc.setFont(Font.font("Courier New", 40));
        gc.fillText("GAME OVER", getWidth() / 2, getHeight() / 2 - 20);

        gc.setFill(Color.web("#aaaaaa"));
        gc.setFont(Font.font("Courier New", 16));
        gc.fillText("Final Score: " + game.getPlayer().getScore(),
                getWidth() / 2, getHeight() / 2 + 20);

        gc.setFill(Color.web("#00d4ff"));
        gc.setFont(Font.font("Courier New", 14));
        gc.fillText("Press R to Restart", getWidth() / 2, getHeight() / 2 + 50);
    }
}