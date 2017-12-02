package com.SAM2018.model;

import java.util.List;

public class PermissionRequestDisplay {
    private User user;
    private String permissionLevel;

    public PermissionRequestDisplay(User _user, String _permissionLevel) {
        this.user = _user;
        this.permissionLevel = _permissionLevel;
    }

    public User getUser() {
        return user;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }
}
