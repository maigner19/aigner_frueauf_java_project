package aignerfrueauf.schach.schach;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private String ipAddress;
    private final ServerSocket server;
    public Server(int port) throws IOException {
        server = new ServerSocket(port);
    }

    private void verbinde() {

        while (true) {
            Socket socket = null;
            try {
                socket = server.accept();
                reinRaus(socket);
            }

            catch (IOException e) {
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
        BufferedReader rein = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream raus = new PrintStream(socket.getOutputStream());
        String s;

        while(rein.ready()) {
            raus.println("hallo");

            rein.readLine();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(3141);
        server.verbinde();
    }
}
