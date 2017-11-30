package com.SAM2018.model;

public class Review {
    //Attributes
    private User reviewer;
    private Paper subject;
    private double rating;
    private String reviewerComments;
    private boolean needsRereviewed;

    public Review(User _reviewer, Paper _subject, double _rating, String _rc, boolean _needsRereviewed) {
        this.reviewer = _reviewer;
        this.subject = _subject;
        this.rating = _rating;
        this.reviewerComments = _rc;
        this.needsRereviewed = _needsRereviewed;
    }

    public Review(User _reviewer, Paper _subject) {
        this.reviewer = _reviewer;
        this.subject = _subject;
        this.rating = -1;
        this.reviewerComments = null;
        this.needsRereviewed = false;
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

    public boolean getNeedsRereviewed() {
        return needsRereviewed;
    }

    public void setRating(double _rating) {
        this.rating = _rating;
    }

    public void setReviewerComments(String _reviewerComments) {
        this.reviewerComments = _reviewerComments;
    }

    public void setNeedsRereviewed(boolean _needsRereviewed) {
        this.needsRereviewed = _needsRereviewed;
    }
}
