package com.SAM2018.ui;

import com.SAM2018.model.Paper;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The PostRequestedPaperRoute for displaying requested Papers.
 *
 * @author <a href='mailto:rp3737@rit.edu'>Raseshwari Pulle</a>
 */
public class PostRequestedPaperRoute implements TemplateViewRoute {

  public static final String REQUESTED_PAPERS= "papersRequested";
  public static final String NO_PAPER_REQUESTED = "You have not requested any paper.."; //message when no paper is requested
  @Override
  public ModelAndView handle(Request request, Response response) {
    Map<String, Object> vm = new HashMap<>();
    GetRequestPaperReview paperReq = new GetRequestPaperReview();
    ArrayList paperReqs =new ArrayList();

    QueryParamsMap map = request.queryMap("requestedPaper");
    System.out.println(map.booleanValue());
    if(map.booleanValue()==null)
    {
      vm.put("title",NO_PAPER_REQUESTED);
      return new ModelAndView(vm , "reviewManagement.ftl"); //return with no paper requested message
    }

    String papers[] = map.values();

    System.out.println("PsotRequested Paper size: "+papers.length);
    ArrayList requestedPapers = paperReq.getPaperFromPaperIDs(papers);

    for (Object obj : requestedPapers)
    {
      Paper paper = (Paper)obj;
      paperReqs.add(paper);
    }

    vm.put(REQUESTED_PAPERS,paperReqs);
    vm.put("title", "Papers Requested Are:");
    return new ModelAndView(vm , "reviewManagement.ftl");
  }

}