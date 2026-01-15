package character;

import world.World;
import java.util.*;

public class BehaviorManager {
    private final List<Behavior> behaviors;
    
    public BehaviorManager() {
        behaviors = new ArrayList<>();
        // Add behaviors in priority order (highest priority first)
        behaviors.add(new EatBehavior());        // Eating doesn't count as movement
        behaviors.add(new SeekFoodBehavior());   // Hunger is top priority
        behaviors.add(new SocialBehavior());     // Social needs second
        behaviors.add(new RandomMoveBehavior()); // Random movement as fallback
    }
    
    /**
     * Execute the highest priority behavior that should be executed.
     * Ensures the character only moves once per tick.
     * @param character The character to act on
     * @param world The world the character is in
     */
    public void act(SimCharacter character, World world) {
        boolean actionTaken = false;
        
        // Execute behaviors in priority order until one succeeds
        for (Behavior behavior : behaviors) {
            if (behavior.shouldExecute(character)) {
                boolean moved = behavior.execute(character, world);
                if (moved) {
                    // Character moved, don't execute any more behaviors
                    actionTaken = true;
                    break;
                }
            }
        }
        
        // If no movement behavior was executed, ensure random movement happens
        if (!actionTaken) {
            // Find and execute the fallback behavior (RandomMoveBehavior)
            for (Behavior behavior : behaviors) {
                if (behavior instanceof RandomMoveBehavior) {
                    behavior.execute(character, world);
                    break;
                }
            }
        }
        
        // Update character stats
        character.setHunger(character.getHunger() + 1);
        character.setSocial(character.getSocial() + 1);
        
        // Handle health effects
        if (character.getHunger() > 100) {
            character.setHealth(character.getHealth() - 1);
        }
        
        // Random thoughts
        if (character.getRandom().nextDouble() < 0.05) {
            character.addThought(character.getRandomThought());
        }
    }
}
