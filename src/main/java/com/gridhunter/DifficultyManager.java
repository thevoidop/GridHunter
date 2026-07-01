package com.gridhunter;

public class DifficultyManager {

    public enum Level {
        EASY(1, 500),
        MEDIUM(2, 380),
        HARD(3, 260),
        INSANE(4, 160),
        NIGHTMARE(5, 100);

        public final int enemyCount;
        public final long enemyIntervalMs; // milliseconds between enemy moves

        Level(int enemyCount, long enemyIntervalMs) {
            this.enemyCount = enemyCount;
            this.enemyIntervalMs = enemyIntervalMs;
        }
    }

    private int currentScore = 0;
    private Level currentLevel = Level.EASY;

    // Score thresholds to trigger each level
    private static final int[] THRESHOLDS = { 0, 15, 35, 60, 90 };

    public void update(int score) {
        this.currentScore = score;
        Level newLevel = calculateLevel(score);

        if (newLevel != currentLevel) {
            currentLevel = newLevel;
            SoundEngine.playLevelUp();
        }
    }

    private Level calculateLevel(int score) {
        Level[] levels = Level.values();
        for (int i = levels.length - 1; i >= 0; i--) {
            if (score >= THRESHOLDS[i])
                return levels[i];
        }
        return Level.EASY;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public long getEnemyIntervalNanos() {
        return currentLevel.enemyIntervalMs * 1_000_000L;
    }

    public int getRequiredEnemyCount() {
        return currentLevel.enemyCount;
    }

    public String getLevelName() {
        return switch (currentLevel) {
            case EASY -> "EASY";
            case MEDIUM -> "MEDIUM";
            case HARD -> "HARD";
            case INSANE -> "INSANE";
            case NIGHTMARE -> "NIGHTMARE 💀";
        };
    }

    public String getNextLevelInfo() {
        Level[] levels = Level.values();
        int idx = currentLevel.ordinal();
        if (idx < levels.length - 1) {
            int needed = THRESHOLDS[idx + 1] - currentScore;
            return "Next level in " + needed + " moves";
        }
        return "MAX LEVEL REACHED";
    }

    public void reset() {
        currentScore = 0;
        currentLevel = Level.EASY;
    }
}