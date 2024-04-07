package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import java.awt.image.BufferedImage;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import java.net.MalformedURLException;

import app.*;

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
	TextField capField;

	@FXML
	Label dateLabel;

	@FXML
	TextField dateField;

	@FXML
	Label tags;

	@FXML
	ListView tagList;

	ObservableList<Tag> taglist = FXCollections.observableArrayList();
	ObservableList<User> members = FXCollections.observableArrayList();
	Album album;
	User user;
	Photo photo;

	public void setData(List<User> members, Photo photo, Album album, User user) throws MalformedURLException{
		this.members = FXCollections.observableArrayList(members);
	    this.album = album;
	    this.user = user;
	    this.photo = photo;
	}

	public void initalizeImage(Photo photo) throws MalformedURLException, IOException{
		String path = photo.getFile().getAbsolutePath();
		InputStream instream = new FileInputStream(path);
		Image image = new Image(instream);
		picView.setImage(image);
		System.out.print(photo.getDate().toString());
		String date = photo.getDate().toString().substring(0, 11);
		dateField.setText(date);
	    taglist = FXCollections.observableArrayList(photo.getTags());
		tagList.setItems(taglist);
		capField.setText(photo.getCaption());
	}

	public void back(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(getClass().getResource("/view/PhotosInAlbum.fxml"));
	    Parent oa_parent = (Parent)loader.load();
	    PhotosInAlbumController oacontroller = loader.getController();
	    oacontroller.setData(album, members, user);
	    Scene admin_scene = new Scene(oa_parent);
	    Stage photoStage = (Stage)((Node) e.getSource()).getScene().getWindow();
	    photoStage.hide();
	    photoStage.setScene(admin_scene);
	    photoStage.show();
	}

	public void next(ActionEvent e) throws FileNotFoundException{
		List<Photo> photos = this.album.getAllPhotos();
		if(photos.get(photos.size()-1) != this.photo){
			for(int i = 0; i < photos.size()-1; i ++){
				if(photos.get(i) == this.photo){
					this.photo = photos.get(i+1);
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

	public void prev(ActionEvent e) throws FileNotFoundException{
		List<Photo> photos = this.album.getAllPhotos();
		if(photos.get(0) != this.photo){
			for(int i = 0; i < photos.size(); i ++){
				if(photos.get(i) == this.photo){
					this.photo = photos.get(i-1);
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
