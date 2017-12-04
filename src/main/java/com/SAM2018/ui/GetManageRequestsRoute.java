package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The Web Controller for the Manage Requests page.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetManageRequestsRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /manageRequests} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetManageRequestsRoute(final PaperManager _paperManager) {
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

        if(!(userType.equals("PCC") || userType.equals("Admin"))) { //Redirect any non-PCC or Admin users
            response.redirect("/managePapers");
            halt();
            return null;
        }

        //Put all PCM Users and all Papers requested in the ViewModel
        vm.put("pcmUsers", paperManager.getAllPCMs());
        vm.put("papersRequested", paperManager.getRequestedReviews());
        vm.put("title", "Manage Paper Requests");
        return new ModelAndView(vm , "reviewManagement.ftl");
    }
}
