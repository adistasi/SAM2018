package com.SAM2018.model;

import java.util.Date;
import java.util.List;

public class Notification {
    //Attributes
    private String message;
    private List<User> recipients;
    private Date sendDate;

    public Notification(String _message, List<User> _recipients) {
        this.message = _message;
        this.recipients = _recipients;
        this.sendDate = null;
    }

    public Notification(String _message, List<User> _recipients, Date _sendDate) {
        this.message = _message;
        this.recipients = _recipients;
        this.sendDate = _sendDate;
    }

    public String getMessage() {
        return message;
    }

    public List<User> getRecipients() {
        return recipients;
    }

    public Date getSendDate() {
        return sendDate;
    }
}
