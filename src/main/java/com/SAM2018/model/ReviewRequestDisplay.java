package com.SAM2018.model;

import java.util.List;

public class ReviewRequestDisplay {
    public Paper paper;
    public List<User> users;

    public ReviewRequestDisplay(Paper _paper, List<User> _users) {
        this.paper = _paper;
        this.users = _users;
    }

    public Paper getPaper() {
        return paper;
    }

    public List<User> getUsers() {
        return users;
    }
}
