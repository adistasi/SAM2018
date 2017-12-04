package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Notification;
import com.SAM2018.model.PCM;
import com.SAM2018.model.Review;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The Web Controller for the reviewPaper POST.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
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
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);

        if(!(userType.equals("PCM") || userType.equals("Admin"))) { //Redirect non-PCM & Admin users
            response.redirect("/managePapers");
            halt();
            return null;
        }

        //Get the form info
        String username = request.session().attribute("username");
        int pid = UIUtils.parseIntInput(request.queryParams("pid"));
        double score = UIUtils.parseDoubleInput(request.queryParams("score"));
        String comments = request.queryParams("comment");

        if(pid == -2 || score == -2.0) { //redirect if invalid numbers were submitted in form
            response.redirect("/reviewPapers");
            halt();
            return null;
        }

        if(UIUtils.validateInputText(comments)) { //Validate for text
            vm.put("title", "Review Paper");
            vm.put("username", username);
            vm.put("paper", paperManager.getPaperbyID(pid));
            return UIUtils.error(vm, "Review information cannot be blank or contain the characters '|||'", "reviewPaper.ftl");
        }

        Review review = paperManager.getReview(pid, username);

        if(review != null) { //Validate that the review they are posting for exists
            PCM user = (PCM)paperManager.getUser(username);
            review = user.reviewPaper(review, score, comments);
            paperManager.saveReviews();

            int remainingReviews = paperManager.getReviewsLeftForPaper(Integer.toString(pid));
            if(remainingReviews == 0) { //If all reviews are completed, notify the PCC
                String messageString = "All PCMs have submitted their Review for '" + review.getSubject().getTitle() + "'.";
                Notification notification = new Notification(paperManager.getNotificationsSize(), null, paperManager.getPCC(), messageString, false, new Date());
                paperManager.addNotification(notification);
                paperManager.saveNotifications();
            }
        }

        response.redirect("/reviewPapers");
        halt();
        return null;
    }
}
