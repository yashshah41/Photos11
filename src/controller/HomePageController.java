package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Action;

import app.Album;
import app.Photo;
import app.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Controller for the home page in the application. It handles the interaction
 * between the user and the app's home page view.
 * It provides functionalities such as displaying albums, creating new content,
 * and navigating to other pages.
 * 
 * @author Yash Shah and Ayush Gupta
 * @version 1.0
 */

public class HomePageController {

	@FXML
	Button deleteButton;

	@FXML
	Button logoutButton;

	@FXML
	Button searchButton;

	@FXML
	Button finishButton;

	@FXML
	Button openAlbum;

	@FXML
	Button viewContent;

	@FXML
	ListView<String> allAlbums;

	@FXML
	Button createContent;

	@FXML
	TextField contentName;

	@FXML
	TextField editName;

	@FXML
	Label NumOfItems;

	@FXML
	Label DateRange;

	User user;

	ObservableList<Album> show = FXCollections.observableArrayList();

	List<User> listOfUsers;

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the FXML file has been loaded.
	 * It sets up listeners and initializes the view components with necessary data.
	 */

	public void initialize() {
		allAlbums.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				updateAlbumDetails(newValue);
			}
		});
	}

	/**
	 * Updates the album details displayed on the UI based on the album selected.
	 * It sets the text for the number of items and the date range of the album.
	 * 
	 * @param albumName The name of the album for which details are to be updated.
	 */

	private void updateAlbumDetails(String albumName) {
		Album selectedAlbum = user.getAllAlbums().stream()
				.filter(album -> album.getName().equals(albumName))
				.findFirst()
				.orElse(null);

		if (selectedAlbum != null) {
			NumOfItems.setText(String.valueOf(selectedAlbum.getCount()));

			Calendar startDate = selectedAlbum.getStartDate();
			Calendar endDate = selectedAlbum.getEndDate();
			if (startDate != null && endDate != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
				String dateRange = dateFormat.format(startDate.getTime()) + " - "
						+ dateFormat.format(endDate.getTime());
				DateRange.setText(dateRange);
			} else {
				DateRange.setText("Date Range Not Available");
			}
		} else {
			NumOfItems.setText("0");
			DateRange.setText("Date Range Not Available");
		}
	}

	/**
	 * Sets the data for the controller including the current user and the list of
	 * all users.
	 * It initializes the albums list view with the names of the albums belonging to
	 * the current user.
	 * 
	 * @param user  The current user of the application.
	 * @param users The list of all users in the application.
	 */

	public void setData(User user, List<User> users) {
		this.user = user;
		this.listOfUsers = users;
		ObservableList<String> albumNames = FXCollections.observableArrayList(
				user.getAllAlbums().stream().map(Album::getName).collect(Collectors.toList()));
		allAlbums.setItems(albumNames);
	}

	/**
	 * Handles the deletion of an album. It removes the selected album from the
	 * current user's list of albums
	 * and updates the UI accordingly.
	 * 
	 * @param event The action event that triggered the delete.
	 * @throws IOException If there is an issue loading the FXML file during UI
	 *                     update.
	 */

	public void deleteContent(ActionEvent event) throws IOException {
		String selectedAlbumName = allAlbums.getSelectionModel().getSelectedItem();
		if (selectedAlbumName == null) {
			return;
		}

		Album albumToDelete = user.getAllAlbums().stream()
				.filter(album -> album.getName().equals(selectedAlbumName))
				.findFirst()
				.orElse(null);

		if (albumToDelete != null) {
			user.getAllAlbums().remove(albumToDelete);

			show.remove(albumToDelete);

			allAlbums.setItems(FXCollections.observableArrayList(
					user.getAllAlbums().stream().map(Album::getName).collect(Collectors.toList())));

			this.save();
		}
	}

	/**
	 * Handles the creation of new content.
	 * 
	 * @param event The event that triggered the create action.
	 * @throws IOException If an I/O error occurs.
	 */

	public void createNewContent(ActionEvent event) throws IOException {
		String contentNameText = contentName.getText().trim();
		if (contentName.getText().isEmpty() || contentNameText == " ") {
			return;
		}
		boolean albumExists = user.getAllAlbums().stream()
				.anyMatch(album -> album.getName().equalsIgnoreCase(contentNameText));
		if (!albumExists) {
			Album newAlbum = new Album(contentNameText);

			user.getAllAlbums().add(newAlbum);

			show.add(newAlbum);

			ObservableList<String> contentNames = FXCollections.observableArrayList(
					user.getAllAlbums().stream().map(Album::getName).collect(Collectors.toList()));
			allAlbums.setItems(contentNames);
			this.contentName.clear();

			this.save();
		}
	}

	/**
     * Handles the viewing of content. It loads the view for the selected album.
     * 
     * @param event The event that triggered the view action.
     */

	public void viewContent(ActionEvent event) {
		Album target = null;
		if (allAlbums.getSelectionModel().getSelectedItem() != null) {
			String selectedAlbumName = allAlbums.getSelectionModel().getSelectedItem();
			target = user.getAllAlbums().stream().filter(album -> album.getName().equals(selectedAlbumName)).findFirst()
					.orElse(null);
		}

		if (target != null) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/PhotosInAlbum.fxml"));
				Parent admin_parent = (Parent) loader.load();
				PhotosInAlbumController s = loader.getController();
				s.setData(target, listOfUsers, user);
				Scene admin_scene = new Scene(admin_parent);
				Stage photoStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				photoStage.hide();
				photoStage.setScene(admin_scene);
				photoStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Renames the selected album to the new name provided.
	 * 
	 * @param event The action event that triggered the rename.
	 * @throws IOException If there is an issue loading the FXML file during UI
	 *                     update.
	 */

	public void renameContent(ActionEvent event) throws IOException {
		String selectedAlbumName = allAlbums.getSelectionModel().getSelectedItem();
		Album albumToRename = user.getAllAlbums().stream()
				.filter(album -> album.getName().equals(selectedAlbumName))
				.findFirst()
				.orElse(null);

		if (albumToRename != null) {
			String newName = editName.getText();
			boolean nameExists = user.getAllAlbums().stream()
					.anyMatch(album -> album.getName().equals(newName));

			if (!nameExists) {
				albumToRename.setName(newName);
				allAlbums.setItems(FXCollections.observableArrayList(
						user.getAllAlbums().stream().map(Album::getName).collect(Collectors.toList())));
			}
		}
		this.editName.clear();
		this.save();
	}

	/**
	 * Logs the current user
	 * 
	 * @param event The action event that triggered the rename.
	 */

	public void logOut(ActionEvent event)
			throws IOException {
		FXMLLoader load = new FXMLLoader();
		load.setLocation(getClass().getResource("/view/Login.fxml"));
		Parent parentView = (Parent) load.load();
		LoginController logincontroller = load.getController();
		logincontroller.setData(listOfUsers);
		Scene adminView = new Scene(parentView);
		Stage pictureStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		pictureStage.hide();
		pictureStage.setScene(adminView);
		pictureStage.show();
	}

	/**
     * Switches to the search view.
     * 
     * @param event The event that triggered the switch to search action.
     * @throws IOException If an I/O error occurs.
     */
	public void switchToSearch(ActionEvent event)
			throws IOException {
		FXMLLoader load = new FXMLLoader();
		load.setLocation(getClass().getResource("/view/SearchItem.fxml"));
		Parent adm_parent = (Parent) load.load();
		SearchController SearchController = load.getController();
		SearchController.setData(user.getAllAlbums(), user, listOfUsers);
		Scene adminView = new Scene(adm_parent);
		Stage pictureStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		pictureStage.hide();
		pictureStage.setScene(adminView);
		pictureStage.show();
	}


	/**
     * Saves the current state, writing to users.ser
     * 
     * @throws IOException If an I/O error occurs while saving.
     */
	public void save() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin.fxml"));
		Parent root = loader.load();
		AdminController admincontroller = loader.getController();

		if (admincontroller != null) {
			admincontroller.setUsers(this.listOfUsers);
			admincontroller.save();
		}
	}
}