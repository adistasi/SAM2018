package com.SAM2018.model;

import java.util.List;

public class ReviewRequestDisplay {
    private Paper paper;
    private List<User> users;
    private boolean reviewExists;

    public ReviewRequestDisplay(Paper _paper, List<User> _users, boolean _reviewExists) {
        this.paper = _paper;
        this.users = _users;
        this.reviewExists = _reviewExists;
    }

    public Paper getPaper() {
        return paper;
    }

    public List<User> getUsers() {
        return users;
    }

    public boolean getReviewExists() {
        return reviewExists;
    }
}
