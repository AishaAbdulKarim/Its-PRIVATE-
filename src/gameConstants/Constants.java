package gameConstants;

import java.awt.Color;


public class Constants {

    // Display Constants
    public final static int REFRESH_RATE = 100;
    public final static int FRAME_WIDTH = 500;
    public final static int FRAME_HEIGHT = 700;
    public final static Color SKY_BLUE = new Color(174,227,245);       // Colour for background

    // Bucket Constants
    public final static int BASKET_WIDTH = 100;
    public final static int BASKET_HEIGHT = 100;
    public final static int BASKET_X = (FRAME_WIDTH/2)-(BASKET_WIDTH/2);
    public final static int BASKET_Y = (int)(FRAME_HEIGHT*.75)-BASKET_HEIGHT;

    //Heart Constant
    public final static int HEART_SIZE = 30;

}
