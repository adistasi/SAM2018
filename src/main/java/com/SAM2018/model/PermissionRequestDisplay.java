package com.SAM2018.model;

/**
 * ViewModel style class that aggregates and returns a complex dataset to the View
 * This class shows the permission levels of users to admins as they assign User permissions
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PermissionRequestDisplay {
    private User user;
    private String permissionLevel;

    /**
     * Constructor for the PermissionRequestDisplay
     * @param _user The User account
     * @param _permissionLevel The String representation of the permission level associated with that user account
     */
    public PermissionRequestDisplay(User _user, String _permissionLevel) {
        this.user = _user;
        this.permissionLevel = _permissionLevel;
    }

    /**
     * Accessor for the User attribute
     * @return user The User object who's permissions we're viewing
     */
    public User getUser() {
        return user;
    }

    /**
     * Accessor for the permissionLevel
     * @return permissionLevel A String representation of the permission level
     */
    public String getPermissionLevel() {
        return permissionLevel;
    }
}
