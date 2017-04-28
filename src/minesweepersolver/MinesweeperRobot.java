package minesweepersolver;

import com.sun.glass.events.KeyEvent;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesweeperRobot {
    private Robot robot;

    public MinesweeperRobot(){
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(MinesweeperRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void moveMouse(int positionX, int positionY){
            robot.mouseMove(73 + 35 * positionY, 310 +  35 * positionX);
    }
    
    public BufferedImage returnScreenshot(){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rectangle = new Rectangle(dimension);
        BufferedImage screenshot = robot.createScreenCapture(rectangle);
        
        return screenshot;
    }
    
    public void clickMouse(int mask){
        robot.mousePress(mask);
        robot.mouseRelease(mask);
    }
    
    public void pressSpace(){
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.delay(20);
    }
}
