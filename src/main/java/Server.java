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

    public Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
    }

    private void ClientHandler(Socket socket, String filePath, String folderPath) throws IOException {
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        //Move to another method
        File imagesFolder = new File(folderPath, "images");
        if (!imagesFolder.exists()) {
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

    public void listen(Boolean isServerBusy, ServerSocket serverSocket, Queue clientQueue, String folderPath, String filePath) throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            if (isServerBusy) {
                clientQueue.add(socket);
            } else {
                isServerBusy = true;
                //Socket nextSocket = clientQueue.poll();
                ClientHandler(socket, filePath, folderPath);
            }
        }
    }

    private void blurImage() throws IOException {
        
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.listen(isServerBusy);
    }
}