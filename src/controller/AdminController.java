package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import app.Album;
import app.Photo;
import app.User;
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

public class AdminController {

    @FXML
    TextField username;


    @FXML
    ListView<User> users;

    ObservableList<User> listOfVisibleUsers = FXCollections.observableArrayList();

    public void initialize() {
        loadUsersFromFile();
    }

    private void loadUsersFromFile() {
        try {
            File file = new File("users.ser");
            if (file.exists()) {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                listOfVisibleUsers.addAll((List<User>) in.readObject());
                in.close();
                fileIn.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            // Handle file loading errors
            e.printStackTrace();
        }
    }

    public void setUsers(List<User> list) {
        this.listOfVisibleUsers = FXCollections.observableArrayList(list);
        users.setItems(listOfVisibleUsers);
    }

    public void createUser(ActionEvent event) {

        String usernameInput = this.username.getText();
        for(User u : listOfVisibleUsers) {
            if(u.getUserName().equals(usernameInput)) {
                return;
            }
        }

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
        System.out.println(newUser.allAlbums.size());
        listOfVisibleUsers.add(newUser);
        this.username.clear();
        users.setItems(listOfVisibleUsers);
        this.save();
    }

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

    public void deleteUser(ActionEvent e) {
        if (listOfVisibleUsers.contains((User) users.getSelectionModel().getSelectedItem()))
            listOfVisibleUsers.remove(listOfVisibleUsers.get(users.getSelectionModel().getSelectedIndex()));
        users.setItems(listOfVisibleUsers);
        this.save();
    }

    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(new ArrayList<User>(listOfVisibleUsers));
            System.out.println("Success");
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
