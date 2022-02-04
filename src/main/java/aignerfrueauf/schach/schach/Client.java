package aignerfrueauf.schach.schach;

import java.io.*;
import java.net.Socket;

public class Client {
    private final String ipAddress;
    private final int port;

    public Client(String[] strings){
        this.port = Integer.parseInt(strings[0]);
        this.ipAddress = strings[1];
    }

    public void clientConnect() throws IOException {
        Socket socket = null;
        try {
            socket = new Socket(ipAddress, port);

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
            throw new IOException();
            //e.printStackTrace();
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
