import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    public static List<Rectangle> loadLevel(String levelFile, int screenWidth, int screenHeight) throws IOException {
        List<Rectangle> walls = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(levelFile));
        
        int rows = lines.size();
        int cols = lines.get(0).length();
        
        double cellWidth = (double) screenWidth / cols;
        double cellHeight = (double) screenHeight / rows;
        
        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                if (line.charAt(j) == '#') {
                    walls.add(new Rectangle(
                        (int)(j * cellWidth),
                        (int)(i * cellHeight),
                        (int)cellWidth,
                        (int)cellHeight
                    ));
                }
            }
        }
        return walls;
    }
}