import javax.swing.JFrame;

public class Init {
public static void main(String[] args) {
    
    /*
     * Initializes frame and adds panel
     * Implements additional settings for the frame
     */
    JFrame frame = new JFrame();
    GamePanel panel = new GamePanel();
    frame.add(panel);

    int frameSize = 60;                                      // Simple variable to resize width and height of frame
    frame.setSize(frameSize*9, frameSize*14);                // Sets size of frame (window) (ratio of frameSize variable)
    frame.setVisible(true);                                // Sets frame as visable
    frame.requestFocus();                                    // Frame takes focus for keyboard and mouse
    panel.requestFocus();                                    // Panel takes focus for keyboard and mouse
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // Program will terminate on exit from frame
    frame.setLocationRelativeTo(null);                     // Sets Frame to center of screen
    frame.setResizable(false);                     // Prevents resizing frame

    /*
     * Constants
     */
    final int REFRESH_RATE = 25;

    /*
     * Game Loop
     * 
     */
    long startTime = System.currentTimeMillis();
    while(true){
        long elapsedTime = System.currentTimeMillis() - startTime;
        if(elapsedTime > REFRESH_RATE){
            

            startTime = System.currentTimeMillis();                         // This function must remain at the end of this statement
        }
    }
}
}
