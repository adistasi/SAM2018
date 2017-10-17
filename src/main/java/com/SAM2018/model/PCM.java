package com.SAM2018.model;

import java.util.List;

public class PCM extends User {

    //Attributes
    private List<Review> pendingPCMReviews;

    public PCM(String _username, String _password, String _firstName, String _lastName) {
        super(_username, _password, _firstName, _lastName);
    }

    public void requestReview(Paper _paper) {
        //TODO: Add PCM to list of requested reviews for a paper or something
    }

    public List<Review> getPendingPCMReviews() {
        return pendingPCMReviews;
    }
}
