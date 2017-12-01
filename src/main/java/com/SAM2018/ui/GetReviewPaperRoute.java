package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Report;
import com.SAM2018.model.Review;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class GetReviewPaperRoute implements TemplateViewRoute{
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /reviewPapers} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetReviewPaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);

        if(!(userType.equals("PCM") || userType.equals("Admin"))) {
            response.redirect("/managePapers");
            halt();
            return null;
        }

        String username = request.session().attribute("username");
        String pid = request.queryParams("pid");
        int paperID = UIUtils.parseIntInput(pid);
        if(paperID == -2) {
            response.redirect("/reviewPapers");
            halt();
            return null;
        }

        vm.put("title", "Review Paper");
        vm.put("username", username);

        Review thisReview = paperManager.getReview(paperID, username);

        if(thisReview != null && !thisReview.getReviewer().getUsername().equals(username)) {
            response.redirect("/reviewPapers");
            halt();
            return null;
        }

        vm.put("paper", paperManager.getPaperbyID(paperID));

        if(thisReview != null && thisReview.getNeedsRereviewed()) {
            List<Review> otherReviews = paperManager.getReviewsForPaper(pid);

            vm.put("otherReviews", otherReviews);
        }

        return new ModelAndView(vm , "reviewPaper.ftl");
    }
}
