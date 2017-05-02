package minesweepersolver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesweeperMain {
    public static void main(String[] args) throws InterruptedException {
        try {
//            Desktop.getDesktop().browse(new URI("http://minesweeperonline.com/#beginner"));
            Thread.sleep(5000);
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(MinesweeperSolver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MinesweeperSolver.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(MinesweeperSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new MinesweeperSolver();
    }
}
