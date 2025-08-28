import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SimCharacter {
    String name;
    int x, y;
    String gender;
    Random random = new Random();
    int hunger;
    int health;
    int social;
    Map<SimCharacter, Integer> relationships;
    java.util.List<String> thoughts = new java.util.ArrayList<>();
    private BufferedImage sprite;

    public SimCharacter(int x, int y) {
        this.name = generateName();
        this.x = x;
        this.y = y;
        this.gender = random.nextBoolean() ? "Male" : "Female";
        this.hunger = 0;
        this.health = 100;
        this.social = 0;
        this.relationships = new HashMap<>();
        setRandomSprite();
    }

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

    public void eat(World world) {
        if (world.tiles[x][y].type.equals("food")) {
            world.tiles[x][y].type = "grass";
            hunger = Math.max(0, hunger - 40);
          //  System.out.println("DEBUG: " + name + " ate food at (" + x + "," + y + ")"+"\n DELETE FOOD TILE ACIVATED");
        }
    }


        //REFACTOR THIS!!! CHARACTERS CAN MOVE TWICE IN ONE TICK || SEPERATE ALL BEHAVIOR LOGIC INTO ITS OWN CLASS
    public void act(World world) {
        //Thought randomly
        if(random.nextDouble()<0.05) {
            addThought(getRandomThought());
        }
        // If standing on food, eat if hunger is at least 50
        if (world.tiles[x][y].type.equals("food") && hunger >= 50) {
            eat(world);
        }
        // If hunger < 50, move randomly
        if (hunger < 50) {
            moveRandom(world.width, world.height, world);
        } else {
            // Try to move toward nearest food tile within radius 10
            int[] foodTarget = findNearestFood(world, 10);
            if (foodTarget != null) {
                moveToward(foodTarget[0], foodTarget[1], world.width, world.height, world);
            } else {
                moveRandom(world.width, world.height, world);
            }
        }
        // After moving, increase hunger
        hunger++;

        //Temporary social increase for testing
        social++;

        // If hunger > 80, decrease health
        if (hunger > 80) {
            health--;
        }

        //Social needs
        if(social >= 45){
            SimCharacter other = findNearestCharacter(world, 5);
            if(other != null){
                moveToward(other.x,other.y,world.width, world.height, world);
        }else{
             // No one nearby, random move already called above
            }
        }
    }

    public void moveRandom(int maxX, int maxY, World world) {
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

    private void moveToward(int tx, int ty, int maxX, int maxY, World world) {
        int dx = Integer.compare(tx, x);
        int dy = Integer.compare(ty, y);
        int nx = Math.max(0, Math.min(maxX - 1, x + dx));
        int ny = Math.max(0, Math.min(maxY - 1, y + dy));
        // Avoid moving into water or mountain
        if (!world.tiles[nx][ny].type.equals("water") && !world.tiles[nx][ny].type.equals("mountain")) {
            x = nx;
            y = ny;
        } else {
            // fallback to random move if blocked
            moveRandom(maxX, maxY, world);
        }
    }

    private int[] findNearestFood(World world, int radius) {
        int bestDist = Integer.MAX_VALUE;
        int fx = -1, fy = -1;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < world.width && ny >= 0 && ny < world.height) {
                    if (world.tiles[nx][ny].type.equals("food")) {
                        int dist = Math.abs(dx) + Math.abs(dy);
                        if (dist < bestDist) {
                            bestDist = dist;
                            fx = nx;
                            fy = ny;
                        }
                    }
                }
            }
        }
        if (fx != -1 && fy != -1) {
            return new int[]{fx, fy};
        }
        return null;
    }

    public SimCharacter findNearestCharacter(World world, int radius) {
    SimCharacter nearest = null;
    int nearestDist = Integer.MAX_VALUE;

    for (SimCharacter other : world.characters) {
        if (other == this) continue; // don’t chase yourself

        int dx = other.x - this.x;
        int dy = other.y - this.y;
        int dist = Math.abs(dx) + Math.abs(dy); // Manhattan distance (works fine for grids)

        if (dist < nearestDist && dist <= radius) {
            nearestDist = dist;
            nearest = other;
        }
    }

    return nearest;
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
