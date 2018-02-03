import java.io.DataInputStream;
import java.io.IOException;

public class ClientChatPrinter implements Runnable {
    private final DataInputStream dis;

    public ClientChatPrinter(DataInputStream dataInputStream) {
        dis = dataInputStream;
    }

    @Override
    public void run() {
        try {
            String mes = "";
            while (!"q".equals(mes)) {
                mes = dis.readUTF();
                System.out.println(mes);
            }
            dis.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}