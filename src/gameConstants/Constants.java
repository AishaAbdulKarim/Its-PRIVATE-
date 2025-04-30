package gameConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Constants {

    // Display Constants
    public static final int REFRESH_RATE = 25;
    public static final int FRAME_WIDTH;
    public static final int FRAME_HEIGHT;
    public static final Color SKY_BLUE = new Color(174, 227, 245);

    // Bucket Constants
    public static final int BASKET_WIDTH = 100;
    public static final int BASKET_HEIGHT = 100;
    public static final int BASKET_X;
    public static final int BASKET_Y;

    // Heart Constant
    public static final int HEART_SIZE = 30;

    static {
        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        FRAME_WIDTH = (int) (screenSize.width * 0.3);   // 50% of screen width
        FRAME_HEIGHT = (int) (screenSize.height * 0.9); // 80% of screen height

        BASKET_X = (FRAME_WIDTH / 2) - (BASKET_WIDTH / 2);
        BASKET_Y = (int)(FRAME_HEIGHT * 0.9) - BASKET_HEIGHT;
    }
}