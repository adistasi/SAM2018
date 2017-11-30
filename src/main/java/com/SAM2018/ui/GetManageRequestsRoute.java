package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class GetManageRequestsRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /submitPaper} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetManageRequestsRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        vm.put("userType", paperManager.getUserType(request.session().attribute("username")));

        vm.put("pcmUsers", paperManager.getAllPCMs());
        vm.put("papersRequested", paperManager.getRequestedReviews());
        vm.put("title", "Manage Paper Requests");
        return new ModelAndView(vm , "reviewManagement.ftl");
    }
}
