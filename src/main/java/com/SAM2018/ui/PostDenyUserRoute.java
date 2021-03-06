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

/**
 * The Web Controller for the Deny User POST (AJAX Post)
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
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
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));
        User user = paperManager.getUser(request.session().attribute("username"));

        if(!userType.equals("Admin")) { //Redirect any non-Admin users
            response.redirect("/manageAccounts");
            halt();
            return null;
        }

        //Parse in the UID & validate it
        String uid = request.body().substring(1, request.body().length() - 1);
        paperManager.updatePermissionsRequests(uid, null, false);

        //Deny that user's permission request & send them a notification
        User requester = paperManager.getUser(uid);
        Notification note = new Notification(paperManager.getNotificationsSize(), user, requester, "Your request for elevated status has been denied", false);
        paperManager.addNotification(note);
        paperManager.saveNotifications();

        return new Message("Permission Elevation Requested Denied", "info");
    }
}
