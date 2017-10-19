package com.SAM2018.model;

import java.io.File;
import java.util.List;

public class Paper {

    //Attributes
    private int paperID;
    private List<String> authors;
    private User contactAuthor;
    private String title;
    private String format;
    private int version;
    //private File paperUpload;
    private String paperUpload;

    public Paper(int _paperID, List<String> _authors, User _contactAuthor, String _title, String _format, int _version, String _paperUpload) {
        this.paperID = _paperID;
        this.authors = _authors;
        this.contactAuthor = _contactAuthor;
        this.title = _title;
        this.format = _format;
        this.version = _version;
        this.paperUpload = _paperUpload;
    }

    public int getPaperID() {
        return paperID;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getAuthorsAsString() {
        String authorString = "";
        for(String auth : authors) {
            authorString = authorString.concat(auth + ", ");
        }

        authorString = authorString.substring(0, authorString.length() - 2);

        return authorString;
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

    public String getPaperUpload() {
        return paperUpload;
    }
}
