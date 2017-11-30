package com.SAM2018.model;

public class SubmissionReportDisplay {
    public Paper paper;
    public Report report;

    public SubmissionReportDisplay(Paper _paper, Report _report) {
        this.paper = _paper;
        this.report = _report;
    }

    public Paper getPaper() {
        return paper;
    }

    public Report getReport() {
        return report;
    }
}
