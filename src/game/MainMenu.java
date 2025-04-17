package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class MainMenu extends JPanel {
    private game.Init frame;
    private BufferedImage backgroundImage;
    private boolean isSinglePlayer = false;
    private boolean isMultiPlayer = false;

    @SuppressWarnings({"CallToPrintStackTrace", "OverridableMethodCallInConstructor"})
    public MainMenu(game.Init frame) {
        this.frame = frame;

        // Load background image using class loader
        String fileName = "Mainmenu.png";
        URL imageUrl = getClass().getClassLoader().getResource("images/" + fileName);
        if (imageUrl == null) {
            System.out.println("Background image not found: images/" + fileName);
        } else {
            try {
                backgroundImage = ImageIO.read(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setOpaque(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // Bubbly title
        JLabel title = new JLabel("   Omelette Rescue");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 42)); // Fun font
        title.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        String[] buttons = {"Play", "Multiplayer", "Highscores"};
        for (String buttonText : buttons) {
            JButton btn = createStyledButton(buttonText);
            gbc.gridy++;
            add(btn, gbc);

            switch (buttonText) {
                case "Multiplayer":
                    setMultiPlayer(true);
                    btn.addActionListener(e -> frame.showMPGame());
                    break;
                case "Play":
                    setSinglePlayer(true);
                    btn.addActionListener(e -> frame.showSPGame());
                    break;
                default:
                    btn.addActionListener(e -> {
                        int score = frame.getHighScore();
                        JOptionPane.showMessageDialog(MainMenu.this, "🏆 Highest Score: " + score, "High Scores", JOptionPane.INFORMATION_MESSAGE);
                    });
                    break;
            }
        } 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Button Set-up
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(240, 55));
        btn.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBackground(new Color(70, 130, 180)); // Soft blue
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new RoundedBorder(25)); // Apply rounded border

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(60, 120, 170)); // slightly darker
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(new Color(70, 130, 180));
            }
        });

        return btn;
    }

    // Correct Border Implementation
    static class RoundedBorder implements Border {
        private final int RADIUS;

        RoundedBorder(int RADIUS) {
            this.RADIUS = RADIUS;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(RADIUS, RADIUS, RADIUS, RADIUS);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(((JButton) c).getBackground().darker());
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x, y, width - 1, height - 1, RADIUS, RADIUS);
        }
    }

    // Getters and Setters
    public boolean isSinglePlayer() {
        return isSinglePlayer;
    }

    public void setSinglePlayer(boolean isSinglePlayer) {
        this.isSinglePlayer = isSinglePlayer;
    }

    public boolean isMultiPlayer() {
        return isMultiPlayer;
    }

    public void setMultiPlayer(boolean isMultiPlayer) {
        this.isMultiPlayer = isMultiPlayer;
    }

    public game.Init getFrame() {
        return frame;
    }

    public void setFrame(game.Init frame) {
        this.frame = frame;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
