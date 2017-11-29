package com.SAM2018.model;

import java.util.ArrayList;
import java.util.List;

public class PCM extends User {

    //Attributes
    private List<Review> pendingPCMReviews;
    private List<Paper> requestedReviews;

    public PCM(String _username, String _password, String _firstName, String _lastName) {
        super(_username, _password, _firstName, _lastName);
        pendingPCMReviews = new ArrayList<>();
        requestedReviews = new ArrayList<>();
    }

    public List<Review> getPendingPCMReviews() {
        return pendingPCMReviews;
    }

    public List<Paper> getRequestedReviews() {
        return requestedReviews;
    }

    public String getRequestedReviewsAsString() {
        String requests = " ";
        for(Paper req : requestedReviews) {
            requests += req.getPaperID() + "/";
        }
        return requests;
    }

    public void requestReview(Paper _paper) {
        requestedReviews.add(_paper);
    }

    public void addReview(Review review) {
        pendingPCMReviews.add(review);
    }
}
