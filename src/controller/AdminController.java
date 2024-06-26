package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import app.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

/**
 * Controller class for the admin panel. It handles user creation, deletion, and
 * serialization of user data.
 * 
 * @author Ayush Gupta
 * @version 1.0
 */

public class AdminController {

    @FXML
    TextField username;

    @FXML
    ListView<User> users;

    ObservableList<User> listOfVisibleUsers = FXCollections.observableArrayList();

    /**
     * It loads users from a serialized file
     */

    public void initialize() {
        try {
            File file = new File("users.ser");
            if (file.exists()) {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                @SuppressWarnings("unchecked")
                List<User> loadedUsers = (List<User>) in.readObject();
                listOfVisibleUsers.addAll(loadedUsers);
                in.close();
                fileIn.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the list of visible users
     * 
     * @param x The list of users to display in the ListView.
     */
    public void setUsers(List<User> x) {
        this.listOfVisibleUsers = FXCollections.observableArrayList(x);
        users.setItems(listOfVisibleUsers);
    }

    /**
     * Handles the creation of a new user account
     * 
     * @param event The event that triggers the creation of a new user.
     */
    public void createUser(ActionEvent event) {
        String usernameInput = this.username.getText();
        if (usernameInput.equalsIgnoreCase("admin")) {
            this.username.clear();
            return;
        }

        for (User u : listOfVisibleUsers) {
            if (u.getUserName().equals(usernameInput)) {
                return;
            }
        }

        ArrayList<Album> albums = new ArrayList<Album>();
        List<Photo> photos = new ArrayList<Photo>();
        File stock1 = new File("data/imageOne.png");
        File stock2 = new File("data/imageTwo.png");
        File stock3 = new File("data/imageThree.png");
        File stock4 = new File("data/imageFour.png");
        File stock5 = new File("data/imageFive.png");
        photos.add(new Photo(stock1, new ArrayList<Tag>()));
        photos.add(new Photo(stock2, new ArrayList<Tag>()));
        photos.add(new Photo(stock3, new ArrayList<Tag>()));
        photos.add(new Photo(stock4, new ArrayList<Tag>()));
        photos.add(new Photo(stock5, new ArrayList<Tag>()));
        Album album = new Album("Stock Album", photos);
        // albums.add(album);
        listOfVisibleUsers.add(new User(usernameInput, albums));
        users.setItems(listOfVisibleUsers);
        this.username.clear();
        this.save();
    }

    /**
     * Logs the admin out and sets the location to login view.
     * 
     * @param e The event that triggers the logout.
     * @throws IOException If an I/O error occurs while loading the login view.
     */
    public void logOut(ActionEvent e) throws IOException {
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("/view/Login.fxml"));
        Parent parentView = (Parent) load.load();
        LoginController logincontroller = load.getController();
        logincontroller.setData(listOfVisibleUsers);
        Scene adminView = new Scene(parentView);
        Stage pictureStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        pictureStage.hide();
        pictureStage.setScene(adminView);
        pictureStage.show();
    }

    /**
     * Deletes the selected user from the application and updates the ListView.
     * 
     * @param e The event that triggers the deletion of a user.
     */

    public void deleteUser(ActionEvent e) {
        listOfVisibleUsers.remove(users.getSelectionModel().getSelectedItem());
        users.setItems(listOfVisibleUsers);
        this.save();

    }

    /**
     * Saves the current list of users to a serialized file.
     */

    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(new ArrayList<User>(listOfVisibleUsers));
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
