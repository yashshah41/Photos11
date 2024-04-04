package hellofx.controller;

import hellofx.app.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;


public class LoginController  {

    @FXML
    TextField usernameField;

    @FXML
	Button logInButton;
	
    @FXML
	Label username;


    ObservableList<User> listOfUsers = FXCollections.observableArrayList();
    

    public void onLoginButtonClick(ActionEvent e) throws IOException {
        if((Button)e.getSource()  == logInButton) {
            if(usernameField.getText().isEmpty()) {
                return;
            } else if(usernameField.getText().equals("admin")) {
                FXMLLoader load = new FXMLLoader();
			    load.setLocation(getClass().getResource("/view/Admin.fxml"));
			    Parent admin_parent = (Parent)load.load();
			    AdminController admincontroller = load.getController();
                admincontroller.setUsers(listOfUsers);
                Scene admin_scene = new Scene(admin_parent);
                Stage photoStage = (Stage)((Node) e.getSource()).getScene().getWindow();
                photoStage.hide();
			    photoStage.setScene(admin_scene);
			    photoStage.show();
            } else {
                User a = new User(usernameField.getText());
                for(User u : listOfUsers) {
                    if(u.equals(a)) {
                        a = u;
                        break;
                    }
                }
                FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/UserHome.fxml"));
				Parent user_parent = (Parent)loader.load();
				UserController usercontroller = loader.getController();
				usercontroller.setData(a, listOfUsers);
				Scene admin_scene = new Scene(user_parent);
				Stage photoStage = (Stage)((Node) e.getSource()).getScene().getWindow();
				photoStage.hide();
				photoStage.setScene(admin_scene);
				photoStage.show();
            }
        }
    }
}
