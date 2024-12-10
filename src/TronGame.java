import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TronGame {
    private GameView gameView;
    private Database db;

    public TronGame() {
        db = new Database();
        gameView = new GameView(
            e -> startNewGame(),
            e -> showHighScores(),
            e -> System.exit(0)
        );
        gameView.setVisible(true);
        startNewGame();
    }

    private void startNewGame() {
        String player1Name = JOptionPane.showInputDialog(gameView, "Enter Player 1 name:", "Player Setup", JOptionPane.QUESTION_MESSAGE);
        if (player1Name == null) return;
        
        String player2Name = JOptionPane.showInputDialog(gameView, "Enter Player 2 name:", "Player Setup", JOptionPane.QUESTION_MESSAGE);
        if (player2Name == null) return;
        
        Color player1Color = JColorChooser.showDialog(gameView, "Choose Player 1 Color", Color.BLUE);
        if (player1Color == null) player1Color = Color.BLUE;
        
        Color player2Color = JColorChooser.showDialog(gameView, "Choose Player 2 Color", Color.RED);
        if (player2Color == null) player2Color = Color.RED;

        String[] levels = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9", "Level 10"};
        String levelChoice = (String) JOptionPane.showInputDialog(gameView, "Choose Level:", "Level Selection", JOptionPane.QUESTION_MESSAGE, null, levels, levels[0]);
        if (levelChoice == null) return;
        String levelFile = "levels/" + levelChoice.toLowerCase().replace(" ", "") + ".txt";

        GamePanel gamePanel = new GamePanel(player1Name, player1Color, player2Name, player2Color, levelFile, levelChoice);
        gameView.setGamePanel(gamePanel);
    }

    private void showHighScores() {
        try {
            ResultSet scores = db.getTopPlayers();
            StringBuilder scoreText = new StringBuilder("Top 10 Players:\n\n");
            
            while (scores.next()) {
                String name = scores.getString("username");
                int wins = scores.getInt("total_wins");
                scoreText.append(String.format("%s: %d wins\n", name, wins));
            }
            
            JOptionPane.showMessageDialog(gameView, scoreText.toString(), "High Scores", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(gameView, "Error loading high scores: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
         new TronGame();
    }
}