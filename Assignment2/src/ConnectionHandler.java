import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionHandler implements Runnable {
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(9099)) {
            while (Server.running) {
                ClientHandler handler = new ClientHandler(serverSocket.accept());
                synchronized (Server.requestHandlers) {
                    Server.requestHandlers.add(handler);
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            Server.running = false;
        }
    }
}
