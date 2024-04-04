package hellofx.app;
import java.util.List;

public class Album {
    public List<Photo> photos;
    String name;
    public Album(String userName, List<Photo> photos) {
        this.name = userName;
        this.photos = photos;
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
    public void setName(String name) {
        this.name = name;
    }
}
