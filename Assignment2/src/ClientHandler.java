import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;

public class ClientHandler {
    private final DataInputStream dis;
    private final DataOutputStream dos;
    public boolean printChats = true;

    public ClientHandler(Socket socket) throws IOException {
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    public String promptJoinRoom(Set<String> rooms) throws IOException {
        if (printChats) {
            dos.writeUTF("Choose one of the available rooms: " + rooms);
            printChats = false;
        }
        if (dis.available() > 0) {
            String room = dis.readUTF();
            if (rooms.contains(room)) {
                dos.writeUTF("You have successfully joined " + room +
                        "\nTo switch rooms use /switch\n" +
                        "To quit chat use /quit");
                return room;
            } else
                dos.writeUTF("Please enter a valid room name");
        }
        return null;
    }

    public ArrayList<String> getMessages() throws IOException {
        ArrayList<String> messages = new ArrayList<>();
        while (dis.available() > 0)
            messages.add(dis.readUTF());
        return messages;
    }

    public void sendMessages(ArrayList<String> messages) throws IOException {
        for (String msg : messages)
            dos.writeUTF(msg);
        dos.flush();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        dis.close();
        dos.close();
    }
}
