package hellofx.controller;

import java.util.List;

import hellofx.app.Album;
import hellofx.app.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class UserController {


    @FXML
	ListView<Album> allAlbums;



    public User user;
    public List<User> users;
	ObservableList<Album> show = FXCollections.observableArrayList();


    public void setData(User user,List<User> users) {
        this.user = user;
        this.users = users;
        show = FXCollections.observableArrayList(user.getAllAlbums());
        allAlbums.setItems(show);
    }

}
