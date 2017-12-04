package com.SAM2018.model;

import java.util.List;

/**
 * ViewModel-like class that returns a complex dataset when a view needs connected data across multiple objects
 * Used when a PCC views what PCMs have requested what papers
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class ReviewRequestDisplay {
    private Paper paper;
    private List<User> users;
    private boolean reviewExists;

    /**
     * Constuctor for the ReviewReqeustDisplay object
     * @param _paper The paper that is being requested
     * @param _users A list of all PCMs who have requested it
     * @param _reviewExists Whether or not reviews have already been created for this paper
     */
    public ReviewRequestDisplay(Paper _paper, List<User> _users, boolean _reviewExists) {
        this.paper = _paper;
        this.users = _users;
        this.reviewExists = _reviewExists;
    }

    /**
     * Accessor for paper
     * @return paper The Paper that PCMs are requesting to review
     */
    public Paper getPaper() {
        return paper;
    }

    /**
     * Accessor for users
     * @return users The list of users that have requested to review a paper
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Accessor for reviewExists
     * @return reviewExists Whether or not a Review already exists for the paper
     */
    public boolean getReviewExists() {
        return reviewExists;
    }
}
