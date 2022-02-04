package aignerfrueauf.schach.schach;

import java.io.*;
import java.net.Socket;

public class Client {
    private String ipAddress;

    public Client(String ipAddress){
        this.ipAddress = ipAddress;
    }

    public void clientConnect() {
        Socket socket = null;
        try {
            socket = new Socket(ipAddress, 3141);

            OutputStream raus = socket.getOutputStream();
            PrintStream ps = new PrintStream(raus, true);
            InputStream rein = socket.getInputStream();
            BufferedReader buff = new BufferedReader(new InputStreamReader(rein));

            while (true) {
                System.out.println(buff.readLine());

                ps.println();
            }

        }  catch (IOException e) {
            System.out.println("IOProbleme...");
            e.printStackTrace();
        } finally {
            if (socket != null)
                try {
                    socket.close();
                    System.out.println("Socket geschlossen...");
                } catch (IOException e) {
                    System.out.println("Socket nicht zu schliessen...");
                    e.printStackTrace();
                }
        }
    }
}
