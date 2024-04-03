package hellofx.app;

import java.util.List;

import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Photo implements Serializable {
    private File file;
    private String path;
    private List<Tag> tags;
    private Calendar calendar;
    private String caption;
    private transient Image img;
    private static final long serialVersionUID = -2936687026040726549L;

    public Photo(Image img, File file, List<Tag> tags) {
        this.img = img;
        this.file = file;
        this.path = this.file.getPath();
        this.tags = tags;
        this.calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public Image getImage() {
        return this.img;
    }

    public void setImage(Image img) {
        this.img = img;
    }

    public String getPath() {
        return this.path;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public Date getDate() {
        return this.calendar.getTime();
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return this.caption;
    }

}