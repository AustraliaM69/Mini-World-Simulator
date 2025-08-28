package world;
import java.awt.*;
import java.awt.image.BufferedImage;

import utility.SpriteManager;

public class Tile {
    String type;
    private String spriteName;
    private BufferedImage sprite;
    
    public Tile(String type) {
        this.type = type;
        setSpriteForType();
    }
    
    // Getter method for cross-package access
    public String getType() { return type; }
    
    // Setter method for cross-package access
    public void setType(String type) { 
        this.type = type; 
        setSpriteForType();
    }
    
    private void setSpriteForType() {
        switch (type) {
            case "grass":
                spriteName = "grass";
                break;
            case "texturedGrass":
                spriteName = "texturedGrass";
                break;
            case "deadGrass":
                spriteName = "deadGrass";
                break;
            case "water":
                spriteName = "water";
                break;
            case "mountain":
                spriteName = "cliff";
                break;
            case "food":
                spriteName = "wheat";
                break;
            case "tree":
                spriteName = "tree";
                break;
            case "rock":
                spriteName = "rock";
                break;
            case "building":
                spriteName = "house";
                break;
            default:
                spriteName = "grass";
                break;
        }
        sprite = SpriteManager.getSprite(spriteName);
    }
    
    public Color getColor() {
        return switch (type) {
            case "grass" -> Color.GREEN;
            case "texturedGrass" -> new Color(34, 139, 34); // Forest green
            case "deadGrass" -> new Color(139, 115, 85); // Tan
            case "water" -> Color.CYAN;
            case "mountain" -> Color.GRAY;
            case "food" -> new Color(210, 140, 60); // Brown/orange for food
            case "tree" -> new Color(0, 100, 0); // Dark green
            case "rock" -> new Color(105, 105, 105); // Dim gray
            case "building" -> new Color(139, 69, 19); // Saddle brown
            default -> Color.BLACK;
        };
    }
    
    public BufferedImage getSprite() {
        return sprite;
    }
    
    public String getSpriteName() {
        return spriteName;
    }
    
    public void setSprite(String spriteName) {
        this.spriteName = spriteName;
        this.sprite = SpriteManager.getSprite(spriteName);
    }
}
