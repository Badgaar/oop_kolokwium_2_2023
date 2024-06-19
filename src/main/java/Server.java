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
    public static String folderPath = "/Users/wojciechrzucidlo/IdeaProjects/kolokwium2_2023/src/main/java";
    public static String filePath = "/Users/wojciechrzucidlo/IdeaProjects/kolokwium2_2023/src/main/java/images";

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void ClientHandler(Socket socket, String filePath, String folderPath) throws IOException {
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        File imagesFolder = new File(folderPath, "images")

        if (imagesFolder.exists()){
            return;
        } else {
            imagesFolder.mkdir();
        }

        String fileName = today + "/" + now + ".png";
        File file = new File(filePath, fileName);

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
                Socket nextSocket = (Socket) clientQueue.poll();
                ClientHandler(socket, filePath, folderPath);
            }
        }
    }

    private void blurImage() throws IOException {
    }
}