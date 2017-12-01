package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Message;
import com.SAM2018.model.Notification;
import com.SAM2018.model.Report;
import com.SAM2018.model.Review;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

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
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);

        String nid = request.body();
        int notificationID = UIUtils.parseIntInput(nid);
        if(notificationID == -2) {
            response.redirect("/viewNotifications");
            halt();
            return null;
        }

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
