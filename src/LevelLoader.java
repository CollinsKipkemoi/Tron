import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    public static List<Rectangle> loadLevel(String levelFile, int panelWidth, int panelHeight) throws IOException {
        List<Rectangle> walls = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(levelFile));
        String line;
        int y = 0;
        int numRows = 0;
        int numCols = 0;

        // First pass to determine the number of rows and columns
        while ((line = reader.readLine()) != null) {
            numCols = Math.max(numCols, line.length());
            numRows++;
        }
        reader.close();

        // Calculate scaling factors
        double xScale = (double) panelWidth / numCols;
        double yScale = (double) panelHeight / numRows;

        // Second pass to create walls with scaling
        reader = new BufferedReader(new FileReader(levelFile));
        y = 0;
        while ((line = reader.readLine()) != null) {
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') {
                    walls.add(new Rectangle((int) (x * xScale), (int) (y * yScale), (int) xScale, (int) yScale));
                }
            }
            y++;
        }
        reader.close();
        return walls;
    }
}