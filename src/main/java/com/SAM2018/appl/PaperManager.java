package com.SAM2018.appl;

import com.SAM2018.model.*;

import java.io.*;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //USER FUNCTIONALITY
    public Set<String> getUsernames() {
        return users.keySet();
    }

    public User getContactAuthorByUsername(String _username) {
        return users.get(_username);
    }

    public void addUser(User user){
        users.put(user.getUsername(), user);
        saveUsers();
    }

    public void removeUser(String username){
        users.remove(username);
    }

    public boolean userExists(String username){
        return users.containsKey(username);
    }

    public User getUser(String username){
        return users.get(username);
    }

    public List<User> getAllPCMs() {
        List<User> pcms = new ArrayList<>();
        for(User u : users.values()) {
            if(u instanceof PCM)
                pcms.add(u);
        }

        return pcms;
    }

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

    public List<User> getAllUsers(String _username) {
        List<User> userList = new ArrayList<>();

        for(User u : users.values()) {
            if(!u.getUsername().equals(_username)) {
                userList.add(u);
            }
        }

        return userList;
    }

    public User getPCC() {
        for(User u : users.values()) {
            if(u instanceof PCC)
                return u;
        }

        return null;
    }

    public int getCountPCC() {
        int pccs = 0;
        for(User u : users.values()) {
            if(u instanceof PCC)
                pccs++;
        }

        return pccs;
    }

    public User getAdmin() {
        for(User u : users.values()) {
            if(u instanceof Admin)
                return u;
        }

        return null;
    }

    public void requestPermissions(User _user, String _permissionlevel) {
        requestedPermissions.put(_user, _permissionlevel);
    }

    public List<PermissionRequestDisplay> getRequestedPermissions() {
        List<PermissionRequestDisplay> prd = new ArrayList<>();
        for(User u : requestedPermissions.keySet()) {
            PermissionRequestDisplay p = new PermissionRequestDisplay(u, requestedPermissions.get(u));
            prd.add(p);
        }

        return prd;
    }

    public void assignRole(String _username, boolean _approved) {
        User user = getUser(_username);
        String type = requestedPermissions.get(user);

        String username = user.getUsername();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        if(_approved) {
            if(type.equals("PCC")) {
                users.remove(user.getUsername());
                PCC newPCC = new PCC(username, password, firstName, lastName);
                users.put(newPCC.getUsername(), newPCC);
            } else if(type.equals("PCM")) {
                users.remove(user.getUsername());
                PCM newPCM = new PCM(username, password, firstName, lastName);
                users.put(newPCM.getUsername(), newPCM);
            }
        }

        requestedPermissions.remove(user);
        saveUsers();
    }

    public void deleteUser(String _username) {
        users.remove(_username);
        requestedPermissions.remove(getUser(_username));
        //TODO: Remove from reviews, requests, papers, & ratings
        saveUsers();
    }


    //PAPER FUNCTIONALITY
    public void addPaper(List<String> _authors, User _contactAuthor, String _title, String _format, int _version, String _paperUpload) {
        Paper paper = new Paper(papers.size(), _authors, _contactAuthor, _title, _format, _version, _paperUpload);

        papers.add(paper);
        _contactAuthor.submitPaper(paper);
        savePapers();
    }

    public void addPaper(Paper _paper) {
        papers.add(_paper);
    }

    public int getPaperCount() {
        return papers.size();
    }

    public List<String> validateAuthors(String _authors) {
        List<String> authors = new ArrayList<>();
        String[] authorsArr = _authors.split("/");
        List<String> authorsRaw = Arrays.asList(authorsArr);

        for(String auth : authorsRaw) {
            if(auth != null && !auth.equals("")) {
                authors.add(auth);
            }
        }

        return authors;
    }

    public Paper getPaperbyID(int id) {
        try {
            return papers.get((id));
        } catch(Exception e) {
            return null;
        }
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public List<Paper> getPapersForReview(String username) {
        List<Paper> reviewPapers = new ArrayList<>();

        for(Paper p : papers) {
            List<Review> paperReviews = getReviewsForPaper(Integer.toString(p.getPaperID()));
            if(!p.getAuthors().contains(username) && !p.getContactAuthor().getUsername().equals(username) && paperReviews == null)
                reviewPapers.add(p);
        }

        return reviewPapers;
    }


    //REQUEST FUNCTIONALITY
    public void addRequest(Paper paper, User user) {
        String paperID = Integer.toString(paper.getPaperID());
        if(requestedReviews.get(paperID) != null) {
            List<User> reqUsers = requestedReviews.get(paperID);
            reqUsers.add(user);
            requestedReviews.put(paperID, reqUsers);
        } else {
            List<User> reqUsers = new ArrayList<>();
            reqUsers.add(user);
            requestedReviews.put(paperID, reqUsers);
        }

        savePapers();
    }

    public void clearRequests() {
        requestedReviews.clear();
    }

    public List<ReviewRequestDisplay> getRequestedReviews() {
        List<ReviewRequestDisplay> rrds = new ArrayList<>();

        for(Paper p : papers) {
            String paperID = Integer.toString(p.getPaperID());
            List<Review> reviews = getReviewsForPaper(paperID);
            ReviewRequestDisplay rrd = new ReviewRequestDisplay(p, requestedReviews.get(paperID), reviews == null);
            rrds.add(rrd);
        }
        return rrds;
    }

    public boolean hasUserMadeRequest(String username) {
        User user = getUser(username);
        for(List<User> reqUsers : requestedReviews.values()) {
            if(reqUsers.contains(user))
                return true;
        }

        return false;
    }

    public boolean areOutstandingRequests() {
        return requestedReviews.size() > 0;
    }


    //REVIEW FUNCTIONALITY
    public void addReview(String paperID, Review review) {
        if(reviews.get(paperID) != null) {
            List<Review> paperReviews = reviews.get(paperID);
            paperReviews.add(review);
            reviews.put(paperID, paperReviews);
        } else {
            List<Review> paperReviews = new ArrayList<>();
            paperReviews.add(review);
            reviews.put(paperID, paperReviews);
        }
    }

    public List<Review> getReviewsForUser(String username) {
        List<Review> userReviews = new ArrayList<>();

        for(List<Review> revs : reviews.values()) {
            for(Review r : revs) {
                if (r.getReviewer().getUsername().equals(username)) {
                    userReviews.add(r);
                }
            }
        }

        return userReviews;
    }

    public List<Review> getPendingReviewsForUser(String username) {
        List<Review> userReviews = new ArrayList<>();

        for(List<Review> revs : reviews.values()) {
            for(Review r : revs) {
                if (r.getReviewer().getUsername().equals(username) && (r.getNeedsRereviewed() || r.getRating() == -1))
                    userReviews.add(r);
            }
        }

        return userReviews;
    }

    public List<Review> getCompletedReviewsForUser(String username) {
        List<Review> completedReviews = new ArrayList<>();

        for(List<Review> revs : reviews.values()) {
            for(Review r : revs) {
                if(r.getReviewer().getUsername().equals(username) && (r.getNeedsRereviewed() || r.getRating() >= 0))
                    completedReviews.add(r);
            }
        }

        return completedReviews;
    }

    public Review getReview(int paperID, String username) {
        if(getPaperbyID(paperID) != null) {
            for (Review r : reviews.get(Integer.toString(paperID))) {
                if (r.getSubject().getPaperID() == paperID && r.getReviewer().getUsername().equals(username))
                    return r;
            }
        }

        return null;
    }

    public List<Review> getReviewsForPaper(String paperID) {
        return reviews.get(paperID);
    }

        public int getReviewsLeftForPaper(String _paperID) {
        List<Review> paperReviews = reviews.get(_paperID);
        int countCompleted = 0;

        for(Review r : paperReviews) {
            if(r.getRating() != -1 && !r.getNeedsRereviewed())
                countCompleted++;
        }
        return REVIEWS_PER_PAPER - countCompleted;
    }

    //REPORTS FUNCTIONALITY
    public void addReport(Report _report) {
        reports.add(_report);
    }

    public List<Report> getReports() {return reports; }

    public Report getReportByID(int paperID) {
        for(Report r : reports) {
            if(r.getSubject().getPaperID() == paperID) {
                return r;
            }
        }

        return null;
    }

    public List<Paper> getRatablePapers() {
        List<Paper> ratablePapers = new ArrayList<>();

        for(List<Review> revs: reviews.values()) {
            if(revs.size() == REVIEWS_PER_PAPER) {
                boolean allReviewsComplete = true;

                for(int i=0; i < REVIEWS_PER_PAPER; i++) {
                    if(revs.get(i).getRating() == -1 || revs.get(i).getNeedsRereviewed()) {
                        allReviewsComplete = false;
                        break;
                    }
                }

                if(allReviewsComplete && !reports.contains(getReportByID(revs.get(0).getSubject().getPaperID())))
                    ratablePapers.add(revs.get(0).getSubject());
            }
        }

        return ratablePapers;
    }

    //NOTIFICATION FUNCTIONALITY
    public List<Notification> getNotifications() {
        return notifications;
    }

    public List<Notification> getUnreadNotificationsForUser(String username) {
        List<Notification> nots = new ArrayList<>();

        for(Notification n : notifications) {
            if(n.getRecipient().getUsername().equals(username) && !n.getIsRead())
                nots.add(n);
        }

        return nots;
    }

    public List<Notification> getReadNotificationsForUser(String username) {
        List<Notification> nots = new ArrayList<>();

        for(Notification n : notifications) {
            if(n.getRecipient().getUsername().equals(username) && n.getIsRead())
                nots.add(n);
        }

        return nots;
    }

    public int getUnreadNotificationCount(String _username) {
        List<Notification> nots = getUnreadNotificationsForUser(_username);
        return nots.size();
    }

    public void addNotification(Notification n) {
        notifications.add(n);
    }

    public int getNotificationsSize() {
        return notifications.size();
    }

    public Notification getNotificationByID(int id) {
        try {
            return notifications.get((id));
        } catch(Exception e) {
            return null;
        }
    }


    //DEADLINE FUNCTIONALITY
    public void addDeadline(String _title, Deadline _deadline) {
        deadlines.put(_title, _deadline);
    }

    public Deadline getDeadline(String title) {
        return deadlines.get(title);
    }

    public Map<String, Deadline> getDeadlines() {
        return deadlines;
    }

    public void enforceSubmissionDeadline() {
        List<User> pcms = getAllPCMs();

        for(User pcm : pcms) {
            Notification not = new Notification(getNotificationsSize(), null, pcm, "The Submission Deadline has passed.  Please request papers you wish to review.", false, new Date());
            addNotification(not);
        }
        saveNotifications();

    }

    public void enforceRequestDeadline() {
        if(areOutstandingRequests()) {
            User pcc = getPCC();

            Notification not = new Notification(getNotificationsSize(), null, pcc, "The Submission Deadline has passed, but there are still unassigned papers.  Please assign these papers ASAP.", false, new Date());
            addNotification(not);
            //Notify PCC every day for any unassigned papers

            saveNotifications();
        }
    }

    public void enforceReviewDeadline() {
        List<User> pcms = getAllPCMs();

        for(User pcm : pcms) {
            List<Review> pendingReviews = getPendingReviewsForUser(pcm.getUsername());

            if(pendingReviews.size() > 0) {
                Notification not = new Notification(getNotificationsSize(), null, pcm, "The Review Deadline has passed, but you still have pending Reviews.  Please review these papers ASAP.", false, new Date());
                addNotification(not);
            }
        }
    }

    public void enforceRatingDeadline() {
        User pcc = getPCC();

        if(getRatablePapers().size() > 0) {
            Notification not = new Notification(getNotificationsSize(), null, pcc, "The Final Report Generation Deadline has passed, but you still have pending Reports for Papers.  Please review these papers ASAP.", false, new Date());
            addNotification(not);
            saveNotifications();
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer _timer) {
        this.timer = _timer;
    }



    //SAVING FUNCTIONALITY
    public void savePapers() {
        try {
            FileWriter writer = new FileWriter("papers.txt");
            writer.write("=====PAPERS=====\n");
            for(Paper p : papers) {
                writer.write(p.savePaper());

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
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPapers() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("papers.txt"))) {
                String header = br.readLine();
                String line = br.readLine();
                while (line != null) {
                    String[] paperLine = line.split("\\|\\|\\|");
                    int id = Integer.parseInt(paperLine[0]);
                    List<String> authors = Arrays.asList(paperLine[1].split(","));
                    User contactAuthor = getContactAuthorByUsername(paperLine[2]);
                    String title = paperLine[3];
                    String format = paperLine[4];
                    int version = Integer.parseInt(paperLine[5]);
                    String paperUpload = paperLine[6];

                    Paper p = new Paper(id, authors, contactAuthor, title, format, version, paperUpload);
                    papers.add(p);
                    contactAuthor.submitPaper(p);

                    if(paperLine.length > 7) {
                        String[] requestors = paperLine[7].split("/");
                        if(!requestors[0].equals(" ")) {
                            for(int i=0; i < requestors.length; i++) {
                                String username = requestors[i].trim();
                                PCM reqUser = (PCM)getUser(username);
                                reqUser.requestReview(p);

                                if(requestedReviews.get(Integer.toString(id)) != null) {
                                    List<User> reqUsers = requestedReviews.get(Integer.toString(id));
                                    reqUsers.add(reqUser);
                                    requestedReviews.put(Integer.toString(id), reqUsers);
                                } else {
                                    List<User> reqUsers = new ArrayList<>();
                                    reqUsers.add(reqUser);
                                    requestedReviews.put(Integer.toString(id), reqUsers);
                                }
                            }
                        }
                    }

                    line = br.readLine();
                }
            }
        } catch(Exception e) {
            e.getStackTrace();
        }
    }

    public void saveUsers() {
        try {
            FileWriter writer = new FileWriter("users.txt");
            writer.write("=====USERS=====\n");
            for(User u : users.values()) {
                writer.write(u.saveUser());

                String requestedPermissionVal = requestedPermissions.get(u);
                if(requestedPermissionVal != null)
                    writer.write(requestedPermissionVal + "\n");
                else
                    writer.write("\n");
            }

            writer.close();
        } catch(Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public void loadUsers() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
                String header = br.readLine();
                String line = br.readLine();
                while (line != null) {
                    String[] userLine = line.split("\\|\\|\\|");
                    String username = userLine[0];
                    String classString = userLine[1];
                    String password = userLine[2];
                    String firstName = userLine[3];
                    String lastName = userLine[4];

                    User user;
                    if(classString.contains("Admin")) {
                        user = new Admin(username, password, firstName, lastName);
                    } else if(classString.contains("PCC")) {
                        user = new PCC(username, password, firstName, lastName);
                    } else if(classString.contains("PCM")) {
                        user = new PCM(username, password, firstName, lastName);
                    } else {
                        user = new Submitter(username, password, firstName, lastName);
                    }

                    users.put(username, user);

                    if(userLine.length == 6) {
                        requestedPermissions.put(user, userLine[5]);
                    }

                    line = br.readLine();
                }
            }
        } catch(Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public void saveReviews() {
        try {
            FileWriter writer = new FileWriter("reviews.txt");
            writer.write("=====REVIEWS=====\n");
            for(List<Review> rev : reviews.values()) {
                for(Review r : rev) {
                    writer.write(r.saveReview());
                }
            }

            writer.close();
        } catch(Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public void loadReviews() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("reviews.txt"))) {
                String header = br.readLine();
                String line = br.readLine();
                while (line != null) {
                    String[] reviewLine = line.split("\\|\\|\\|");
                    int paperID = Integer.parseInt(reviewLine[0]);
                    String reviewerUsername = reviewLine[1];
                    double score = Double.parseDouble(reviewLine[2]);
                    String comments = reviewLine[3];
                    boolean needsReReviewed = Boolean.valueOf(reviewLine[4]);

                    Paper paper = getPaperbyID(paperID);
                    User reviewer = getUser(reviewerUsername);

                    Review review = new Review(reviewer, paper, score, comments, needsReReviewed);

                    if(reviews.get(Integer.toString(paperID)) != null) {
                        List<Review> paperReviews = reviews.get(Integer.toString(paperID));
                        paperReviews.add(review);
                        reviews.put(Integer.toString(paperID), paperReviews);
                    } else {
                        List<Review> paperReviews = new ArrayList<>();
                        paperReviews.add(review);
                        reviews.put(Integer.toString(paperID), paperReviews);
                    }

                    line = br.readLine();
                }
            }
        } catch(Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public void saveReports() {
        try {
            FileWriter writer = new FileWriter("reports.txt");
            writer.write("=====REPORTS=====\n");
            for(Report r : reports) {
                writer.write(r.saveReport());
            }

            writer.close();
        } catch(Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public void loadReports() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("reports.txt"))) {
                String header = br.readLine();
                String line = br.readLine();
                while (line != null) {
                    String[] ratingLine = line.split("\\|\\|\\|");
                    int paperID = Integer.parseInt(ratingLine[0]);
                    String pccUsername = ratingLine[1];
                    double pccScore = Double.parseDouble(ratingLine[2]);
                    String pccComments = ratingLine[3];
                    AcceptanceStatus acceptanceStatus = AcceptanceStatus.valueOf(ratingLine[4]);

                    Paper paper = getPaperbyID(paperID);
                    PCC pcc = (PCC)getUser(pccUsername);
                    List<Review> pcmReviews = reviews.get(Integer.toString(paperID));

                    Review pccReview = new Review(pcc, paper, pccScore, pccComments, false);
                    Report report = new Report(paper, pcc, pcmReviews, pccReview, acceptanceStatus);
                    reports.add(report);
                    line = br.readLine();
                }
            }
        } catch(Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public void saveNotifications() {
        try {
            FileWriter writer = new FileWriter("notifications.txt");
            writer.write("=====NOTIFICATIONS=====\n");
            for(Notification n : notifications) {
                writer.write(n.saveNotification());
            }

            writer.close();
        } catch(Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public void loadNotifications() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("notifications.txt"))) {
                String header = br.readLine();
                String line = br.readLine();
                while (line != null) {
                    String[] notificationLine = line.split("\\|\\|\\|");
                    int id = Integer.parseInt(notificationLine[0]);

                    User generator;
                    if(notificationLine[1].equals("System"))
                        generator = null;
                    else
                        generator = getUser(notificationLine[1]);

                    User recipient = getUser(notificationLine[2]);
                    String message = notificationLine[3];
                    boolean isRead = Boolean.valueOf(notificationLine[4]);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
                    Date dateGenerated = sdf.parse(notificationLine[5]);


                    Notification notification = new Notification(id, generator, recipient, message, isRead, dateGenerated);
                    notifications.add(notification);

                    line = br.readLine();
                }
            }
        } catch(Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public void saveDeadlines() {
        try {
            FileWriter writer = new FileWriter("deadlines.txt");
            writer.write("=====DEADLINES=====\n");
            for(Deadline d : deadlines.values()) {
                writer.write(d.saveDeadline());
            }

            writer.close();
        } catch(Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public void loadDeadlines() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("deadlines.txt"))) {
                String header = br.readLine();
                String line = br.readLine();
                while (line != null) {
                    String[] deadlineLine = line.split("\\|\\|\\|");

                    String title = deadlineLine[0];

                    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
                    Date date = sdf.parse(deadlineLine[1]);

                    Deadline deadline = new Deadline(title, date);
                    deadlines.put(title, deadline);

                    line = br.readLine();
                }
            }
        } catch(Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public void loadApplication() {
        loadUsers();
        loadPapers();
        loadReviews();
        loadReports();
        loadNotifications();
        loadDeadlines();
    }
}