package aignerfrueauf.schach.schach;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;


public class Server {
    Stage stage;
    private final ServerSocket server;
    public Server(int port, Stage stage) throws IOException {
        server = new ServerSocket(port);
        this.stage = stage;
    }

    public void verbinde() {
        System.out.println("Server will verbinden");

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
        String move = "first";

        ChessBoard board = new ChessBoard(true);
        stage.setScene(new Scene(board.chessPane));
        stage.setTitle("Server");
        board.startChess(stage);

        String tmpMove;
        while(!move.equals("f") && rein.ready()) {
            move = board.playChessMove(move);
            raus.println(move);

            do {
                tmpMove = rein.readLine();
            }while (tmpMove.equals(move));
            move = tmpMove;
        }

        System.out.println("Aufgehört" + LocalDateTime.now());
    }
}
