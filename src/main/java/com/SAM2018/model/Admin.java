package com.SAM2018.model;

import com.SAM2018.appl.PaperManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class that represents an Admin User.  This is an elevated user permission that has full access to the application
 * This class extends User
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class Admin extends User {
    /**
     * Parameterized constructor for the Admin class - calls the super() constructor for the parent class
     * @param _username The Account username
     * @param _password The Account password
     * @param _firstName The User's first name
     * @param _lastName The User's last name
     */
    public Admin(String _username, String _password, String _firstName, String _lastName) {
        super(_username, _password, _firstName, _lastName);
    }

    /**
     * A method that allows Administrative users to create a deadline
     * @param dateTime A string representation of a Date & Time for the deadline to be enforced
     * @param title The title/name of the Deadline
     * @param paperManager Reference to the application layer, used here to update and maintain the timer for the deadlines within the application
     */
    public void setDeadline(String dateTime, String title, PaperManager paperManager) {
        try { //Format the date into a java.util.Date format
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
            Date dateGenerated = sdf.parse(dateTime);

            Deadline deadline = new Deadline(title, dateGenerated);
            paperManager.addDeadline(title, deadline);
            paperManager.saveDeadlines();

            //Get the timer, unset it, and reapply a new Timer for the Application with the updated deadline information
            Timer timer = paperManager.getTimer();
            timer.cancel();
            Timer timer2 = new Timer();
            for( Deadline d : paperManager.getDeadlines().values()) { //Loop through every deadline and apply a timer method should one exist
                if(d.getTitle().equals("Submission Deadline")) { //Don't set the submission deadline timer to repeat it's method call
                    timer2.schedule(new TimerTask() {
                        public void run() {paperManager.enforceSubmissionDeadline();
                        }
                    }, d.getDate());
                } else {
                    timer2.schedule(new TimerTask() {
                        public void run() { //Set all other deadlines to re-send notifications daily
                            if (d.getTitle().equals("Request Deadline")) {
                                paperManager.enforceRequestDeadline();
                            } else if (d.getTitle().equals("Review Deadline")) {
                                paperManager.enforceReviewDeadline();
                            } else if (d.getTitle().equals("Rating Deadline")) {
                                paperManager.enforceRatingDeadline();
                            }
                        }
                    }, d.getDate(), 86400000);
                }
            }

            paperManager.setTimer(timer2);
        } catch (Exception e){ //Error handling
            e.printStackTrace();
        }
    }

    public User addRole(User _user, String _role) {
        if(_role.equals("PCC")) {
            PCC newPCC = new PCC(_user.getUsername(), _user.getPassword(), _user.getFirstName(), _user.getLastName());
            return newPCC;
        } else {
            PCM newPCM = new PCM(_user.getUsername(), _user.getPassword(), _user.getFirstName(), _user.getLastName());
            return newPCM;
        }
    }

    public void deleteUser() {
        //TODO: SHOULD THIS GO HERE?
    }
}