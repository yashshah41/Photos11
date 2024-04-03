package hellofx.controller;

import hellofx.app.*;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;

public class AlbumNavigator implements java.io.Serializable {
  

	@FXML
	ListView<Photo> photosList;

	@FXML
	Button back;
	Label tags;
	
	@FXML
	ListView<Tag> tagList;
	
	@FXML
	Button deleteTag;
	
	@FXML
	TextField tag;
	
	@FXML
	Button addTag;
	
	@FXML 
	Label caption;

	@FXML
	TextField capField;
	
	@FXML
	Button updateCaption;
	
	@FXML
	Button addPhoto;
	
	@FXML
	Button removePhoto;
	
	@FXML
	ToggleButton next;
	
	@FXML
	ToggleButton prev;
	
	@FXML
	Button moveOrCopy;
	
	@FXML
	Button displayMode;
	
	@FXML
	TextField value;
	
	@FXML 
	Label tagLabel;
	
	Album album;
	
    ObservableList<Photo> pictures = FXCollections.observableArrayList();
    ObservableList<Tag> tagsInPhoto = FXCollections.observableArrayList();
    User user;
	
	public void setData(Album album, List<User> members, User user){
		this.album = album;
		this.user = user;
		if(album.getAllPhotos() == null){
			return;
		}
	    // idk where to go from here
	}

	public void addPhoto(ActionEvent e) throws IOException{
		// figure this out 
	}

	    public void addCaption(ActionEvent e) throws IOException{
	    	String caption = this.capField.getText();
	    	if(!(caption.equals(null))){
	    		Photo target = (Photo) photosList.getSelectionModel().getSelectedItem();
	    		target.setCaption(caption);
		        photosList.setItems(pictures);
			    photosList.setItems(pictures);
			    capField.setText("");
	    	}
	    }	   
	    public void addTag(ActionEvent e) throws IOException{
	    	String name = tag.getText();
	    	String value = this.value.getText();
	    	if(!(name.equals(null) && value.equals(null))){	    		
	    		List<Photo> photos = new ArrayList<Photo>();
	    		Photo tagged = (Photo) photosList.getSelectionModel().getSelectedItem();
	    		photos.add(tagged);
	    		Tag newTag = new Tag(name, value, photos);
	    		this.user.addTag(newTag);
	    		tagged.addTag(newTag);
	    		tagsInPhoto.add(newTag);
	    	}
	    }

		public void deleteTag(ActionEvent e) throws IOException {
			Photo target = (Photo) photosList.getSelectionModel().getSelectedItem();
			Tag selected = (Tag) tagList.getSelectionModel().getSelectedItem();
			if (target.getTags().contains(selected)) {
				target.removeTag(selected);
				this.user.deleteTag(selected);
			}
		}
		
	    
	    
	 
	    public void updateTag(MouseEvent m){
	    	Photo photo = (Photo) photosList.getSelectionModel().getSelectedItem();
	    	tagsInPhoto = FXCollections.observableArrayList(photo.getTags());
	    	tagList.setItems(tagsInPhoto);
	    }
	   
	    public void removePhoto(ActionEvent e) throws IOException{
	    	Photo target = (Photo) photosList.getSelectionModel().getSelectedItem();
	        // figure out? 
	    }
	    public void next(ActionEvent e){
	    	Photo selected = (Photo) photosList.getSelectionModel().getSelectedItem();
	    	if(pictures.get(pictures.size()-1) != selected){
	    		photosList.getSelectionModel().selectNext();
	    	}
	    }

		public void prev(ActionEvent e) {
			Photo selected = (Photo) photosList.getSelectionModel().getSelectedItem();
			if (pictures.get(0) != selected) {
				photosList.getSelectionModel().selectPrevious();
			}
		}

		public void save() {
			// write this
		}
}
