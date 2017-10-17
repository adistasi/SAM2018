package com.SAM2018.model;

public class Review {
    //Attributes
    public User reviewer;
    public Paper subject;
    public double rating;
    public String reviewerComments;

    public Review(User _reviewer, Paper _subject, double _rating, String _rc) {
        this.reviewer = _reviewer;
        this.subject = _subject;
        this.rating = _rating;
        this.reviewerComments = _rc;
    }

    public Review editReview(Review _review) {
        //TODO: Review editing
        return _review;
    }

    public User getReviewer() {
        return reviewer;
    }

    public Paper getSubject() {
        return subject;
    }

    public double getRating() {
        return rating;
    }

    public String getReviewerComments() {
        return reviewerComments;
    }
}
