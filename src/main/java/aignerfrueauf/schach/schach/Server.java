package aignerfrueauf.schach.schach;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    private final ServerSocket server;
    public Server(int port) throws IOException {
        server = new ServerSocket(port);
    }

    private void verbinde() {
        boolean verbunden = true;
        while (verbunden) {
            Socket socket = null;
            try {
                socket = server.accept();
                reinRaus(socket);
            }catch (SocketException e){
                System.out.println("Client hat die verbindung abgebrochen");
                verbunden = false;
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
        String move ="0,0,0,0";

        ChessBoard board = new ChessBoard();
        board.startChess();
        board.setIsWhite(true);
        while(!move.equals("f")) {
            move = board.playChessMove(move);
            raus.println(move);

            move = rein.readLine();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(Integer.parseInt(args[0]));
        server.verbinde();
    }
}
