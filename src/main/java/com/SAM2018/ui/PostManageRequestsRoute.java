package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.*;
import spark.*;

import java.util.*;

import static spark.Spark.halt;

/**
 * The PostRequestPaperRoute for displaying requested Papers.
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
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);
        vm.put("title", "Manage Requests");


        if(!(userType.equals("PCC") || userType.equals("Admin"))) { //Redirect any non-PCC or Admin users
            response.redirect("/managePapers");
            halt();
            return null;
        }

        final Session session = request.session();
        PCC pcc = (PCC)paperManager.getUser(session.attribute("username"));

        QueryParamsMap approvedRequests = request.queryMap("requests");

        if(approvedRequests.booleanValue()== null) { //Catch for empty form
            vm.put("pcmUsers", paperManager.getAllPCMs());
            vm.put("papersRequested", paperManager.getRequestedReviews());
            return UIUtils.error(vm, "You have not approved any requests", "reviewManagement.ftl");
        }


        Map<String, List<String>> potentialReviews = new HashMap<>();
        for(String potentialReq : approvedRequests.values()) { //Loop through each submitted request (splitting on |||)
            String[] pcmAndPaper = potentialReq.split("\\|\\|\\|"); //Turns into username and the requested Paper ID
            String username = pcmAndPaper[0];
            String pid = pcmAndPaper[1];

            if(potentialReviews.get(pid) != null) { //If there are requests for that paper, add the PCM to the list
                List<String> reviewerUsernames = potentialReviews.get(pid);
                reviewerUsernames.add(username);
                potentialReviews.put(pid, reviewerUsernames);
            } else { //If there are no requests, create a list with the PCM in it
                List<String> reviewerUsernames = new ArrayList<>();
                reviewerUsernames.add(username);
                potentialReviews.put(pid, reviewerUsernames);
            }
        }

        for(List<String> prs : potentialReviews.values()) { //Loop trhough each set of requests
            if(prs.size() != 3) { //If not every paper has been assigned 3 PCM Reviewers, return an error message
                vm.put("pcmUsers", paperManager.getAllPCMs());
                vm.put("papersRequested", paperManager.getRequestedReviews());
                return UIUtils.error(vm, "Not all papers have 3 Assigned PCMs", "reviewManagement.ftl");
            } else {
                Set<String> prSet = new HashSet<String>(prs);
                if(prSet.size() != 3) { //Check if a PCM has been assigned to a paper twice
                    vm.put("pcmUsers", paperManager.getAllPCMs());
                    vm.put("papersRequested", paperManager.getRequestedReviews());
                    return UIUtils.error(vm, "You cannot assign a PCM to the same paper twice", "reviewManagement.ftl");
                }
            }
        }

        for(String req : approvedRequests.values()) { //If all validation is successful, create the reviews for each
            String[] pcmAndPaper = req.split("\\|\\|\\|");
            PCM pcm = (PCM)paperManager.getUser(pcmAndPaper[0]);
            Paper p = paperManager.getPaperbyID(Integer.parseInt(pcmAndPaper[1]));


            Review review = pcc.assignReview(pcm, p);
            paperManager.addReview(Integer.toString(p.getPaperID()), review);

            String messageString = "You have been assigned '" + p.getTitle() + "' for Review";
            Notification notification = new Notification(paperManager.getNotificationsSize(), null, review.getReviewer(), messageString, false, new Date());
            paperManager.addNotification(notification);
        }

        //Save Reviews, Papers, & Notifications, & clear requests
        paperManager.saveReviews();
        paperManager.clearRequests();
        paperManager.savePapers();
        paperManager.saveNotifications();

        response.redirect("/manageRequests");
        halt();
        return null;
    }
}