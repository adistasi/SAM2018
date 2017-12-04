package com.SAM2018.ui;

import java.util.*;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Admin;
import spark.*;

import static spark.Spark.halt;

/**
 * The Web Controller for the Create Deadline POST
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostCreateDeadlineRoute implements TemplateViewRoute {
    private PaperManager paperManager;

    /**
     * The constructor for the {@code POST /createDeadline} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostCreateDeadlineRoute(final PaperManager _paperManager) {
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

        if(!userType.equals("Admin")) { //Redirect any non-admin users
            response.redirect("/manageDeadlines");
            halt();
            return null;
        }

        //Bring in each query Parameter from the form
        String title = request.queryParams("title");
        String date = request.queryParams("date");
        String time = request.queryParams("time");
        String dateTime = date + " " + time;

        Admin user = (Admin)paperManager.getUser(request.session().attribute("username"));
        user.setDeadline(dateTime, title, paperManager);

        response.redirect("/manageDeadlines");
        halt();
        return null;
    }
}