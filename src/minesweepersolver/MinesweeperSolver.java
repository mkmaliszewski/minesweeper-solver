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
    private static boolean hasGameStateChanged;
    
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
        
        int counter = 0;
        int temp = 0;
        game = new MinesweeperGame();
        game.makeRandomMove();
//        long start = System.currentTimeMillis();
        while (true){
            hasGameStateChanged = false;
            Thread.sleep(20);
            game.readGameState();
            Thread.sleep(20);
            
            flagNumber(1);
            flagNumber(2);
            flagNumber(3);
            flagNumber(4);
            flagNumber(5);

            clearUnknownCells();

            if (temp++ == 15)
                break;  
            if (hasGameStateChanged){
                continue;
            }
//            if (game.checkIfGameOver()){
//                break;
//            }
            else {
                counter++;
                if (counter == 3){
                    Thread.sleep(5000);
                    game.makeRandomMove();
                    counter = 0;
                    
                }
            }
            
            
        }
//        long end = System.currentTimeMillis();
//        System.out.println("main loop time: " + (end - start));
    }
    
    public static void clearUnknownCells() throws InterruptedException{
//        long start = System.currentTimeMillis();
        int rows = game.returnRows();
        int columns = game.returnColumns();
        boolean isBomb, isUnknownCell;
        int [][] gameState = game.returnGameState();
        
        for (int i = 1; i < rows - 1; i++){
            for (int j = 1; j < columns - 1; j++){
                if (gameState[i][j] != 0 && gameState[i][j] != 6 &&
                        gameState[i][j] != -1 && gameState[i][j] != 7){
                    isBomb = false;
                    isUnknownCell = false;
                    for (int k = -1; k < 2; k++){
                        for (int l = -1; l < 2; l++){
                            if (gameState[i + k][j + l] == 7){
                                isBomb = true;
                            }
                            else if(gameState[i + k][j + l] == 0){
                                isUnknownCell = true;
                            }
                        }
                    }
                    
                    if (isBomb && isUnknownCell){
                        game.returnRobot().moveMouse(i, j);
                        game.returnRobot().pressSpace();  
//                        Thread.sleep(1000);
                    }
                }
            }
        }
//        long end = System.currentTimeMillis();
//        System.out.println("time of clearing: " + (end-start));
    }
    
    public static void flagNumber(int number) throws InterruptedException{
//        long start = System.currentTimeMillis();
        int[][] gameState = game.returnGameState();
        int rows = game.returnRows();
        int columns = game.returnColumns();
        int unknownCells, flaggedCells;
        int minePositionRow = 0, minePositionColumn = 0;
        
        for (int i = 1; i < rows - 1; i++){
            for (int j = 1; j < columns - 1; j++){
                if (gameState[i][j] == number){
                    unknownCells = 0;
                    flaggedCells = 0;
                    for (int k = -1; k < 2; k++){
                        for (int l = -1; l < 2; l++){
                            if (k == 0 && l == 0){
                                continue;
                            }
                            
                            if (gameState[i + k][j + l] == 0){
                                unknownCells++;
                                minePositionRow = i + k;
                                minePositionColumn = j + l;
                            }
                            else if (gameState[i + k][j + l] == 7){
                                flaggedCells++;
                            }
                        }
                    }

                    if ((unknownCells == 1 && flaggedCells == number - 1) || 
                            (unknownCells == 2 && flaggedCells == number - 2)){
                        game.returnRobot().moveMouse(minePositionRow, minePositionColumn);
                        game.returnRobot().clickMouse(right);
                        gameState[minePositionRow][minePositionColumn] = 7;
                        game.updateGameState(minePositionRow, minePositionColumn, 7);
                        hasGameStateChanged = true;
//                        Thread.sleep(1000);
                    }
                }
            }
        }
//        long end = System.currentTimeMillis();
//        System.out.println("time of flag " + number + ": " + (end - start));
    }
}
