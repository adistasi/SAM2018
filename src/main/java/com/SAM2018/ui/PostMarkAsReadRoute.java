package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Message;
import com.SAM2018.model.Notification;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The Web Controller for the Mark as Read POST.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostMarkAsReadRoute implements Route {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /markAsRead} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostMarkAsReadRoute(final PaperManager _paperManager) {
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

        String nid = request.body();
        int notificationID = UIUtils.parseIntInput(nid);
        if(notificationID == -2) { //Parse for invalid notification ID from route
            response.redirect("/viewNotifications");
            halt();
            return null;
        }

        //Get the notification and mark it as read if it exists
        Notification notification = paperManager.getNotificationByID(notificationID);
        if(notification != null) {
            notification.markAsRead();
            paperManager.saveNotifications();
            return new Message("Notification Marked as Read", "info");
        } else {
            return new Message("Invalid Notification", "error");
        }
    }
}
