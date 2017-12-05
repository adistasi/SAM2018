package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The Web Controller for the Review Rating page.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
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
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        vm.put("userType", paperManager.getUserType(request.session().attribute("username")));

        Session session = request.session();
        String username = session.attribute("username");
        String pid = request.queryParams("pid");
        int paperID = UIUtils.parseIntInput(pid);

        //Validate that a valid PaperID came from the route and that the user has access to the paper (redirect if they don't)
        if(paperID == -2 || paperManager.getPaperbyID(paperID) == null || !paperManager.getPaperbyID(paperID).getContactAuthor().usernameMatches(username)) {
            response.redirect("/manageSubmissions");
            halt();
            return null;
        }

        //Put the report in the VM if they do
        vm.put("title", "Review Paper Report");
        vm.put("username", session.attribute("username"));
        vm.put("report", paperManager.getReportByID(paperID));

        return new ModelAndView(vm , "reviewReport.ftl");
    }
}