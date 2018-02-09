import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static boolean running = true;
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 9099);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
            Thread clientWriter = new Thread(new ClientChatPrinter(socket));
            clientWriter.start();
            Scanner scanner = new Scanner(System.in);
            while (running) {
                String msg = scanner.nextLine();
                if (msg.equals("/quit")) {
                    running = false;
                    break;
                }
                dos.writeUTF(msg);
                dos.flush();
            }
            clientWriter.join();
        } catch (IOException|InterruptedException x) {
            System.err.format("IOException: %s%n", x);
            running = false;
        }
    }
}
