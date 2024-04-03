package hellofx.app;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	public String userName;
	public ArrayList<Album> allAlbums;

	public User(String userName) {
		this.userName = userName;
		this.allAlbums = new ArrayList<Album>();
	}

	public String getUserName() {
		return userName;
	}
	public ArrayList<Album> getPhotoAlbum() {
		return allAlbums;
	}

	public void newAlbum(Album album) {
		boolean exists = false;
		if (album != null) {
			for (int i = 0; i < allAlbums.size();i++) {
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
		if(album != null) {
			for(int i = 0; i < allAlbums.size(); i++) {
				if(allAlbums.get(i).name.toLowerCase() == album.name.toLowerCase()) {
					index = i;
					break;
				}
			}
			album.name = newName;
		}
		
	}
	
	public void deleteAlbum(Album album) {
		if (album != null) {
			for (int i = 0; i < allAlbums.size();i++) {
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