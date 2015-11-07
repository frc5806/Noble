import java.util.*;
import net.java.games.input.*;

// Responsible for finding an open joystick
public class JoystickHandler {
    ArrayList<Joystick> takenJoysticks = new ArrayList<Joystick>();
    ArrayList<Joystick> availableJoysticks = new ArrayList<Joystick>();
    
    
    public void start() {
    	Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (Controller controller : controllers) {
            System.out.println("c: " + controller.getName() + "; " + controller.getPortNumber());
            if(controller.getType() == Controller.Type.STICK) {
            	availableJoysticks.add(new Joystick(controller));
            }
        }
    	
    }

    // Gets a joystick that has not been returned by this method already (unless
    // it was disconnected and then plugged back in again)
    // If there are no available joysticks, null is returned
    public Joystick getOpenJoystick() {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        if(availableJoysticks.size() <= 0) return null;
        takenJoysticks.add(availableJoysticks.get(0));
        availableJoysticks.remove(0);
        return takenJoysticks.get(takenJoysticks.size()-1);
    }

    // Removes the name of a disconnected joystick from namesAlreadyTaken
    public void joystickWasDisconnected(Joystick joystick) {
        takenJoysticks.remove(joystick);
        availableJoysticks.add(joystick);
    }
}
