package app;

import java.util.List;
import java.util.ArrayList;

public class Album {
    public List<Photo> photos;
    String name;

    public Album(String userName, List<Photo> photos) {
        this.name = userName;
        this.photos = photos;
    }
    public Album(String userName) {
        this.name = userName;
        this.photos = new ArrayList<Photo>();
    }
    
    public Photo getPhotoByIndex(int index) {
        Photo toReturn = this.photos.get(index);
        return toReturn;
    }
    public List<Photo> getAllPhotos() {
        return this.photos;
    }
    public int getCount() {
        int len = this.photos.size();
        return len;

    }
    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
