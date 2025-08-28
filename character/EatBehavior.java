package character;

import world.World;

public class EatBehavior implements Behavior {
    
    @Override
    public boolean execute(SimCharacter character, World world) {
        // Check if character is standing on food and is hungry enough
        if (world.getTiles()[character.getX()][character.getY()].getType().equals("food") && character.getHunger() >= 50) {
            // Eat the food
            world.getTiles()[character.getX()][character.getY()].setType("grass");
            character.setHunger(Math.max(0, character.getHunger() - 40));
            character.addThought("Yum! That food was delicious!");
            return false; // Eating doesn't count as movement
        }
        return false;
    }
    
    @Override
    public boolean shouldExecute(SimCharacter character) {
        return character.getHunger() >= 50; // Only eat if hungry enough
    }
}
