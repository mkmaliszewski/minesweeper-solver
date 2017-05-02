package minesweepersolver;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesweeperMain {
    // choose level
//    private static String level = "beginner";
//    private static String level = "intermediate";
    private static String level = "expert";
    
    public static void main(String[] args) {
        try {
            Desktop.getDesktop().browse(new URI("http://minesweeperonline.com/#" + level));
            Thread.sleep(5000);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MinesweeperSolver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MinesweeperSolver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MinesweeperSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new MinesweeperSolver(level);
    }
}
