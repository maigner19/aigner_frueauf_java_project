package aignerfrueauf.schach.schach;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    public static int port = 3141;
    private final ServerSocket server;
    public Server() throws IOException {
        server = new ServerSocket(port);
    }

    private void verbinde() {

        while (true) {
            Socket socket = null;
            try {
                socket = server.accept();
                reinRaus(socket);
            }catch (SocketException e){
                System.out.println("Client hat die verbindung abgebrochen");
            }catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null)
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    private void reinRaus(Socket socket) throws IOException {
        System.out.println("verbunden lol");
        BufferedReader rein = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream raus = new PrintStream(socket.getOutputStream());
        String s;

        while(true) {
            raus.println("hallo");

            rein.readLine();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.verbinde();
    }
}
