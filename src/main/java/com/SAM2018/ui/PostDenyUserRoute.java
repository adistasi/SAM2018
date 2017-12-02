package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Message;
import com.SAM2018.model.Notification;
import com.SAM2018.model.User;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class PostDenyUserRoute implements Route {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /denyUser} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostDenyUserRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));
        User user = paperManager.getUser(request.session().attribute("username"));

        if(!userType.equals("Admin")) {
            response.redirect("/manageAccounts");
            halt();
            return null;
        }

        String uid = request.body().substring(1, request.body().length() - 1);
        paperManager.assignRole(uid, false);

        User requester = paperManager.getUser(uid);
        Notification note = new Notification(paperManager.getNotificationsSize(), user, requester, "Your request for elevated status has been denied", false);
        paperManager.addNotification(note);
        paperManager.saveNotifications();

        return new Message("Permission Elevation Requested Denied", "info");
    }
}
