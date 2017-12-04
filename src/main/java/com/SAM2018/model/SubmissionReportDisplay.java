package com.SAM2018.model;

/**
 * ViewModel-like class that returns a complex dataset when a view needs connected data across multiple objects
 * Used when a Submitter views a report generated for their paper
 */
public class SubmissionReportDisplay {
    //Attributes
    public Paper paper;
    public Report report;

    /**
     * Parameterized Constructor for the class
     * @param _paper The Paper that a report was generated for
     * @param _report The report generated for the Paper
     */
    public SubmissionReportDisplay(Paper _paper, Report _report) {
        this.paper = _paper;
        this.report = _report;
    }

    /**
     * Accessor for paper Attribute
     * @return paper The Paper that a report was generated for
     */
    public Paper getPaper() {
        return paper;
    }

    /**
     * Accessor for report
     * @return report The report that was generated for the paper
     */
    public Report getReport() {
        return report;
    }
}
