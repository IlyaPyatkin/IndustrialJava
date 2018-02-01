import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;

public class ClientHandler {
    private final DataInputStream dis;
    private final DataOutputStream dos;

    public ClientHandler(Socket socket) throws IOException {
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    public String promptJoinRoom(Set<String> rooms) throws IOException {
        dos.writeUTF("Choose one of the available rooms: " + rooms);
        String room = dis.readUTF();
        while (!rooms.contains(room)) {
            dos.writeUTF("Please enter a valid room name");
            room = dis.readUTF();
        }
        dos.writeUTF("You have successfully joined " + room);
        return room;
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
