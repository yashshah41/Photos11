package app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class AlbumList implements Serializable {
    static final long serialVersionUID = 1L;

    private List<Album> albumList = new ArrayList<>();
    public final String dataFile = "albums.ser";

    /**serialize list of albums
     * @throws IOException
     */
    public void serialize() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile));
        oos.writeObject(albumList);
        oos.close();
    }

    private void init() {
        try {
            FileInputStream fileIn = new FileInputStream(dataFile);
            ObjectInputStream ois = new ObjectInputStream(fileIn);
            albumList = (ArrayList<Album>) ois.readObject();
            ois.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i){
            System.out.println("No albums exist or class is not found");
            albumList = new ArrayList<>();
        }
    }


    public void addAlbum(Album album) throws IOException {
        init();
        // enforce that album names are unique per user
        List<Album> filtered = albumList.stream()
                .filter(a -> a.getName().equals(album.getName()))
                .collect(Collectors.toList());
        if (!filtered.isEmpty()) {
            throw new IllegalArgumentException("Album name must be unique.");
        }
        albumList.add(album);
        serialize();
    }


    public Album getAlbum(String name) {
        init();
        List<Album> filtered = albumList.stream()
                .filter(a -> a.getName().equals(name))
                .collect(Collectors.toList());
        if (filtered.isEmpty()) {
            throw new NoSuchElementException("Album doesn't exist.");
        }
        return filtered.get(0);
    }


    public void editAlbum(String name, String newName) throws IOException {
        init();
        List<Album> filtered = albumList.stream()
                .filter(a -> a.getName().equals(name))
                .collect(Collectors.toList());
        if (filtered.isEmpty()) {
            throw new NoSuchElementException("Album does not exist.");
        }
        Album album = filtered.get(0);

        if (newName.equals(album.getName())) {
            return;
        }

       
        List<Album> dupFiltered = albumList.stream()
                .filter(a -> a.getName().equals(newName))
                .collect(Collectors.toList());
        if (!dupFiltered.isEmpty()) {
            throw new IllegalArgumentException("Album name must be unique.");
        }

        album.setName(newName);
        serialize();
    }

    public void deleteAlbum(String name) throws IOException {
        init();
        List<Album> filtered = albumList.stream()
                .filter(a -> a.getName().equals(name))
                .collect(Collectors.toList());
        if (filtered.isEmpty()) {
            throw new NoSuchElementException("The album to delete does not exist.");
        }
        // delete the album
        albumList = albumList.stream()
                .filter(a -> !a.getName().equals(name))
                .collect(Collectors.toList());
        serialize();
    }

    public List<Album> getAlbums() {
        init();
        return albumList;
    }
}
