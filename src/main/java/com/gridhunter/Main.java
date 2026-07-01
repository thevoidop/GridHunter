package com.gridhunter;

import com.gridhunter.ui.GameBoard;
import com.gridhunter.ui.HUD;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private GameApp game;
    private GameBoard board;
    private HUD hud;

    private static final long ENEMY_MOVE_INTERVAL = 500_000_000L;
    private long lastEnemyMove = 0;

    @Override
    public void start(Stage stage) {
        game = new GameApp();
        board = new GameBoard(game);
        hud = new HUD(game);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #0f0f1a;");
        root.setTop(hud);
        root.setCenter(board);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();

            if (key == KeyCode.R) {
                game.restart();
                board.render();
                hud.update();
                return;
            }

            if (game.isGameOver())
                return;

            switch (key) {
                case UP -> game.movePlayer(-1, 0);
                case DOWN -> game.movePlayer(1, 0);
                case LEFT -> game.movePlayer(0, -1);
                case RIGHT -> game.movePlayer(0, 1);
                default -> {
                    return;
                }
            }

            board.render();
            hud.update();
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!game.isGameOver()) {
                    long interval = game.getDifficulty().getEnemyIntervalNanos();
                    if ((now - lastEnemyMove) >= interval) {
                        game.moveEnemies();
                        board.render();
                        hud.update();
                        lastEnemyMove = now;
                    }
                }
            }
        };
        timer.start();

        stage.setTitle("Grid Hunter");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        board.render();
    }

    public static void main(String[] args) {
        launch(args);
    }
}