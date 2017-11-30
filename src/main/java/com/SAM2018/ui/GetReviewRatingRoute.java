package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

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
        String username = session.attribute("username");
        String pid = request.queryParams("pid");
        int paperID = UIUtils.parseIntInput(pid);

        if(paperID == -2 || paperManager.getPaperbyID(paperID) == null || !paperManager.getPaperbyID(paperID).getContactAuthor().getUsername().equals(username)) {
            response.redirect("/manageSubmissions");
            halt();
            return null;
        }

        vm.put("title", "Review Paper Report");
        vm.put("username", session.attribute("username"));
        vm.put("report", paperManager.getReportByID(paperID));

        return new ModelAndView(vm , "reviewReport.ftl");
    }
}