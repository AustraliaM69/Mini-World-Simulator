package character;

import world.World;

public class RandomMoveBehavior implements Behavior {
    
    @Override
    public boolean execute(SimCharacter character, World world) {
        boolean moved = character.moveRandom(world.getWidth(), world.getHeight(), world);
        if (!moved) {
            // If random move failed, try again (maybe blocked by terrain)
            moved = character.moveRandom(world.getWidth(), world.getHeight(), world);
        }
        return moved; // Return whether we actually moved
    }
    
    @Override
    public boolean shouldExecute(SimCharacter character) {
        return true; // This is the fallback behavior, always available
    }
}
