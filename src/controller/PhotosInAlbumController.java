package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.image.BufferedImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Calendar;
import javax.imageio.ImageIO;

import app.*;

public class PhotosInAlbumController {

	@FXML
	ListView<Photo> imagesList;

	@FXML
	Label tags;

	@FXML
	TextField tag;

	@FXML
	ListView<Tag> tagList;

	@FXML
	ListView<String> tagNameList;

	@FXML
	Button addTagButton;

	@FXML
	Button deleteTagButton;

	@FXML
	TextField captionField;

	@FXML
	Label captionLabel;

	@FXML
	Button updateCaptionButton;

	@FXML
	Button addPhotoButton;

	@FXML
	Button removePhotoButton;

	@FXML
	Button backButton;

	@FXML
	ToggleButton nextButton;

	@FXML
	ToggleButton prevButton;

	@FXML
	Button moveOrCopyButton;

	@FXML
	Button displayModeButton;

	@FXML
	TextField newTagValue;

	@FXML
	Label tagLabel;

	Album album;
	ObservableList<Photo> picturesList = FXCollections.observableArrayList();
	ObservableList<Tag> tagsInPhoto = FXCollections.observableArrayList();
	List<User> members;
	User user;

	
	/** 
	 * @param album
	 * @param members
	 * @param user
	 */
	public void setData(Album album, List<User> members, User user) {
		this.album = album;
		this.members = members;
		this.user = user;
		if (album.getAllPhotos() != null) {
			picturesList = FXCollections.observableArrayList(album.getAllPhotos());
		} else {
			// Initialize with an empty list to avoid NullPointerException
			picturesList = FXCollections.observableArrayList();
		}
		imagesList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {
			@Override
			public ListCell<Photo> call(ListView<Photo> p) {

				ListCell<Photo> cell = new ListCell<Photo>() {

					@Override
					protected void updateItem(Photo t, boolean boo) {
						super.updateItem(t, boo);
						setText(null);
						setGraphic(null);
						if (t != null) {
							File file = t.getFile();
							if (file.exists() && !file.isDirectory()) {
								try (InputStream instream = new FileInputStream(file)) {
									Image image = new Image(instream);
									ImageView imageView = new ImageView(image);
									imageView.setFitHeight(100);
									imageView.setFitWidth(100);
									imageView.setPreserveRatio(true);
									setText(t.getCaption());
									setGraphic(imageView);
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {
								System.err.println("File not found: " + file.getAbsolutePath());
							}
						}
					}

				};

				refreshTagNameList();
				return cell;
			}
		});
		imagesList.setItems(picturesList);
		imagesList.getSelectionModel().selectFirst();
		Photo selected = (Photo) imagesList.getSelectionModel().getSelectedItem();
		if (selected != null) {
			if (selected.getTags() != null) {
				tagsInPhoto = FXCollections.observableArrayList(selected.getTags());
			} else {
				tagsInPhoto = FXCollections.observableArrayList();
			}
			tagList.setItems(tagsInPhoto);
		}

		// Populate tagNameList with unique tag names
		Set<String> uniqueTagNames = user.getAllTags().stream().map(Tag::getName).collect(Collectors.toSet());
		// System.out.println("Unique tag names: " + uniqueTagNames.size() + " names");
		uniqueTagNames.add("location");
		uniqueTagNames.add("person");
		uniqueTagNames.add("event");
		// System.out.println("Unique tag names: " + uniqueTagNames.size() + " names");
		tagNameList.setItems(FXCollections.observableArrayList(uniqueTagNames));
		tagNameList.getItems().clear();

	}

	
	/** 
	 * @param e
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void addPhoto(ActionEvent e) throws IOException {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter exfilJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.png", "*.bmp", "*.gif");
	
		fileChooser.getExtensionFilters().addAll(exfilJPG);
		File file = fileChooser.showOpenDialog(null);
		if (file == null) {
			return;
		}
		for (User u : members) {
			for (Album a : u.getAllAlbums()) {
				for (Photo p : a.getAllPhotos()) {
					if (p.getFile().equals(file)) {
						this.picturesList.add(p);
						this.album.addPhoto(p);
						imagesList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {

							@Override
							public ListCell<Photo> call(ListView<Photo> p) {
				
								ListCell<Photo> cell = new ListCell<Photo>() {
				
									@Override
									protected void updateItem(Photo t, boolean boo) {
										super.updateItem(t, boo);
										if (t != null) {
											ImageView imageView = new ImageView();
											String path = t.getFile().getAbsolutePath();
											InputStream instream = null;
											try {
												instream = new FileInputStream(path);
											} catch (FileNotFoundException e) {
												e.printStackTrace();
											}
											Image image = new Image(instream);
											imageView.setImage(image);
											imageView.setFitHeight(100);
											imageView.setFitWidth(100);
											imageView.setPreserveRatio(true);
											setText(t.getCaption());
											setGraphic(imageView);
										}
									}
				
								};
								return cell;
							}
						});
						imagesList.setItems(picturesList);
						this.save();
						return;
					}
				}
			}
			
		}

		ImageView picture = new ImageView();
		Image image = new Image(file.toURL().toString(), 353, 341, true, true);
		picture.setImage(image);
		picture.setFitHeight(341);
		picture.setFitWidth(353);
		picture.setPreserveRatio(true);
		List<Tag> tags = new ArrayList<Tag>();
		Photo photo = new Photo(file, tags);
		this.picturesList.add(photo);
		this.album.addPhoto(photo);

		imagesList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {

			@Override
			public ListCell<Photo> call(ListView<Photo> p) {

				ListCell<Photo> cell = new ListCell<Photo>() {

					@Override
					protected void updateItem(Photo t, boolean boo) {
						super.updateItem(t, boo);
						if (t != null) {
							ImageView imageView = new ImageView();
							String path = t.getFile().getAbsolutePath();
							InputStream instream = null;
							try {
								instream = new FileInputStream(path);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Image image = new Image(instream);
							imageView.setImage(image);
							imageView.setFitHeight(100);
							imageView.setFitWidth(100);
							imageView.setPreserveRatio(true);
							setText(t.getCaption());
							setGraphic(imageView);
						}
					}

				};
				return cell;
			}
		});
		imagesList.setItems(picturesList);
		this.save();
	}

	public void addCaption(ActionEvent e) throws IOException {
		String caption = this.captionField.getText();
		if (!(caption.equals(null))) {
			Photo target = (Photo) imagesList.getSelectionModel().getSelectedItem();
			target.setCaption(caption);
			imagesList.setItems(picturesList);
			imagesList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {

				@Override
				public ListCell<Photo> call(ListView<Photo> p) {

					ListCell<Photo> cell = new ListCell<Photo>() {

						@Override
						protected void updateItem(Photo t, boolean boo) {
							super.updateItem(t, boo);
							if (t != null) {
								ImageView imageView = new ImageView();
								String path = t.getFile().getAbsolutePath();
								InputStream instream = null;
								try {
									instream = new FileInputStream(path);
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
								Image image = new Image(instream);
								imageView.setImage(image);
								imageView.setFitHeight(100);
								imageView.setFitWidth(100);
								imageView.setPreserveRatio(true);
								setText(t.getCaption());
								setGraphic(imageView);
							}
						}

					};
					return cell;
				}
			});
			imagesList.setItems(picturesList);
			captionField.setText("");
			this.save();
		}
	}

	public void addTag(ActionEvent e) throws IOException {
		String name = tag.getText().trim();
		String value = this.newTagValue.getText().trim();
	
		if (!name.isEmpty() && !value.isEmpty()) {
			Photo selectedPhoto = imagesList.getSelectionModel().getSelectedItem();
			if (selectedPhoto == null) {
				return;
			}
	
			boolean duplicateExists = selectedPhoto.getTags().stream()
					.anyMatch(t -> t.getName().equalsIgnoreCase(name) && t.getValue().equalsIgnoreCase(value));
	
			if (!duplicateExists) {
				List<Photo> photosForNewTag = new ArrayList<>();
				photosForNewTag.add(selectedPhoto);
	
				Tag newTag = new Tag(name, value, photosForNewTag);
				selectedPhoto.addTag(newTag);
				tagsInPhoto.add(newTag);
				tagList.setItems(tagsInPhoto);
	
				user.addTag(newTag); 
				refreshTagNameList(); 
			}
	
			tag.setText("");
			this.newTagValue.setText("");
			this.save();
		}
	}


	public void tagNameListClicked(MouseEvent event) {
		if (event.getClickCount() == 1) {
			String selectedTagName = tagNameList.getSelectionModel().getSelectedItem();
			tag.setText(selectedTagName); 
		}
	}
	

	private void refreshTagNameList() {
		Set<String> uniqueTagNames = user.getAllTags().stream()
										  .map(Tag::getName)
										  .collect(Collectors.toSet());
		tagNameList.setItems(FXCollections.observableArrayList(uniqueTagNames));
		// System.out.println("Refreshing tag names, found: " + uniqueTagNames.size() + " unique names");
	}
	

	public void deleteTag(ActionEvent e) throws IOException {
		Photo target = (Photo) imagesList.getSelectionModel().getSelectedItem();
		Tag selected = (Tag) tagList.getSelectionModel().getSelectedItem();
		if (target.getTags().contains(selected)) {
			target.removeTag(selected);
			this.user.deleteTag(selected);
		}
		tagsInPhoto = FXCollections.observableArrayList(target.getTags());
		tagList.setItems(tagsInPhoto);
		this.save();
	}

	public void back(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/HomePage.fxml"));
		Parent parentView = (Parent) loader.load();
		HomePageController usercontroller = loader.getController();
		usercontroller.setData(user, members);
		Scene adminView = new Scene(parentView);
		Stage pictureStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		pictureStage.hide();
		pictureStage.setScene(adminView);
		pictureStage.show();
	}

	public void displayMode(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/ImageInfo.fxml"));
		Parent parentView = (Parent) loader.load();
		ImageInfoController imageInfoController = loader.getController();
		Photo selected = (Photo) imagesList.getSelectionModel().getSelectedItem();
		imageInfoController.setData(members, selected, album, user);
		imageInfoController.initalizeImage(selected);
		Scene adminView = new Scene(parentView, 1300, 850);
		Stage pictureStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		pictureStage.hide();
		pictureStage.setScene(adminView);
		pictureStage.show();
	}

	public void updateTag(MouseEvent m) {
		Photo photo = (Photo) imagesList.getSelectionModel().getSelectedItem();
		tagsInPhoto = FXCollections.observableArrayList(photo.getTags());
		tagList.setItems(tagsInPhoto);
	}

	public void removePhoto(ActionEvent e) throws IOException {
		Photo target = (Photo) imagesList.getSelectionModel().getSelectedItem();
		if (this.album.getAllPhotos().contains(target)) {
			this.album.removePhoto(target);
		}
		picturesList.remove(target);
		imagesList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {

			@Override
			public ListCell<Photo> call(ListView<Photo> p) {

				ListCell<Photo> cell = new ListCell<Photo>() {

					@Override
					protected void updateItem(Photo t, boolean boo) {
						super.updateItem(t, boo);
						if (t != null) {
							ImageView imageView = new ImageView();
							String path = t.getFile().getAbsolutePath();
							InputStream instream = null;
							try {
								instream = new FileInputStream(path);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							Image image = new Image(instream);
							imageView.setImage(image);
							imageView.setFitHeight(100);
							imageView.setFitWidth(100);
							imageView.setPreserveRatio(true);
							setText(t.getCaption());
							setGraphic(imageView);
						}
					}

				};
				return cell;
			}
		});
		imagesList.setItems(picturesList);
		this.save();
	}

	public void prevButton(ActionEvent e) {
		Photo selected = (Photo) imagesList.getSelectionModel().getSelectedItem();
		if (picturesList.get(0) != selected) {
			imagesList.getSelectionModel().selectPrevious();
		}
	}

	public void nextButton(ActionEvent e) {
		Photo selected = (Photo) imagesList.getSelectionModel().getSelectedItem();
		if (picturesList.get(picturesList.size() - 1) != selected) {
			imagesList.getSelectionModel().selectNext();
		}
	}

	public void switchToMC(ActionEvent e) throws IOException {
		FXMLLoader load = new FXMLLoader();
		load.setLocation(getClass().getResource("/view/MoveItem.fxml"));
		Parent parentView = (Parent) load.load();
		MoveItemController movecontroller = load.getController();
		Photo selected = (Photo) imagesList.getSelectionModel().getSelectedItem();
		movecontroller.setData(user, album, selected, members);
		Scene adminView = new Scene(parentView);
		Stage pictureStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		pictureStage.hide();
		pictureStage.setScene(adminView);
		pictureStage.show();
	}

	public void save() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Admin.fxml"));
		Parent parentView = (Parent) loader.load();
		AdminController admincontroller = loader.getController();
		admincontroller.setUsers(this.members);
		admincontroller.save();
	}
}
