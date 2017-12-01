package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.*;
import spark.*;

import java.io.File;
import java.util.*;

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
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);

        if(!(userType.equals("PCC") || userType.equals("Admin"))) {
            response.redirect("/managePapers");
            halt();
            return null;
        }
        String pid = request.queryParams("pid");
        int paperID = UIUtils.parseIntInput(pid);
        if(paperID == -2) {
            response.redirect("/ratePapers");
            halt();
            return null;
        }


        Paper p = paperManager.getPaperbyID(paperID);
        PCC user = (PCC)paperManager.getUser(session.attribute("username"));

        String pccRatingString = request.queryParams("score");
        String pccComment = request.queryParams("comment");
        String approval = request.queryParams("approval");
        AcceptanceStatus as = AcceptanceStatus.valueOf(approval);

        Double pccRating = UIUtils.parseDoubleInput(pccRatingString);
        if(pccRating == -2.0) {
            response.redirect("/ratePapers");
            halt();
            return null;
        }

        if(UIUtils.validateInputText(pccComment)) {
            vm.put("title", "Rate Papers");
            vm.put("username", session.attribute("username"));
            vm.put("paper", p);
            vm.put("reviews", paperManager.getReviewsForPaper(request.queryParams("pid")));
            return UIUtils.error(vm, "Review information cannot be blank or contain the characters '|||'", "ratePaper.ftl");
        }

        Review pccReview = new Review(user, p, pccRating, pccComment, false);
        List<Review> pcmReviews = paperManager.getReviewsForPaper(pid);
        Report report = new Report(p, user, pcmReviews, pccReview, as);
        paperManager.addReport(report);
        paperManager.saveReports();

        String acceptanceString = report.getAcceptanceStatus().toString();

        if(acceptanceString.equals("Modify"))
            acceptanceString = "Accepted with modifications";

        String messageString = "Your paper '" + report.getSubject().getTitle() + "' has been rated by the SAM2018 Review Committee and has been " + acceptanceString + ".";
        Notification notification = new Notification(paperManager.getNotificationsSize(), null, report.getSubject().getContactAuthor(), messageString, false, new Date());
        paperManager.addNotification(notification);
        paperManager.saveNotifications();

        response.redirect("/ratePapers");
        halt();
        return null;
    }
}
