package hellofx.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Album {

    public ArrayList<Photo> photos;
    String name;

    public Album(String userName) {
        this.name = userName;
        this.photos = new ArrayList<Photo>();
    }

    public Photo getPhotoByIndex(int index) {
        Photo toReturn = this.photos.get(index);
        return toReturn;
    }

    public ArrayList<Photo> getAllPhotos() {
        return this.photos;
    }

    public int getCount() {
        int len = this.photos.size();
        return len;

    }

    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }
  

    public class sorter implements Comparator<Photo> {
        public int compare(Photo x, Photo y) {
            return x.getDateTime().compareTo(y.getDateTime());
        }

    }

}
