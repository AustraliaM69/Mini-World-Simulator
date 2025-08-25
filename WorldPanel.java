import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WorldPanel extends JPanel {
    private final World world;
    private final int tileSize;
    // Mouse position for tooltip
    private int mouseX = -1;
    private int mouseY = -1;

    public WorldPanel(World world, int tileSize) {
        this.world = world;
        this.tileSize = tileSize;
        setPreferredSize(new Dimension(world.width * tileSize, world.height * tileSize));

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int tileX = e.getX() / tileSize;
                int tileY = e.getY() / tileSize;
                for (SimCharacter c : world.characters) {
                    if (c.x == tileX && c.y == tileY) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Name: ").append(c.name).append("\n");
                        sb.append("Gender: ").append(c.gender).append("\n");
                        sb.append("Thoughts / Memories:\n");
                        if (c.thoughts.isEmpty()) {
                            sb.append("(none)");
                        } else {
                            for (String thought : c.thoughts) {
                                sb.append("- ").append(thought).append("\n");
                            }
                        }
                        JOptionPane.showMessageDialog(WorldPanel.this, sb.toString(), c.name + "'s Thoughts", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Use Graphics2D for antialiasing
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Draw tiles
        for (int y = 0; y < world.height; y++) {
            for (int x = 0; x < world.width; x++) {
                Tile tile = world.tiles[x][y];
                g2.setColor(tile.getColor());
                g2.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
        // Draw characters
        for (SimCharacter c : world.characters) {
            g2.setColor(Color.RED);
            g2.fillOval(c.x * tileSize + tileSize/4, c.y * tileSize + tileSize/4, tileSize/2, tileSize/2);
        }
        // Tooltip logic
        if (mouseX >= 0 && mouseY >= 0) {
            int tileX = mouseX / tileSize;
            int tileY = mouseY / tileSize;
            if (tileX >= 0 && tileX < world.width && tileY >= 0 && tileY < world.height) {
                Tile tile = world.tiles[tileX][tileY];
                SimCharacter charAtTile = null;
                for (SimCharacter c : world.characters) {
                    if (c.x == tileX && c.y == tileY) {
                        charAtTile = c;
                        break;
                    }
                }
                StringBuilder tooltip = new StringBuilder();
                tooltip.append("Tile: ").append(tile.type);
                if (charAtTile != null) {
                    tooltip.append("\n");
                    tooltip.append("Name: ").append(charAtTile.name).append("\n");
                    tooltip.append("Hunger: ").append(charAtTile.hunger).append("\n");
                    tooltip.append("Health: ").append(charAtTile.health);
                }
                // Prepare to draw tooltip
                String[] lines = tooltip.toString().split("\n");
                Font font = g2.getFont().deriveFont(Font.PLAIN, 13f);
                g2.setFont(font);
                FontMetrics fm = g2.getFontMetrics();
                int width = 0;
                for (String line : lines) {
                    width = Math.max(width, fm.stringWidth(line));
                }
                int lineHeight = fm.getHeight();
                int height = lineHeight * lines.length + 6;
                width += 12;
                // Tooltip position (avoid going out of bounds)
                int tx = mouseX + 15;
                int ty = mouseY + 15;
                if (tx + width > getWidth()) tx = getWidth() - width - 2;
                if (ty + height > getHeight()) ty = getHeight() - height - 2;
                // Draw background
                g2.setColor(new Color(255, 255, 220, 240));
                g2.fillRoundRect(tx, ty, width, height, 8, 8);
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(tx, ty, width, height, 8, 8);
                // Draw text
                g2.setColor(Color.BLACK);
                int textY = ty + fm.getAscent() + 4;
                for (String line : lines) {
                    g2.drawString(line, tx + 6, textY);
                    textY += lineHeight;
                }
            }
        }
        g2.dispose();
    }
}
