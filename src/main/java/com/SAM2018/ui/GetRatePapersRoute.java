package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The Web Controller for the Rate Papers page.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetRatePapersRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /ratePapers} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetRatePapersRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);

        if(!(userType.equals("PCC") || userType.equals("Admin"))) { //If the user is a non-PCC or Admin, redirect them
            response.redirect("/managePapers");
            halt();
            return null;
        }

        //Put the ratable papers & generated Reports in the VM & return the page
        Session session = request.session();
        vm.put("title", "Manage Paper Ratings");
        vm.put("username", session.attribute("username"));
        vm.put("ratablePapers", paperManager.getRatablePapers());
        vm.put("generatedReports", paperManager.getReports());

        return new ModelAndView(vm , "manageRatings.ftl");
    }
}
