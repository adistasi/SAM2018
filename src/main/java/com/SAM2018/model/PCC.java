package com.SAM2018.model;

import java.util.List;

/**
 * A class to represent a PCC (Program Committee Chair) - A User with elevated permissions
 * The class extends User
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PCC extends User {
    //Attributes
    private List<Review> pendingPCCReivew;

    /**
     * Constructor for the PCC Object (calls super())
     * @param _username Account username
     * @param _password Account password
     * @param _firstName User's First Name
     * @param _lastName User's Last Name
     */
    public PCC(String _username, String _password, String _firstName, String _lastName) {
        super(_username, _password, _firstName, _lastName);
    }

    public Report createReport(Paper _paper) {
        return null;
        //TODO: Report generation
    }

    /**
     * Functionality to assign a Review of a Paper to a PCM
     * @param _pcm The PCM who the review is being assigned to
     * @param _paper The paper being reviewed
     * @return review The Review object created
     */
    public Review assignReview(PCM _pcm, Paper _paper) {
        Review review = new Review(_pcm, _paper);
        _pcm.addReview(review);

        return review;
    }

    /**
     * A method that lets a PCC generate a report
     * @param pcc The PCC generating the report
     * @param paper The paper the report is for
     * @param pccRating The PCC's score of the paper
     * @param pccComments The PCC's comments on the paper
     * @param pcmReiviews The List of PCM Review objects
     * @param acceptStatus If the paper was accepted
     * @return report THe report that was generated
     */
    public Report ratePaper(PCC pcc, Paper paper, Double pccRating, String pccComments, List<Review> pcmReiviews, AcceptanceStatus acceptStatus) {
        Review pccReview = new Review(pcc, paper, pccRating, pccComments, false);
        Report report = new Report(paper, pcc, pcmReiviews, pccReview, acceptStatus);

        return report;
    }

    /**
     * Accessor for the pendingPCCReview attribute
     * @return pendingPCCReview The list of pending PCC Reviews
     */
    public List<Review> getPendingPCCReivew() {
        return pendingPCCReivew;
    }

    /**
     * Mutator for the pendingPCCReview attribute
     * @param _reviews The list of pending PCC Reviews
     */
    public void setPendingPCCReivew(List<Review> _reviews) {
        this.pendingPCCReivew = _reviews;
    }
}
