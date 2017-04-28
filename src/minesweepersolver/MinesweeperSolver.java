package minesweepersolver;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesweeperSolver {
    private static MinesweeperGame game;
    private static int left = InputEvent.BUTTON1_DOWN_MASK;
    private static int right = InputEvent.BUTTON3_DOWN_MASK;
    private static boolean[][] isAlreadyClicked;
    
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
        
        
        int temp = 0 ;
        game = new MinesweeperGame();
        int rows = game.returnRows();
        int columns = game.returnColumns();
        isAlreadyClicked = new boolean[rows][columns];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                isAlreadyClicked[i][j] = false;
            }
        }
//        game.readGameState();
        while(true){
            Thread.sleep(20);
            game.readGameState();
            Thread.sleep(20);
            flagOnes();
            Thread.sleep(20);
            clearUnknownCells();
            temp++;    

            if (temp == 5){
                
                break;
            }
        }
//System.out.println("lol");
    }
    
    
    public static void flagOnes() throws InterruptedException{
        int[][] gs = game.returnGameState();
        int rows = game.returnRows();
        int columns = game.returnColumns();
        for (int i = 1; i < rows - 1; i++){
            for (int j = 1; j < columns - 1; j++){
                if (gs[i][j] == 1){
                    int[] pos = checkIfCorner(i, j, gs);
                    if (pos[0] != 0 && pos[1] != 0){
                        game.returnRobot().moveMouse(pos[0] - 1, pos[1] - 1);
                        game.returnRobot().clickMouse(right);
//                        Thread.sleep(500);
//                        
//                        gs[pos[0]][pos[1]] = 7;
                        game.updateGameState(pos[0], pos[1], 7);
//                        clearUnknownCells(pos[0], pos[1], gs);
//                        game.printGameState(gs);    
                    }
                }
            }
        }
    }
    
    public static int[] checkIfCorner(int row, int column, int[][] gs){
        int unknownCorners = 0;
        int[] minePosition = new int[2];
        
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                if (i == 0 || j == 0){
                    continue;
                }
                if (gs[row + i][column + j] == 0){
                    unknownCorners++;
                    minePosition[0] = row + i;
                    minePosition[1] = column + j;
                    if (gs[row][column + j] == 0 || gs[row + i][column] == 0 ||
                            gs[row][column + j] == 7 || gs[row + i][column] == 7){
                        unknownCorners--;
                    }
                }
            }
        }

        
        if (unknownCorners == 1){
//            System.out.println(minePosition[0] + " " + minePosition[1]);
            return minePosition;
        }
        else return new int[]{0, 0};
    }
    
    public static void clearUnknownCells() throws InterruptedException{
        int[][] gameState = game.returnGameState();
        for (int i = 1; i < 10; i++){
            for (int j = 1; j < 10; j++){
                if (gameState[i][j] == 7){
                    for (int k = -1; k < 2; k++){
                         for (int l = -1; l < 2; l++){
                            if (k == 0 && l == 0){
                                continue;
                            }
                            if (gameState[i + k][j + l] == 1 && isAlreadyClicked[i + k][j + l] == false){
                                game.returnRobot().moveMouse(i + k - 1, j + l - 1);
                                game.returnRobot().pressSpace();
                                isAlreadyClicked[i + k][j + l] = true;
//                                Thread.sleep(500);
                                
                            }
                        }
                    }
                }
            }
        }     
    }
    
}
