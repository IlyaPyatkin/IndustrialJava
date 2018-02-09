import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    public final static HashMap<String, ArrayList<ClientHandler>> rooms = new HashMap<>();
    public final static ArrayList<ClientHandler> requestHandlers = new ArrayList<>();
    public static boolean running = true;

    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(new RoomHandler()).start();
        rooms.put("BestRoom", new ArrayList<>());
        rooms.put("WorstRoom", new ArrayList<>());
        rooms.put("AverageRoom", new ArrayList<>());

        while (running) {
            synchronized (rooms) {
                for (ArrayList<ClientHandler> handlers : rooms.values()) {
                    ArrayList<ClientHandler> toRemove = new ArrayList<>();
                    for (ClientHandler handler1 : handlers) {
                        ArrayList<String> messages = handler1.getMessages();

                        for (int i = 0; i < messages.size(); i++)
                            if (messages.get(i).equals("/switch")) {
                                toRemove.add(handler1);
                                synchronized (requestHandlers) {
                                    requestHandlers.add(handler1);
                                    handler1.printChats = true;
                                }
                                messages = new ArrayList<>(messages.subList(0, i));
                                break;
                            }
                        if (messages.isEmpty())
                            continue;
                        for (ClientHandler handler2 : handlers) {
                            if (handler1 != handler2) {
                                try {
                                    handler2.sendMessages(messages);
                                } catch (SocketException x) {
                                    toRemove.add(handler2);
                                }
                            }
                        }
                    }
                    handlers.removeAll(toRemove);
                }
            }
            Thread.sleep(500);
        }
    }
}
