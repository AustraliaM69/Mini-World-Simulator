package ui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import character.Animal;
import character.SimCharacter;
import utility.SpriteManager;
import world.Tile;
import world.World;

public class WorldPanel extends JPanel {
    private final World world;
    private final int tileSize;
    // Mouse position for tooltip
    private int mouseX = -1;
    private int mouseY = -1;

    public WorldPanel(World world, int tileSize) {
        this.world = world;
        this.tileSize = tileSize;
        setPreferredSize(new Dimension(world.getWidth() * tileSize, world.getHeight() * tileSize));

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
                
                // Check for characters || Character click UI
                for (SimCharacter c : world.getCharacters()) {
                    if (c.getX() == tileX && c.getY() == tileY) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Name: ").append(c.getName()).append("\n");
                        sb.append("Gender: ").append(c.getGender()).append("\n");
                        sb.append("Thoughts / Memories:\n");
                        if (c.getThoughts().isEmpty()) {
                            sb.append("(none)");
                        } else {
                            for (String thought : c.getThoughts()) {
                                sb.append("- ").append(thought).append("\n");
                            }
                        }
                        JOptionPane.showMessageDialog(WorldPanel.this, sb.toString(), c.getName() + "'s Thoughts", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                
                // Check for animals
                for (Animal a : world.getAnimals()) {
                    if (a.getX() == tileX && a.getY() == tileY) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Name: ").append(a.getName()).append("\n");
                        sb.append("Type: ").append(a.getType()).append("\n");
                        sb.append("Location: (").append(a.getX()).append(", ").append(a.getY()).append(")");
                        JOptionPane.showMessageDialog(WorldPanel.this, sb.toString(), a.getName() + " the " + a.getType(), JOptionPane.INFORMATION_MESSAGE);
                        return;
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
        // Draw ground tiles first (base layer)
        for (int y = 0; y < world.getHeight(); y++) {
            for (int x = 0; x < world.getWidth(); x++) {
                Tile tile = world.getTiles()[x][y];
                // Always draw grass as the base ground
                BufferedImage grassSprite = SpriteManager.getSprite("grass");
                if (grassSprite != null) {
                    SpriteManager.drawSprite(g2, grassSprite, x * tileSize, y * tileSize, tileSize);
                } else {
                    // Fallback to color if sprite not available
                    g2.setColor(Color.GREEN);
                    g2.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                }
            }
        }
        
        // Draw terrain features on top (trees, rocks, buildings, etc.)
        for (int y = 0; y < world.getHeight(); y++) {
            for (int x = 0; x < world.getWidth(); x++) {
                Tile tile = world.getTiles()[x][y];
                if (!tile.getType().equals("grass")) { // Don't redraw grass
                    BufferedImage tileSprite = tile.getSprite();
                    if (tileSprite != null) {
                        SpriteManager.drawSprite(g2, tileSprite, x * tileSize, y * tileSize, tileSize);
                    } else {
                        // Fallback to color if sprite not available
                        g2.setColor(tile.getColor());
                        g2.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }
                }
            }
        }
        // Draw characters
        for (SimCharacter c : world.getCharacters()) {
            BufferedImage charSprite = c.getSprite();
            if (charSprite != null) {
                SpriteManager.drawSprite(g2, charSprite, c.getX() * tileSize, c.getY() * tileSize, tileSize);
            } else {
                // Fallback to colored circle if sprite not available
                g2.setColor(Color.RED);
                g2.fillOval(c.getX() * tileSize + tileSize/4, c.getY() * tileSize + tileSize/4, tileSize/2, tileSize/2);
            }
        }
        // Draw animals
        for (Animal a : world.getAnimals()) {
            BufferedImage animalSprite = a.getSprite();
            if (animalSprite != null) {
                SpriteManager.drawSprite(g2, animalSprite, a.getX() * tileSize, a.getY() * tileSize, tileSize);
            }
        }
        // Tooltip logic
        if (mouseX >= 0 && mouseY >= 0) {
            int tileX = mouseX / tileSize;
            int tileY = mouseY / tileSize;
            if (tileX >= 0 && tileX < world.getWidth() && tileY >= 0 && tileY < world.getHeight()) {
                Tile tile = world.getTiles()[tileX][tileY];
                SimCharacter charAtTile = null;
                Animal animalAtTile = null;
                for (SimCharacter c : world.getCharacters()) {
                    if (c.getX() == tileX && c.getY() == tileY) {
                        charAtTile = c;
                        break;
                    }
                }
                for (Animal a : world.getAnimals()) {
                    if (a.getX() == tileX && a.getY() == tileY) {
                        animalAtTile = a;
                        break;
                    }
                }
                StringBuilder tooltip = new StringBuilder();
                tooltip.append("Tile: ").append(tile.getType());
                if (charAtTile != null) {
                    tooltip.append("\n");
                    tooltip.append("Character: ").append(charAtTile.getName()).append("\n");
                    tooltip.append("Hunger: ").append(charAtTile.getHunger()).append("\n");
                    tooltip.append("Social: ").append(charAtTile.getSocial()).append("\n");
                    tooltip.append("Health: ").append(charAtTile.getHealth());
                    
                }
                if (animalAtTile != null) {
                    tooltip.append("\n");
                    tooltip.append("Animal: ").append(animalAtTile.getName()).append(" (").append(animalAtTile.getType()).append(")");
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
