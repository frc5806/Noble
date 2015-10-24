import java.net.*;
import java.io.*;

public class RobotThread implements Runnable
{
    Socket socket;
    OutputStream outStream;
    InputStream inStream;
    
    public RobotThread(Socket socket_) {
        this.socket = socket;
    }
    
    public void run() {
        
    }
}
