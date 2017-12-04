package com.SAM2018.model;

/**
 * A class to represent a review of a paper completed by a PCC or PCM
 */
public class Review {
    //Attributes
    private User reviewer;
    private Paper subject;
    private double rating;
    private String reviewerComments;
    private boolean needsRereviewed;

    /**
     * Constructor for the Review object
     * @param _reviewer The user who reviewed the paper
     * @param _subject The paper the review is on
     * @param _rating The numeric score given to the paper
     * @param _reviewerComments The comments the PCC/PCM made on the paper
     * @param _needsRereviewed Whether or not a PCM has said this review needs to be redone (If all reviews are too different)
     */
    public Review(User _reviewer, Paper _subject, double _rating, String _reviewerComments, boolean _needsRereviewed) {
        this.reviewer = _reviewer;
        this.subject = _subject;
        this.rating = _rating;
        this.reviewerComments = _reviewerComments;
        this.needsRereviewed = _needsRereviewed;
    }

    /**
     * Overloaded constructor that initiates a review when it's first assigned to a PCM (sets rating to -1 and comments to null)
     * @param _reviewer The PCM the paper is assigned to
     * @param _subject The Paper the review is about
     */
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

    /**
     * Accessor for review
     * @return review The PCC/PCM who is creating the review
     */
    public User getReviewer() {
        return reviewer;
    }

    /**
     * Accessor for subject
     * @return subject The Paper that the review is for
     */
    public Paper getSubject() {
        return subject;
    }

    /**
     * Accessor for rating
     * @return rating The numerical score given to the paper
     */
    public double getRating() {
        return rating;
    }

    /**
     * Accessor for reviewerComments
     * @return reviewerComments The comments made by the PCM/PCC about the paper
     */
    public String getReviewerComments() {
        return reviewerComments;
    }

    /**
     * Accessor for needsRereviewed
     * @return needsRereviewed A boolean indicating whether or not the PCC indicated the reviews are too different and need to be revisted
     */
    public boolean getNeedsRereviewed() {
        return needsRereviewed;
    }

    /**
     * Mutator for rating
     * @param _rating The numeric score given to the paper
     */
    public void setRating(double _rating) {
        this.rating = _rating;
    }

    /**
     * Mutator for reviewerComments
     * @param _reviewerComments The textual comments the PCM/PCC supplied in the review
     */
    public void setReviewerComments(String _reviewerComments) {
        this.reviewerComments = _reviewerComments;
    }

    /**
     * Mutator for needsRereviewed
     * @param _needsRereviewed A boolean indicating whether or not the PCC indicated the reviews are too different and need to be revisted
     */
    public void setNeedsRereviewed(boolean _needsRereviewed) {
        this.needsRereviewed = _needsRereviewed;
    }

    /**
     * Helper function to get the Review information to save to file
     * @return saveString A ||| delimited string that will be written to file
     */
    public String saveReview() {
        String saveString = subject.getPaperID() + "|||" + reviewer.getUsername() + "|||" + rating + "|||" + reviewerComments + "|||" + needsRereviewed + "\n";
        return saveString;
    }
}
