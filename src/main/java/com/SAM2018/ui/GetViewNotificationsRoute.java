package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The Web Controller for the View Notifications page.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetViewNotificationsRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /viewNotifications} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetViewNotificationsRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String username = request.session().attribute("username");
        String userType = paperManager.getUserType(username);

        //Put the notifications count, unread notifications, and read notifications in the VM
        vm.put("title", "View Notifications");
        vm.put("userType", userType);
        vm.put("notificationCount", paperManager.getUnreadNotificationCount(username));
        vm.put("notifications", paperManager.getUnreadNotificationsForUser(username));
        vm.put("readNotifications", paperManager.getReadNotificationsForUser(username));
        return new ModelAndView(vm , "viewNotifications.ftl");
    }
}
