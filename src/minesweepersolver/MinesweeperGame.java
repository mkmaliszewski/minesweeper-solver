package minesweepersolver;

import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesweeperGame {
    // 0 - unknown, 1 - 5 - number of mines, 6 - known, 
    // 7 - flag, 8 - mine, -1 - out of bounds
    private int[][]  gameState;
    private int rows = 16, columns = 30;
     
    public MinesweeperGame(){
        rows += 2;
        columns += 2;
        gameState = new int[rows][columns];
        
        //game initialization
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if (i == 0 || j == 0 || i == rows - 1 || j == columns - 1){
                    gameState[i][j] = -1;
                }
                else {
                    gameState[i][j] = 0;
                }
            }
        }
    }
    
    public void readGameState(MinesweeperRobot robot) throws InterruptedException {
        BufferedImage screen = robot.makeScreenshot();
        
        for (int i = 1; i < rows - 1; i++){
            for (int j = 1; j < columns - 1; j++){
                gameState[i][j] = readCellState(i, j, screen);
                if (gameState[i][j] == 8){
                    System.exit(0);
                }
            }
        }
    }
    
    public void printGameState(){
        for (int i = 1; i < rows - 1; i++){
            for (int j = 1; j < columns - 1; j++){
                System.out.print(gameState[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public boolean checkIfGameOver(){
        for (int i = 1; i < rows - 1; i++){
            for (int j = 1; j < columns - 1; j++){
                if (gameState[i][j] == 0){
                    return false;
                }                    
            }
        }
        
        return true;
    }
    
    //567 - unknown; 255 - 1; 130 - 2; 515 - 3; 479 - 4
    //494 - 5; 480 - known; 61 - flag; 0 - bomb
    public int readCellState(int column, int row, BufferedImage screen) throws InterruptedException {
//        Color color = new Color(screen.getRGB(73 + 35 * (row - 1),
//                310 + 35 * (column - 1)));
        Color color = new Color(screen.getRGB(51 + 35 * (row - 1),
                310 + 35 * (column - 1)));
        int sum = color.getRed() + color.getGreen() + color.getBlue();
        int state;
//        System.out.println(color);
        switch(sum){
        case 567:   //color = new Color(screen.getRGB(73 + 35 * (row - 1),
                    //    310 + 35 * (column - 1) + 10));
                    color = new Color(screen.getRGB(51 + 35 * (row - 1),
                        310 + 35 * (column - 1) + 10));
                    sum = color.getRed() + color.getGreen() + color.getBlue();
                    if (sum == 480)
                        state = 0;
                    else
                        state = 6;
                    break;
        case 255:   state = 1;
                    break;
        case 130:     state = 2;
                    break;
        case 515:   state = 3;
                    break;
        case 479:   state = 4;
                    break;
        case 494:   state = 5;
                    break;
        case 61:    state = 7;
                    break;
        case 0:     state = 8;
                    break;
        default:    state = 0;
        }

        return state;       
    }
    
    public int returnRows(){
        return rows;
    }
    
    public int returnColumns(){
        return columns;
    }
    
    public int[][] returnGameState(){
        return gameState;
    }
    
    public void updateGameState(int i, int j, int value){
        gameState[i][j] = value;
    }
}
