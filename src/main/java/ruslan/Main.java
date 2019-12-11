package ruslan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;;

import java.io.IOException;

public class Main extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loader("main").load());
        stage.setScene(scene);
        stage.setTitle("Caesar");
        stage.show();
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loader(fxml).load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FXMLLoader loader(String fxml) {
        return new FXMLLoader(Main.class.getResource( "/"+ fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch();
    }

}

