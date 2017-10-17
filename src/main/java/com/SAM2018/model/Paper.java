package com.SAM2018.model;

import java.io.File;
import java.util.List;

public class Paper {

    //Attributes
    private List<User> authors;
    private User contactAuthor;
    private String title;
    private String format;
    private int version;
    private File paperUpload;

    public Paper(List<User> _authors, User _contactAuthor, String _title, String _format, int _version, File _paperUpload) {
        this.authors = _authors;
        this.contactAuthor = _contactAuthor;
        this.title = _title;
        this.format = _format;
        this.version = _version;
        this.paperUpload = _paperUpload;
    }

    public List<User> getAuthors() {
        return authors;
    }

    public User getContactAuthor() {
        return contactAuthor;
    }

    public String getTitle() {
        return title;
    }

    public String getFormat() {
        return format;
    }

    public int getVersion() {
        return version;
    }

    public File getPaperUpload() {
        return paperUpload;
    }
}
