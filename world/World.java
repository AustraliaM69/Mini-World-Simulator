package world;
import java.util.*;

import character.Animal;
import character.SimCharacter;

public class World {
    int width, height;
    Tile[][] tiles;
    java.util.List<SimCharacter> characters;
    java.util.List<Animal> animals;
    Random random = new Random();
    List<SimCharacter> pendingSpawns = new ArrayList<>();

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        characters = new java.util.ArrayList<>();
        animals = new java.util.ArrayList<>();
        
        generateTiles();
        initSpawnCharacters(10);
        spawnAnimals(8);
    }

    // Getter methods for cross-package access
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Tile[][] getTiles() { return tiles; }
    public java.util.List<SimCharacter> getCharacters() { return characters; }
    public java.util.List<Animal> getAnimals() { return animals; }

private void generateTiles() {
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            // 70% chance: copy a neighbor’s terrain
            if (x > 0 && y > 0 && random.nextDouble() < 0.7) {
                ArrayList<Tile> neighbors = new ArrayList<>();
                if (x > 0) neighbors.add(tiles[x - 1][y]);     // left
                if (y > 0) neighbors.add(tiles[x][y - 1]);     // top
                if (x > 0 && y > 0) neighbors.add(tiles[x - 1][y - 1]); // top-left
                if (x < width-1 && y > 0) neighbors.add(tiles[x + 1][y - 1]); // top-right

                // Pick a random neighbor and copy its type
                Tile chosen = neighbors.get(random.nextInt(neighbors.size()));
                tiles[x][y] = new Tile(chosen.type);
                
            } else {
                // fallback: normal random roll
                double r = random.nextDouble();
                if (r < 0.08) tiles[x][y] = new Tile("water");
                else if (r < 0.15) tiles[x][y] = new Tile("mountain");
                else if (r < 0.18) tiles[x][y] = new Tile("food");
                else if (r < 0.25) tiles[x][y] = new Tile("tree");
                else if (r < 0.28) tiles[x][y] = new Tile("rock");
                else if (r < 0.30) tiles[x][y] = new Tile("building");
                else tiles[x][y] = new Tile("grass");
            }
        }
    }
}


    private void initSpawnCharacters(int count) {
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

                characters.add(new SimCharacter(x, y));
        }
        // Initialize relationships
        for (SimCharacter c : characters) {
            for (SimCharacter other : characters) {
                if (c != other) c.relationships.put(other, 0);
            }
        }
    }

    //Method to spawn at specific location - Expand to include parenting later
    private void spawnPendingCharacters() {
 
  for (SimCharacter baby : pendingSpawns) {
        characters.add(baby);

        // DEBUG log actual position
        System.out.println("DEBUG: Spawned new character at (" + baby.getX() + ", " + baby.getY() + ") With name: " + baby.getName());

        // Initialize relationships with existing characters
        for (SimCharacter other : characters) {
            if (baby != other) {
                baby.relationships.putIfAbsent(other, 0);
                other.relationships.putIfAbsent(baby, 0);
            }
        }
    }
    pendingSpawns.clear();
}

    public void tick() {
        // Handle pending spawns first
        if(!pendingSpawns.isEmpty()) {
            spawnPendingCharacters();
        }
        // Character actions
        for (SimCharacter c : characters) {
            c.act(this);
        }
        // Move animals
        for (Animal a : animals) {
            a.moveRandom(width, height, this);
        }
        // Check for meetings and update relationships -- Horrible way to do this, but simple for now FIXME
        for (SimCharacter c1 : characters) {
            for (SimCharacter c2 : characters) {
                if (c1 != c2 && c1.getX() == c2.getX() && c1.getY() == c2.getY()) {
                    int rel = c1.relationships.getOrDefault(c2, 0);
                    c1.relationships.put(c2, rel + 1);
                    c1.addThought("Met " + c2.getName() + " (" + c2.getGender() + ") Relationship: " + (rel));
                    c1.setSocial(0); // Reset social on meeting
                    c2.setSocial(0); // Reset social on meeting

                    int r1 = c1.relationships.get(c2);
                    int r2 = c2.relationships.get(c1);
                    
                    boolean oppositeGender = !c1.getGender().equals(c2.getGender());
                    if (oppositeGender && r1 > 1 && r2 > 1) {
                        c1.addThought("I'm attracted to " + c2.getName() + "!");
                        c2.addThought("I'm attracted to " + c1.getName() + "!");

                        pendingSpawns.add(new SimCharacter(c1.getX(), c1.getY()));

                        c1.addEventThought("Had a child with " + c2.getName() + "!");
                        c2.addEventThought("Had a child with " + c1.getName() + "!");

                        //DEBUG 
                        System.out.println(c1.getName() + " and " + c2.getName() + " should have a child!");
                    }
                }
            }
        }
    }
    
    private void spawnAnimals(int count) {
        String[] animalTypes = {"sheep", "hornedSheep", "chicken", "pig", "boar"};
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            // Only spawn on grass tiles
            if (tiles[x][y].getType().equals("grass")) {
                String animalType = animalTypes[random.nextInt(animalTypes.length)];
                animals.add(new Animal(animalType, x, y));
            }
        }
    }
}
