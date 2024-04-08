package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import app.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SearchController {
	@FXML
	private Button backButton, searchButton, createButton;
	@FXML
	private DatePicker startDatePicker, endDatePicker;
	@FXML
	private TextField tag1, value1, tag2, value2, albumName;
	@FXML
	private ComboBox<String> searchType;
	@FXML
	private ListView<Photo> result;

	private User user;
	private List<Album> albums;
	List<User> users;

	public void initialize() {
		searchType.setItems(FXCollections.observableArrayList("AND", "OR"));
	}

	public void setData(List<Album> albums, User user, List<User> users) {
		this.albums = albums;
		this.user = user;
		this.users = users;
	}

	private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}

	@FXML
	private void searchPhotosByDate(ActionEvent event) {
		LocalDate startDate = startDatePicker.getValue();
		LocalDate endDate = endDatePicker.getValue();

		List<Photo> matchedPhotos = new ArrayList<>();
		for (Album album : albums) {
			for (Photo photo : album.getAllPhotos()) {
				LocalDate photoDate = convertToLocalDateViaInstant(photo.getDate());
				if ((startDate == null || !photoDate.isBefore(startDate))
						&& (endDate == null || !photoDate.isAfter(endDate))) {
					matchedPhotos.add(photo);
				}
			}
		}

		displayPhotos(matchedPhotos);

	}

	@FXML
	private void searchPhotosByTag(ActionEvent event) {
		String firstTag = tag1.getText().trim();
		String firstValue = value1.getText().trim();
		String secondTag = tag2.getText().trim();
		String secondValue = value2.getText().trim();
		String searchMethod = searchType.getValue();

		List<Photo> matchedPhotos = new ArrayList<>();
		for (Album album : albums) {
			for (Photo photo : album.getAllPhotos()) {
				boolean tagMatch = false;

				if (!firstTag.isEmpty() && !firstValue.isEmpty()) {
					boolean firstTagMatch = photo.hasTagValuePair(firstTag, firstValue);
					boolean secondTagMatch = !secondTag.isEmpty() && !secondValue.isEmpty() &&
							photo.hasTagValuePair(secondTag, secondValue);

					if ("AND".equals(searchMethod)) {
						tagMatch = firstTagMatch && secondTagMatch;
					} else if ("OR".equals(searchMethod)) {
						tagMatch = firstTagMatch || secondTagMatch;
					} else {
						tagMatch = firstTagMatch || secondTagMatch;
					}
				}

				if (tagMatch) {
					matchedPhotos.add(photo);
				}
			}
		}

		displayPhotos(matchedPhotos);
	}

	private void displayPhotos(List<Photo> photos) {
		result.setItems(FXCollections.observableArrayList(photos));
		result.setCellFactory(lv -> new ListCell<Photo>() {
			@Override
			protected void updateItem(Photo photo, boolean empty) {
				super.updateItem(photo, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					ImageView imageView = new ImageView();
					try (FileInputStream instream = new FileInputStream(photo.getFile().getAbsolutePath())) {
						Image image = new Image(instream);
						imageView.setImage(image);
						imageView.setFitHeight(100);
						imageView.setFitWidth(100);
						imageView.setPreserveRatio(true);
						setText(photo.getCaption());
						setGraphic(imageView);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@FXML
	private void createAlbum(ActionEvent event) throws IOException {
		String name = albumName.getText().trim();
		if (!name.isEmpty() && !result.getItems().isEmpty()) {
			Album newAlbum = new Album(name, new ArrayList<>(result.getItems()));
			user.addAlbum(newAlbum);
			backToHome(event);
		}
	}

	@FXML
	public void backToHome(ActionEvent e) throws IOException {
		FXMLLoader load = new FXMLLoader();
		load.setLocation(getClass().getResource("/view/HomePage.fxml"));
		Parent parentView = (Parent) load.load();
		HomePageController usercontroller = load.getController();
		usercontroller.setData(user, users);
		Scene adminView = new Scene(parentView);
		Stage pictureStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		pictureStage.hide();
		pictureStage.setScene(adminView);
		pictureStage.show();
	}

	@FXML
	private void searchByDate(ActionEvent event) {

	}

	@FXML
	private void searchByTag(ActionEvent event) {

	}

}