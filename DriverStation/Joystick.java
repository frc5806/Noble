import net.java.games.input.*;

// Represents a Joystick. Used for querying a desired motor value.
public class Joystick
{
    Controller controller;
    
    // Initializes the joystick with a Controller object
    public Joystick(net.java.games.input.Controller controller_) {
        controller = controller_;
    }
    
    // Gets a motor value from the Joystick
    public int getMotor() {
        Component[] components = controller.getComponents();
        for(Component component : components) {
            if(component.isAnalog() && component.getIdentifier() == Component.Identifier.Axis.X) {
                return (int)component.getPollData()*100;
            }
        }
        
        return 0;
    }
}
