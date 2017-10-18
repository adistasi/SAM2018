package com.SAM2018.appl;

import com.SAM2018.model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaperManager {
    //Attributes
    private Map<String, User> users = new HashMap<>();
    private List<Paper> papers = new ArrayList<>();

    public void addPaper(List<String> _authors, User _contactAuthor, String _title, String _format, int _version, String _paperUpload) {
        Paper paper = new Paper(_authors, _contactAuthor, _title, _format, _version, _paperUpload);

        papers.add(paper);
    }

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

    public User getContactAuthorByUsername(String _username) {
        return users.get(_username);
    }

    //TEST STUFF!
    public void initForTest() {
        users.put("add5980", new Admin("add5980", "pass", "Andy", "DiStasi"));
        users.put("microMan", new PCM("microMan", "pass", "Bill", "Gates"));
        users.put("username", new Submitter("username", "pass", "Test", "User"));
    }

    public void printPapersData() {
        System.out.println("THERE ARE " + papers.size() + " PAPERS");
        for(Paper pape : papers) {
            System.out.println("----------");
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
}
