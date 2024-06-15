import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public class Server {

    private ServerSocket serverSocket;
    private final Queue<Socket> clientQueue = new LinkedList<>();
    private Boolean isServerBusy;
    public static String path = "/Users/wojciechrzucidlo/Desktop/sft";

    public Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
    }

    private void ClientHandler(Socket socket, String path) throws IOException {
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        String fileName = today + "/" + now + ".png";
        File file = new File(path, fileName);

        InputStream input = socket.getInputStream();
        FileOutputStream output = new FileOutputStream(file);

        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }

        input.close();
        output.close();
        socket.close();
    }

    public void listen() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            if (isServerBusy) {
                clientQueue.add(socket);
            } else {
                isServerBusy = true;
                Socket nextSocket = clientQueue.poll();
                if (nextSocket != null) {
                    ClientHandler(nextSocket, path);
                } else {
                    ClientHandler(socket, path);
                }
                isServerBusy = false;
            }
        }
    }
}