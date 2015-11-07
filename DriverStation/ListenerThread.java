import java.net.*;
import java.util.*;
import java.util.function.*;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class ListenerThread implements Runnable {
    public static final int PORT = 3000;

    ServerSocket listeningSocket = null;
    ArrayList<Thread> robotThreads;
    JoystickHandler jHandler;

    /*
     * The entry point of this thread. Listens for connections on the specified
     * port. Once a connection is detected, a RobotThread is started with that
     * connection and a joystick. If there is no open joystick, nothing is done
     * with connection.
       */
    public void run() {
        robotThreads = new ArrayList<Thread>();

        jHandler = new JoystickHandler();
        jHandler.start();
        
        test();

        try {
            listeningSocket = new ServerSocket(PORT);
        } catch (Exception e) {
            System.out.println("Could not establish socket at port " + PORT);
            e.printStackTrace();
            System.exit(1);
        }

        while (!Thread.currentThread().isInterrupted()) {
            Socket newConnection = null;
            try {
                System.out.println("Starting");
                newConnection = listeningSocket.accept();
                System.out.println("Connected");
            } catch (Exception e) {
                System.out.println("Error accepting connection");
                continue;
            }
            
            try {
            	newConnection.setSoTimeout(3000);
            } catch(Exception e) {
            	e.printStackTrace();
            	continue;
            }

            Joystick j = jHandler.getOpenJoystick();
            if (j != null) {
                Consumer<RobotThread> callback = (RobotThread rThread) -> jHandler
                        .joystickWasDisconnected(rThread.joystick);
                robotThreads.add(new Thread(new RobotThread(newConnection, j, callback)));
                robotThreads.get(robotThreads.size() - 1).start();
            } else {
            	System.out.print("\n\n\nNO JOYSTICK AVAILABLE\n\n\n");
                try {
                    newConnection.close();
                } catch (Exception e) {
                	e.printStackTrace();
                }
            }
        }

        System.out.println("Stop");

        /// Shut down threads and close sockets
        /// Only reached once this thread is interrupted
        for (Thread robotThread : robotThreads) {
            try {
                robotThread.interrupt();
            } catch (Exception e) {
                System.out.println("Error shutting down RobotThread");
            }
        }
        try {
            listeningSocket.close();
        } catch (Exception e) {
            System.out.println("Error shutting down ListenThread");
        }
    }
    
    public void test() {
    	Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (Controller controller : controllers) {
            
            if(controller.getType() == Controller.Type.STICK) {
            	System.out.println("c: " + controller.getName() + "; " + controller.getPortNumber());
            }
        }
    }
    
    public void close() {
        try {
            listeningSocket.close();
        } catch (Exception e) {
        }
        Thread.currentThread().interrupt();
    }
}
