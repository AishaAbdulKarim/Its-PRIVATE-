package game;

import javax.swing.JPanel;
import gameConstants.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener {
    private GameManager game;

    public GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        game = new GameManager();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics = (Graphics2D) g;
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHints(hints);

        drawBackground(graphics);
        game.drawSprites(graphics, this);
    }

    public void drawBackground(Graphics2D graphics){
        graphics.setColor(Constants.SKY_BLUE);
        graphics.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    }

    public void update(){
        game.update();
        this.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Basket basket = game.getBasket();
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            basket.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            basket.moveRight();
        }
    }
    

    @Override
    public void keyReleased(KeyEvent e) {
        // No need for actions on key release for now
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
