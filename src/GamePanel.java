import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;
    private static final int DELAY = 100;

    private Player player1;
    private Player player2;
    private Timer timer;
    private Database db;
    private long startTime;
    private boolean gameOver;
    private List<Rectangle> walls;
    private String levelName;

    public GamePanel(String player1Name, Color player1Color, String player2Name, Color player2Color, String levelFile, String levelName) {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        player1 = new Player(player1Name, player1Color, 200, 300, 1);
        player2 = new Player(player2Name, player2Color, 600, 300, 3);

        setupControls();

        db = new Database();

        try {
            walls = LevelLoader.loadLevel(levelFile, PANEL_WIDTH, PANEL_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
            walls = new ArrayList<>();
        }

        this.levelName = levelName;

        timer = new Timer(DELAY, this);
        startTime = System.currentTimeMillis();
        gameOver = false;
        timer.start();
    }

    private void setupControls() {
        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "p1_up");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "p1_right");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "p1_down");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "p1_left");

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "p2_up");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "p2_right");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "p2_down");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "p2_left");

        am.put("p1_up", new AbstractAction() { public void actionPerformed(ActionEvent e) { player1.setDirection(0); }});
        am.put("p1_right", new AbstractAction() { public void actionPerformed(ActionEvent e) { player1.setDirection(1); }});
        am.put("p1_down", new AbstractAction() { public void actionPerformed(ActionEvent e) { player1.setDirection(2); }});
        am.put("p1_left", new AbstractAction() { public void actionPerformed(ActionEvent e) { player1.setDirection(3); }});

        am.put("p2_up", new AbstractAction() { public void actionPerformed(ActionEvent e) { player2.setDirection(0); }});
        am.put("p2_right", new AbstractAction() { public void actionPerformed(ActionEvent e) { player2.setDirection(1); }});
        am.put("p2_down", new AbstractAction() { public void actionPerformed(ActionEvent e) { player2.setDirection(2); }});
        am.put("p2_left", new AbstractAction() { public void actionPerformed(ActionEvent e) { player2.setDirection(3); }});
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawPlayerTrace(g2d, player1);
        drawPlayerTrace(g2d, player2);

        g2d.setColor(Color.GRAY);
        for (Rectangle wall : walls) {
            g2d.fill(wall);
        }

        g2d.setColor(Color.WHITE);
        g2d.drawString("Time: " + getGameTime() + "s", 10, 20);

        g2d.drawString(levelName, PANEL_WIDTH - 100, 20);

        if (gameOver) {
            drawGameOver(g2d);
        }
    }

    private void drawPlayerTrace(Graphics2D g2d, Player player) {
        g2d.setColor(player.getTraceColor());
        g2d.setStroke(new BasicStroke(3));
        ArrayList<Point> trace = player.getTrace();
        for (int i = 1; i < trace.size(); i++) {
            Point p1 = trace.get(i-1);
            Point p2 = trace.get(i);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
        g2d.setStroke(new BasicStroke(1));
    }

    private void drawGameOver(Graphics2D g2d) {
        String message = "Game Over! " + (player1.isAlive() ? player1.getName() : player2.getName()) + " wins!";
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = g2d.getFontMetrics();
        int x = (PANEL_WIDTH - fm.stringWidth(message)) / 2;
        int y = PANEL_HEIGHT / 2;
        g2d.drawString(message, x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            player1.move();
            player2.move();

            checkCollisions();

            repaint();
        }
    }

    private void checkCollisions() {
        if (player1.getX() < 0 || player1.getX() >= PANEL_WIDTH ||
                player1.getY() < 0 || player1.getY() >= PANEL_HEIGHT) {
            player1.setAlive(false);
            endGame(player2);
        }
        if (player2.getX() < 0 || player2.getX() >= PANEL_WIDTH ||
                player2.getY() < 0 || player2.getY() >= PANEL_HEIGHT) {
            player2.setAlive(false);
            endGame(player1);
        }

        checkTraceCollision(player1, player2);
        checkTraceCollision(player2, player1);

        for (Rectangle wall : walls) {
            if (wall.contains(player1.getX(), player1.getY())) {
                player1.setAlive(false);
                endGame(player2);
            }
            if (wall.contains(player2.getX(), player2.getY())) {
                player2.setAlive(false);
                endGame(player1);
            }
        }
    }

    private void checkTraceCollision(Player player, Player opponent) {
        Point currentPos = new Point(player.getX(), player.getY());

        ArrayList<Point> playerTrace = player.getTrace();
        for (int i = 0; i < playerTrace.size() - 1; i++) {
            if (currentPos.equals(playerTrace.get(i))) {
                player.setAlive(false);
                endGame(opponent);
                return;
            }
        }

        ArrayList<Point> opponentTrace = opponent.getTrace();
        for (Point p : opponentTrace) {
            if (currentPos.equals(p)) {
                player.setAlive(false);
                endGame(opponent);
                return;
            }
        }
    }

    private void endGame(Player winner) {
        gameOver = true;
        timer.stop();

        db.updateWinner(winner.getName());
        db.updateLoser(winner == player1 ? player2.getName() : player1.getName());

        String message = winner.getName() + " wins!";
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    private int getGameTime() {
        return (int)((System.currentTimeMillis() - startTime) / 1000);
    }

    public void restartGame() {
        gameOver = false;
        startTime = System.currentTimeMillis();

        player1 = new Player(player1.getName(), player1.getTraceColor(), 200, 300, 1);
        player2 = new Player(player2.getName(), player2.getTraceColor(), 600, 300, 3);

        timer.start();
        repaint();
    }
}