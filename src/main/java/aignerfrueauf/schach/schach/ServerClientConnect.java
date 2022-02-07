package aignerfrueauf.schach.schach;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerClientConnect extends Application {
    static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        String port = "3141";
        String ip = "";
        String[] strings = {port,ip};
        String regex = "\"^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\\\.(?!$)|$)){4}$\"";

        FXMLLoader fxmlLoader = new FXMLLoader(ServerClientConnect.class.getResource("ClientServer.fxml"));
        double size = 408;
        Scene scene = new Scene(fxmlLoader.load(), size, size);

        stage.setTitle("Schach");
        stage.getIcons().add(new Image(ChessBoard.bKing));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


        TextField text = (TextField) scene.lookup("#inputIp");
        Button buttonClient = (Button) scene.lookup("#clientButton");

        BooleanBinding booleanBind = text.textProperty().isEmpty();
        buttonClient.disableProperty().bind(booleanBind);

        Label invalidAddress = (Label) scene.lookup("#invalidAddress");
        TextField inputIp = (TextField) scene.lookup("#inputIp");

        buttonClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String ip = inputIp.getText();

                ip = "localhost";
                if(true /**ip.matches(regex)**/){
                    invalidAddress.setOpacity(0);
                    strings[1] = ip;

                    Client client = new Client(strings,stage);
                    try {
                        client.clientConnect();
                    } catch (IOException exception) {
                        invalidAddress.setText("No Server with this Address");
                        invalidAddress.setOpacity(1);
                    }
                }else {
                    invalidAddress.setText("Invalid IP-Address");
                    invalidAddress.setOpacity(1);
                }
            }
        });

        Button buttonServer = (Button) scene.lookup("#serverButton");
        buttonServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    Server server = new Server(Integer.parseInt(port),stage);
                    server.verbinde();
                }catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
    public static Stage getStage(){
        return stage;
    }
    public static void main (String[]args){
        launch();
    }
}
