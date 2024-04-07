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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePageController {

	@FXML
	Button deleteButton;

	@FXML
	Button logoutButton;

	@FXML
	Button openAlbum;

	@FXML
	ListView<String> allAlbums; // This will now hold Strings

	@FXML
	Button createAlbum;

	@FXML
	TextField albumName;

	@FXML
	TextField editName;

	User user;

	ObservableList<Album> show = FXCollections.observableArrayList();

	List<User> listOfUsers;

	public void setData(User user, List<User> users) {
		this.user = user;
		this.listOfUsers = users;
		// Convert Album objects to their names and create an ObservableList of names
		ObservableList<String> albumNames = FXCollections.observableArrayList(
			user.getAllAlbums().stream().map(Album::getName).collect(Collectors.toList())
		);
		allAlbums.setItems(albumNames);
	}
	

	public void deleteContent(ActionEvent event) {
		// Method implementation
	}

	public void createNewContent(ActionEvent event) {
		// Method implementation
	}

	public void viewContent(ActionEvent event) {
		// Method implementation
	}
	public void renameContent(ActionEvent event) {
		// Method implementation
	}

	public void logOut(ActionEvent e)
			throws IOException {
		FXMLLoader load = new FXMLLoader();
		load.setLocation(getClass().getResource("/view/Login.fxml"));
		Parent parentView = (Parent) load.load();
		LoginController logincontroller = load.getController();
		logincontroller.setData(listOfUsers);
		Scene adminView = new Scene(parentView);
		Stage pictureStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		pictureStage.hide();
		pictureStage.setScene(adminView);
		pictureStage.show();
	}

	public void deleteAlbum(ActionEvent e) throws IOException {
		if (show.contains((Album) allAlbums.getSelectionModel().getSelectedItem())) {
			show.remove(allAlbums.getSelectionModel().getSelectedIndex());
		}
		allAlbums.setItems(show);
		this.save();
	}

	public void switchToSearch(ActionEvent e)
			throws IOException {
		FXMLLoader load = new FXMLLoader();
		load.setLocation(getClass().getResource("/view/SearchItem.fxml"));
		Parent adm_parent = (Parent) load.load();
		SearchController SearchController = load.getController();
		SearchController.setData(show, user, listOfUsers);
		Scene adminView = new Scene(adm_parent);
		Stage pictureStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		pictureStage.hide();
		pictureStage.setScene(adminView);
		pictureStage.show();
	}

	public void addAlbum(ActionEvent e) throws IOException {
		Album album = new Album(albumName.getText());
		show.add(album);
		allAlbums.setItems(show);
		this.save();
	}

	public void renameAlbum(ActionEvent e) throws IOException {
		Album album = (Album) allAlbums.getSelectionModel().getSelectedItem();
		album.setName(editName.getText());
		show.set(show.indexOf(album), (Album) allAlbums.getSelectionModel().getSelectedItem());
		allAlbums.setItems(show);
		this.save();
	}

	public void save() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Admin.fxml"));
		AdminController admincontroller = loader.getController();
		admincontroller.setUsers(this.listOfUsers);
		admincontroller.save();
	}

}