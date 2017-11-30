package com.SAM2018.model;

import java.util.List;

public class Report {

    //Attributes
    private Paper subject;
    private PCC generator;
    private List<Review> pcmReviews;
    private Review pccReview;
    private AcceptanceStatus acceptanceStatus;

    public Report(Paper _subject, PCC _generator, List<Review> _pcmReviews, Review _pccReview, AcceptanceStatus _acceptanceStatus) {
        this.subject = _subject;
        this.generator = _generator;
        this.pcmReviews = _pcmReviews;
        this.pccReview = _pccReview;
        this.acceptanceStatus = _acceptanceStatus;
    }

    public Paper getSubject() {
        return subject;
    }

    public PCC getGenerator() {
        return generator;
    }

    public List<Review> getPcmReviews() {
        return pcmReviews;
    }

    public Review getPccReview() {
        return pccReview;
    }

    public AcceptanceStatus getAcceptanceStatus() {
        return acceptanceStatus;
    }
}
