package minesweepersolver;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MinesweeperMain {
    // choose level
//    private final static String LEVEL = "beginner";
//    private final static String LEVEL = "intermediate";
    private final static String LEVEL = "expert";
    
    public static void main(String[] args) {
        try {
            Desktop.getDesktop().browse(new URI("http://minesweeperonline.com/#" + LEVEL));
            Thread.sleep(5000);
        } catch (URISyntaxException | InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
        
        new MinesweeperSolver(LEVEL);
    }
}
