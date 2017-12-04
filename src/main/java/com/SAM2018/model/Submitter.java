package com.SAM2018.model;

/**
 * A class that represents a base level of user that can only submit papers
 * The class extends User
 */
public class Submitter extends User {
    /**
     * Parameterized Constructor for the class - calls User's super()
     * @param _username Account username
     * @param _password Account password
     * @param _firstName The User's First Name
     * @param _lastName The User's Last Name
     */
    public Submitter(String _username, String _password, String _firstName, String _lastName) {
        super(_username, _password, _firstName, _lastName);
    }
}
