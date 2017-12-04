package com.SAM2018.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that represents a notification generated and displayed to users
 */
public class Notification {
    //Attributes
    private int id;
    private User creator;
    private String message;
    private User recipient;
    private boolean isRead;
    private Date dateGenerated;

    /**
     * Parameterized Constructor for the class, used to create a new notification
     * @param _id ID number of the notification, used internally for identification
     * @param _creator The User Account that created the notification (can be null, indicating a system generated message)
     * @param _recipient The User Account of the message recipient
     * @param _message The text content of the message
     * @param _isRead Whether or not the message has been read (usually defaults to false)
     */
    public Notification(int _id, User _creator, User _recipient, String _message, boolean _isRead) {
        this.id= _id;
        this.creator  =_creator;
        this.recipient = _recipient;
        this.message = _message;
        this.isRead = _isRead;
        dateGenerated = new Date();
    }

    /**
     * Overloaded Parameterized Constructor for notifications - this is used to create notifications from file on application start
     * @param _id ID number of the notification, used internally for identification
     * @param _creator The User Account that created the notification (can be null, indicating a system generated message)
     * @param _recipient The User Account of the message recipient
     * @param _message The text content of the message
     * @param _isRead Whether or not the message has been read (usually defaults to false)
     * @param _dateGenerated The Date the the message was generated
     */
    public Notification(int _id, User _creator, User _recipient, String _message, boolean _isRead, Date _dateGenerated) {
        this.id = _id;
        this.creator = _creator;
        this.recipient = _recipient;
        this.message = _message;
        this.isRead = _isRead;
        this.dateGenerated = _dateGenerated;
    }

    /**
     * Accessor for the ID attribute
     * @return id The notification's internal ID number
     */
    public int getID() {
        return id;
    }

    /**
     * Accessor for the creator attribute
     * @return creator The User Account that created the notification
     */
    public User getCreator() { return creator; }

    /**
     * Accessor for the message attribute
     * @return message The text content of the notification
     */
    public String getMessage() {
        return message;
    }

    /**
     * Accessor for the recipient attribute
     * @return recipient The notification's recipient
     */
    public User getRecipient() {
        return recipient;
    }

    /**
     * Accessor for the isRead attribute
     * @return isRead Whether or not the application is read
     */
    public boolean getIsRead() { return isRead; }

    /**
     * Accessor for the dateGenerated attribute
     * @return dateGenerated The date the notification was created
     */
    public Date getDateGenerated() {
        return dateGenerated;
    }

    /**
     * Mutator for the creator attribute
     * @param _creator The User that created the notification
     */
    public void setCreator(User _creator){
        this.creator = _creator;
    }

    /**
     * Mutator for the recipient attribute
     * @param _recipient The user account receiving the notification
     */
    public void setRecipient(User _recipient){
        this.recipient = _recipient;
    }

    /**
     * Mutator for the message attribute
     * @param _message The text content of the notification
     */
    public void setMessage(String _message){
        this.message = _message;
    }

    /**
     * Mutator for the date generated attribute
     * @param _dateGenerated The date the notification was generated
     */
    public void setDateGenerated(Date _dateGenerated) {
        this.dateGenerated = _dateGenerated;
    }

    /**
     * Mutator for the isRead attribute - sets the message as read to true (we never need to set it to false)
     */
    public void markAsRead() {
        this.isRead = true;
    }

    /**
     * Helper method to format a notification for saving to a flat file
     * @return saveString A formatted string that is written to the file
     */
    public String saveNotification() {
        //Parse the date into a string format
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
        String dateStr = sdf.format(dateGenerated);

        //For the purposes of saving, if the creator is null, it is represented as "System" (as null creators means a System Generated Message)
        String creatorString = "System";
        if(creator != null)
            creatorString = creator.getUsername();

        String saveString = id + "|||" + creatorString + "|||" + recipient.getUsername() + "|||" + message + "|||" + isRead + "|||" + dateStr + "|||\n";
        return saveString;
    }
}
