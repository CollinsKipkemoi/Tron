import java.awt.*;
import java.util.ArrayList;

public class Player {
    private String name;
    private Color traceColor;
    private int x, y;
    private int direction;
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
            case 0: y -= 1; break;
            case 1: x += 1; break;
            case 2: y += 1; break;
            case 3: x -= 1; break;
        }
        trace.add(new Point(x, y));
    }

    public void setDirection(int newDirection) {
        if (Math.abs(newDirection - direction) != 2) {
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
