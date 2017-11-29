package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.PCC;
import com.SAM2018.model.PCM;
import com.SAM2018.model.Paper;
import com.SAM2018.model.Review;
import spark.*;

import java.util.*;

import static spark.Spark.halt;

/**
 * The PostRequestPaperRoute for displaying requested Papers.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostManageRequestsRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /manageRequests} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostManageRequestsRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        final Session session = request.session();
        PCC pcc = (PCC)paperManager.getUser(session.attribute("username"));

        QueryParamsMap approvedRequests = request.queryMap("requests");

        if(approvedRequests.booleanValue()== null) {
            vm.put("title", "You have not approved any requests");
            return new ModelAndView(vm , "reviewManagement.ftl"); //return with no paper requested message
        }

        for(String req : approvedRequests.values()) {
            String[] pcmAndPaper = req.split("\\|\\|\\|");
            PCM pcm = (PCM)paperManager.getUser(pcmAndPaper[0]);
            Paper p = paperManager.getPaperbyID(Integer.parseInt(pcmAndPaper[1]));

            Review review = pcc.assignReview(pcm, p);
            paperManager.addReview(Integer.toString(p.getPaperID()), review);
        }
        //TODO: CLEAR OUT PENDING REVIEWS?
        paperManager.saveReviews();

        response.redirect("/manageRequests");
        halt();
        return null;
    }
}