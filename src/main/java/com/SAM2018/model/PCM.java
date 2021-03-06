package com.SAM2018.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a PCM Program Committee Member - A user with elevated permissions
 * This class extends User
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PCM extends User {

    //Attributes
    private List<Review> pendingPCMReviews;
    private List<Paper> requestedReviews;

    /**
     * Constructor for the PCM (class super() and instantiates the lists)
     * @param _username Account username
     * @param _password Account password
     * @param _firstName User's First Name
     * @param _lastName User's last name
     */
    public PCM(String _username, String _password, String _firstName, String _lastName) {
        super(_username, _password, _firstName, _lastName);
        pendingPCMReviews = new ArrayList<>();
        requestedReviews = new ArrayList<>();
    }

    /**
     * Accessor for pendingPCMReviews attribute
     * @return pendingPCMReviews The list of pending reviews assigned to a PCM
     */
    public List<Review> getPendingPCMReviews() {
        return pendingPCMReviews;
    }

    /**
     * Accessor for requestedPCMReviews attribute
     * @return requestedPCMReviews The list of papers the PCM has requested to review
     */
    public List<Paper> getRequestedReviews() {
        return requestedReviews;
    }

    /**
     * A method to add a Request to a User's list of requested papers
     * @param _paper
     */
    public void addRequestedReview(Paper _paper) {
        requestedReviews.add(_paper);
    }

    /**
     * Helper function to format the list of requestedReviews as a '/' delimited string list
     * @return
     */
    public String getRequestedReviewsAsString() {
        String requests = " ";
        for(Paper req : requestedReviews) {
            requests += req.getPaperID() + "/";
        }
        return requests;
    }

    /**
     * Function to add a paper to the PCM's list of requested reviews
     * @param _paper The paper the user is requesting to review
     */
    public void requestReview(Paper _paper) {
        requestedReviews.add(_paper);
    }

    /**
     * Function to add a review to the PCM's list of assigned reviews
     * @param review The Review that the PCM must complete
     */
    public void addReview(Review review) {
        pendingPCMReviews.add(review);
    }

    /**
     * A method to complete the review of a paper by a PCM
     * @param review The Review being completed
     * @param score The score the PCM is assigning to the Paper
     * @param comments The PCM's comments about the paper
     * @return review The Review Object
     */
    public Review reviewPaper(Review review, double score, String comments) {
        review.setRating(score);
        review.setReviewerComments(comments);
        review.setNeedsRereviewed(false);

        return review;
    }

    /**
     * Helper method to identify if the PCM has any pending reviews
     * @return Whether or not the PCM has any reviews
     */
    public boolean hasReviews() {
        return pendingPCMReviews.size() > 0;
    }
}
