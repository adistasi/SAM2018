package com.SAM2018.ui;

import com.SAM2018.model.Paper;
import com.SAM2018.model.Submitter;
import com.SAM2018.model.User;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GetRequestPaperReview for Requesting Available papers.
 *
 * @author <a href='mailto:rp3737@rit.edu'>Raseshwari Pulle</a>
 */

public class GetRequestPaperReview implements TemplateViewRoute {
  public static final String PAPERS_FOR_REVIEW= "paperForReview";
  public static final String REQUESTED_PAPERS= "requestedPapers";

  private static final ArrayList papersAvailableForReview = new ArrayList();
  private static final List authors=new ArrayList();
  @Override
  public ModelAndView handle(Request request, Response response) {
    Map<String, Object> vm = new HashMap<>();
    Submitter author1=new Submitter("Ross", "abc", "Ross", "Pulle");
    Submitter author2=new Submitter("Andy", "abc", "Andy", "Distasi");
    Submitter author3=new Submitter("Niharika", "abc", "Niharika", "Dalal");


    authors.add(author1);
    authors.add(author2);
    authors.add(author3);

    Paper paper1= new Paper(1, authors, author1, "Paper 1", "IEEE", 1);
    Paper paper2= new Paper(2, authors, author2, "Paper 2", "IEEE", 1);
    Paper paper3= new Paper(3, authors, author3, "Paper 3", "IEEE", 1);
    papersAvailableForReview.add(paper1);
    papersAvailableForReview.add(paper2);
    papersAvailableForReview.add(paper3);

    vm.put("title", "Papers available:");
    vm.put(PAPERS_FOR_REVIEW,papersAvailableForReview);
    return new ModelAndView(vm , "reviewPaper.ftl");
  }


  public ArrayList getPaperFromPaperIDs(String ids[])
  {

    ArrayList requestedPapers= new ArrayList();
    List paperIds=new ArrayList();

    for(int i=0;i<ids.length;i++) //parse and store all the requested papers ids
    {
      paperIds.add(Integer.parseInt(ids[i]));
    }

    for (int j=0;j<paperIds.size();j++) //retrieve papers matching the paper ids stored in previous for loop
    {
      System.out.println("inside 2nd for");
      for(int k=0;k<papersAvailableForReview.size();k++)
      {
        Paper paper = (Paper) papersAvailableForReview.get(k);
        System.out.println(paper.getAuthors());
        System.out.println((int)paperIds.get(j));
        System.out.println(paper.getPaperID());
        if ((int) paperIds.get(j) == paper.getPaperID())
        {
          requestedPapers.add(paper);
        }
      }

    }
    return requestedPapers;

  }
}