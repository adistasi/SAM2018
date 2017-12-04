package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Message;
import com.SAM2018.model.Notification;
import com.SAM2018.model.Review;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.*;

import static spark.Spark.halt;

/**
 * The Web Controller for the ReReviewPaper POST.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
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
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);

        if(!(userType.equals("PCC") || userType.equals("Admin"))) { //Redirect non PCC & Admin users
            response.redirect("/managePapers");
            halt();
            return null;
        }

        //Read in the input (paper ID) and parse it for validity
        String rawPid = request.body();
        int pid = UIUtils.parseIntInput(rawPid);
        if(pid == -2) {
            response.redirect("/ratePapers");
            halt();
            return null;
        }

        List<Review> rereviews = paperManager.getReviewsForPaper(rawPid);

        for(Review r : rereviews) { //Loop through each review for the paper & send a notification to that Review's PCM that they need to resubmit
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
