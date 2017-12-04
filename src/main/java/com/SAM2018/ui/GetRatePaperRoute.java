package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Review;
import spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The Web Controller for the Rate Paper page.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetRatePaperRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /ratePaper} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetRatePaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        Session session = request.session();

        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);
        vm.put("title", "Rate Paper");
        vm.put("username", session.attribute("username"));

        if(!(userType.equals("PCC") || userType.equals("Admin"))) { //Redirect any non-PCC or Admin users
            response.redirect("/managePapers");
            halt();
            return null;
        }

        //Read in the Paper ID & check for invalid inputs, redirecting if it's invalid
        String paperIDString = request.queryParams("pid");
        int paperID = UIUtils.parseIntInput(paperIDString);
        if(paperID == -2) {
            response.redirect("/ratePapers");
            halt();
            return null;
        }

        List<Review> reviews = paperManager.getReviewsForPaper(paperIDString);

        if(reviews.size() < 3) { //If there aren't enough Reviews, then they can't access the rating page yet
            response.redirect("/ratePapers");
            halt();
            return null;
        }

        //Put the paper & it's reviews in the VM and load the page
        vm.put("paper", paperManager.getPaperbyID(paperID));
        vm.put("reviews", reviews);
        return new ModelAndView(vm , "ratePaper.ftl");
    }
}
