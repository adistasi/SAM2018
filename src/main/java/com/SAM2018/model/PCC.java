package com.SAM2018.model;

import java.util.List;

public class PCC extends User {
    //Attributes
    private List<Review> pendingPCCReivew;

    public PCC(String _username, String _password, String _firstName, String _lastName) {
        super(_username, _password, _firstName, _lastName);
    }

    public Report createReport(Paper _paper) {
        return null;
        //TODO: Report generation
    }

    public void assignReview(PCM _pcm, Paper _paper) {
        //TODO: assign functionality
    }

    public void ratePaper(Paper _paper) {
        //TODO: Rate paper
    }

    public List<Review> getPendingPCCReivew() {
        return pendingPCCReivew;
    }
}
