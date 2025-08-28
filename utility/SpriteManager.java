package utility;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SpriteManager {
    private static Map<String, BufferedImage> sprites = new HashMap<>();
    private static Map<String, BufferedImage> spriteSheets = new HashMap<>();
    private static Random random = new Random();
    
    // Sprite sheet dimensions (16x16 sprites)
    private static final int SPRITE_SIZE = 16;
    
    static {
        loadSprites();
    }
    
    private static void loadSprites() {
        try {
            // Load sprite sheets for ground tiles
            spriteSheets.put("grassSheet", ImageIO.read(new java.io.File("res/Ground/Grass.png")));
            spriteSheets.put("deadGrassSheet", ImageIO.read(new java.io.File("res/Ground/DeadGrass.png")));
            spriteSheets.put("cliffSheet", ImageIO.read(new java.io.File("res/Ground/Cliff.png")));
            spriteSheets.put("shoreSheet", ImageIO.read(new java.io.File("res/Ground/Shore.png")));
            spriteSheets.put("winterSheet", ImageIO.read(new java.io.File("res/Ground/Winter.png")));
            
            // Load sprite sheets for nature elements
            spriteSheets.put("treesSheet", ImageIO.read(new java.io.File("res/Nature/Trees.png")));
            spriteSheets.put("rocksSheet", ImageIO.read(new java.io.File("res/Nature/Rocks.png")));
            spriteSheets.put("cactusSheet", ImageIO.read(new java.io.File("res/Nature/Cactus.png")));
            spriteSheets.put("wheatSheet", ImageIO.read(new java.io.File("res/Nature/Wheatfield.png")));
            
            // Load sprite sheets for animals
            spriteSheets.put("sheepSheet", ImageIO.read(new java.io.File("res/Animals/Sheep.png")));
            spriteSheets.put("hornedSheepSheet", ImageIO.read(new java.io.File("res/Animals/HornedSheep.png")));
            spriteSheets.put("chickenSheet", ImageIO.read(new java.io.File("res/Animals/Chicken.png")));
            spriteSheets.put("chickSheet", ImageIO.read(new java.io.File("res/Animals/Chick.png")));
            spriteSheets.put("pigSheet", ImageIO.read(new java.io.File("res/Animals/Pig.png")));
            spriteSheets.put("boarSheet", ImageIO.read(new java.io.File("res/Animals/Boar.png")));
            spriteSheets.put("horseSheet", ImageIO.read(new java.io.File("res/Animals/Horse(32x32).png")));
            
            // Load sprite sheets for characters
            spriteSheets.put("farmerCyanSheet", ImageIO.read(new java.io.File("res/Characters/Workers/CyanWorker/FarmerCyan.png")));
            spriteSheets.put("farmerLimeSheet", ImageIO.read(new java.io.File("res/Characters/Workers/LimeWorker/FarmerLime.png")));
            spriteSheets.put("farmerPurpleSheet", ImageIO.read(new java.io.File("res/Characters/Workers/PurpleWorker/FarmerPurple.png")));
            spriteSheets.put("farmerRedSheet", ImageIO.read(new java.io.File("res/Characters/Workers/RedWorker/FarmerRed.png")));
            
            // Load sprite sheets for buildings
            spriteSheets.put("housesSheet", ImageIO.read(new java.io.File("res/Buildings/Wood/Houses.png")));
            spriteSheets.put("hutsSheet", ImageIO.read(new java.io.File("res/Buildings/Wood/Huts.png")));
            spriteSheets.put("towerSheet", ImageIO.read(new java.io.File("res/Buildings/Wood/Tower.png")));
            spriteSheets.put("workshopsSheet", ImageIO.read(new java.io.File("res/Buildings/Wood/Workshops.png")));
            
            // Debug: Print sheet information
            debugSpriteSheets();
            
            // Extract some default sprites for immediate use
            extractDefaultSprites();
            
        } catch (IOException e) {
            System.err.println("Error loading sprites: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void debugSpriteSheets() {
        System.out.println("=== SPRITE SHEET DEBUG INFO ===");
        for (Map.Entry<String, BufferedImage> entry : spriteSheets.entrySet()) {
            BufferedImage sheet = entry.getValue();
            if (sheet != null) {
                int width = sheet.getWidth();
                int height = sheet.getHeight();
                int spritesX = width / SPRITE_SIZE;
                int spritesY = height / SPRITE_SIZE;
                System.out.println(entry.getKey() + ": " + width + "x" + height + 
                                 " pixels, " + spritesX + "x" + spritesY + " sprites");
            } else {
                System.out.println(entry.getKey() + ": NULL (failed to load)");
            }
        }
        System.out.println("===============================");
    }
    
    private static void extractDefaultSprites() {
        // Extract some default sprites from sheets - trying different positions
        sprites.put("grass", extractSprite(spriteSheets.get("grassSheet"), 1, 0)); // Try position (1,0) instead of (0,0)
        sprites.put("deadGrass", extractSprite(spriteSheets.get("deadGrassSheet"), 0, 0));
        sprites.put("cliff", extractSprite(spriteSheets.get("cliffSheet"), 3, 0));
        sprites.put("shore", extractSprite(spriteSheets.get("shoreSheet"), 0, 0));
        sprites.put("winter", extractSprite(spriteSheets.get("winterSheet"), 0, 0));
        
        sprites.put("tree", extractSprite(spriteSheets.get("treesSheet"), 1, 0));
        sprites.put("rock", extractSprite(spriteSheets.get("rocksSheet"), 0, 0));
        sprites.put("cactus", extractSprite(spriteSheets.get("cactusSheet"), 0, 0));
        sprites.put("wheat", extractSprite(spriteSheets.get("wheatSheet"), 0, 0));
        
        sprites.put("sheep", extractSprite(spriteSheets.get("sheepSheet"), 0, 0));
        sprites.put("hornedSheep", extractSprite(spriteSheets.get("hornedSheepSheet"), 0, 0));
        sprites.put("chicken", extractSprite(spriteSheets.get("chickenSheet"), 0, 0));
        sprites.put("pig", extractSprite(spriteSheets.get("pigSheet"), 0, 0));
        sprites.put("boar", extractSprite(spriteSheets.get("boarSheet"), 0, 0));
        
        sprites.put("farmerCyan", extractSprite(spriteSheets.get("farmerCyanSheet"), 0, 0));
        sprites.put("farmerLime", extractSprite(spriteSheets.get("farmerLimeSheet"), 0, 0));
        sprites.put("farmerPurple", extractSprite(spriteSheets.get("farmerPurpleSheet"), 0, 0));
        sprites.put("farmerRed", extractSprite(spriteSheets.get("farmerRedSheet"), 0, 0));
        
        sprites.put("house", extractSprite(spriteSheets.get("housesSheet"), 0, 0));
        sprites.put("hut", extractSprite(spriteSheets.get("hutsSheet"), 0, 0));
        sprites.put("tower", extractSprite(spriteSheets.get("towerSheet"), 0, 0));
        sprites.put("workshop", extractSprite(spriteSheets.get("workshopsSheet"), 0, 0));
        
        // Debug: Check what we extracted
        System.out.println("=== EXTRACTED SPRITES ===");
        for (Map.Entry<String, BufferedImage> entry : sprites.entrySet()) {
            BufferedImage sprite = entry.getValue();
            if (sprite != null) {
                System.out.println(entry.getKey() + ": " + sprite.getWidth() + "x" + sprite.getHeight());
            } else {
                System.out.println(entry.getKey() + ": NULL (extraction failed)");
            }
        }
        System.out.println("=========================");
    }
    
    /**
     * Extract a single sprite from a sprite sheet at the specified grid position
     */
    private static BufferedImage extractSprite(BufferedImage sheet, int gridX, int gridY) {
        try {
            if (sheet == null) return null;
            
            int x = gridX * SPRITE_SIZE;
            int y = gridY * SPRITE_SIZE;
            
            // Make sure we don't go out of bounds
            if (x + SPRITE_SIZE > sheet.getWidth() || y + SPRITE_SIZE > sheet.getHeight()) {
                System.err.println("Sprite position out of bounds: " + gridX + "," + gridY + " in sheet");
                return null;
            }
            
            return sheet.getSubimage(x, y, SPRITE_SIZE, SPRITE_SIZE);
        } catch (Exception e) {
            System.err.println("Error extracting sprite at " + gridX + "," + gridY + ": " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get a random sprite from a sprite sheet
     */
    public static BufferedImage getRandomSpriteFromSheet(String sheetName) {
        BufferedImage sheet = spriteSheets.get(sheetName);
        if (sheet == null) return null;
        
        int maxX = sheet.getWidth() / SPRITE_SIZE;
        int maxY = sheet.getHeight() / SPRITE_SIZE;
        
        int gridX = random.nextInt(maxX);
        int gridY = random.nextInt(maxY);
        
        return extractSprite(sheet, gridX, gridY);
    }
    
    public static BufferedImage getSprite(String name) {
        return sprites.get(name);
    }
    
    public static BufferedImage getRandomSprite(String category) {
        switch (category) {
            case "ground":
                return getRandomSpriteFromSheet("grassSheet");
            case "tree":
                return getRandomSpriteFromSheet("treesSheet");
            case "animal":
                String[] animalSheets = {"sheepSheet", "hornedSheepSheet", "chickenSheet", "pigSheet", "boarSheet"};
                String randomSheet = animalSheets[random.nextInt(animalSheets.length)];
                return getRandomSpriteFromSheet(randomSheet);
            case "character":
                String[] characterSheets = {"farmerCyanSheet", "farmerLimeSheet", "farmerPurpleSheet", "farmerRedSheet"};
                String randomCharSheet = characterSheets[random.nextInt(characterSheets.length)];
                return getRandomSpriteFromSheet(randomCharSheet);
            case "building":
                String[] buildingSheets = {"housesSheet", "hutsSheet", "towerSheet", "workshopsSheet"};
                String randomBuildingSheet = buildingSheets[random.nextInt(buildingSheets.length)];
                return getRandomSpriteFromSheet(randomBuildingSheet);
            default:
                return sprites.get("grass");
        }
    }
    
    public static void drawSprite(Graphics2D g, String spriteName, int x, int y, int size) {
        BufferedImage sprite = sprites.get(spriteName);
        if (sprite != null) {
            g.drawImage(sprite, x, y, size, size, null);
        }
    }
    
    public static void drawSprite(Graphics2D g, BufferedImage sprite, int x, int y, int size) {
        if (sprite != null) {
            // Enable alpha blending for transparency
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g.drawImage(sprite, x, y, size, size, null);
        }
    }
    
    /**
     * Helper method to test different sprite positions
     * Call this to see what sprite is at a specific position
     */
    public static BufferedImage testSpritePosition(String sheetName, int gridX, int gridY) {
        System.out.println("Testing sprite at position (" + gridX + "," + gridY + ") in " + sheetName);
        return extractSprite(spriteSheets.get(sheetName), gridX, gridY);
    }
    
    /**
     * Set a specific sprite from a specific position in a sheet
     */
    public static void setSpriteFromPosition(String spriteName, String sheetName, int gridX, int gridY) {
        BufferedImage sprite = extractSprite(spriteSheets.get(sheetName), gridX, gridY);
        if (sprite != null) {
            sprites.put(spriteName, sprite);
            System.out.println("Set " + spriteName + " to sprite at (" + gridX + "," + gridY + ") in " + sheetName);
        } else {
            System.out.println("Failed to set " + spriteName + " - sprite extraction failed");
        }
    }
}
