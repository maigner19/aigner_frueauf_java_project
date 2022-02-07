package aignerfrueauf.schach.schach;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client {
    private final String ipAddress;
    private final int port;
    private Stage stage;
    public Client(String[] strings, Stage stage){
        this.port = Integer.parseInt(strings[0]);
        this.ipAddress = strings[1];
        this.stage = stage;
    }

    public void clientConnect() throws IOException {
        Socket socket = null;
        try {
            socket = new Socket(ipAddress, port);

            OutputStream raus = socket.getOutputStream();
            PrintStream ps = new PrintStream(raus, true);
            InputStream rein = socket.getInputStream();
            BufferedReader buff = new BufferedReader(new InputStreamReader(rein));


            ChessBoard board = new ChessBoard(false);
            stage.setScene(new Scene(board.chessPane));
            stage.setTitle("Client");
            board.startChess(stage);

            String move = "";
            String tmpMove = "foo";
            while (!move.equals("f") && buff.ready()){
                do {
                    tmpMove = buff.readLine();
                    System.out.println(buff.readLine());
                }while (tmpMove.equals(move));
                move = tmpMove;

                move = board.playChessMove(move);
                ps.println(move);
            }

        }  catch (IOException e) {
            System.out.println("IOProbleme...");
            e.printStackTrace();
            throw new IOException();
        } finally {
            if (socket != null)
                try {
                    socket.close();
                    System.out.println("Socket geschlossen..." + LocalDateTime.now());
                } catch (IOException e) {
                    System.out.println("Socket nicht zu schliessen...");
                    e.printStackTrace();
                }
        }
    }
}
