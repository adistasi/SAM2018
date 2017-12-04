package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Notification;
import com.SAM2018.model.User;
import spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static spark.Spark.halt;

/**
 * The Web Controller for the Create Notification POST
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostCreateNotificationRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /createNotification} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostCreateNotificationRoute(final PaperManager _paperManager) {
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

        if(!(userType.equals("PCC") || userType.equals("Admin"))) { //Redirect non-PCC & Admin Users
            response.redirect("/");
            halt();
            return null;
        }

        vm.put("title", "Create Notification");
        vm.put("userType", userType);
        vm.put("notificationCount", paperManager.getUnreadNotificationCount(username));
        vm.put("users", paperManager.getAllUsers(username));

        //Get the form data and parse it
        String recipientString = request.queryParams("recipient");
        String message = request.queryParams("message");

        if(recipientString.length() == 0 || paperManager.getUser(recipientString) == null) {
            return UIUtils.error(vm, "A Notification must have a recipient", "createNotification.ftl");
        } else if(message.contains("|||")) {
            return UIUtils.error(vm, "An author may not contain the characters '|||'", "createNotification.ftl");
        }

        //Create the notification & refresh the page
        User generator = paperManager.getUser(username);
        User recipient = paperManager.getUser(recipientString);

        Notification notification = new Notification(paperManager.getNotificationsSize(), generator, recipient, message, false);
        paperManager.addNotification(notification);
        paperManager.saveNotifications();

        response.redirect("/createNotification");
        halt();
        return null;
    }
}
