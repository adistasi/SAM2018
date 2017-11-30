package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GetReviewRatingRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /reviewRating} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetReviewRatingRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        vm.put("userType", paperManager.getUserType(request.session().attribute("username")));

        Session session = request.session();
        String paperID = request.queryParams("pid");


        vm.put("title", "Review Paper Report");
        vm.put("username", session.attribute("username"));
        vm.put("report", paperManager.getReportByID(Integer.parseInt(paperID)));

        return new ModelAndView(vm , "reviewReport.ftl");
    }
}