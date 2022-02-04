package aignerfrueauf.schach.schach;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ClientServer {

    public Button clientButton;
    public TextField inputIp;
    public Label invalidAddress;

    String port = "3141";
    String ip;
    String[] args = {port};
    String regex = "\"^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\\\.(?!$)|$)){4}$\"";

    public void pressServer(ActionEvent actionEvent) throws IOException {
        Server.main(args);
    }

    public void pressJoin(ActionEvent actionEvent) {
        String ip = inputIp.getText();
        if(ip.matches(regex)){
            invalidAddress.setOpacity(0);
            args[1] = ip;

            Client client = new Client(args);
            try {
                client.clientConnect();
            } catch (IOException e) {
                invalidAddress.setText("No Server with this Address");
                invalidAddress.setOpacity(1);
            }
        }else {
            invalidAddress.setText("Invalid IP-Address");
            invalidAddress.setOpacity(1);
        }
    }
}