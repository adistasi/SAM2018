package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.PCM;
import com.SAM2018.model.Paper;
import spark.*;

import java.util.*;

import static spark.Spark.halt;

/**
 * The PostRequestPaperRoute for displaying requested Papers.
 *
 * @author <a href='mailto:rp3737@rit.edu'>Raseshwari Pulle</a>
 */
public class PostRequestPaperRoute implements TemplateViewRoute {

    public static final String NO_PAPER_REQUESTED = "You have not requested any papers."; //message when no paper is requested

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
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);

        final Session session = request.session();
        String username = session.attribute("username");
        String userType = paperManager.getUserType(username);
        vm.put("userType", userType);

        if(!(userType.equals("PCM") || userType.equals("Admin"))) {
            response.redirect("/managePapers");
            halt();
            return null;
        }

        PCM user = (PCM)paperManager.getUser(username);
        QueryParamsMap paperRequests = request.queryMap("requestedPaper");

        if(paperRequests.booleanValue()== null) {
            vm.put("title",NO_PAPER_REQUESTED);
            return new ModelAndView(vm , "reviewManagement.ftl"); //return with no paper requested message
        }

        for(String id : paperRequests.values()) {
            int paperID = UIUtils.parseIntInput(id);
            if(paperID == -2) {
                response.redirect("/managePapers");
                halt();
                return null;
            }

            Paper p = paperManager.getPaperbyID(paperID);
            if(p != null) {
                user.requestReview(p);
                paperManager.addRequest(p, paperManager.getUser(username));
            }
        }

        response.redirect("/manageRequests");
        halt();
        return null;
    }
}