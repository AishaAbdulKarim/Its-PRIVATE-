package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    private Init frame;

    public MainMenu(Init frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
        setBackground(gameConstants.Constants.SKY_BLUE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel title = new JLabel("🥚 Egg Catcher Game 🧺");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        JButton playBtn = new JButton("Play");
        playBtn.setPreferredSize(new Dimension(200, 50));
        playBtn.addActionListener(e -> frame.showGame());
        gbc.gridy++;
        add(playBtn, gbc);

        JButton multiBtn = new JButton("Multiplayer");
        multiBtn.setPreferredSize(new Dimension(200, 50));
        multiBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Multiplayer coming soon!"));
        gbc.gridy++;
        add(multiBtn, gbc);

        JButton highscoreBtn = new JButton("Highscores");
        highscoreBtn.setPreferredSize(new Dimension(200, 50));
        highscoreBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Highscores coming soon!"));
        gbc.gridy++;
        add(highscoreBtn, gbc);
    }
}