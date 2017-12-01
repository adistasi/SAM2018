package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Message;
import com.SAM2018.model.Notification;
import com.SAM2018.model.Report;
import com.SAM2018.model.Review;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.*;

import static spark.Spark.halt;

public class PostRereviewPaperRoute implements Route {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /rereviewPaper} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostRereviewPaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);

        if(!(userType.equals("PCC") || userType.equals("Admin"))) {
            response.redirect("/managePapers");
            halt();
            return null;
        }


        String rawPid = request.body();
        int pid = UIUtils.parseIntInput(rawPid);
        if(pid == -2) {
            response.redirect("/ratePapers");
            halt();
            return null;
        }

        List<Review> rereviews = paperManager.getReviewsForPaper(rawPid);

        for(Review r : rereviews) {
            r.setNeedsRereviewed(true);
            String messageString = "The Reviews for '" + r.getSubject().getTitle() + "' need to be redone.  Please read each other's comments and then re-submit an updated Review";
            Notification notification = new Notification(paperManager.getNotificationsSize(), null, r.getReviewer(), messageString, false, new Date());
            paperManager.addNotification(notification);
        }

        paperManager.saveReviews();
        paperManager.saveNotifications();

        return new Message("PCMs have been asked to review the paper again", "info");
    }
}
