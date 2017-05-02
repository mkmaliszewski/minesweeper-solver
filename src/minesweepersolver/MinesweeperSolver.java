package minesweepersolver;

import java.awt.event.InputEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesweeperSolver {
    private MinesweeperGame game;
    private MinesweeperRobot robot;
    private int left = InputEvent.BUTTON1_DOWN_MASK;
    private int right = InputEvent.BUTTON3_DOWN_MASK;
    private boolean hasGameStateChanged;
    
    public MinesweeperSolver(){
        game = new MinesweeperGame();
        robot = new MinesweeperRobot();
        
        try {
            solveGame();
        } catch (InterruptedException ex) {
            Logger.getLogger(MinesweeperSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clearUnknownCells() throws InterruptedException{
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
                        robot.moveMouse(i, j);
                        robot.pressSpace();  
//                        Thread.sleep(1000);
                    }
                }
            }
        }
    }
    
    public void flagNumber(int number) throws InterruptedException{
        int[][] gameState;
        int rows = game.returnRows();
        int columns = game.returnColumns();
        int unknownCells, flaggedCells;
        int minePositionRow = 0, minePositionColumn = 0;
        
        for (int i = 1; i < rows - 1; i++){
            for (int j = 1; j < columns - 1; j++){
                gameState = game.returnGameState();
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
                        robot.moveMouse(minePositionRow, minePositionColumn);
                        robot.clickMouse(right);
                        game.updateGameState(minePositionRow, minePositionColumn, 7);
                        hasGameStateChanged = true;
//                        Thread.sleep(1000);
                    }
                }
            }
        }
    }
    
    public void makeRandomMove(){
        int[][] gameState = game.returnGameState();
        Random generator = new Random();
        int x, y;
        boolean isProperCell = false;
        int rows = game.returnRows();
        int columns = game.returnColumns();
        
        do {
            x = generator.nextInt(rows - 2) + 1;
            y = generator.nextInt(columns - 2) + 1;
            if (gameState[x][y] == 0){
                isProperCell = true;    
            }
        } while (!isProperCell);
        
        robot.moveMouse(x, y);
        robot.clickMouse(left);
    }
    
    public void solveGame() throws InterruptedException{
        makeRandomMove();
//        robot.moveMouse(1,1);
//        System.exit(0);
        int temp = 0, counter = 0;
        while (true){
            hasGameStateChanged = false;
            Thread.sleep(20);
            game.readGameState(robot);
//            System.exit(0);
//            Thread.sleep(20);
            
            flagNumber(1);
            flagNumber(2);
            flagNumber(3);
            flagNumber(4);
            flagNumber(5);

            clearUnknownCells();

            if (temp++ == 150)
                System.exit(0);
            if (hasGameStateChanged){
                counter = 0;
                continue;
            }
            else {
                counter++;
                if (counter == 4){
                    Thread.sleep(2000);
                    makeRandomMove();
                    counter = 0;
                }
            }   
        }
    }
}
