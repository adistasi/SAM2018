package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.util.*;

import static spark.Spark.halt;

/**
 * The GetRequestPaperRoute for Requesting Available papers.
 *
 * @author <a href='mailto:rp3737@rit.edu'>Raseshwari Pulle</a>
 */

public class GetRequestPaperRoute implements TemplateViewRoute {
  public static final String PAPERS_FOR_REVIEW= "paperForReview";

  //Attributes
  private final PaperManager paperManager;

  /**
   * The constructor for the {@code POST /submitPaper} route handler
   * @param _paperManager The {@link PaperManager} for the application.
   */
  GetRequestPaperRoute(final PaperManager _paperManager) {
    Objects.requireNonNull(_paperManager, "PaperManager must not be null");

    this.paperManager = _paperManager;
  }
  @Override
  public ModelAndView handle(Request request, Response response) {
    Map<String, Object> vm = new HashMap<>();

    if(request.session().attribute("username") != null) {
      vm.put("username", request.session().attribute("username"));
    } else {
      response.redirect("/login");
      halt();
      return null;
    }

    vm.put("title", "Request a Paper");
    vm.put(PAPERS_FOR_REVIEW, paperManager.getPapers());
    return new ModelAndView(vm , "reviewPaper.ftl");
  }
}