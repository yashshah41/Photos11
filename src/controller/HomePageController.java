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
	Button searchButton;

	@FXML
	Button finishButton;

	@FXML
	Button openAlbum;

	@FXML
	Button viewContent;

	@FXML
	ListView<String> allAlbums; // This will now hold Strings

	@FXML
	Button createContent;

	@FXML
	TextField contentName;

	@FXML
	TextField editName;

	@FXML
	TextField editNum;

	@FXML
	TextField editRange;

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
	
	

	public void createNewContent(ActionEvent event) throws IOException {
		String contentNameText = contentName.getText().trim();
		boolean albumExists = user.getAllAlbums().stream().anyMatch(album -> album.getName().equalsIgnoreCase(contentNameText));
	
		if (!albumExists) {
			Album newAlbum = new Album(contentNameText);
			
			user.getAllAlbums().add(newAlbum); 
	
			show.add(newAlbum);
		
			ObservableList<String> contentNames = FXCollections.observableArrayList(
				user.getAllAlbums().stream().map(Album::getName).collect(Collectors.toList())
			);
			allAlbums.setItems(contentNames);
			this.contentName.clear();
		
			this.save();
		}
	}
	

	public void viewContent(ActionEvent event) {
		Album target = null;
		if (allAlbums.getSelectionModel().getSelectedItem() != null) {
			// Check if an item is selected
			String selectedAlbumName = allAlbums.getSelectionModel().getSelectedItem();
			target = user.getAllAlbums().stream()
					.filter(album -> album.getName().equals(selectedAlbumName))
					.findFirst()
					.orElse(null);
		}
	
		if (target != null) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/PhotosInAlbum.fxml"));
				Parent admin_parent = (Parent) loader.load();
				PhotosInAlbumController oacontroller = loader.getController();
				oacontroller.setData(target, listOfUsers, user);
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
		this.save();
	}
	

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