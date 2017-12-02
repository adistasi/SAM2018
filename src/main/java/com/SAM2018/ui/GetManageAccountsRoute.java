package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class GetManageAccountsRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /manageAccounts} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetManageAccountsRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        vm.put("title", "Account Management");
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);
        vm.put("notificationCount", paperManager.getUnreadNotificationCount(request.session().attribute("username")));

        if(!userType.equals("Admin")) {
            response.redirect("/");
            halt();
            return null;
        }

        vm.put("users", paperManager.getAllUsers(request.session().attribute("username")));
        vm.put("requestedPermissions", paperManager.getRequestedPermissions());

        return new ModelAndView(vm, "accountManagement.ftl");
    }
}
