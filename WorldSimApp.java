
import javax.swing.*;

public class WorldSimApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimFrame(50, 50, 20));
    }
}