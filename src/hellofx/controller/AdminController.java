package hellofx.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hellofx.app.Album;
import hellofx.app.Photo;
import hellofx.app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AdminController {

    @FXML
    TextField username;

    public void createUser(ActionEvent event) {
        if (this.username == null) {
            return;
        }
        String name = this.username.getText();
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
        User newUser = new User(name);
        newUser.allAlbums = albums;
        // idk what to do from here, write it to a file? idk how to do that, also update
        // the view
    }

    public void deleteUser() {
        System.out.println("Delete User");
    }

}
