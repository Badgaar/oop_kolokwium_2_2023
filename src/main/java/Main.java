import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.print("[S/C]? ");
        switch (new Scanner(System.in).nextLine().toLowerCase().charAt(0)) {
        case 's':
            {int port = new Scanner(System.in).nextInt();
            Server server = new Server(port);
            server.listen();}
        break;
        case 'c': Client client = new Client();
            int port = new Scanner(System.in).nextInt();
            client.connectToServer(new Scanner(System.in).nextLine().toLowerCase(), port);
            client.send("input.png");
            client.receive("output.png");
        }
    }
}