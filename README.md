# Tron Game

## Overview
Tron Game is a two-player game inspired by the classic arcade game Tron. Players control light cycles that leave a trail behind them. The objective is to avoid crashing into walls, the opponent's trail, or your own trail.

## Features
- Two-player mode
- Customizable player names and colors
- Multiple levels with different layouts
- High score tracking

## Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/CollinsKipkemoi/Tron.git
    ```
2. Navigate to the project directory:
    ```sh
    cd tron-game
    ```
3. Compile the Java files:
    ```sh
    javac -d bin src/*.java
    ```
4. Run the game:
    ```sh
    java -cp bin TronGame
    ```

## How to Play
1. Start the game and enter the names for Player 1 and Player 2.
2. Choose the colors for each player.
3. Select a level from the list.
4. Use the arrow keys to control Player 1 and the WASD keys to control Player 2.
5. Avoid crashing into walls, your own trail, or your opponent's trail.
6. The game ends when one player crashes, and the winner is declared.

## Controls
- **Player 1**: Arrow keys
- **Player 2**: WASD keys

## High Scores
The game tracks the top 10 players based on the number of wins. You can view the high scores from the menu.

## Code Structure
- `Database.java`: Manages the connection to the database and retrieves high scores.
- `TronGame.java`: Main class that initializes the game and handles player setup.
- `GameView.java`: Manages the game window and menu.
- `GamePanel.java`: Handles the game logic and rendering.
- `LevelLoader.java`: Loads the level layout from a file.

## Acknowledgements
- Inspired by the classic Tron arcade game.
- Developed as part of an assignment for Programming Technology course.
