package com.SAM2018.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a User of the SAM2018 System
 * This class is abstract and is extended by the different levels of permission that exist in the system
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public abstract class User {
    //Attributes
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<Paper> submittedPapers;

    /**
     * Parameterized Constructor for the class
     * @param _username Account username
     * @param _password Account password
     * @param _firstName User's first name
     * @param _lastName User's last name
     */
    public User(String _username, String _password, String _firstName, String _lastName) {
        this.username = _username;
        this.password = _password;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.submittedPapers = new ArrayList<>();
    }

    /**
     * Method to add a paper to the list of the User's submitted papers
     * @param _paper
     */
    public void submitPaper(Paper _paper) {
        submittedPapers.add(_paper);
    }

    public void editPaper(Paper _paper) {
        //TODO: get paper in list & set it's value
    }

    /**
     * Accessor for username
     * @return username The Account's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Accessor for password
     * @return password The Account's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Accessor for firstName
     * @return firstName The User's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Accessor for lastName
     * @return lastName The User's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Accessor for submittedPapers
     * @return submittedPapers The list of papers the user has submitted to the application
     */
    public List<Paper> getSubmissions() {
        return submittedPapers;
    }

    /**
     * Mutator for the username
     * @param _username The Account's username
     */
    public void setUsername(String _username) {
        this.username = _username;
    }

    /**
     * Mutator for the password
     * @param _password The Account's password
     */
    public void setPassword(String _password) {
        this.password = _password;
    }

    /**
     * Mutator for the firstName
     * @param _firstName The User's first name
     */
    public void setFirstName(String _firstName) {
        this.firstName = _firstName;
    }

    /**
     * Mutator for the lastName
     * @param _lastName The Account's last name
     */
    public void setLastName(String _lastName) {
        this.lastName = _lastName;
    }

    /**
     * Helper method to get a User's full name
     * @return
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Helper method to format the User information for saving to a file
     * @return saveString A '|||' delimited string that contains all the class information
     */
    public String saveUser() {
        String saveString = username + "|||" + getClass().toString() + "|||" + password + "|||" + firstName + "|||" + lastName + "|||";
        return saveString;
    }

    /**
     * Helper method to validate if a given username matches this user
     * @param _username The username we're checking for equality
     * @return Whether or not the username matches
     */
    public boolean usernameMatches(String _username) {
        return username.equals(_username);
    }
}
