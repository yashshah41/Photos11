package hellofx;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import hellofx.app.User;
import hellofx.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        List<User> data = null;
        try {
		    ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.ser") );
		    List<User> list = (List<User>) ois.readObject();
		    data = list;
		    ois.close();}
		    catch(Exception e){
			e.printStackTrace();
		}
            FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("C:\\Users\\diamo\\Downloads\\Photos11\\src\\hellofx\\view\\Login.fxml"));
		    AnchorPane root = (AnchorPane)loader.load();
		    LoginController loginController = loader.getController();
		    loginController.setData(data);
			Scene scene = new Scene(root,600,600);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Photos11");
			primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}