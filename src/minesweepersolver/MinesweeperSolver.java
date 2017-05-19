package minesweepersolver;

import java.awt.event.InputEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesweeperSolver {
    private MinesweeperGame game;
    private MinesweeperRobot robot;
    private final int left = InputEvent.BUTTON1_DOWN_MASK;
    private final int right = InputEvent.BUTTON3_DOWN_MASK;
    private boolean hasGameStateChanged;
    private int maxTries;
    
    public MinesweeperSolver(String level){
        game = new MinesweeperGame(level);
        robot = new MinesweeperRobot(level);
        switch (level){
            case "beginner":        maxTries = 20;
                                    break;
            case "intermediate":    maxTries = 50;
                                    break;
            case "expert":          maxTries = 150;
                                    break;
        }
        try {
            solveGame();
        } catch (InterruptedException ex) {
            Logger.getLogger(MinesweeperSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void clearUnknownCells(){
        int rows = game.returnRows();
        int columns = game.returnColumns();
        int flags;
        boolean isUnknownCell;
        int [][] gameState = game.returnGameState();
        
        for (int i = 1; i < rows - 1; i++){
            for (int j = 1; j < columns - 1; j++){
                if (gameState[i][j] != 0 && gameState[i][j] != 9 &&
                        gameState[i][j] != -1 && gameState[i][j] != 7){
                    flags = 0;
                    isUnknownCell = false;
                    for (int k = -1; k < 2; k++){
                        for (int l = -1; l < 2; l++){
                            if (gameState[i + k][j + l] == 7){
                                flags++;
                            }
                            else if(gameState[i + k][j + l] == 0){
                                isUnknownCell = true;
                            }
                        }
                    }
                    
                    if (flags == gameState[i][j] && isUnknownCell){
                        robot.moveMouse(i, j);
                        robot.pressSpace();  
                    }
                }
            }
        }
    }
    
    private void flagNumber(int number){
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
                    }
                }
            }
        }
    }
    
    private void makeRandomMove() throws InterruptedException{
        int[][] gameState;
        Random generator = new Random();
        int x, y, cellState = 1, unknownCells, cellCounter = 2;
        int rows = game.returnRows();
        int columns = game.returnColumns();
        
        outer:
        for (int i = 1; i < rows - 1; i++){
            for (int j = 1; j < columns - 1; j++){
                gameState = game.returnGameState();
                if (gameState[i][j] == cellState){
                    unknownCells = 0;
                    for (int k = -1; k < 2; k++){
                        for (int l = -1; l < 2; l++){
                            if (k == 0 && l == 0){
                                continue;
                            }
                            
                            if (gameState[i + k][j + l] == 0){
                                unknownCells++;
                            }
                        }
                    }

                    if (unknownCells == cellCounter){
                        while (true){
                            x = generator.nextInt(3) - 1;
                            y = generator.nextInt(3) - 1;
                            if (gameState[i + x][j + y] == 0){
                                robot.moveMouse(i + x, j + y);
                                robot.clickMouse(left);
                                break outer; 
                            }
                        }
                    }
                }
                if (i == rows - 2 && j == columns - 2){
                    cellState++;
                    i = 1;
                    j = 1;
                    if (cellState == 6 && cellCounter == 4){
                        System.exit(0);
                    }
                    if (cellState == 6){
                        cellCounter++;
                        cellState = 1;
                    }
                }
            }
        }
    }
    
    private void solveGame() throws InterruptedException{
        robot.moveMouse(5, 4);
        robot.clickMouse(left);
 
        int counter = 0, tries = 0;
        while (true){
            hasGameStateChanged = false;
            Thread.sleep(20);
            game.readGameState(robot);
            
            flagNumber(1);
            flagNumber(2);
            flagNumber(3);
            flagNumber(4);
            flagNumber(5);
            flagNumber(6);
            clearUnknownCells();

            if (tries++ == maxTries)
                System.exit(0);
            if (hasGameStateChanged){
                counter = 0;
            }
            else {
                counter++;
                if (counter == 3){
                    makeRandomMove();
                    counter = 0;
                }
            }   
        }
    }
}
