package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.util.Callback;
import javafx.stage.Stage;

import app.*;

/**
 * Controller for handling the movement and copying of photos between albums.
 * Provides functionality to move or copy a selected photo to another album,
 * and to cancel the action or finish and return to the album view.
 * 
 * @version 1.0
 * @author Ayush Gupta
 */
public class MoveItemController {
    @FXML
    Button moveButton;

    @FXML
    Button copyButton;

    @FXML
    Button cancelButton;

    @FXML
    Button finishButton;

    @FXML
    ListView<Album> itemsToMoveList;

    @FXML
    ListView<Album> itemsToCopyList;

    User user;
    List<User> members;
    Album album;
    Photo currentPhoto;
    List<Album> totalAlbums;

    ObservableList<Album> albumsToMove = FXCollections.observableArrayList();
    ObservableList<Album> albumsToCopy = FXCollections.observableArrayList();

    /**
     * Initializes the controller with the user's albums and the photo to be moved
     * or copied.
     * Sets up the lists of albums where the photo can be moved or copied.
     * 
     * @param user    The current user.
     * @param album   The current album containing the photo.
     * @param photo   The photo to be moved or copied.
     * @param members The list of all users.
     */
    public void setData(User user, Album album, Photo photo, List<User> members) {
        this.user = user;
        this.album = album;
        this.currentPhoto = photo;
        this.members = members;
        albumsToMove = FXCollections.observableArrayList(user.getAllAlbums());
        albumsToCopy = FXCollections.observableArrayList(user.getAllAlbums());
        itemsToMoveList.setItems(albumsToMove);
        itemsToMoveList.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>() {
            @Override
            public ListCell<Album> call(ListView<Album> param) {
                return new ListCell<Album>() {
                    @Override
                    protected void updateItem(Album item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getName()); // Set the name of the album
                        }
                    }
                };
            }
        });

        itemsToCopyList.setItems(albumsToCopy);
        itemsToCopyList.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>() {
            @Override
            public ListCell<Album> call(ListView<Album> param) {
                return new ListCell<Album>() {
                    @Override
                    protected void updateItem(Album item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getName()); // Set the name of the album
                        }
                    }
                };
            }
        });
    }

    /**
     * Handles the action to cancel moving or copying the photo and returns to the
     * album view.
     * 
     * @param e The event triggered by clicking the cancel button.
     * @throws IOException If loading the PhotosInAlbum view fails.
     */
    public void cancel(ActionEvent e) throws IOException {
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("/view/PhotosInAlbum.fxml"));
        Parent parentView = (Parent) load.load();
        PhotosInAlbumController oacontroller = load.getController();
        oacontroller.setData(album, members, user);
        Scene adminView = new Scene(parentView);
        Stage pictureStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        pictureStage.hide();
        pictureStage.setScene(adminView);
        pictureStage.show();
    }

    /**
     * Moves the selected photo to the selected album, removing it from the current
     * album.
     * 
     * @param e The event triggered by clicking the move button.
     * @throws IOException If an IO operation fails during the move.
     */
    public void moveItem(ActionEvent e) throws IOException {
        Album target = (Album) itemsToMoveList.getSelectionModel().getSelectedItem();
        if (target != album && !target.getAllPhotos().contains(currentPhoto)) {
            target.addPhoto(currentPhoto);
            album.removePhoto(currentPhoto);
            this.save();
        }
    }    

    /**
     * Copies the selected photo to the selected album, retaining it in the current
     * album.
     * 
     * @param e The event triggered by clicking the copy button.
     * @throws IOException If an IO operation fails during the copy.
     */
    public void copyItem(ActionEvent e) throws IOException {
        Album target = itemsToCopyList.getSelectionModel().getSelectedItem();
        if (target != album && !target.getAllPhotos().contains(currentPhoto)) {
            target.addPhoto(currentPhoto);
            save();
        }
    }    

    /**
     * Completes the move or copy action and returns to the album view.
     * 
     * @param e The event triggered by clicking the finish button.
     * @throws IOException If loading the PhotosInAlbum view fails.
     */

    public void finish(ActionEvent e) throws IOException {
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("/view/PhotosInAlbum.fxml"));
        Parent parentView = (Parent) load.load();
        PhotosInAlbumController oacontroller = load.getController();
        oacontroller.setData(album, members, user);
        Scene adminView = new Scene(parentView);
        Stage pictureStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        pictureStage.hide();
        pictureStage.setScene(adminView);
        pictureStage.show();
    }

    /**
     * Saves changes to the users or albums.
     * 
     * @throws IOException If an IO operation fails during the save.
     */

    public void save() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Admin.fxml"));
        Parent parentView = (Parent) loader.load()  ;
        AdminController admincontroller = loader.getController();
        admincontroller.setUsers(this.members);
        admincontroller.save();
    }
}
