package com.SAM2018.ui;

import java.util.*;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Paper;
import com.SAM2018.model.SubmissionReportDisplay;
import com.SAM2018.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * The Web Controller for the Submitted Paper Management page
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetManageSubmissionsRoute implements TemplateViewRoute {
    private PaperManager paperManager;

    /**
     * The constructor for the {@code GET /manageSubmissions} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetManageSubmissionsRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        vm.put("title", "Manage Submissions");

        String username = request.session().attribute("username");
        String userType = paperManager.getUserType(username);
        vm.put("userType", userType);
        vm.put("username", username);

        User user = paperManager.getUser(username);

        //Return a SubmissionReportDisplay (containing info on a submission and it's report) to the view model for each paper the user has submitted
        List<SubmissionReportDisplay> srd = new ArrayList<>();

        if(userType.equals("PCC") || userType.equals("Admin")) { //PCC & Admin can see all papers
            for(User u : paperManager.getAllUsers()) { //loop through every user & get all their submissions
                for(Paper p : u.getSubmissions()) {
                    srd.add(new SubmissionReportDisplay(p, paperManager.getReportByID(p.getPaperID())));
                }
            }
            vm.put("isAdminOrPCC", true);
        } else { //Other users can only see their submissions
            for(Paper p : user.getSubmissions()) { //loop through the user's submissino
                srd.add(new SubmissionReportDisplay(p, paperManager.getReportByID(p.getPaperID())));
            }
            vm.put("isAdminOrPCC", false);
        }

        vm.put("papers", srd);
        return new ModelAndView(vm , "manageSubmissions.ftl");
    }
}