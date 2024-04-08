package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

/**
 * Controls the search functionality within the application, allowing users to
 * search for photos
 * based on dates or tags and to create new albums from the search results.
 */

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

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the FXML file has been loaded. It sets up the search type choices.
	 * 
	 * @version 1.0
	 * @author Ayush Gupta
	 */

	public void initialize() {
		searchType.setItems(FXCollections.observableArrayList("AND", "OR"));
	}

	/**
	 * Sets the necessary data for the search functionality.
	 * 
	 * @param albums The list of albums to search from.
	 * @param user   The user performing the search.
	 * @param users  The list of all users, for saving changes globally.
	 */

	public void setData(List<Album> albums, User user, List<User> users) {
		this.albums = albums;
		this.user = user;
		this.users = users;
	}

	/**
	 * Converts a Date to LocalDate using the system's default zone.
	 * 
	 * @param dateToConvert The date to convert.
	 * @return The corresponding LocalDate.
	 */

	private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}

	/**
	 * Searches for photos by a specified date range and displays the results.
	 * 
	 * @param event The action event triggering this search.
	 */

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

	/**
	 * Searches for photos by specified tags and displays the results.
	 * 
	 * @param event The action event triggering this search.
	 */

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

	/**
	 * Displays the search results in a ListView with custom cell formatting to show
	 * photo thumbnails.
	 * 
	 * @param photos The list of photos that matched the search criteria.
	 */

	public void displayPhotos(List<Photo> photos) {
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

	/**
	 * Creates a new album with the specified name from the search results.
	 * 
	 * @param event The action event triggering this operation.
	 * @throws IOException If there is an issue creating the album.
	 */

	@FXML
	private void createAlbum(ActionEvent event) throws IOException {
		String name = albumName.getText().trim();
		if (!name.isEmpty() && !result.getItems().isEmpty()) {
			Album newAlbum = new Album(name, new ArrayList<>(result.getItems()));
			user.addAlbum(newAlbum);
			backToHome(event);
			this.save();
		}
	}

	/**
	 * Returns the user to the home page.
	 * 
	 * @param e The action event triggering this operation.
	 * @throws IOException If there is an issue loading the home page.
	 */
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

	/**
	 * Saves the current state, including any changes made to the users' albums.
	 */

	public void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream("users.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(new ArrayList<User>(users));
			out.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}