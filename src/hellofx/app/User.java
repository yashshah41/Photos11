package hellofx.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

	public String userName;
	public ArrayList<Album> allAlbums;
	public List<Tag> tags;

	public User(String userName, List<Tag> tags) {
		this.userName = userName;
		this.allAlbums = new ArrayList<Album>();
		this.tags = tags;
	}

	public User(String userName, ArrayList<Album> allAlbums) {
		this.userName = userName;
		this.allAlbums = allAlbums;
		this.tags = new ArrayList<Tag>();	
	}


	public User(String userName) {
		this.userName = userName;
		this.allAlbums = new ArrayList<Album>();
		this.tags = new ArrayList<Tag>();	
	}

	public String getUserName() {
		return userName;
	}

	public ArrayList<Album> getAllAlbums() {
		return this.allAlbums;
	}

	public List<Tag> getAllTags() {
		return this.tags;
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
	public void deleteTag(Tag tag) {
		this.tags.remove(tag);
	}
	

	public ArrayList<Album> getPhotoAlbum() {
		return allAlbums;
	}

	public void newAlbum(Album album) {
		boolean exists = false;
		if (album != null) {
			for (int i = 0; i < allAlbums.size(); i++) {
				if (allAlbums.get(i).name.toLowerCase() == album.name.toLowerCase()) {
					exists = true;
					break;
				}
			}
		}
		if (!exists) {
			allAlbums.add(album);
		}
	}

	public void renameAlbum(Album album, String newName) {
		int index;
		if (album != null) {
			for (int i = 0; i < allAlbums.size(); i++) {
				if (allAlbums.get(i).name.toLowerCase() == album.name.toLowerCase()) {
					index = i;
					break;
				}
			}
			album.name = newName;
		}

	}

	public void deleteAlbum(Album album) {
		if (album != null) {
			for (int i = 0; i < allAlbums.size(); i++) {
				if (allAlbums.get(i).equals(album)) {
					allAlbums.remove(i);
					return;
				}
			}
		}
	}

	public Album getAlbum(int index) {
		return this.allAlbums.get(index);
	}
}