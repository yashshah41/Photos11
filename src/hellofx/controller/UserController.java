package hellofx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hellofx.app.Album;
import hellofx.app.Photo;
import hellofx.app.User;
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


public class UserController {
	
    @FXML
	Button deleteButton;
	
	
	@FXML
	Button logoutButton;
	
	@FXML
	Button openAlbum;
	
	@FXML
	ListView allAlbums;
	
	@FXML 
	Button createAlbum;
	
	@FXML
	TextField albumName;
	
	@FXML
	TextField editName;

	User user;
	
	ObservableList<Album> show = FXCollections.observableArrayList();
	
	List<User> members;
	
	public void setData(User user,List<User> users){
	    this.user = user;
		this.members = users;
		show = FXCollections.observableArrayList(user.getAlbums());
		allAlbums.setItems(show);		
	}
	public void save() throws IOException{
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(getClass().getResource("/view/Admin.fxml"));
	    AdminController admincontroller = loader.getController();
	    admincontroller.setUsers(this.members);
	    admincontroller.save();
	}
	public void logOut(ActionEvent e)
			throws IOException{
				FXMLLoader load = new FXMLLoader();
			    load.setLocation(getClass().getResource("/view/Login.fxml"));
			    Parent admin_parent = (Parent)load.load();
			    LoginController logincontroller = load.getController();
			    logincontroller.setData(members);
			    Scene admin_scene = new Scene(admin_parent);
			    Stage photoStage = (Stage)((Node) e.getSource()).getScene().getWindow();
			    photoStage.hide();
			    photoStage.setScene(admin_scene);
			    photoStage.show();
			}
	
	public void deleteAlbum(ActionEvent e) throws IOException{
		if(show.contains((Album) allAlbums.getSelectionModel().getSelectedItem())){
			show.remove(allAlbums.getSelectionModel().getSelectedIndex());
		}
		allAlbums.setItems(show);
		this.save(); 
	}
	public void switchToSearch(ActionEvent e) throws IOException{
				// to implement
			}

    public void renameAlbum(ActionEvent e) throws IOException{
    	Album album = (Album)allAlbums.getSelectionModel().getSelectedItem();
    	album.setName(editName.getText());
    	show.set(show.indexOf(album),  (Album)allAlbums.getSelectionModel().getSelectedItem());
    	allAlbums.setItems(show);
    	this.save();
    }

}