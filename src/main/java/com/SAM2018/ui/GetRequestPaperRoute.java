package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Deadline;
import spark.*;

import java.util.*;

import static spark.Spark.halt;

/**
 * The GetRequestPaperRoute for Requesting Available papers.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */

public class GetRequestPaperRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
    * The constructor for the {@code GET /requestPaper} route handler
    * @param _paperManager The {@link PaperManager} for the application.
    */
    GetRequestPaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String username = request.session().attribute("username");
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);


        if(!(userType.equals("PCM") || userType.equals("Admin"))) { //Redirect any non PCM or Admin users
            response.redirect("/managePapers");
            halt();
            return null;
        }

        //Get the deadline & put "closed" as true if the deadline has passed
        Deadline reqDead = paperManager.getDeadline("Request Deadline");
        vm.put("closed", (reqDead != null && reqDead.hasPassed()));

        //Put if the user has already made requests and a list of the papers for review in the VM
        vm.put("title", "Request a Paper");
        vm.put("hasRequested", paperManager.hasUserMadeRequest(username));
        vm.put("paperForReview", paperManager.getPapersForReview(username));
        return new ModelAndView(vm , "requestPaper.ftl");
    }
}