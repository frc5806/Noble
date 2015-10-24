import java.net.*;
import java.util.*;

public class ListenerThread implements Runnable {
    public static final int PORT = 2000;

    ServerSocket listeningSocket = null;
    ArrayList<Thread> robotThreads;
    
    /*
     * The entry point of this thread.
     * Listens for connections on the specified port.
     * Once a connection is detected, a RobotThread is started with that connection and a joystick.
     * If there is no open joystick, nothing is done with connection.
     */
    public void run() {
        robotThreads = new ArrayList<Thread>();

        try {
            listeningSocket = new ServerSocket(PORT);
        } catch(Exception e) {
            System.out.println("Could not establish socket at port " + PORT);
            System.exit(1);
        }

        while(!Thread.currentThread().isInterrupted()) {
            Socket newConnection = null;
            try {
                newConnection = listeningSocket.accept();
            } catch(Exception e) {
                System.out.println("Error accepting connection");
                continue;
            }

            robotThreads.add(new Thread(new RobotThread(newConnection)));
            robotThreads.get(robotThreads.size()-1).start();
        }
        
        /// Shut down threads and close sockets
        /// Only reached once this thread is interrupted
        for(Thread robotThread : robotThreads) {
            try {
                robotThread.interrupt();
            } catch(Exception e) {
                System.out.println("Error shutting down RobotThread");
            }
        }
        try {
            listeningSocket.close();
        } catch(Exception e) {
            System.out.println("Error shutting down ListenThread");
        }
    }
}
