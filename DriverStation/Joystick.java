import net.java.games.input.*;

// Represents a Joystick. Used for querying a desired motor value.
public class Joystick {
    Controller controller;

    // Initializes the joystick with a Controller object
    public Joystick(Controller controller_) {
        controller = controller_;
    }

    // Gets a motor value from the Joystick
    public int[] getMotor() {
        controller.poll();
        Component[] components = controller.getComponents();
        int x = -1000;
        int y = -1000;
        for (Component component : components) {
            if (component.isAnalog() && component.getIdentifier() == Component.Identifier.Axis.Y) {
            	y = (int)((double)-component.getPollData()*100);
            }
            if (component.isAnalog() && component.getIdentifier() == Component.Identifier.Axis.X) {
            	x = (int)((double)-component.getPollData()*100);
            }
        }
        
        int drivePower = (((Math.abs(y) > 10)) ? y : 0)*100/128;
        int turnPower = (((Math.abs(x) > 10)) ? x : 0)*100/128;
        
        int left = (int)bound(drivePower + turnPower, -100, 100);
        int right = (int)bound(drivePower-turnPower, -100, 100);
        System.out.println(left+", "+right);
        return new int[]{left, right};
    }
    
    public double bound(double n, double lo, double hi) {
    	return (n < lo) ? lo : (n > hi) ? hi : n;
    }
}
