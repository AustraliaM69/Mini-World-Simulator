import java.awt.image.BufferedImage;
import java.util.Random;

public class Animal {
    String name;
    String type;
    int x, y;
    Random random = new Random();
    private BufferedImage sprite;
    
    public Animal(String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.name = generateName();
        setSpriteForType();
    }
    
    private void setSpriteForType() {
        switch (type) {
            case "sheep":
                sprite = SpriteManager.getSprite("sheep");
                break;
            case "hornedSheep":
                sprite = SpriteManager.getSprite("hornedSheep");
                break;
            case "chicken":
                sprite = SpriteManager.getSprite("chicken");
                break;
            case "pig":
                sprite = SpriteManager.getSprite("pig");
                break;
            case "boar":
                sprite = SpriteManager.getSprite("boar");
                break;
            default:
                sprite = SpriteManager.getRandomSprite("animal");
                break;
        }
    }
    
    private String generateName() {
        String[] animalNames = {"Fluffy", "Spot", "Buddy", "Max", "Luna", "Shadow", "Mittens", "Whiskers"};
        return animalNames[random.nextInt(animalNames.length)];
    }
    
    public void moveRandom(int maxX, int maxY, World world) {
        // Animals move less frequently than characters
        if (random.nextDouble() < 0.3) {
            int dx = random.nextInt(3) - 1;
            int dy = random.nextInt(3) - 1;
            int nx = Math.max(0, Math.min(maxX - 1, x + dx));
            int ny = Math.max(0, Math.min(maxY - 1, y + dy));
            // Don't move into water or mountain
            if (!world.tiles[nx][ny].type.equals("water") && !world.tiles[nx][ny].type.equals("mountain")) {
                x = nx;
                y = ny;
            }
        }
    }
    
    public BufferedImage getSprite() {
        return sprite;
    }
    
    public String getType() {
        return type;
    }
}
