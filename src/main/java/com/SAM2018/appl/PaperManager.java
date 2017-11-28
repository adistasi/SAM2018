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
    private Map<String, List<User>> requestedReviews = new HashMap();

    public void addPaper(List<String> _authors, User _contactAuthor, String _title, String _format, int _version, String _paperUpload) {
        Paper paper = new Paper(papers.size(), _authors, _contactAuthor, _title, _format, _version, _paperUpload);

        papers.add(paper);
        _contactAuthor.addPaperToSubmissions(paper);
        savePapers();
    }

    //TODO: ACTUAL AUTHOR RETRIEVAL
    public List<String> getAllAuthors(String authorLine1, String authorLine2, String authorLine3) {
        List<String> authors = new ArrayList<>();
        if(authorLine1 != null || !authorLine1.equals("")) {
            authors.add(authorLine1);
        }

        if(authorLine2 != null || !authorLine2.equals("")) {
            authors.add(authorLine2);
        }

        if(authorLine2 != null || !authorLine2.equals("")) {
            authors.add(authorLine3);
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
                writer.write(p.getPaperUpload() + "\n");
            }

            writer.close();
        } catch(Exception e) {
            System.out.println(e);
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

                    Paper p = new Paper(papers.size(), authors, contactAuthor, title, format, version, paperUpload);
                    papers.add(p);
                    contactAuthor.addPaperToSubmissions(p);

                    line = br.readLine();
                }
            }
        } catch(Exception e) {
            System.out.println(e.getStackTrace());
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
                /*String paperIDs = "";
                if(u.getSubmissions() != null) {
                    for (Paper p : u.getSubmissions()) {
                        paperIDs = paperIDs.concat(p.getPaperID() + ",");
                    }
                    if (paperIDs.length() > 0)
                        paperIDs = paperIDs.substring(0, paperIDs.length() - 1); //trim off extra comma
                    writer.write(paperIDs + "\n");
                } else {
                    writer.write(" \n");
                }*/
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

    public void loadApplication() {
        loadUsers();
        loadPapers();
    }

    public void printPapersData() {
        System.out.println("THERE ARE " + papers.size() + " PAPERS");
        for(Paper pape : papers) {
            System.out.println("----------");
            System.out.println("Paper ID: " + pape.getPaperID());
            for(String a : pape.getAuthors()) {
                System.out.println("Author: " + a);
            }
            System.out.println("Contact Author: " + pape.getContactAuthor().getFirstName() + " " + pape.getContactAuthor().getLastName());
            System.out.println("Title: " + pape.getTitle());
            System.out.println("Format: " + pape.getFormat());
            System.out.println("Version: " + pape.getVersion());
            System.out.println("File Upload: " + pape.getPaperUpload());
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
    }

    public List<ReviewRequestDisplay> getRequestedReviews() {
        List<ReviewRequestDisplay> rrds = new ArrayList<>();
        for(String s : requestedReviews.keySet()) {
            Paper p = getPaperbyID(Integer.parseInt(s));
            ReviewRequestDisplay rrd = new ReviewRequestDisplay(p, requestedReviews.get(s));
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
