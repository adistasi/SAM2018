package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Review;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class PostReviewPaperRoute implements TemplateViewRoute{
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /reviewPaper} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostReviewPaperRoute(final PaperManager _paperManager) {
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

        int pid = UIUtils.parseIntInput(request.queryParams("pid"));
        double score = UIUtils.parseDoubleInput(request.queryParams("score"));
        String comments = request.queryParams("comment");

        if(pid == -2 || score == -2.0) {
            response.redirect("/reviewPapers");
            halt();
            return null;
        }

        if(UIUtils.validateInputText(comments)) {
            vm.put("title", "Review Paper");
            vm.put("username", username);
            vm.put("paper", paperManager.getPaperbyID(pid));
            return UIUtils.error(vm, "Review information cannot be blank or contain the characters '|||'", "reviewPaper.ftl");
        }

        Review review = paperManager.getReview(pid, username);

        if(review != null) {
            review.setRating(score);
            review.setReviewerComments(comments);
            review.setNeedsRereviewed(false);
            paperManager.saveReviews();
        }

        //we'll also need to differentiate between completed and uncompleted reviews (maybe for score = -1)
        response.redirect("/reviewPapers");
        halt();
        return null;
    }
}
