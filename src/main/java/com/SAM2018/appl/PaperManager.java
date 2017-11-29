package com.SAM2018.appl;

import com.SAM2018.Application;
import com.SAM2018.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import com.SAM2018.model.*;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaperManager {
    //Attributes
    private  Map<String, User> users = new HashMap<>();
    private List<Paper> papers = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private Map<String, List<User>> requestedReviews = new HashMap();

    public void addPaper(List<String> _authors, User _contactAuthor, String _title, String _format, int _version, String _paperUpload) {
        Paper paper = new Paper(papers.size(), _authors, _contactAuthor, _title, _format, _version, _paperUpload);

        papers.add(paper);
        _contactAuthor.addPaperToSubmissions(paper);
        savePapers();
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public List<String> validateAuthors(String _authors) {
        List<String> authors = new ArrayList<>();
        String[] authorsArr = _authors.split("/");
        List<String> authorsRaw = Arrays.asList(authorsArr);

        for(String auth : authorsRaw) {
            System.out.println(auth);
            if(auth != null && !auth.equals("")) {
                authors.add(auth);
            }
        }

        return authors;
    }

    public Set<String> getUsernames() {
        return users.keySet();
    }

    public User getContactAuthorByUsername(String _username) {
        return users.get(_username);
    }

    public Paper getPaperbyID(int id) {
        return papers.get((id));
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public List<Paper> getPapersForReview(String username) {
        List<Paper> reviewPapers = new ArrayList<>();

        for(Paper p : papers) {
            if(!p.getAuthors().contains(username) && !p.getContactAuthor().getUsername().equals(username))
                reviewPapers.add(p);
        }

        return reviewPapers;
    }

    public void savePapers() {
        try {
            FileWriter writer = new FileWriter("papers.txt");
            writer.write("=====PAPERS=====\n");
            for(Paper p : papers) {
                writer.write(p.getPaperID() + "|||");
                String authors = "";
                for(String author : p.getAuthors()) {
                    authors = authors.concat(author + ",");
                }
                if(authors.length() > 0)
                    authors = authors.substring(0, authors.length()-1); //trim off extra comma at end
                writer.write(authors + "|||");
                writer.write(p.getContactAuthor().getUsername() + "|||");
                writer.write(p.getTitle() + "|||");
                writer.write(p.getFormat() + "|||");
                writer.write(Integer.toString(p.getVersion()) + "|||");
                writer.write(p.getPaperUpload() + "|||");

                List<User> requestedReviewers = requestedReviews.get(Integer.toString(p.getPaperID()));
                String reqRevString = " ";
                if(requestedReviewers != null) {
                    for (User u : requestedReviewers) {
                        reqRevString += u.getUsername() + "/";
                    }
                }
                writer.write(reqRevString);


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
                System.out.println(line);
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
                    contactAuthor.addPaperToSubmissions(p);

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
                writer.write(u.getUsername() + "|||");
                writer.write(u.getClass().toString() + "|||");
                writer.write(u.getPassword() + "|||");
                writer.write(u.getFirstName() + "|||");
                writer.write(u.getLastName() + "|||\n");
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
            for(Review r : reviews) {
                writer.write(Integer.toString(r.getSubject().getPaperID()) + "|||");
                writer.write(r.getReviewer().getUsername() + "|||");
                writer.write(Double.toString(r.getRating()) + "|||");
                writer.write(r.getReviewerComments() + "\n");
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

                    Paper paper = getPaperbyID(paperID);
                    User reviewer = getUser(reviewerUsername);

                    Review review = new Review(reviewer, paper, score, comments);
                    reviews.add(review);

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
    }

    public void printPapersData() {
        System.out.println("THERE ARE " + papers.size() + " PAPERS");
        for(Paper paper : papers) {
            System.out.println("----------");
            System.out.println("Paper ID: " + paper.getPaperID());
            for(String a : paper.getAuthors()) {
                System.out.println("Author: " + a);
            }
            System.out.println("Contact Author: " + paper.getContactAuthor().getFirstName() + " " + paper.getContactAuthor().getLastName());
            System.out.println("Title: " + paper.getTitle());
            System.out.println("Format: " + paper.getFormat());
            System.out.println("Version: " + paper.getVersion());
            System.out.println("File Upload: " + paper.getPaperUpload());
            System.out.println("----------\n\n");
        }

    }

    public void printUsersData() {
        System.out.println("THERE ARE " + users.size() + " USERS");
        for (User u : users.values()) {
            System.out.println("----------");
            System.out.println("Username: " + u.getUsername());
            System.out.println("Type: " + u.getClass().toString());
            System.out.println("Password: " + u.getPassword());
            System.out.println("First name: " + u.getFirstName());
            System.out.println("Last name: " + u.getLastName());
            System.out.println("----------\n\n");
        }
    }

    /**
     * Methods for Login And Register
     */
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

    /**
     * Methods for requesting papers
     */
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

    public List<ReviewRequestDisplay> getRequestedReviews() {
        List<ReviewRequestDisplay> rrds = new ArrayList<>();

        for(Paper p : papers) {
            String paperID = Integer.toString(p.getPaperID());
            ReviewRequestDisplay rrd = new ReviewRequestDisplay(p, requestedReviews.get(paperID));
            rrds.add(rrd);
        }
        return rrds;
    }

    public void printReviewData() {
        System.out.println("-----REQUEST REVIEWS-----");
        for(String s : requestedReviews.keySet()) {
            System.out.println("----------");
            Paper p = getPaperbyID(Integer.parseInt(s));
            System.out.println(p.getTitle());

            for(User u : requestedReviews.get(s)) {
                System.out.println("     " + u.getFirstName() + " " + u.getLastName());
            }
            System.out.println("----------");
        }
    }
}
