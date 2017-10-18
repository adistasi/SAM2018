package com.SAM2018.model;

import java.util.List;

public abstract class User {
    //Attributes
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int permissionLevel;
    private List<Paper> submittedPapers;

    public User(String _username, String _password, String _firstName, String _lastName) {
        this.username = _username;
        this.password = _password;
        this.firstName = _firstName;
        this.lastName = _lastName;
    }

    public void submitPaper(Paper _paper) {
        submittedPapers.add(_paper);
    }

    public void editPaper(Paper _paper) {
        //TODO: get paper in list & set it's value
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public List<Paper> getSubmissions() {
        return submittedPapers;
    }

    public void addPaperToSubmissions(Paper _paper) {
        submittedPapers.add(_paper);
    }

    //Not 100% sure these should go here
    public void login() {

    }

    public void logout() {

    }
}
