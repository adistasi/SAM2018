package com.SAM2018.model;

/**
 * A class to represent a review of a paper completed by a PCC or PCM
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
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

    /**
     * Helper Method to check if a given username corresponds to a the Review's Reviewer
     * @param _username The username we're checking for
     * @return If the reviewer has that username
     */
    public boolean userHasReview(String _username) {
        return reviewer.getUsername().equals(_username);
    }

    /**
     * Helper method to return whether or not a review exists and is pending (i.e. needs to be completed)
     * @return If this Review either needs to be rereviewed or hasn't been rated yet
     */
    public boolean isReviewPending() {
        return (needsRereviewed || rating == -1);
    }

    /**
     * Helper method to return whether or not a review exists and is completed
     * @return If this Review has been rated and has a score of greater than 0
     */
    public boolean isReviewComplete() {
        return (!needsRereviewed && rating >= 0);
    }

    /**
     * Helper function to validate that a Review is for a specified paper ID
     * @param paperID The ID number of the paper
     * @return Whether or not the paper ID matches
     */
    public boolean isReviewForPaper(int paperID) {
        return subject.getPaperID() == paperID;
    }
}
