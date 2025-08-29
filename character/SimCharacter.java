package character;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import utility.SpriteManager;
import world.World;

public class SimCharacter {
    String name;
    int x, y;
    String gender;
    int hunger;
    int health;
    int social;

    boolean isMarried = false;
    SimCharacter spouse = null;

    public Map<SimCharacter, Integer> relationships;
    java.util.List<String> thoughts = new java.util.ArrayList<>();

    private BufferedImage sprite;
    private BehaviorManager behaviorManager;

    Random random = new Random();

    public SimCharacter(int x, int y) {
        this.name = generateName();
        this.x = x;
        this.y = y;
        this.gender = random.nextBoolean() ? "Male" : "Female";
        this.hunger = 0;
        this.health = 100;
        this.social = 0;
        this.relationships = new HashMap<>();
        this.behaviorManager = new BehaviorManager();
        setRandomSprite();
    }

    // Getter methods for cross-package access
    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public String getGender() { return gender; }
    public int getHunger() { return hunger; }
    public int getHealth() { return health; }
    public int getSocial() { return social; }
    public java.util.List<String> getThoughts() { return thoughts; }
    public Random getRandom() { return random; }
    public boolean isMarried() { return isMarried; }
    public SimCharacter getSpouse() { return spouse; }
    
    // Setter methods for cross-package access
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setHunger(int hunger) { this.hunger = hunger; }
    public void setHealth(int health) { this.health = health; }
    public void setSocial(int social) { this.social = social; }

    public void addThought(String thought) {
        if (thoughts.size() > 20) thoughts.remove(0);
        thoughts.add(thought);
    }
    public void addEventThought(String event) {
        if (thoughts.size() > 20) thoughts.remove(0);
        thoughts.add(event);
    }

    public String getRandomThought() {
        String[] randoms = {"I wonder what's for dinner...", "The grass is nice today.", "I feel lucky!", "Is it going to rain?", "I hope I meet someone new."};
        if (random.nextDouble() < 0.3) {
            return randoms[random.nextInt(randoms.length)];
        }
        if (!thoughts.isEmpty()) {
            return thoughts.get(random.nextInt(thoughts.size()));
        }
        return "Just thinking...";
    }

    public void initRelationships(java.util.List<SimCharacter> allCharacters) {
        for (SimCharacter c : allCharacters) {
            if (c != this) {
                relationships.put(c, 0);
            }
        }
    }

    public boolean isAlive() {
        return health > 0;
    }




    public void act(World world) {
        // Use the behavior manager to handle all character actions
        behaviorManager.act(this, world);
    }

    public boolean moveRandom(int maxX, int maxY, World world) {
        int dx = random.nextInt(3) - 1;
        int dy = random.nextInt(3) - 1;
        int nx = Math.max(0, Math.min(maxX - 1, x + dx));
        int ny = Math.max(0, Math.min(maxY - 1, y + dy));
        // Don't move into water or mountain
        if (!world.getTiles()[nx][ny].getType().equals("water") && !world.getTiles()[nx][ny].getType().equals("mountain")) {
            x = nx;
            y = ny;
            return true; // Successfully moved
        }
        return false; // Couldn't move
    }

    public boolean moveToward(int tx, int ty, int maxX, int maxY, World world) {
        int dx = Integer.compare(tx, x);
        int dy = Integer.compare(ty, y);
        int nx = Math.max(0, Math.min(maxX - 1, x + dx));
        int ny = Math.max(0, Math.min(maxY - 1, y + dy));
        // Avoid moving into water or mountain
        if (!world.getTiles()[nx][ny].getType().equals("water") && !world.getTiles()[nx][ny].getType().equals("mountain")) {
            x = nx;
            y = ny;
            return true; // Successfully moved
        } else {
            // fallback to random move if blocked
            return moveRandom(maxX, maxY, world);
        }
    }



    public SimCharacter findNearestCharacter(World world, int radius) {
    SimCharacter nearest = null;
    int nearestDist = Integer.MAX_VALUE;

    for (SimCharacter other : world.getCharacters()) {
        if (other == this) continue; // don't chase yourself

        int dx = other.getX() - this.x;
        int dy = other.getY() - this.y;
        int dist = Math.abs(dx) + Math.abs(dy); // Manhattan distance (works fine for grids)

        if (dist < nearestDist && dist <= radius) {
            nearestDist = dist;
            nearest = other;
        }
    }

    return nearest;
}

    public void marry(SimCharacter other){
        this.isMarried = true;
        this.spouse = other;
        other.isMarried = true;
        other.spouse = this;
    }
    
    private void setRandomSprite() {
        sprite = SpriteManager.getRandomSprite("character");
    }
    
    public BufferedImage getSprite() {
        return sprite;
    }
        private String generateName() {
        // Placeholder for name generation logic
        try{
        List<String> names = Files.readAllLines(Paths.get("names.txt"));
        String name = names.get(random.nextInt(names.size()));
        return name;
    } catch (IOException e) {
        e.printStackTrace();
        return "Unknown";
    }
    }
}
