package minesweepersolver;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MinesweeperGame {
    // 0 - unknown, 1 - 6 - number of mines,
    // 7 - flag, 8 - mine, 9 - known, -1 - out of bounds
    private final int[][] gameState;
    private int rows, columns, offX, offY, distance;
     
    public MinesweeperGame(String level){
        switch (level){
            case "beginner":        rows = 11;
                                    columns = 11;
                                    offX = 73;
                                    break;
            case "intermediate":    rows = 18;
                                    columns = 18;
                                    offX = 73;
                                    break;
            case "expert":          rows = 18;
                                    columns = 32;
                                    offX = 51;
                                    break;
        }
        offY = 310;
        distance = 35;
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
    
    public void readGameState(MinesweeperRobot robot){
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
    
    private int readCellState(int column, int row, BufferedImage screen){
        Color color = new Color(screen.getRGB(offX + distance * (row - 1),
                offY + distance * (column - 1)));
        int sum = color.getRed() + color.getGreen() + color.getBlue();
        int state;

        switch(sum){
            case 567:   color = new Color(screen.getRGB(offX + distance * (row - 1),
                            offY + distance * (column - 1) - distance/2));
                        sum = color.getRed() + color.getGreen() + color.getBlue();
                        if (sum == 756)
                            state = 0;
                        else
                            state = 9;
                        break;
            case 255:   state = 1;
                        break;
            case 123:   state = 2;
                        break;
            case 488:   state = 3;
                        break;
            case 443:   state = 4;
                        break;
            case 454:   state = 5;
                        break;
            case 485:   state = 6;
                        break;
            case 63:    state = 7;
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
