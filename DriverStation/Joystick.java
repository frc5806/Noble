import net.java.games.input.*;

// Represents a Joystick. Used for querying a desired motor value.
public class Joystick {
    Controller controller;

    // Initializes the joystick with a Controller object
    public Joystick(Controller controller_) {
        controller = controller_;
    }

    // Gets a motor value from the Joystick
    public int getMotor() {
        controller.poll();
        Component[] components = controller.getComponents();
        for (Component component : components) {
            if (component.isAnalog() && component.getIdentifier() == Component.Identifier.Axis.Y) {
                return (int)(component.getPollData()*100);
            }
        }
        
        return -1;
    }
}
