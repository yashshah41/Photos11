package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.net.MalformedURLException;

import app.*;

/**
 * Controller for displaying detailed information of an image such as its
 * caption,
 * date, tags, and the image itself. It allows navigation through images in an
 * album
 * and returning back to the album view.
 * 
 * @version 1.0
 * @author Yash Shah
 */

public class ImageInfoController {

	@FXML
	ImageView picView;

	@FXML
	Button backButton;

	@FXML
	Button prevButton;

	@FXML
	Button nextButton;

	@FXML
	Label captionLabel;

	@FXML
	Label capField;

	@FXML
	Label dateLabel;

	@FXML
	Label dateField;

	@FXML
	Label tags;

	@FXML
	ListView<Tag> tagList;

	ObservableList<Tag> taglist = FXCollections.observableArrayList();

	ObservableList<User> members = FXCollections.observableArrayList();
	Album album;
	User user;
	Photo photo;

	/**
	 * Sets the data for the ImageInfo view, initializes the view components with
	 * the given photo, album, user, and the list of members.
	 *
	 * @param members The list of users of the application.
	 * @param photo   The photo to be displayed.
	 * @param album   The album containing the photo.
	 * @param user    The user viewing the photo.
	 * @throws MalformedURLException if the path to the photo is incorrect.
	 */

	public void setData(List<User> listOfAlUsers, Photo photo, Album album, User user) throws MalformedURLException {
		this.members = FXCollections.observableArrayList(listOfAlUsers);
		this.album = album;
		this.user = user;
		this.photo = photo;
	}

	/**
	 * Initializes the view with the selected photo, setting the image view, date,
	 * caption, and list of tags associated with the photo.
	 *
	 * @param photo The photo to initialize in the view.
	 * @throws MalformedURLException if the path to the photo is incorrect.
	 * @throws IOException           if there is an error loading the photo or tags.
	 */

	public void initalizeImage(Photo photo) throws MalformedURLException, IOException {
		String path = photo.getFile().getAbsolutePath();
		InputStream instream = new FileInputStream(path);
		Image image = new Image(instream);
		picView.setImage(image);
		dateField.setText(photo.lastModifiedDate.toString());
		taglist = FXCollections.observableArrayList(photo.getTags());
		tagList.setItems(taglist);
		capField.setText(photo.getCaption());
	}

	/**
	 * Handles the action to return to the album view.
	 *
	 * @param e The action event triggered when the back button is clicked.
	 * @throws IOException if there is an error returning to the album view.
	 */

	public void back(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/PhotosInAlbum.fxml"));
		Parent oa_parent = (Parent) loader.load();
		PhotosInAlbumController oacontroller = loader.getController();
		oacontroller.setData(album, members, user);
		Scene admin_scene = new Scene(oa_parent);
		Stage photoStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		photoStage.hide();
		photoStage.setScene(admin_scene);
		photoStage.show();
	}

	/**
	 * Handles the action to navigate to the next photo in the album.
	 *
	 * @param e The action event triggered when the next button is clicked.
	 * @throws FileNotFoundException if the next photo's file is not found.
	 */

	public void next(ActionEvent e) throws FileNotFoundException {
		List<Photo> photos = this.album.getAllPhotos();
		if (photos.get(photos.size() - 1) != this.photo) {
			for (int i = 0; i < photos.size() - 1; i++) {
				if (photos.get(i) == this.photo) {
					this.photo = photos.get(i + 1);
					String path = photo.getFile().getAbsolutePath();
					InputStream instream = new FileInputStream(path);
					Image image = new Image(instream);
					picView.setImage(image);
					String date = photo.getDate().toString().substring(0, 11);
					dateField.setText(date);
					taglist = FXCollections.observableArrayList(photo.getTags());
					tagList.setItems(taglist);
					capField.setText(photo.getCaption());
					break;
				}
			}
		}
	}

	 /**
     * Handles the action to navigate to the previous photo in the album.
     *
     * @param e The action event triggered when the previous button is clicked.
     * @throws FileNotFoundException if the previous photo's file is not found.
     */

	public void prev(ActionEvent e) throws FileNotFoundException {
		List<Photo> photos = this.album.getAllPhotos();
		if (photos.get(0) != this.photo) {
			for (int i = 0; i < photos.size(); i++) {
				if (photos.get(i) == this.photo) {
					this.photo = photos.get(i - 1);
					String path = photo.getFile().getAbsolutePath();
					InputStream instream = new FileInputStream(path);
					Image image = new Image(instream);
					picView.setImage(image);
					String date = photo.getDate().toString().substring(0, 11);
					dateField.setText(date);
					taglist = FXCollections.observableArrayList(photo.getTags());
					tagList.setItems(taglist);
					capField.setText(photo.getCaption());
					break;
				}
			}
		}

	}

}
