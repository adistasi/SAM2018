package com.SAM2018.model;

import java.util.Arrays;
import java.util.List;

/**
 * A class to represent a Paper submitted to SAM2018
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
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

    /**
     * Constructor for the Paper class
     * @param _paperID ID of the paper used for internal tracking
     * @param _authors A list of author names submitted with the paper
     * @param _contactAuthor The user account that submitted the paper
     * @param _title The title of the paper
     * @param _format The submitted file format of the paper
     * @param _version The version number of the paper (essentially how many times the paper has been edited)
     * @param _paperUpload The uploaded paper file
     */
    public Paper(int _paperID, List<String> _authors, User _contactAuthor, String _title, String _format, int _version, String _paperUpload) {
        this.paperID = _paperID;
        this.authors = _authors;
        this.contactAuthor = _contactAuthor;
        this.title = _title;
        this.format = _format;
        this.version = _version;
        this.paperUpload = _paperUpload;
    }

    /**
     * Accessor for the paperID attribute
     * @return paperID The internal ID attribute
     */
    public int getPaperID() {
        return paperID;
    }

    /**
     * Accessor for the authors attribute
     * @return authors A List of Author names as strings
     */
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * Accessor for the contactAuthor attribute
     * @return contactAuthor The user account that submitted the paper
     */
    public User getContactAuthor() {
        return contactAuthor;
    }

    /**
     * Accessor for the title attribute
     * @return title The title of the paper
     */
    public String getTitle() {
        return title;
    }

    /**
     * Accessor for the format attribute
     * @return format The file format of the attribute
     */
    public String getFormat() {
        return format;
    }

    /**
     * Accessor for the version attribute
     * @return version The version number of the attribute (number of times paper has been edited, basically)
     */
    public int getVersion() {
        return version;
    }

    /**
     * Accessor for the paperUpload attribute
     * @return paperUpload The file submitted for the paper
     */
    public String getPaperUpload() {
        return paperUpload;
    }

    /**
     * Mutator for the authors attribute
     * @param _authors A List of Strings containing inputted names of authors
     */
    public void setAuthors(List<String> _authors) {
        this.authors = _authors;
    }

    /**
     * Mutator for the contactAuthor attribute
     * @param _contactAuthor The user account who submitted the paper
     */
    public void setContactAuthor(User _contactAuthor) {
        this.contactAuthor = _contactAuthor;
    }

    /**
     * Mutator for the title attribute
     * @param _title The title of the paper
     */
    public void setTitle(String _title) {
        this.title = _title;
    }

    /**
     * Mutator for the format attribute
     * @param _format The file format of the paper
     */
    public void setFormat(String _format) {
        this.format = _format;
    }

    /**
     * Mutator for the version attribute
     * @param _version The version number of the paper
     */
    public void setVersion(int _version) {
        this.version = _version;
    }

    /**
     * Mutator for the paperUpload Attribute
     * @param _paperUpload The file uploaded for the paper
     */
    public void setPaperUpload(String _paperUpload) {
        this.paperUpload = _paperUpload;
    }

    /**
     * Method to update information for a paper that a user edits the submission for
     * @param _authors The submitted author names
     * @param _title The submitted paper title
     * @param _format The file format
     * @param _paperUpload The submitted file for the paper
     */
    public void updatePaper(List<String> _authors, String _title, String _format, String _paperUpload) {
        setAuthors(_authors);
        setTitle(_title);
        setFormat(_format);
        setVersion(this.version + 1);
        setPaperUpload(_paperUpload);
    }

    /**
     * Helper function to return the list of authors as a readable string
     * @return authorString The author names delimited by a comma
     */
    public String getAuthorsAsString() {
        String authorString = "";
        for(String auth : authors) { //Loop through the list of authors and append them together, separated by a comma and a space
            authorString = authorString.concat(auth + ", ");
        }
         //Trim the extra trailing comma & space off
        authorString = authorString.substring(0, authorString.length() - 2);

        return authorString;
    }

    /**
     * Helper function to save the paper information to a flat file
     * @return saveString The String to be written to file
     */
    public String savePaper() {
        String authorsString = "";
        for(String auth : authors) //For each author, append their name and a comma(no space)
            authorsString = authorsString.concat(auth + ",");
        if(authorsString.length() > 0) //If there is more than one author, trim off the trailing comma
            authorsString = authorsString.substring(0, authorsString.length() - 1);

        String saveString = paperID + "|||" + authorsString + "|||" + contactAuthor.getUsername() + "|||" + title + "|||" + format + "|||" + version + "|||" + paperUpload + "|||";

        return saveString;
    }

    /* ADDED IN REFACTORING */

    /**
     * Helper method to indicate whether or not a given username relates to a User who authored the paper
     * @param _username The username of the user we're checking
     * @return If the user is an author (or contact author) for the paper
     */
    public boolean isUserAnAuthor(String _username) {
        return authors.contains(_username) || contactAuthor.getUsername().equals(_username);
    }
}
