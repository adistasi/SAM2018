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
        vm.put("userType", paperManager.getUserType(session.attribute("username")));

        PCM user = (PCM)paperManager.getUser(session.attribute("username"));
        QueryParamsMap paperRequests = request.queryMap("requestedPaper");

        if(paperRequests.booleanValue()== null) {
            vm.put("title",NO_PAPER_REQUESTED);
            return new ModelAndView(vm , "reviewManagement.ftl"); //return with no paper requested message
        }

        for(String id : paperRequests.values()) {
            Paper p = paperManager.getPaperbyID(Integer.parseInt(id));
            user.requestReview(p);
            paperManager.addRequest(p, paperManager.getUser(session.attribute("username")));
        }

        response.redirect("/manageRequests");
        halt();
        return null;
    }
}