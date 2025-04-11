package game;
import javax.swing.JFrame;

import gameConstants.Constants;

public class Init {
public static void main(String[] args) {
    
    /*
     * Initializes frame and adds panel
     * Implements additional settings for the frame
     */
    JFrame frame = new JFrame();
    GamePanel panel = new GamePanel();
    frame.add(panel);

    frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);           // Sets size of frame (window) (ratio of frameSize variable)
    frame.setVisible(true);                                               // Sets frame as visable
    frame.requestFocus();                                                   // Frame takes focus for keyboard and mouse
    panel.requestFocus();                                                   // Panel takes focus for keyboard and mouse
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                   // Program will terminate on exit from frame
    frame.setLocationRelativeTo(null);                                    // Sets Frame to center of screen
    frame.setResizable(false);                                    // Prevents resizing frame

    /*
     * Game Loop
     * 
     */
    long startTime = System.currentTimeMillis();
    while(true){
        long elapsedTime = System.currentTimeMillis() - startTime;
        if(elapsedTime > Constants.REFRESH_RATE){
            panel.update();
            startTime = System.currentTimeMillis();
        }
    }
    
}
}
