package hellofx.app;

import java.util.List;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Photo implements Serializable {
    public File file;
    public String path;
    public List<Tag> tags;
    public Calendar calendar;
    public String caption;
    public Date lastModifiedDate;

    public Photo(File file, List<Tag> tags) {
        this.file = file;
        this.path = this.file.getPath();
        this.lastModifiedDate = new Date(this.file.lastModified());
        this.tags = tags;
        calendar.set(Calendar.MILLISECOND, 0);
        this.calendar = Calendar.getInstance();
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