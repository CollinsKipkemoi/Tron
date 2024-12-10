import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameView extends JFrame {
    private GamePanel gamePanel;

    public GameView(ActionListener newGameListener, ActionListener highScoresListener, ActionListener exitListener) {
        super("Tron Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMenuBar(newGameListener, highScoresListener, exitListener);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createMenuBar(ActionListener newGameListener, ActionListener highScoresListener, ActionListener exitListener) {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Menu");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(newGameListener);

        JMenuItem highScores = new JMenuItem("High Scores");
        highScores.addActionListener(highScoresListener);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(exitListener);

        gameMenu.add(newGame);
        gameMenu.add(highScores);
        gameMenu.addSeparator();
        gameMenu.add(exit);

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    public void setGamePanel(GamePanel gamePanel) {
        if (this.gamePanel != null) {
            remove(this.gamePanel);
        }
        this.gamePanel = gamePanel;
        add(gamePanel);
        pack();
    }
}