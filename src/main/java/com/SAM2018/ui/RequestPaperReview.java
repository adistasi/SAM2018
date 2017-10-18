package com.SAM2018.ui;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Web Controller for the Home page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class RequestPaperReview implements TemplateViewRoute {
  public static final String PAPERS_FOR_REVIEW= "paperForReview";


  @Override
  public ModelAndView handle(Request request, Response response) {
    Map<String, Object> vm = new HashMap<>();
    List papersAvailableForReview = new ArrayList();
    papersAvailableForReview.add(0,"Paper1");
    papersAvailableForReview.add(1,"Paper2");
    papersAvailableForReview.add(2,"Paper 3");
    papersAvailableForReview.add(3,"Paper 4");
    papersAvailableForReview.add(4,"Paper 5");

    vm.put("title", "Welcome!");
    vm.put(PAPERS_FOR_REVIEW,papersAvailableForReview);
    return new ModelAndView(vm , "reviewPaper.ftl");
  }

}