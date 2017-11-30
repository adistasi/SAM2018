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
        vm = UIUtils.validateLoggedIn(request, response, vm);

        final Session session = request.session();
        PCC pcc = (PCC)paperManager.getUser(session.attribute("username"));

        QueryParamsMap approvedRequests = request.queryMap("requests");

        if(approvedRequests.booleanValue()== null) {
            //TODO: CREATE ACTUAL ERROR MESSAGE
            vm.put("title", "You have not approved any requests");
            return new ModelAndView(vm , "reviewManagement.ftl"); //return with no paper requested message
        }

        Map<String, List<String>> potentialReviews = new HashMap<>();

        for(String potentialReq : approvedRequests.values()) {
            String[] pcmAndPaper = potentialReq.split("\\|\\|\\|");
            String username = pcmAndPaper[0];
            String pid = pcmAndPaper[1];

            if(potentialReviews.get(pid) != null) {
                List<String> reviewerUsernames = potentialReviews.get(pid);
                reviewerUsernames.add(username);
                potentialReviews.put(pid, reviewerUsernames);
            } else {
                List<String> reviewerUsernames = new ArrayList<>();
                reviewerUsernames.add(username);
                potentialReviews.put(pid, reviewerUsernames);
            }
        }

        for(List<String> prs : potentialReviews.values()) {
            if(prs.size() != 3) {
                //TODO: CREATE ACTUAL ERROR MESSAGE
                vm.put("title", "Not all papers have 3 Assigned PCMs");
                return new ModelAndView(vm , "reviewManagement.ftl"); //return with no paper requested message
            } else {
                Set<String> prSet = new HashSet<String>(prs);
                if(prSet.size() != 3) {
                    //TODO: CREATE ACTUAL ERROR MESSAGE
                    vm.put("title", "You cannot assign the same PCM to a paper twice");
                    return new ModelAndView(vm, "reviewManagement.ftl");
                }
            }
        }

        for(String req : approvedRequests.values()) {
            String[] pcmAndPaper = req.split("\\|\\|\\|");
            PCM pcm = (PCM)paperManager.getUser(pcmAndPaper[0]);
            Paper p = paperManager.getPaperbyID(Integer.parseInt(pcmAndPaper[1]));


            Review review = pcc.assignReview(pcm, p);
            paperManager.addReview(Integer.toString(p.getPaperID()), review);
        }
        paperManager.saveReviews();
        paperManager.clearRequests();
        paperManager.savePapers();
        response.redirect("/manageRequests");
        halt();
        return null;
    }
}