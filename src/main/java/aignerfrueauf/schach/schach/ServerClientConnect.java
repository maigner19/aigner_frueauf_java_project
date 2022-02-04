package aignerfrueauf.schach.schach;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerClientConnect extends Application {
    static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        double size = 408;

        FXMLLoader fxmlLoader = new FXMLLoader(ServerClientConnect.class.getResource("ClientServer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), size, size);


        stage.setTitle("Schach");
        stage.getIcons().add(new Image(ChessBoard.bKing));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


        TextField text = (TextField) scene.lookup("#inputIp");
        Button button = (Button) scene.lookup("#clientButton");

        BooleanBinding booleanBind = text.textProperty().isEmpty();
        button.disableProperty().bind(booleanBind);

    }
    public static Stage getStage(){
        return stage;
    }
    public static void main (String[]args){
        launch();
    }
}
