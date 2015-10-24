import java.util.*;

/**
 * A command line menu for the user.
 * Entry point into the program.
 * Allows the user to stop the program and shut down all sockets.
 */
public class Menu {
    // Main function. Entry point into program.
    public static void main(String[] args) {
        System.out.println("\u000c");
        System.out.println("Welcomd to the B and N Drive System!");
        
        Thread listenerThread = new Thread(new ListenerThread());
        listenerThread.start();
        
        System.out.println("Write something here when you want to shut down the program:");
        new Scanner(System.in).nextLine();
        
        listenerThread.interrupt();
    }
}
