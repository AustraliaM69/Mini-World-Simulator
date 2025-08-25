import java.awt.*;

public class Tile {
    String type;
    public Tile(String type) {
        this.type = type;
    }
    public Color getColor() {
        return switch (type) {
            case "grass" -> Color.GREEN;
            case "water" -> Color.CYAN;
            case "mountain" -> Color.GRAY;
            case "food" -> new Color(210, 140, 60); // Brown/orange for food
            default -> Color.BLACK;
        };
    }
}
