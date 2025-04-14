package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MainMenu extends JPanel {
    private game.Init frame;
    private BufferedImage backgroundImage;

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
        JLabel title = new JLabel("🍳 Omelette Rescue");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 42)); // Bubbly font
        title.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        String[] buttons = {"Play", "Multiplayer", "Highscores"};
        for (int i = 0; i < buttons.length; i++) {
            final String buttonText = buttons[i];
            JButton btn = createStyledButton(buttonText);
            gbc.gridy++;
            add(btn, gbc);

            if (buttonText.equals("Play")) {
                btn.addActionListener(e -> frame.showGame());
            } else {
                btn.addActionListener(e -> JOptionPane.showMessageDialog(MainMenu.this, buttonText + " coming soon!"));
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

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(240, 55));
        btn.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(new Color(30, 100, 200), 2));
        btn.setBackground(new Color(70, 130, 180)); // Nice sky blue
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        btn.setBorder(new RoundedBorder(20)); // Rounded edges

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(60, 120, 170)); // slightly darker
            }

            public void mouseExited(MouseEvent evt) {
                btn.setBackground(new Color(70, 130, 180));
            }
        });

        return btn;
    }

    // Custom rounded border class
    static class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(((JButton)c).getBackground().darker());
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
