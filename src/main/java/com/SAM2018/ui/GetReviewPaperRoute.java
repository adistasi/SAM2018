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

        String username = request.session().attribute("username");
        int pid = Integer.parseInt(request.queryParams("pid"));

        vm.put("title", "Review Paper");
        vm.put("username", username);
        vm.put("paper", paperManager.getPaperbyID(pid));
        vm.put("userType", paperManager.getUserType(request.session().attribute("username")));

        Review thisReview = paperManager.getReview(pid, username);

        if(thisReview != null && thisReview.getNeedsRereviewed()) {
            Report report = paperManager.getReportByID(pid);
            List<Review> otherReviews = report.getPcmReviews();

            vm.put("otherReviews", otherReviews);
        }
        return new ModelAndView(vm , "reviewPaper.ftl");
    }
}
