import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.print("[S/C]? ");
        switch (new Scanner(System.in).nextLine().toLowerCase().charAt(0)) {
        case 's':
            System.out.println("Podaj port");
            {int port = new Scanner(System.in).nextInt();
            Server server = new Server(port);
            server.listen();}
        break;
        case 'c': Client client = new Client();
            System.out.println("Podaj port");
            int port = new Scanner(System.in).nextInt();
            System.out.println("Podaj nazwe hosta");
            client.connectToServer(new Scanner(System.in).nextLine().toLowerCase(), port);
            client.send("input.png");
            client.receive("output.png");
        }
    }
}