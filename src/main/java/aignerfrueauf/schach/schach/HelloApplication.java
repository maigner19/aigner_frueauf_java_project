package aignerfrueauf.schach.schach;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    double resolution = 1;
    int pixels = 272;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        GridPane pane = initiatePane(stage);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        Scene chessScene = new Scene(pane, pixels*resolution,pixels*resolution);
        stage.setTitle("Schach!");
        stage.setScene(chessScene);
        stage.show();

    }

    private GridPane initiatePane(Stage stage){
        GridPane pane = new GridPane();

        // Create 64 rectangles and add to pane
        int count = 0;
        double s = pixels/8f*resolution; // side of rectangle
        for (int i = 0; i < 8; i++) {
            count++;
            for (int j = 0; j < 8; j++) {
                Rectangle r = new Rectangle(s, s, s, s);
                if (count % 2 == 0)
                    r.setFill(Color.WHITE);
                pane.add(r, j, i);
                count++;
            }
        }

        // Create a scene and place it in the stage
        return pane;
    }

    public static void main(String[] args) {
        launch();
    }
}