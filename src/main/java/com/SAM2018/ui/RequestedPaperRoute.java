package com.SAM2018.ui;

import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Web Controller for the Home page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class RequestedPaperRoute implements TemplateViewRoute {

  public static final String REQUESTED_PAPERS= "papersRequested";

  @Override
  public ModelAndView handle(Request request, Response response) {
    Map<String, Object> vm = new HashMap<>();
    QueryParamsMap map=request.queryMap("requestedPaper");
    String papers[]=map.values();

    //String papers = request.queryParams("requestedPaper");
    //String papers ;
    System.out.println(papers[0]);
    //QueryParamsMap papersAvailableForReview =request.queryMap("requestedPaper");
    //List papers = (List) papersAvailableForReview.get("requestedPaper");


    vm.put("title", "Welcome!");
    vm.put(REQUESTED_PAPERS,papers);
    return new ModelAndView(vm , "reviewManagement.ftl");
  }

}