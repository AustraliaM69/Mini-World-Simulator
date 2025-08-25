import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class World {
    int width, height;
    Tile[][] tiles;
    java.util.List<SimCharacter> characters;
    Random random = new Random();

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        characters = new java.util.ArrayList<>();

        generateTiles();
        spawnCharacters(10);
    }

    private void generateTiles() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = random.nextDouble();
                if (r < 0.1) tiles[x][y] = new Tile("water");
                else if (r < 0.2) tiles[x][y] = new Tile("mountain");
                else if (r < 0.25) tiles[x][y] = new Tile("food"); // 5% food
                else tiles[x][y] = new Tile("grass");
            }
        }
    }

    private void spawnCharacters(int count) {
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            try {
                characters.add(new SimCharacter(generateName(), x, y));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // Initialize relationships
        for (SimCharacter c : characters) {
            for (SimCharacter other : characters) {
                if (c != other) c.relationships.put(other, 0);
            }
        }
    }

    private String generateName() throws IOException {
        // Placeholder for name generation logic
        List<String> names = Files.readAllLines(Paths.get("names.txt"));
        String name = names.get(random.nextInt(names.size()));
        return name;
    }

    public void tick() {
        // Move characters
        for (SimCharacter c : characters) {
            c.moveRandom(width, height, this);
        }
        // Check for meetings and update relationships
        for (SimCharacter c1 : characters) {
            for (SimCharacter c2 : characters) {
                if (c1 != c2 && c1.x == c2.x && c1.y == c2.y) {
                    int rel = c1.relationships.getOrDefault(c2, 0);
                    c1.relationships.put(c2, rel + 1);
                    c1.addThought("Met " + c2.name + " (" + c2.gender + ")");
                }
            }
        }
        // Check for food eaten and random thoughts
        for (SimCharacter c : characters) {
            if (tiles[c.x][c.y].type.equals("grass") && random.nextDouble() < 0.1) {
                c.addThought(c.getRandomThought());
            }
            if (tiles[c.x][c.y].type.equals("food")) {
                c.addThought("Ate food at (" + c.x + "," + c.y + ")");
            }
        }
    }
}
