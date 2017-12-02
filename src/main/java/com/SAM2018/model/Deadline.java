package com.SAM2018.model;

import java.text.SimpleDateFormat;
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

    public String saveDeadline() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
        String dateStr = sdf.format(date);

        String saveString = title + "|||" + dateStr + "\n";
        return saveString;
    }
}
