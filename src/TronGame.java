import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TronGame extends JFrame {
    private GamePanel gamePanel;
    private Database db;

    public TronGame() {
        super("Tron Light Cycle Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        db = new Database();
        
        // Create menu bar
        createMenuBar();
        
        // Start new game
        startNewGame();
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Menu");
        
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> startNewGame());
        
        JMenuItem highScores = new JMenuItem("High Scores");
        highScores.addActionListener(e -> showHighScores());
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        
        gameMenu.add(newGame);
        gameMenu.add(highScores);
        gameMenu.addSeparator();
        gameMenu.add(exit);
        
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void startNewGame() {
        // Get player names and colors
        String player1Name = JOptionPane.showInputDialog(this, "Enter Player 1 name:", "Player Setup", JOptionPane.QUESTION_MESSAGE);
        if (player1Name == null) return;
        
        String player2Name = JOptionPane.showInputDialog(this, "Enter Player 2 name:", "Player Setup", JOptionPane.QUESTION_MESSAGE);
        if (player2Name == null) return;
        
        Color player1Color = JColorChooser.showDialog(this, "Choose Player 1 Color", Color.BLUE);
        if (player1Color == null) player1Color = Color.BLUE;
        
        Color player2Color = JColorChooser.showDialog(this, "Choose Player 2 Color", Color.RED);
        if (player2Color == null) player2Color = Color.RED;

        // Create new game panel
        if (gamePanel != null) {
            remove(gamePanel);
        }
        gamePanel = new GamePanel(player1Name, player1Color, player2Name, player2Color);
        add(gamePanel);
        pack();
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
            
            JOptionPane.showMessageDialog(this, scoreText.toString(), "High Scores", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading high scores: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
