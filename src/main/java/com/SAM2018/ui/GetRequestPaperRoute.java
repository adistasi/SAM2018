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
        vm = UIUtils.validateLoggedIn(request, response, vm);

        String username = request.session().attribute("username");
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);


        if(!(userType.equals("PCM") || userType.equals("Admin"))) {
            response.redirect("/managePapers");
            halt();
            return null;
        }

        vm.put("title", "Request a Paper");
        vm.put("hasRequested", paperManager.hasUserMadeRequest(username));
        vm.put(PAPERS_FOR_REVIEW, paperManager.getPapersForReview(username));
        return new ModelAndView(vm , "requestPaper.ftl");
    }
}