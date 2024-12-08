import java.awt.*;
import java.util.ArrayList;

public class Player {
    private String name;
    private Color traceColor;
    private int x, y;
    private int direction; // 0: up, 1: right, 2: down, 3: left
    private ArrayList<Point> trace;
    private boolean alive;

    public Player(String name, Color traceColor, int startX, int startY, int startDirection) {
        this.name = name;
        this.traceColor = traceColor;
        this.x = startX;
        this.y = startY;
        this.direction = startDirection;
        this.trace = new ArrayList<>();
        this.alive = true;
        trace.add(new Point(startX, startY));
    }

    public void move() {
        switch (direction) {
            case 0: y -= 1; break; // Up
            case 1: x += 1; break; // Right
            case 2: y += 1; break; // Down
            case 3: x -= 1; break; // Left
        }
        trace.add(new Point(x, y));
    }

    public void setDirection(int newDirection) {
        // Prevent 180-degree turns
        if (Math.abs(direction - newDirection) != 2) {
            direction = newDirection;
        }
    }

    // Getters and setters
    public String getName() { return name; }
    public Color getTraceColor() { return traceColor; }
    public int getX() { return x; }
    public int getY() { return y; }
    public ArrayList<Point> getTrace() { return trace; }
    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
}
