package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.util.*;

import static spark.Spark.halt;

/**
 * The GetReviewPapersRoute for Viewing Papers assigned to you for Reviewing
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */

public class GetReviewPapersRoute implements TemplateViewRoute {

    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /reviewPapers} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetReviewPapersRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }
    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);

        String username = request.session().attribute("username");

        vm.put("title", "Your Reviews");
        vm.put("username", username);
        vm.put("reviews", paperManager.getPendingReviewsForUser(username));
        return new ModelAndView(vm , "manageReviews.ftl");
    }
}