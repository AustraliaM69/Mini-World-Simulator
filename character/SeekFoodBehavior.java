package character;

import world.World;

public class SeekFoodBehavior implements Behavior {
    
    @Override
    public boolean execute(SimCharacter character, World world) {
        // Try to move toward nearest food tile within radius 10
        int[] foodTarget = findNearestFood(character, world, 10);
        if (foodTarget != null) {
            return character.moveToward(foodTarget[0], foodTarget[1], world.getWidth(), world.getHeight(), world);
        }
        return false; // No food found, didn't move
    }
    
    @Override
    public boolean shouldExecute(SimCharacter character) {
        return character.getHunger() >= 50; // Only seek food if hungry enough
    }
    
    private int[] findNearestFood(SimCharacter character, World world, int radius) {
        int bestDist = Integer.MAX_VALUE;
        int fx = -1, fy = -1;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                int nx = character.getX() + dx;
                int ny = character.getY() + dy;
                if (nx >= 0 && nx < world.getWidth() && ny >= 0 && ny < world.getHeight()) {
                    if (world.getTiles()[nx][ny].getType().equals("food")) {
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
}
