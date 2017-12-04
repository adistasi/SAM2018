package com.SAM2018.model;

/**
 * A class that represents an Admin User.  This is an elevated user permission that has full access to the application
 * This class extends User
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class Admin extends User {
    /**
     * Parameterized constructor for the Admin class - calls the super() constructor for the parent class
     * @param _username The Account username
     * @param _password The Account password
     * @param _firstName The User's first name
     * @param _lastName The User's last name
     */
    public Admin(String _username, String _password, String _firstName, String _lastName) {
        super(_username, _password, _firstName, _lastName);
    }

    public void setDeadline(Deadline _deadline) {
        //TODO: STUFF
    }

    public void manageUserAccounts() {
        //TODO: STUFF
    }

    public void addRole(User _user, String _role) {
        //TODO: STUFF
    }

    public void removeRole(User _user, String _role) {
        //TODO: STUFF
    }
}