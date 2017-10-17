package com.SAM2018.model;

import java.util.Date;

public class Deadline {
    //Attributes
    private String title;
    private Date date;

    public Deadline(String _title, Date _date) {
        this.title = _title;
        this.date = _date;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }
}
