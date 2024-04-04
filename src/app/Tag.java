package app;
import java.io.Serializable;
import java.util.List;

public class Tag implements Serializable{
	public String name;
	public String value;
	List<Photo> photos;
	public Tag(String name,String value, List<Photo> photos){
		this.name = name;
		this.value = value;
		this.photos = photos;
	}
	public void addPhoto(Photo photo){
		this.photos.add(photo);
	}
	public void removePhoto(Photo photo){
		photos.remove(photo);
	}
	public String getName(){
		return this.name;
	}
	public String getValue(){
		return this.value;
	}
	public String toString(){
		return this.name + ": "+ this.value;
	}

}