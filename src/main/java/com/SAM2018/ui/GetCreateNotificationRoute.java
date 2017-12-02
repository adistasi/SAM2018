package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class GetCreateNotificationRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /createNotification} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetCreateNotificationRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        String asdf = response.body();


        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String username = request.session().attribute("username");
        String userType = paperManager.getUserType(username);

        if(!(userType.equals("PCC") || userType.equals("Admin"))) {
            response.redirect("/");
            halt();
            return null;
        }

        vm.put("title", "Create Notification");
        vm.put("userType", userType);
        vm.put("notificationCount", paperManager.getUnreadNotificationCount(username));
        vm.put("users", paperManager.getAllUsers(username));
        return new ModelAndView(vm , "createNotification.ftl");
    }
}
