package hellofx.controller;
import hellofx.app.*;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlbumNavigator implements java.io.Serializable {
  

	@FXML
	ListView<Photo> photosList;

	@FXML
	Button back;
	Label tags;
	
	@FXML
	ListView<Tag> tagList;
	
	@FXML
	TextField tag;
	
	@FXML
	Button addTag;

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
	TextField value;
	
	
	
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
	}

	public void addPhoto(ActionEvent e) {
		// figure this out 
	}

	public void addCaption(ActionEvent e) {
	    String caption = this.capField.getText();
	    if(!(caption.equals(null))){
	    	Photo target = (Photo) photosList.getSelectionModel().getSelectedItem();
	    	target.setCaption(caption);
		    photosList.setItems(pictures);
			photosList.setItems(pictures);
			capField.setText("");
		}
	}	   
	public void addTag(ActionEvent e) {
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

		public void deleteTag(ActionEvent e) {
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
