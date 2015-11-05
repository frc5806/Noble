import java.net.*;
import java.io.*;
import java.util.function.*;

public class RobotThread implements Runnable {
    private Socket socket;
    public Joystick joystick;
    private Consumer<RobotThread> callback;

    // Initializes a RobotThread with a joystick and a socket
    public RobotThread(Socket socket_, Joystick joystick_, Consumer<RobotThread> callback_) {
        this.socket = socket_;
        this.joystick = joystick_;
        this.callback = callback_;
    }

    // Continuously sends the motor values that it's joystick detects
    public void run() {
        System.out.println("Started thread");
        DataOutputStream out;
        DataInputStream in;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("RobotThread could not get socket's streams");
            callback.accept(this);
            return;
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                out.writeInt(joystick.getMotor());
                System.out.println("Got something");
                in.readInt();
            } catch (Exception e) {
                break;
            }
        }

        callback.accept(this);
    }
}
