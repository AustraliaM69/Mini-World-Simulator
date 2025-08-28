package ui;
import java.awt.*;
import javax.swing.*;

import world.World;

public class SimFrame extends JFrame {
    private final World world;
    private final javax.swing.Timer timer;
    private final WorldPanel panel;

    public SimFrame(int width, int height, int tileSize) {
        setTitle("World Sim");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true); // Allow resizing so buttons are always visible

        world = new World(width, height);
        panel = new WorldPanel(world, tileSize);
        add(panel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton speedUpBtn = new JButton("Speed Up");
        JButton pauseBtn = new JButton("Pause");
        JButton resetBtn = new JButton("Reset");
        buttonPanel.add(speedUpBtn);
        buttonPanel.add(pauseBtn);
        buttonPanel.add(resetBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        javax.swing.Timer t = new javax.swing.Timer(300, e -> {
            world.tick();
            panel.repaint();
        });
        t.start();
        this.timer = t;

        // Button actions
        speedUpBtn.addActionListener(e -> {
            int current = timer.getDelay();
            timer.setDelay(Math.max(50, current / 2));
        });
        pauseBtn.addActionListener(e -> timer.stop());
        resetBtn.addActionListener(e -> {
            timer.setDelay(300);
            if (!timer.isRunning()) timer.start();
        });
    }
}
