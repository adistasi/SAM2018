package com.SAM2018.appl;

import com.SAM2018.model.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that manages lists of objects and maintains state across the application.
 * Also responsible for the saving of objects to files
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PaperManager {
    //Constants
    private final int REVIEWS_PER_PAPER = 3;

    //Attributes
    private  Map<String, User> users = new HashMap<>();
    private List<Paper> papers = new ArrayList<>();
    private Map<String, List<Review>> reviews = new HashMap<>();
    private Map<String, List<User>> requestedReviews = new HashMap<>();
    private List<Report> reports = new ArrayList<>();
    private List<Notification> notifications = new ArrayList<>();
    private Map<User, String> requestedPermissions = new HashMap<>();
    private Map<String, Deadline> deadlines = new HashMap<>();
    Timer timer = new Timer();

    /*==========USER FUNCTIONALITY=========*/
    /**
     * A method to retreive the author of a paper by their username
     * @param _username The username of the author
     * @return The user that has that username
     */
    public User getContactAuthorByUsername(String _username) {
        return users.get(_username);
    }

    /**
     * A method to add a user to the list of User's when they register
     * @param user The user that is being loaded into the application
     */
    public void addUser(User user){
        users.put(user.getUsername(), user);
        saveUsers();
    }

    /**
     * Return a boolean indicating if a user with a given username exists in the system
     * @param username The username that we're checking to see if it exists
     * @return Whether or not the user exists
     */
    public boolean userExists(String username){
        return users.containsKey(username);
    }

    /**
     * Method to get a user by their username
     * @param username The username that belongs to the User object we need
     * @return The user object with the associated username
     */
    public User getUser(String username){
        return users.get(username);
    }

    /**
     * A method to return every PCM registered with the system
     * @return A List of PCM users
     */
    public List<User> getAllPCMs() {
        List<User> pcms = new ArrayList<>();
        for(User u : users.values()) { //Loop through all users and add them to a list for return if they're a PCM
            if(u instanceof PCM)
                pcms.add(u);
        }

        return pcms;
    }

    /**
     * Method to return a string indicating the user's Usertype
     * @param username The username of the account
     * @return A String showing the account type (Admin/PCC/PCM/Submitter)
     */
    public String getUserType(String username) {
        User u = getUser(username);

        if(u instanceof Admin)
            return "Admin";
        else if(u instanceof PCC)
            return "PCC";
        else if(u instanceof PCM)
            return "PCM";
        else
            return "Submitter";
    }

    /**
     * Method to get EVERY user within the application
     * @param _username The username of the user requesting the search (so we don't return them in the list)
     * @return A list of Users
     */
    public List<User> getAllUsers(String _username) {
        List<User> userList = new ArrayList<>();

        for(User u : users.values()) { //For each user in the application, add them to the list unless their username matches
            if(!u.getUsername().equals(_username)) {
                userList.add(u);
            }
        }

        return userList;
    }

    /**
     * A method to get the Program Committee Chair User object
     * @return The PCC registered with the system (or null if none exist)
     */
    public User getPCC() {
        for(User u : users.values()) { //Loop through each user and return the one which is an instanceof PCC
            if(u instanceof PCC)
                return u;
        }

        return null;
    }

    /**
     * Helper function to return the number of PCC users registered with the system
     * @return pccs An int that shows the number of registered PCCs
     */
    public int getCountPCC() {
        int pccs = 0;
        for(User u : users.values()) { //Loop through all users and add the ones that are an instanceof PCC
            if(u instanceof PCC)
                pccs++;
        }

        return pccs;
    }

    /**
     * A method to get the Admin user for the application
     * @return The Admin user
     */
    public User getAdmin() {
        for(User u : users.values()) { //Loop through the application and return the Admin user (or null if none exist)
            if(u instanceof Admin)
                return u;
        }

        return null;
    }

    /**
     * Method that lets a user request a permission level when the register
     * @param _user The user requesting an elevated permission level
     * @param _permissionlevel The level the user is requesting
     */
    public void requestPermissions(User _user, String _permissionlevel) {
        requestedPermissions.put(_user, _permissionlevel);
    }

    /**
     * A method to return a list of all requested permissions through the PermissionRequestDisplay ViewModel class
     * @return A list of users and the permissions they requested
     */
    public List<PermissionRequestDisplay> getRequestedPermissions() {
        List<PermissionRequestDisplay> prd = new ArrayList<>();
        for(User u : requestedPermissions.keySet()) { //Loop through the users that have requested permissions and add their requests to the List to be returned
            PermissionRequestDisplay p = new PermissionRequestDisplay(u, requestedPermissions.get(u));
            prd.add(p);
        }

        return prd;
    }

    /**
     * A method to either approve or deny a user's request for elevated permissions
     * @param _username The username of the user who requested elevated permissions
     * @param _approved whether or not their request was approved
     */
    public void assignRole(String _username, boolean _approved) {
        User user = getUser(_username);
        String type = requestedPermissions.get(user);

        String username = user.getUsername();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        if(_approved) { //If the request was approved, elevate them and then save.  Otherwise just delete their request
            if(type.equals("PCC")) { //If it's a PCC, create a new PCC user to replace the old account
                users.remove(user.getUsername());
                PCC newPCC = new PCC(username, password, firstName, lastName);
                users.put(newPCC.getUsername(), newPCC);
            } else if(type.equals("PCM")) { //If it's a PCM, create a new PCM user to replace the old account
                users.remove(user.getUsername());
                PCM newPCM = new PCM(username, password, firstName, lastName);
                users.put(newPCM.getUsername(), newPCM);
            }
        }

        //Remove the request and save the user information
        requestedPermissions.remove(user);
        saveUsers();
    }

    /**
     * A method to delete a Users from the System
     * @param _username The username of the user being deleted form the system
     */
    public void deleteUser(String _username) {
        users.remove(_username);
        requestedPermissions.remove(getUser(_username));
        //TODO: Remove from reviews, requests, papers, & ratings
        saveUsers();
    }


    /* ==========PAPER FUNCTIONALITY========== */

    /**
     * A method to add a Paper to the maintined list of papers
     * @param _paper The paper added
     */
    public void addPaper(Paper _paper) {
        papers.add(_paper);
    }

    /**
     * A method to get the number of papers submitted to the system
     * @return
     */
    public int getPaperCount() {
        return papers.size();
    }

    /**
     * A method to validate the inputted strings for Author names for a paper
     * @param _authors A string containing every author name delimited by '/'
     * @return A List of Strings containing the author names
     */
    public List<String> validateAuthors(String _authors) {
        List<String> authors = new ArrayList<>();
        String[] authorsArr = _authors.split("/");
        List<String> authorsRaw = Arrays.asList(authorsArr);

        for(String auth : authorsRaw) { //loop through each inputted author name
            if(auth != null && !auth.equals("")) //If the author name exists, add it to the list
                authors.add(auth);
        }

        return authors;
    }

    /**
     * Method to get a given Paper by it's ID number
     * @param id The ID number of a paper
     * @return Either the paper if the ID number matches or null if none exist
     */
    public Paper getPaperbyID(int id) {
        try {
            return papers.get((id));
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * Method to get all papers from the system
     * @return papers The list of papers
     */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * A method to get the List of Papers that still need reviews assigned to them (i.e. do not have any existing reviews)
     * @param username The username of the user accessing the application
     * @return The list of papers that need reviews assigned (and are available for request)
     */
    public List<Paper> getPapersForReview(String username) {
        List<Paper> reviewPapers = new ArrayList<>();

        for(Paper p : papers) { //Loop through each paper and get any reviews that exist for that paper
            List<Review> paperReviews = getReviewsForPaper(Integer.toString(p.getPaperID()));

            //If the paper was not written by the inputted user and the paper has no reviews, add it to the list
            if(!p.getAuthors().contains(username) && !p.getContactAuthor().getUsername().equals(username) && paperReviews == null)
                reviewPapers.add(p);
        }

        return reviewPapers;
    }


    /* ========== REQUEST FUNCTIONALITY ========== */
    /**
     * A method to add a request to review a paper to the Map of requested reviews
     * @param paper The Paper that a PCM is requesting to review
     * @param user The user that is requesting to review a paper
     */
    public void addRequest(Paper paper, User user) {
        String paperID = Integer.toString(paper.getPaperID());
        if(requestedReviews.get(paperID) != null) { //If there are existing requests, add the user to that list
            List<User> reqUsers = requestedReviews.get(paperID);
            reqUsers.add(user);
            requestedReviews.put(paperID, reqUsers);
        } else { //If there are no existing requests, create a new ArrayList and add the user to that list
            List<User> reqUsers = new ArrayList<>();
            reqUsers.add(user);
            requestedReviews.put(paperID, reqUsers);
        }

        savePapers();
    }

    /**
     * A method to clear out any requests to review papers
     */
    public void clearRequests() {
        requestedReviews.clear();
    }

    /**
     * A method to get the viewmodel-like object to return information about requested reviews for display
     * @return A List of ReviewRequestDisplay objects containing information about papers & their request
     */
    public List<ReviewRequestDisplay> getRequestedReviews() {
        List<ReviewRequestDisplay> rrds = new ArrayList<>();

        for(Paper p : papers) { //Loop through each paper & create a ReviewRequestDisplay object for it
            String paperID = Integer.toString(p.getPaperID());
            List<Review> reviews = getReviewsForPaper(paperID);
            ReviewRequestDisplay rrd = new ReviewRequestDisplay(p, requestedReviews.get(paperID), reviews == null);
            rrds.add(rrd);
        }
        return rrds;
    }

    /**
     * A method to check whether or not a user has made a request
     * @param username The username of the user accessing this function
     * @return A boolean indicating whether or not the user has made any requests
     */
    public boolean hasUserMadeRequest(String username) {
        User user = getUser(username);
        for(List<User> reqUsers : requestedReviews.values()) { //Loop through each paper/request combo, and if a user is associated with that, return true
            if(reqUsers.contains(user))
                return true;
        } //If no users have any associated requests, return false

        return false;
    }

    public boolean areOutstandingRequests() {
        return requestedReviews.size() > 0;
    }


    /* ========== REVIEW FUNCTIONALITY =========*/

    /**
     * A method to add a review to the list of reviews for the application (when they're assigned to PCMs)
     * @param paperID The ID of the paper that the review is for
     * @param review The assigned review object
     */
    public void addReview(String paperID, Review review) {
        if(reviews.get(paperID) != null) { //If there are reviews for that object, append this one to the list
            List<Review> paperReviews = reviews.get(paperID);
            paperReviews.add(review);
            reviews.put(paperID, paperReviews);
        } else { //Otherwise create a new list and add this one to it
            List<Review> paperReviews = new ArrayList<>();
            paperReviews.add(review);
            reviews.put(paperID, paperReviews);
        }
    }

    /**
     * A method to get all reviews that have been assigned to a given user
     * @param username The username of the user we're getting reviews for
     * @return A List of reviews that have been assigned to that user
     */
    public List<Review> getReviewsForUser(String username) {
        List<Review> userReviews = new ArrayList<>();

        for(List<Review> revs : reviews.values()) { //Loop through each paper's reviews
            for(Review r : revs) { //Loop through each review and return the ones that match that user's username
                if (r.getReviewer().getUsername().equals(username)) {
                    userReviews.add(r);
                }
            }
        }

        return userReviews;
    }

    /**
     * A method to get any pending reviews (not completed) for a PCM
     * @param username The PCM's username
     * @return The list of pending reviews for that PCM
     */
    public List<Review> getPendingReviewsForUser(String username) {
        List<Review> userReviews = new ArrayList<>();

        for(List<Review> revs : reviews.values()) { //Loop through each paper's reviews
            for(Review r : revs) { //Loop through each review
                //If the review hasn't been completed (rating is -1) or doesn't need re-reviewed
                if (r.getReviewer().getUsername().equals(username) && (r.getNeedsRereviewed() || r.getRating() == -1))
                    userReviews.add(r);
            }
        }

        return userReviews;
    }

    /**
     * A method to get any completed reviews for a PCM
     * @param username The PCM's username
     * @return The list of completed reviews for that PCM
     */
    public List<Review> getCompletedReviewsForUser(String username) {
        List<Review> completedReviews = new ArrayList<>();

        for(List<Review> revs : reviews.values()) { //Loop through each paper's reviews
            for(Review r : revs) { //Loop through each review
                //If the review has been completed (rating > 0) and doesn't need re-reviwed
                if(r.getReviewer().getUsername().equals(username) && (!r.getNeedsRereviewed() || r.getRating() >= 0))
                    completedReviews.add(r);
            }
        }

        return completedReviews;
    }

    /**
     * Method to get a specific review by it's paper's ID number and username
     * @param paperID The ID of the paper the reivew is for
     * @param username The username of the user
     * @return The Review object
     */
    public Review getReview(int paperID, String username) {
        if(getPaperbyID(paperID) != null) { //If the paper for the given ID exists
            for (Review r : reviews.get(Integer.toString(paperID))) { //Loop through each review for that paper
                if (r.getSubject().getPaperID() == paperID && r.getReviewer().getUsername().equals(username)) //If the Review's paper ID matches and is for the correct user, return it
                    return r;
            }
        }

        return null;
    }

    /**
     * A method to get the List of Reviews for a given paper
     * @param paperID The ID of the paper
     * @return The List of Reviews for that paper
     */
    public List<Review> getReviewsForPaper(String paperID) {
        return reviews.get(paperID);
    }

    /**
     * A method to get the count of reviews that haven't been completed yet for a paper
     * @param _paperID The ID of the paper
     * @return The number of uncompleted reviews
     */
    public int getReviewsLeftForPaper(String _paperID) {
        List<Review> paperReviews = reviews.get(_paperID);
        int countCompleted = 0;

        for(Review r : paperReviews) { //Loop through each review for the paper
            if(r.getRating() != -1 && !r.getNeedsRereviewed()) //If the rating isn't negative 1 and it doesn't need re-reviewed, add it to the count
                countCompleted++;
        }
        return REVIEWS_PER_PAPER - countCompleted; //Return the number of specified reviews minus the number of completed reviews
    }

    /* ========== REPORTS FUNCTIONALITY ==========*/
    /**
     * A method to add a report to the list of all reports
     * @param _report The report to be added
     */
    public void addReport(Report _report) {
        reports.add(_report);
    }

    /**
     * A method to get the list of all reports
     * @return The list of every report in the application
     */
    public List<Report> getReports() {return reports; }

    /**
     * A method to get a report by it's paper's ID number
     * @param paperID The ID number of the paper
     * @return The report for a given paper
     */
    public Report getReportByID(int paperID) {
        for(Report r : reports) { //Loop through each report
            if(r.getSubject().getPaperID() == paperID) { //If it's paper's ID matches, return true
                return r;
            }
        }

        return null;
    }

    /**
     * Function to get a list of papers that still need to be rated
     * @return A list of papers without completed ratings that are eligible for rating
     */
    public List<Paper> getRatablePapers() {
        List<Paper> ratablePapers = new ArrayList<>();

        for(List<Review> revs: reviews.values()) { //Loop through each paper and get it's Reviews
            if(revs.size() == REVIEWS_PER_PAPER) { //If it has the determined amount (3) of reviews, mark that all reviews are complete
                boolean allReviewsComplete = true;

                for(int i=0; i < REVIEWS_PER_PAPER; i++) { //Loop through each review and check that it's completed (marking allReviewsComplete as false if one isn't)
                    if(revs.get(i).getRating() == -1 || revs.get(i).getNeedsRereviewed()) {
                        allReviewsComplete = false;
                        break;
                    }
                }

                if(allReviewsComplete && !reports.contains(getReportByID(revs.get(0).getSubject().getPaperID()))) //If all reviews are completed and there isn't an existing report
                    ratablePapers.add(revs.get(0).getSubject());
            }
        }

        return ratablePapers;
    }

    /* ========== NOTIFICATION FUNCTIONALITY ========== */
    /**
     * A method to get any unread notifications for a user
     * @param username The username of the user
     * @return The list of undread notifications
     */
    public List<Notification> getUnreadNotificationsForUser(String username) {
        List<Notification> nots = new ArrayList<>();

        for(Notification n : notifications) { //Loop through every notification & return the ones for that user that are not read
            if(n.getRecipient().getUsername().equals(username) && !n.getIsRead())
                nots.add(n);
        }

        return nots;
    }

    /**
     * A method to get any read notifications for a user
     * @param username The username of the user
     * @return The list of read notifications
     */
    public List<Notification> getReadNotificationsForUser(String username) {
        List<Notification> nots = new ArrayList<>();

        for(Notification n : notifications) { //Loop through every notification and get the ones for that user that are read
            if(n.getRecipient().getUsername().equals(username) && n.getIsRead())
                nots.add(n);
        }

        return nots;
    }

    /**
     * A method to get the count of undread notifications for a user
     * @param _username The username of the user
     * @return The count of notifications for that user
     */
    public int getUnreadNotificationCount(String _username) {
        List<Notification> nots = getUnreadNotificationsForUser(_username);
        return nots.size();
    }

    /**
     * A method to add a notification to the list of notifications for the whole application
     * @param n The notification being added
     */
    public void addNotification(Notification n) {
        notifications.add(n);
    }

    /**
     * A method to get the count of all notifications within the application
     * @return The integer value of how many notifications there are
     */
    public int getNotificationsSize() {
        return notifications.size();
    }

    /**
     * A method to get a given notification by it's ID number
     * @param id The id number of the notification
     * @return The notification, if it exists
     */
    public Notification getNotificationByID(int id) {
        try {
            return notifications.get((id));
        } catch(Exception e) {
            return null;
        }
    }


    /* ========== DEADLINE FUNCTIONALITY ========== */

    /**
     * A method to add a deadline to the list of deadlines for the application
     * @param _title The title of the deadline
     * @param _deadline The deadline object
     */
    public void addDeadline(String _title, Deadline _deadline) {
        deadlines.put(_title, _deadline);
    }

    /**
     * A method to get a deadline given it's title
     * @param title The title of the deadline
     * @return The deadline object associated with that title
     */
    public Deadline getDeadline(String title) {
        return deadlines.get(title);
    }

    /**
     * A method to get the Map of every deadline in the application
     * @return The map of deadlines with their titles
     */
    public Map<String, Deadline> getDeadlines() {
        return deadlines;
    }

    /**
     * A method to send a notification on the submission deadline to each PCM telling them to request papers
     */
    public void enforceSubmissionDeadline() {
        List<User> pcms = getAllPCMs();

        for(User pcm : pcms) { //Loop through each PCM and send them a notification
            Notification not = new Notification(getNotificationsSize(), null, pcm, "The Submission Deadline has passed.  Please request papers you wish to review.", false, new Date());
            addNotification(not);
        }
        saveNotifications();

    }

    /**
     * A method to send a notification on the request deadline to the PCC telling them to assign papers if there are still outstanding requests
     * This runs every day
     */
    public void enforceRequestDeadline() {
        if(areOutstandingRequests()) { //If there are outstanding requests, send an notification to the PCC
            User pcc = getPCC();

            Notification not = new Notification(getNotificationsSize(), null, pcc, "The Submission Deadline has passed, but there are still unassigned papers.  Please assign these papers ASAP.", false, new Date());
            addNotification(not);

            saveNotifications();
        }
    }

    /**
     * A method to send a notification on the review deadline to each PCM telling them to finish their outstanding reviews (if they have any)
     * This method runs daily
     */
    public void enforceReviewDeadline() {
        List<User> pcms = getAllPCMs();

        for(User pcm : pcms) { //Loop through each PCM
            List<Review> pendingReviews = getPendingReviewsForUser(pcm.getUsername());

            if(pendingReviews.size() > 0) { //Send them a notification if they have pending Reviews
                Notification not = new Notification(getNotificationsSize(), null, pcm, "The Review Deadline has passed, but you still have pending Reviews.  Please review these papers ASAP.", false, new Date());
                addNotification(not);
            }
        }

        saveNotifications();
    }

    /**
     * A method to send a notification on the rating deadline to the PCC telling them to finish their outstanding reports
     * This method runs daily
     */
    public void enforceRatingDeadline() {
        User pcc = getPCC();

        if(getRatablePapers().size() > 0) { //If the PCC has any ratable papers, send them a report
            Notification not = new Notification(getNotificationsSize(), null, pcc, "The Final Report Generation Deadline has passed, but you still have pending Reports for Papers.  Please review these papers ASAP.", false, new Date());
            addNotification(not);
            saveNotifications();
        }
    }

    /**
     * A method to get the Timer object used to queue tasks
     * @return timer The Timer object
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * A method to set the timer object used to queue tasks
     * @param _timer The Timer object
     */
    public void setTimer(Timer _timer) {
        this.timer = _timer;
    }

    /* ========== SAVING FUNCTIONALITY ========== */
    /**
     * A method to save all papers and write to file
     */
    public void savePapers() {
        try { //Write to a file called papers.text
            FileWriter writer = new FileWriter("papers.txt");
            writer.write("=====PAPERS=====\n");
            for(Paper p : papers) { //Loop through each paper and write their data to file
                writer.write(p.savePaper());

                //Loop through each requested review and append that list of usernames to each paper
                List<User> requestedReviewers = requestedReviews.get(Integer.toString(p.getPaperID()));
                String reqRevString = " ";
                if(requestedReviewers != null) {
                    for (User u : requestedReviewers) {
                        reqRevString += u.getUsername() + "/";
                    }
                }
                writer.write(reqRevString + "\n");
            }

            writer.close();
        } catch(Exception e) { //General Error Handling
            e.printStackTrace();
        }
    }

    /**
     * A method to load users in from the file when starting the application
     */
    public void loadPapers() {
        try { //Read in the papers.txt file line by line
            try (BufferedReader br = new BufferedReader(new FileReader("papers.txt"))) {
                String header = br.readLine(); //ignore the first (descriptor) line
                String line = br.readLine();
                while (line != null) { //Loop through each line
                    String[] paperLine = line.split("\\|\\|\\|"); //split on '|||' and parse each line into it's component data
                    int id = Integer.parseInt(paperLine[0]);
                    List<String> authors = Arrays.asList(paperLine[1].split(","));
                    User contactAuthor = getContactAuthorByUsername(paperLine[2]);
                    String title = paperLine[3];
                    String format = paperLine[4];
                    int version = Integer.parseInt(paperLine[5]);
                    String paperUpload = paperLine[6];

                    //Create a new paper, add it to the list, and "submit" that paper on behalf of the author
                    Paper p = new Paper(id, authors, contactAuthor, title, format, version, paperUpload);
                    papers.add(p);
                    contactAuthor.submitPaper(p);

                    if(paperLine.length > 7) { //If there is a 7th item (the requests) create those for the papers they exist for
                        String[] requestors = paperLine[7].split("/");
                        if(!requestors[0].equals(" ")) { //If there are requesters for the paper:
                            for(int i=0; i < requestors.length; i++) { //Loop through each one, get their username and PCM object and "request" the review for them
                                String username = requestors[i].trim();
                                PCM reqUser = (PCM)getUser(username);
                                reqUser.requestReview(p);

                                if(requestedReviews.get(Integer.toString(id)) != null) { //If that paper already has reviews, add them to the list
                                    List<User> reqUsers = requestedReviews.get(Integer.toString(id));
                                    reqUsers.add(reqUser);
                                    requestedReviews.put(Integer.toString(id), reqUsers);
                                } else { //Otherwise, create a new list and put them on it
                                    List<User> reqUsers = new ArrayList<>();
                                    reqUsers.add(reqUser);
                                    requestedReviews.put(Integer.toString(id), reqUsers);
                                }
                            }
                        }
                    }

                    line = br.readLine(); //go to the next line
                }
            }
        } catch(Exception e) { //General error handling
            e.getStackTrace();
        }
    }

    /**
     * A method to save all users of the application to file
     */
    public void saveUsers() {
        try { //Write to a file called "users.txt"
            FileWriter writer = new FileWriter("users.txt");
            writer.write("=====USERS=====\n");
            for(User u : users.values()) { //Loop through each user and write their info to file
                writer.write(u.saveUser());

                //If that user has a pending permission elevation request, save that with their file as well
                String requestedPermissionVal = requestedPermissions.get(u);
                if(requestedPermissionVal != null)
                    writer.write(requestedPermissionVal + "\n");
                else
                    writer.write("\n");
            }

            writer.close();
        } catch(Exception e) { //General error handling
            System.out.println("ERROR: " + e);
        }
    }

    /**
     * A method to load users in from a file on application start
     */
    public void loadUsers() {
        try { //Read in line by line from the users.txt file
            try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
                String header = br.readLine(); //Ignore the header line
                String line = br.readLine();
                while (line != null) { //for each line, split the info into it's data points
                    String[] userLine = line.split("\\|\\|\\|");
                    String username = userLine[0];
                    String classString = userLine[1];
                    String password = userLine[2];
                    String firstName = userLine[3];
                    String lastName = userLine[4];

                    //Create a specific type of user based on the class type and create each user, adding them to the list
                    User user;
                    if(classString.contains("Admin"))
                        user = new Admin(username, password, firstName, lastName);
                    else if(classString.contains("PCC"))
                        user = new PCC(username, password, firstName, lastName);
                    else if(classString.contains("PCM"))
                        user = new PCM(username, password, firstName, lastName);
                    else
                        user = new Submitter(username, password, firstName, lastName);

                    users.put(username, user);

                    if(userLine.length == 6) //If they have a pending request, add that to the map of requested permissions
                        requestedPermissions.put(user, userLine[5]);

                    line = br.readLine();
                }
            }
        } catch(Exception e) { //General error handling
            System.out.println("ERROR: " + e);
        }
    }

    /**
     * A method to save all reviews to file
     */
    public void saveReviews() {
        try { //Create a file called 'reviews.txt' and save each review
            FileWriter writer = new FileWriter("reviews.txt");
            writer.write("=====REVIEWS=====\n");
            for(List<Review> rev : reviews.values()) { //Loop through each review and write it's info to file
                for(Review r : rev) {
                    writer.write(r.saveReview());
                }
            }

            writer.close();
        } catch(Exception e) { //General error handling
            System.out.println("ERROR: " + e);
        }
    }

    /**
     * A method to load in any reviews for the application on startup
     */
    public void loadReviews() {
        try { //Get the data from 'reviews.txt' and load it in
            try (BufferedReader br = new BufferedReader(new FileReader("reviews.txt"))) {
                String header = br.readLine();
                String line = br.readLine();
                while (line != null) { //For each line (split on '|||'), break the data into its data points
                    String[] reviewLine = line.split("\\|\\|\\|");
                    int paperID = Integer.parseInt(reviewLine[0]);
                    String reviewerUsername = reviewLine[1];
                    double score = Double.parseDouble(reviewLine[2]);
                    String comments = reviewLine[3];
                    boolean needsReReviewed = Boolean.valueOf(reviewLine[4]);

                    //Get the paper and reviewer for the review and create the review
                    Paper paper = getPaperbyID(paperID);
                    User reviewer = getUser(reviewerUsername);

                    Review review = new Review(reviewer, paper, score, comments, needsReReviewed);

                    if(reviews.get(Integer.toString(paperID)) != null) { //If a review already exists for that paper, add it to the list
                        List<Review> paperReviews = reviews.get(Integer.toString(paperID));
                        paperReviews.add(review);
                        reviews.put(Integer.toString(paperID), paperReviews);
                    } else { //Otherwise create a new list and add the review
                        List<Review> paperReviews = new ArrayList<>();
                        paperReviews.add(review);
                        reviews.put(Integer.toString(paperID), paperReviews);
                    }

                    line = br.readLine();
                }
            }
        } catch(Exception e) { //General error handling
            System.out.println("ERROR: " + e);
        }
    }

    /**
     * Method to save reports to file
     */
    public void saveReports() {
        try { //Create a file called 'reports.txt' and write each report to it
            FileWriter writer = new FileWriter("reports.txt");
            writer.write("=====REPORTS=====\n");
            for(Report r : reports) { //Loop through reports and save it's info
                writer.write(r.saveReport());
            }

            writer.close();
        } catch(Exception e) { //general error handling
            System.out.println("ERROR: " + e);
        }
    }

    /**
     * A method to load in reports from file on application start
     */
    public void loadReports() {
        try { //Load in the data from reports.txt
            try (BufferedReader br = new BufferedReader(new FileReader("reports.txt"))) {
                String header = br.readLine();
                String line = br.readLine();
                while (line != null) { //Loop through each line and split it into it's data points
                    String[] ratingLine = line.split("\\|\\|\\|");
                    int paperID = Integer.parseInt(ratingLine[0]);
                    String pccUsername = ratingLine[1];
                    double pccScore = Double.parseDouble(ratingLine[2]);
                    String pccComments = ratingLine[3];
                    AcceptanceStatus acceptanceStatus = AcceptanceStatus.valueOf(ratingLine[4]);

                    //Get the Paper, PCC User, and Reviews for a report and then create the report
                    Paper paper = getPaperbyID(paperID);
                    PCC pcc = (PCC)getUser(pccUsername);
                    List<Review> pcmReviews = reviews.get(Integer.toString(paperID));

                    Review pccReview = new Review(pcc, paper, pccScore, pccComments, false);
                    Report report = new Report(paper, pcc, pcmReviews, pccReview, acceptanceStatus);
                    reports.add(report);
                    line = br.readLine();
                }
            }
        } catch(Exception e) { //General error handling
            System.out.println("ERROR: " + e);
        }
    }

    /**
     * A method to save the notifications to file
     */
    public void saveNotifications() {
        try { //Create a file called 'notifications.txt'
            FileWriter writer = new FileWriter("notifications.txt");
            writer.write("=====NOTIFICATIONS=====\n");
            for(Notification n : notifications) { //Loop through each notification and write it to file
                writer.write(n.saveNotification());
            }

            writer.close();
        } catch(Exception e) { //General error handling
            System.out.println("ERROR: " + e);
        }
    }

    /**
     * A method to load notifications into the application on startup
     */
    public void loadNotifications() {
        try { //Load in the data from notifications.txt
            try (BufferedReader br = new BufferedReader(new FileReader("notifications.txt"))) {
                String header = br.readLine();
                String line = br.readLine();
                while (line != null) { //Loop through each line and get the data points
                    String[] notificationLine = line.split("\\|\\|\\|");
                    int id = Integer.parseInt(notificationLine[0]);

                    User generator;
                    if(notificationLine[1].equals("System"))
                        generator = null;
                    else
                        generator = getUser(notificationLine[1]);

                    //Create the necessary components from data
                    User recipient = getUser(notificationLine[2]);
                    String message = notificationLine[3];
                    boolean isRead = Boolean.valueOf(notificationLine[4]);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
                    Date dateGenerated = sdf.parse(notificationLine[5]);

                    //Create a notification and add it to the list
                    Notification notification = new Notification(id, generator, recipient, message, isRead, dateGenerated);
                    notifications.add(notification);

                    line = br.readLine();
                }
            }
        } catch(Exception e) { //General error handling
            System.out.println("ERROR: " + e);
        }
    }

    /**
     * A method to save deadlines to file
     */
    public void saveDeadlines() {
        try { //Create a file called deadlines.txt and write that information to file
            FileWriter writer = new FileWriter("deadlines.txt");
            writer.write("=====DEADLINES=====\n");
            for(Deadline d : deadlines.values()) { //loop through each deadline and save it's info
                writer.write(d.saveDeadline());
            }

            writer.close();
        } catch(Exception e) { //General error handling
            System.out.println("ERROR: " + e);
        }
    }

    /**
     * A method to load in all deadlines on application start
     */
    public void loadDeadlines() {
        try { //Get the data from deadlines.txt
            try (BufferedReader br = new BufferedReader(new FileReader("deadlines.txt"))) {
                String header = br.readLine();
                String line = br.readLine();
                while (line != null) { //Loop through each line and get the data points
                    String[] deadlineLine = line.split("\\|\\|\\|");

                    String title = deadlineLine[0];

                    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
                    Date date = sdf.parse(deadlineLine[1]);

                    //Parse the data and create a Deadline
                    Deadline deadline = new Deadline(title, date);
                    deadlines.put(title, deadline);

                    line = br.readLine();
                }
            }
        } catch(Exception e) { //General error handling
            System.out.println("ERROR: " + e);
        }
    }

    /**
     * A method to load all the data in on application start
     * Loads in the users, then the papers, then the reviews, then the reports, then the notifications, and finally the deadlines
     */
    public void loadApplication() {
        loadUsers();
        loadPapers();
        loadReviews();
        loadReports();
        loadNotifications();
        loadDeadlines();
    }
}