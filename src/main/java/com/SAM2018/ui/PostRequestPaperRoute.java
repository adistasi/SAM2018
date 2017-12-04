package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.PCM;
import com.SAM2018.model.Paper;
import spark.*;

import java.util.*;

import static spark.Spark.halt;

/**
 * The Web Controller for the requestPaper POST.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostRequestPaperRoute implements TemplateViewRoute {
    //Constants
    public static final String NO_PAPER_REQUESTED = "You have not requested any papers.";

    //Attributes
    private final PaperManager paperManager;

    /**
    * The constructor for the {@code POST /submitPaper} route handler
    * @param _paperManager The {@link PaperManager} for the application.
    */
    PostRequestPaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        final Session session = request.session();
        String username = session.attribute("username");
        String userType = paperManager.getUserType(username);
        vm.put("userType", userType);

        if(!(userType.equals("PCM") || userType.equals("Admin"))) { //Redirect non PCM & Admin Users
            response.redirect("/managePapers");
            halt();
            return null;
        }

        PCM user = (PCM)paperManager.getUser(username);
        QueryParamsMap paperRequests = request.queryMap("requestedPaper");

        if(paperRequests.booleanValue()== null) { //Return error message if no papers requested
            return UIUtils.error(vm, NO_PAPER_REQUESTED, "reviewManagement.ftl");
        }

        for(String id : paperRequests.values()) { //Loop through each requested paper
            int paperID = UIUtils.parseIntInput(id);
            if(paperID == -2) { //parse for validity
                response.redirect("/managePapers");
                halt();
                return null;
            }

            Paper p = paperManager.getPaperbyID(paperID);
            if(p != null) { //Create a request for that paper if it doesn't already exist for that user
                user.requestReview(p);
                paperManager.addRequest(p, paperManager.getUser(username));
            }
        }

        response.redirect("/manageRequests");
        halt();
        return null;
    }
}