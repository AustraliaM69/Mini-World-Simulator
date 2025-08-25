# Enhanced Mini World Simulator

This is an enhanced version of your mini world simulator that now uses actual sprites and graphics instead of simple colored rectangles.

## New Features

### 🎨 Sprite-Based Graphics
- **Ground Tiles**: Grass, textured grass, dead grass, cliffs, shores, and winter tiles
- **Nature Elements**: Trees, pine trees, dead trees, rocks, cactus, and wheat fields
- **Animals**: Sheep, horned sheep, chickens, pigs, and boars that roam around the world
- **Characters**: Different colored farmer sprites (Cyan, Lime, Purple, Red)
- **Buildings**: Houses, huts, towers, and workshops scattered throughout the world

### 🐾 Animals
- Animals now roam around the world independently
- Each animal has a name and type
- Animals move less frequently than characters for more realistic behavior
- Click on animals to see their information

### 🏗️ Enhanced World Generation
- More diverse terrain with trees, rocks, and buildings
- Better distribution of different tile types
- Animals spawn only on grass tiles

### 🖱️ Improved Interaction
- Hover over tiles to see detailed information about characters and animals
- Click on characters to see their thoughts and memories
- Click on animals to see their details
- Tooltips show both character and animal information

## How to Run

1. Make sure you have Java installed on your system
2. Compile all Java files: `javac *.java`
3. Run the application: `java WorldSimApp`

## File Structure

- `SpriteManager.java` - Manages loading and accessing all sprite images
- `Animal.java` - Represents animals in the world
- `Tile.java` - Enhanced to support sprite-based rendering
- `SimCharacter.java` - Updated to use character sprites
- `World.java` - Enhanced world generation with animals
- `WorldPanel.java` - Updated rendering to use sprites
- `SimFrame.java` - Main application frame
- `WorldSimApp.java` - Application entry point

## Sprite Categories Used

### Ground Tiles
- Grass.png, TexturedGrass.png, DeadGrass.png
- Cliff.png, Shore.png, Winter.png

### Nature
- Trees.png, PineTrees.png, DeadTrees.png
- Rocks.png, Cactus.png, Wheatfield.png

### Animals
- Sheep.png, HornedSheep.png, Chicken.png
- Pig.png, Boar.png

### Characters
- FarmerCyan.png, FarmerLime.png, FarmerPurple.png, FarmerRed.png

### Buildings
- Houses.png, Huts.png, Tower.png, Workshops.png

## Controls

- **Speed Up**: Increases simulation speed
- **Pause**: Pauses the simulation
- **Reset**: Resets simulation speed and resumes if paused
- **Mouse Hover**: Shows tooltips with tile and entity information
- **Mouse Click**: Shows detailed information about characters and animals

The simulator now provides a much more visually appealing and engaging experience with actual graphics instead of simple colored shapes!
