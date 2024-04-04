package hellofx;

import java.util.List;

import hellofx.app.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        List<User> data = null;
        Parent root = FXMLLoader.load(getClass().getResource("hellofx.fxml"));
        primaryStage.setTitle("hey World");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}