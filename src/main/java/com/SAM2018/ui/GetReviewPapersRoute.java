package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.util.*;

import static spark.Spark.halt;

/**
 * The Web Controller for Viewing Papers assigned to you for Reviewing
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetReviewPapersRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /reviewPapers} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetReviewPapersRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }
    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String username = request.session().attribute("username");
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);

        if(!(userType.equals("PCM") || userType.equals("Admin"))) { //Redirect any non-PCM or Admin Users
            response.redirect("/managePapers");
            halt();
            return null;
        }

        //Put the pending reviews & completed reviews in the VM
        vm.put("title", "Your Reviews");
        vm.put("username", username);
        vm.put("reviews", paperManager.getPendingReviewsForUser(username));
        vm.put("completedReviews", paperManager.getCompletedReviewsForUser(username));
        return new ModelAndView(vm , "manageReviews.ftl");
    }
}