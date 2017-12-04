package com.SAM2018.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that represents time-based deadlines on system functionality
 * Possible Deadlines include:
 *      Submission Deadline
 *      Request Deadline
 *      Review Deadline
 *      Rating Deadline
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class Deadline {
    //Attributes
    private String title;
    private Date date;

    /**
     * Constructor for the Deadline class
     * @param _title The Name/Title of the deadline
     * @param _date The Date the deadline is enacted
     */
    public Deadline(String _title, Date _date) {
        this.title = _title;
        this.date = _date;
    }

    /**
     * Accessor for the Title (Name) attribute
     * @return title The Title/Name
     */
    public String getTitle() {
        return title;
    }

    /**
     * Accessor for the Date attribute
     * @return date The date the deadline is enacted
     */
    public Date getDate() {
        return date;
    }

    /**
     * A method to format the information in the class for saving into a flat file (to maintain application state)
     * @return saveString A string formatted for saving (data|||data|||data...)
     */
    public String saveDeadline() {
        //Parse the date into an appropriate string format
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
        String dateStr = sdf.format(date);

        String saveString = title + "|||" + dateStr + "\n";
        return saveString;
    }

    /**
     * Helper function to indicate whether or not a deadline's date/time has already occurred
     * @return Whether or not the date/time has passed
     */
    public boolean hasPassed() {
        Date currTime = new Date();

        return currTime.after(date);
    }
}
