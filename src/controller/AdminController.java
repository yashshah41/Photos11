package hellofx.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import hellofx.app.Album;
import hellofx.app.Photo;
import hellofx.app.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

public class AdminController {

    @FXML
    TextField username;
    ListView<User> users;

    ObservableList<User> listOfVisibleUsers = FXCollections.observableArrayList();
    public void setUsers(List<User> list) {
        this.listOfVisibleUsers = FXCollections.observableArrayList(list);
        users.setItems(listOfVisibleUsers);
    }

    public void createUser(ActionEvent event) {
        if (this.username == null) {
            return;
        }
        String usernameInput = this.username.getText();


        ArrayList<Album> albums = new ArrayList<Album>();
        List<Photo> photos = new ArrayList<Photo>();
        File stock1 = new File("/stockImages/imageOne.png");
        File stock2 = new File("/stockImages/imageTwo.png");
        File stock3 = new File("/stockImages/imageThree.png");
        File stock4 = new File("/stockImages/imageFour.png");
        File stock5 = new File("/stockImages/imageFive.png");
        photos.add(new Photo(stock1, null));
        photos.add(new Photo(stock2, null));
        photos.add(new Photo(stock3, null));
        photos.add(new Photo(stock4, null));
        photos.add(new Photo(stock5, null));
        Album album = new Album("Stock Album", photos);
        albums.add(album);
        User newUser = new User(usernameInput, albums);
        listOfVisibleUsers.add(newUser);
        this.username.clear();
        users.setItems(listOfVisibleUsers);
        this.save();
    
    }

    public void deleteUser(ActionEvent e) {
		if(listOfVisibleUsers.contains((User) users.getSelectionModel().getSelectedItem())) listOfVisibleUsers.remove(listOfVisibleUsers.get(users.getSelectionModel().getSelectedIndex()));
		users.setItems(listOfVisibleUsers);
		this.save();
    }

    public void save() {
        try {
        FileOutputStream fileOut =
	    new FileOutputStream("users.ser");
	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(new ArrayList<User> (listOfVisibleUsers));
	    out.close();
	    fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
