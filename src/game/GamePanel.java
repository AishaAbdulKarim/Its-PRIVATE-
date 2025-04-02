package game;
import javax.swing.JPanel;
import gameConstants.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener{
    private GameManager game;

    public GamePanel(){
        addKeyListener(this);// Sets up keyboard event listener
        setFocusable(true);
        requestFocusInWindow(); // ensures key input works
        game = new GameManager();

        // Game loop - calls update() 60 times per second
        javax.swing.Timer timer = new javax.swing.Timer(1000 / 60, e -> update());
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics = (Graphics2D)g;
        // Improves image rendering
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHints(hints);

        // Draw Background
        drawBackground(graphics);

        // Draws Sprites (buckets, eggs, timer, score etc.)
        game.drawSprites(graphics, this);
    }

    // Sets background color, size of screen
    public void drawBackground(Graphics2D graphics){
        graphics.setColor(Constants.SKY_BLUE);
        graphics.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    }

    public void update(){
        game.update();
        this.repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
    
}
