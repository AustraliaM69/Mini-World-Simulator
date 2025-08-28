package character;

import world.World;

public class SocialBehavior implements Behavior {
    
    @Override
    public boolean execute(SimCharacter character, World world) {
        // Find nearest character within radius 5
        SimCharacter other = character.findNearestCharacter(world, 5);
        if (other != null) {
            return character.moveToward(other.getX(), other.getY(), world.getWidth(), world.getHeight(), world);
        }
        return false; // No one nearby, didn't move
    }
    
    @Override
    public boolean shouldExecute(SimCharacter character) {
        return character.getSocial() >= 45; // Only seek social contact if social need is high
    }
}
