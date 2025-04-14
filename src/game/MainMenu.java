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
        String fileName = "Mainmenu.png"; // make sure this matches your image file name
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

        setOpaque(false); // Let the background show through
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel title = new JLabel("🥚 Egg Catcher");
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setForeground(new Color(60, 60, 60));
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
        btn.setPreferredSize(new Dimension(220, 50));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(100, 149, 237)); // Cornflower blue
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(70, 130, 180)); // Darker blue
            }

            public void mouseExited(MouseEvent evt) {
                btn.setBackground(new Color(100, 149, 237));
            }
        });

        return btn;
    }
}
