package character;

import world.World;

public interface Behavior {
    /**
     * Execute this behavior for the given character in the given world.
     * @param character The character to act on
     * @param world The world the character is in
     * @return true if the character moved, false otherwise
     */
    boolean execute(SimCharacter character, World world);
    
    /**
     * Check if this behavior should be executed based on the character's current state.
     * @param character The character to check
     * @return true if this behavior should be executed, false otherwise
     */
    boolean shouldExecute(SimCharacter character);
}
