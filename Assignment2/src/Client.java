import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 9099);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {

            Thread clientWriter = new Thread(new ClientChatPrinter(dis));
            clientWriter.start();
            Scanner scanner = new Scanner(System.in);
            String mes = "";

            while (!"q".equals(mes) && clientWriter.isAlive()) {
                mes = scanner.nextLine();
                dos.writeUTF(mes);
                dos.flush();
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
