# GridHunter

GridHunter is a fast-paced, grid-based survival game built with Java and JavaFX. You play as a blue hero trapped in a labyrinth of obstacles, constantly pursued by relentless red monsters. Survive as long as you can while the game gets progressively harder!

## Features

- **Smart AI Pathfinding:** Enemies don't just move randomly; they track you down using a Breadth-First Search (BFS) algorithm to find the shortest path to your location.
- **Dynamic Difficulty:** The game features a progressive difficulty system. As your score increases, you will advance from EASY to NIGHTMARE mode. Higher difficulties spawn more enemies and increase their movement speed.
- **Procedural Sound Effects:** All audio is synthesized in real-time using Java's `javax.sound.sampled` API. Experience retro-style blips, thuds, and alert buzzes without needing external audio files.
- **JavaFX Rendering:** Custom canvas-based graphics featuring smooth breathing animations, atmospheric lighting, and particle-like glow effects.
- **Score System:** Every successful move you make grants you 1 point. Plan your steps carefully to maximize your score.

## Prerequisites

To build and run this project, you will need:

- **Java Development Kit (JDK) 21** or higher.
- **Apache Maven** installed and configured on your system.

## How to Play

### Controls

- **Up Arrow:** Move Up
- **Down Arrow:** Move Down
- **Left Arrow:** Move Left
- **Right Arrow:** Move Right
- **R:** Restart Game (can be used during gameplay or on the Game Over screen)

### Gameplay Rules

1. You are the blue character. The red monsters are trying to catch you.
2. The grey blocks are obstacles. Neither you nor the enemies can pass through them.
3. Every time you move, your score increases by 1.
4. Enemies move automatically based on a timer. As you level up, this timer gets shorter, making them move faster!
5. If an enemy lands on your square, it is Game Over.

## Getting Started

1. Clone or download this repository to your local machine.
2. Open your terminal or command prompt and navigate to the root folder of the project (where the `pom.xml` file is located).
3. Run the following Maven command to start the game:

```bash
mvn javafx:run
```

## Project Structure

- `com.gridhunter.Main`: The main entry point that sets up the JavaFX Stage, Scene, and AnimationTimer.
- `com.gridhunter.GameApp`: The core controller managing the game state, player movement, and enemy logic.
- `com.gridhunter.DifficultyManager`: Handles score thresholds, level progression, and enemy spawn requirements.
- `com.gridhunter.SoundEngine`: A multithreaded audio synthesizer for generating game sounds.
- `com.gridhunter.model.*`: Contains the data models for the game (`Grid`, `Cell`, `Player`, `Enemy`).
- `com.gridhunter.ui.*`: Contains the visual components (`GameBoard` for canvas rendering and `HUD` for the status bar).
- `com.gridhunter.algorithm.BFS`: The pathfinding logic used by enemies to navigate around obstacles and find the player.

## Technologies Used

- **Java 21**
- **JavaFX 21** (Controls, Graphics, Media)
- **Maven** (Build and Dependency Management)
