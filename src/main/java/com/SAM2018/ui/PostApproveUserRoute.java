package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Admin;
import com.SAM2018.model.Message;
import com.SAM2018.model.Notification;
import com.SAM2018.model.User;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The Web Controller for the Approve User POST.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostApproveUserRoute implements Route {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /elevateUser} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostApproveUserRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public Object handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        vm.put("title", "Elevating User");
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);
        User user = paperManager.getUser(request.session().attribute("username"));

        if(!userType.equals("Admin")) { //Redirect any non-Admin users
            response.redirect("/manageAccounts");
            halt();
            return null;
        }

        Admin admin = (Admin)paperManager.getUser(request.session().attribute("username"));

        //Get the user ID, validate it, & assign the role to the user (then send them a notification)
        String uid = request.body().substring(1, request.body().length() - 1);
        User requestor = paperManager.getUser(uid);
        String type = paperManager.getRequestedPermissionforUser(uid);

        User newUser = admin.addRole(requestor, type);
        paperManager.updatePermissionsRequests(uid, newUser, true);

        User requester = paperManager.getUser(uid);
        Notification note = new Notification(paperManager.getNotificationsSize(), user, requester, "Your request for elevated status has been approved", false);
        paperManager.addNotification(note);
        paperManager.saveNotifications();

        return new Message("Account Permissions Elevated", "info");
    }
}
