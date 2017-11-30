package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.*;
import spark.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class PostRatePaperRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /ratePapers} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostRatePaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        Session session = request.session();
        vm.put("userType", paperManager.getUserType(request.session().attribute("username")));

        int pid = Integer.parseInt(request.queryParams("pid"));
        Paper p = paperManager.getPaperbyID(pid);
        PCC user = (PCC)paperManager.getUser(session.attribute("username"));

        Double pccRating = Double.parseDouble(request.queryParams("score"));
        String pccComment = request.queryParams("comment");
        String approval = request.queryParams("approval");
        AcceptanceStatus as = AcceptanceStatus.valueOf(approval);

        if(UIUtils.validateInputText(pccComment)) {
            vm.put("title", "Rate Papers");
            vm.put("username", session.attribute("username"));
            vm.put("paper", p);
            vm.put("reviews", paperManager.getReviewsForPaper(request.queryParams("pid")));
            return UIUtils.error(vm, "Review information cannot be blank or contain the characters '|||'", "ratePaper.ftl");
        }

        Review pccReview = new Review(user, p, pccRating, pccComment, false);
        List<Review> pcmReviews = paperManager.getReviewsForPaper(request.queryParams("pid"));
        Report report = new Report(p, user, pcmReviews, pccReview, as);
        paperManager.addReport(report);
        paperManager.saveReports();

        response.redirect("/ratePapers");
        halt();
        return null;
    }
}
