package com.SAM2018.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
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

        try {
            if(submissionDeadline != null) { //If the review deadline exists, format date and time strings and add them to the ViewModel
                String dateStr = formatDateString(submissionDeadline.getDate());
                String timeStr = formatTimeString(submissionDeadline.getDate());

                vm.put("submissionDeadlineDate", dateStr);
                vm.put("submissionDeadlineTime", timeStr);
            }

            if(requestDeadline != null) { //If the request deadline exists, format date and time strings and add them to the ViewModel
                String dateStr = formatDateString(requestDeadline.getDate());
                String timeStr = formatTimeString(requestDeadline.getDate());

                vm.put("requestDeadlineDate", dateStr);
                vm.put("requestDeadlineTime", timeStr);
            }

            if(reviewDeadline != null) { //If the review deadline exists, format date and time strings and add them to the ViewModel
                String dateStr = formatDateString(reviewDeadline.getDate());
                String timeStr = formatTimeString(reviewDeadline.getDate());

                vm.put("reviewDeadlineDate", dateStr);
                vm.put("reviewDeadlineTime", timeStr);
            }

            if(ratingDeadline != null) { //If the rating deadline exists, format date and time strings and add them to the ViewModel
                String dateStr = formatDateString(ratingDeadline.getDate());
                String timeStr = formatTimeString(ratingDeadline.getDate());

                vm.put("ratingDeadlineDate", dateStr);
                vm.put("ratingDeadlineTime", timeStr);
            }

            return new ModelAndView(vm , "manageDeadlines.ftl");
        }catch(Exception e) {
            response.redirect("/");
            halt();
            return null;
        }
    }

    /**
     * Helper method to utilize SimpleDateFormat to return a Freemarker-usable string for the Date
     * @param _date The date of the deadline (we're concerned with the actual data)
     * @return The formatted string ("yyy-MM-dd")
     */
    private String formatDateString(Date _date) {
        SimpleDateFormat date = new SimpleDateFormat("yyy-MM-dd");
        return date.format(_date);
    }

    /**
     * Helper method to utilize SimpleDateFormat to return a Freemarker-usable string for the Time
     * @param _time The date of the deadline (we're concerned with the time)
     * @return The formatted time("HH:mm")
     */
    private String formatTimeString(Date _time) {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        return time.format(_time);
    }
}