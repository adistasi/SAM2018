package com.SAM2018.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.SAM2018.appl.PaperManager;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * The Web Controller for the Home page.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetHomeRoute implements TemplateViewRoute {
    private PaperManager paperManager;

    /**
     * The constructor for the {@code GET /editPaper} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetHomeRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }
    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String username = request.session().attribute("username");

        //Put the standard information for the page
        vm.put("title", "Welcome!");
        vm.put("userType", paperManager.getUserType(username));
        vm.put("notificationCount", paperManager.getUnreadNotificationCount(username));
        return new ModelAndView(vm , "home.ftl");
    }
}