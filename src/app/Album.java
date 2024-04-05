/**
 * The Album class represents a collection of photographs in the application.
 * It contains a list of photos, along with methods to interact with the collection.
 */

package app;

import java.util.List;
import java.util.ArrayList;

public class Album {

    /** The list of photos in the album. */
    public List<Photo> photos;
    
    /** The name of the album. */
    String name;

    /**
     * Constructor to create an Album object with a given list of photos.
     * 
     * @param userName The name of the album.
     * @param photos The list of photos to initialize the album with.
     */
    public Album(String userName, List<Photo> photos) {
        this.name = userName;
        this.photos = photos;
    }

    /**
     * Constructor to create an Album object with an empty list of photos.
     * 
     * @param userName The name of the album.
     */
    public Album(String userName) {
        this.name = userName;
        this.photos = new ArrayList<Photo>();
    }
    
    /**
     * Get a photo from the album by its index.
     * 
     * @param index The index of the photo in the album.
     * @return The photo at the specified index.
     */
    public Photo getPhotoByIndex(int index) {
        Photo toReturn = this.photos.get(index);
        return toReturn;
    }

    /**
     * Get all photos in the album.
     * 
     * @return The list of all photos in the album.
     */
    public List<Photo> getAllPhotos() {
        return this.photos;
    }

    /**
     * Get the count of photos in the album.
     * 
     * @return The number of photos in the album.
     */
    public int getCount() {
        int len = this.photos.size();
        return len;
    }

    /**
     * Add a photo to the album.
     * 
     * @param photo The photo to add to the album.
     */
    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }

    /**
     * Get the name of the album.
     * 
     * @return The name of the album.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the album.
     * 
     * @param name The new name of the album.
     */
    public void setName(String name) {
        this.name = name;
    }
}