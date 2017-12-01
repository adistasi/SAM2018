package com.SAM2018.model;

import freemarker.template.SimpleDate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Notification {
    //Attributes
    private int id;
    private User creator;
    private String message;
    private User recipient;
    private boolean isRead;
    private Date dateGenerated;

    public Notification(int _id, User _creator, User _recipient, String _message, boolean _isRead) {
        this.id= _id;
        this.creator  =_creator;
        this.recipient = _recipient;
        this.message = _message;
        this.isRead = _isRead;
        dateGenerated = new Date();
    }

    public Notification(int _id, User _creator, User _recipient, String _message, boolean _isRead, Date _dateGenerated) {
        this.id = _id;
        this.creator = _creator;
        this.recipient = _recipient;
        this.message = _message;
        this.isRead = _isRead;
        this.dateGenerated = _dateGenerated;
    }

    public int getID() {
        return id;
    }

    public User getCreator() { return creator; }

    public String getMessage() {
        return message;
    }

    public User getRecipient() {
        return recipient;
    }

    public boolean getIsRead() { return isRead; }

    public Date getDateGenerated() {
        return dateGenerated;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public String saveNotification() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
        String dateStr = sdf.format(dateGenerated);

        String saveString = id + "|||" + creator.getUsername() + "|||" + recipient.getUsername() + "|||" + message + "|||" + isRead + "|||" + dateStr + "|||\n";
        return saveString;
    }
}
