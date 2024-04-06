package controller;

import app.User;
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
import javafx.scene.Node;
import java.io.IOException;
import java.util.List;


public class LoginController  {

    @FXML
    TextField usernameField;

    @FXML
	Button logInButton;

    @FXML
	Label authors;
    

    ObservableList<User> listOfUsers = FXCollections.observableArrayList();
    

    public void onLoginButtonClick(ActionEvent e) throws IOException {
        if ((Button)e.getSource() == logInButton) {
            if (usernameField.getText().isEmpty()) {
                return;
            } else if (usernameField.getText().equals("admin")) {
                // Redirect to Admin page
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/view/Admin.fxml"));
                Parent parentView = (Parent) load.load();
                AdminController admincontroller = load.getController();
                admincontroller.setUsers(listOfUsers);
                Scene adminView = new Scene(parentView);
                Stage pictureStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                pictureStage.hide();
                pictureStage.setScene(adminView);
                pictureStage.show();
            } else {
                // Redirect to Home page
                String username = usernameField.getText();
                User foundUser = null;
                for (User u : listOfUsers) {
                    if (u.getUserName().equals(username)) {
                        foundUser = u;
                        break;
                    }
                }
                if (foundUser == null) {
                    // User not found, handle appropriately
                    return;
                }
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/HomePage.fxml"));
                Parent parentView = (Parent) loader.load();
                HomePageController usercontroller = loader.getController();
                usercontroller.setData(foundUser, listOfUsers);
                Scene adminView = new Scene(parentView);
                Stage pictureStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                pictureStage.hide();
                pictureStage.setScene(adminView);
                pictureStage.show();
            }
        }
    }
    

    public void setData(List<User> asd){
		this.listOfUsers = FXCollections.observableArrayList(asd);
        // System.out.println(listOfUsers);
        // System.out.println(listOfUsers.get(0).getUserName());
	}
}
