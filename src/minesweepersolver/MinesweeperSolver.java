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
            
            flagNumber(1);
            flagNumber(2);
            flagNumber(3);
//            game.printGameState();
//            flagOnes();
//            Thread.sleep(20);
            clearUnknownCells();

            temp++;    

            if (temp == 15){
                
                break;
            }
        }
    }
    
    public static void clearUnknownCells() throws InterruptedException{
        final long startTime = System.currentTimeMillis();
        
        boolean isBomb, isUnknownCell;
        int [][] gameState = game.returnGameState();
        for (int i = 1; i < 10; i++){
            for (int j = 1; j < 10; j++){
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
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time of clearing: " + (endTime - startTime) );
    }
    
    public static void flagNumber(int number) throws InterruptedException{
        final long startTime = System.currentTimeMillis();


        int[][] gameState = game.returnGameState();
        int rows = game.returnRows();
        int columns = game.returnColumns();
        int unknownCells = 0;
        int flaggedCells = 0;
        int minePositionX = 0, minePositionY = 0;
        
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
                                minePositionX = i + k;
                                minePositionY = j + l;
                            }
                            else if (gameState[i + k][j + l] == 7){
                                flaggedCells++;
                            }
                        }
                    }

                    if (unknownCells == 1 && flaggedCells == number - 1){
//                        Thread.sleep(1000);
                        game.returnRobot().moveMouse(minePositionX, minePositionY);
                        game.returnRobot().clickMouse(right);
                        game.updateGameState(minePositionX, minePositionY, 7);
                    }
                }
            }
        }
        
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time flag" + number + ": " + (endTime - startTime) );
    }
    
}
