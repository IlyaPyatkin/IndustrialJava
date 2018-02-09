import java.io.IOException;
import java.util.ArrayList;

public class RoomHandler implements Runnable {
    @Override
    public void run() {
        new Thread(new ConnectionHandler()).start();
        try {
            while (Server.running) {
                synchronized (Server.requestHandlers) {
                    ArrayList<ClientHandler> toRemove = new ArrayList<>();
                    for (ClientHandler handler : Server.requestHandlers) {
                        String room = handler.promptJoinRoom(Server.rooms.keySet());
                        if (room != null) {
                            synchronized (Server.rooms) {
                                Server.rooms.get(room).add(handler);
                            }
                            toRemove.add(handler);
                        }
                    }
                    Server.requestHandlers.removeAll(toRemove);
                }
                Thread.sleep(200);
            }
        } catch (IOException | InterruptedException x) {
            System.err.format("IOException: %s%n", x);
            Server.running = false;
        }
    }
}
