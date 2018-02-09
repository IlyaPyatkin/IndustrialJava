import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientChatPrinter implements Runnable {
    private final Socket socket;

    public ClientChatPrinter(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            while (Client.running) {
                if(dis.available() > 0)
                    System.out.println(dis.readUTF());
                Thread.sleep(200);
            }
        } catch (IOException|InterruptedException x) {
            System.err.format("IOException: %s%n", x);
            Client.running = false;
        }
    }
}