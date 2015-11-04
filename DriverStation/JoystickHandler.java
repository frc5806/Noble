import java.util.*;
import net.java.games.input.*;

// Responsible for finding an open joystick
public class JoystickHandler {
	ArrayList<Integer> takenPorts = new ArrayList<Integer>();

	// Gets a joystick that has not been returned by this method already (unless
	// it was disconnected and then plugged back in again)
	// If there are no available joysticks, null is returned
	public Joystick getOpenJoystick() {
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

		for (Controller controller : controllers) {
			boolean alreadyTaken = false;

			for (Integer port : takenPorts) {
				if (port == controller.getPortNumber() && controller.getType() == Controller.Type.GAMEPAD) {
					alreadyTaken = true;
				}
			}

			if (alreadyTaken == false) {
				takenPorts.add(controller.getPortNumber());
				return new Joystick(controller);
			}
		}

		/// Could not find an open controller
		return null;
	}

	// Removes the name of a disconnected joystick from namesAlreadyTaken
	public void joystickWasDisconnected(Joystick joystick) {
		takenPorts.remove(new Integer(joystick.controller.getPortNumber()));
	}
}
