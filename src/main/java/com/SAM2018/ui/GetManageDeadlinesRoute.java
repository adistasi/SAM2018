package com.SAM2018.ui;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Deadline;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import static spark.Spark.halt;

/**
 * The Web Controller for the Paper Management page.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetManageDeadlinesRoute implements TemplateViewRoute {
    private PaperManager paperManager;

    /**
     * The constructor for the {@code GET /manageDeadlines} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetManageDeadlinesRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        vm.put("title", "Deadline Management");
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);
        vm.put("notificationCount", paperManager.getUnreadNotificationCount(request.session().attribute("username")));

        if(!userType.equals("Admin")) { //Redirect any non admin users
            response.redirect("/manageDeadlines");
            halt();
            return null;
        }

        //Get each deadline
        Deadline submissionDeadline = paperManager.getDeadline("Submission Deadline");
        Deadline requestDeadline = paperManager.getDeadline("Request Deadline");
        Deadline reviewDeadline = paperManager.getDeadline("Review Deadline");
        Deadline ratingDeadline = paperManager.getDeadline("Rating Deadline");

        if(submissionDeadline != null) { //If the review deadline exists, format date and time strings and add them to the ViewModel
            try {
                SimpleDateFormat date = new SimpleDateFormat("yyy-MM-dd");
                String dateStr = date.format(submissionDeadline.getDate());

                SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                String timeStr = time.format(submissionDeadline.getDate());

                vm.put("submissionDeadlineDate", dateStr);
                vm.put("submissionDeadlineTime", timeStr);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        if(requestDeadline != null) { //If the request deadline exists, format date and time strings and add them to the ViewModel
            try {
                SimpleDateFormat date = new SimpleDateFormat("yyy-MM-dd");
                String dateStr = date.format(requestDeadline.getDate());

                SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                String timeStr = time.format(requestDeadline.getDate());

                vm.put("requestDeadlineDate", dateStr);
                vm.put("requestDeadlineTime", timeStr);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        if(reviewDeadline != null) { //If the review deadline exists, format date and time strings and add them to the ViewModel
            try {
                SimpleDateFormat date = new SimpleDateFormat("yyy-MM-dd");
                String dateStr = date.format(reviewDeadline.getDate());

                SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                String timeStr = time.format(reviewDeadline.getDate());

                vm.put("reviewDeadlineDate", dateStr);
                vm.put("reviewDeadlineTime", timeStr);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        if(ratingDeadline != null) { //If the rating deadline exists, format date and time strings and add them to the ViewModel
            try {
                SimpleDateFormat date = new SimpleDateFormat("yyy-MM-dd");
                String dateStr = date.format(ratingDeadline.getDate());

                SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                String timeStr = time.format(ratingDeadline.getDate());

                vm.put("ratingDeadlineDate", dateStr);
                vm.put("ratingDeadlineTime", timeStr);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        return new ModelAndView(vm , "manageDeadlines.ftl");
    }
}