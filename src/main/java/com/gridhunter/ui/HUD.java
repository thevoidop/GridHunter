package com.gridhunter.ui;

import com.gridhunter.GameApp;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HUD extends HBox {
    private final Label scoreLabel;
    private final Label levelLabel;
    private final Label nextLabel;
    private final GameApp game;

    public HUD(GameApp game) {
        this.game = game;
        setStyle("-fx-background-color: #080814;");
        setPadding(new Insets(8, 14, 8, 14));
        setSpacing(20);

        scoreLabel = styledLabel("Score: 0", "#00d4ff", 16);
        levelLabel = styledLabel("EASY", "#44ff88", 16);
        nextLabel = styledLabel("Next level in 15 moves", "#888888", 12);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label hint = styledLabel("↑↓←→ Move   R Restart", "#444466", 12);

        getChildren().addAll(scoreLabel, levelLabel, spacer, nextLabel, hint);
    }

    private Label styledLabel(String text, String color, int size) {
        Label l = new Label(text);
        l.setTextFill(Color.web(color));
        l.setFont(Font.font("Courier New", size));
        return l;
    }

    public void update() {
        var diff = game.getDifficulty();
        scoreLabel.setText("Score: " + game.getPlayer().getScore());
        levelLabel.setText("⚡ " + diff.getLevelName());

        String color = switch (diff.getCurrentLevel()) {
            case EASY -> "#44ff88";
            case MEDIUM -> "#ffdd00";
            case HARD -> "#ff8800";
            case INSANE -> "#ff4444";
            case NIGHTMARE -> "#ff00ff";
        };
        levelLabel.setTextFill(Color.web(color));
        nextLabel.setText(diff.getNextLevelInfo());
    }
}