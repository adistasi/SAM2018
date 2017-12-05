package com.SAM2018.ui;

import java.util.*;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Paper;
import com.SAM2018.model.Report;
import com.SAM2018.model.SubmissionReportDisplay;
import com.SAM2018.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import static spark.Spark.halt;

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
        vm.put("username", username);

        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);

        User user = paperManager.getUser(username);

        //Return a SubmissionReportDisplay (containing info on a submission and it's report) to the view model for each paper the user has submitted
        List<SubmissionReportDisplay> srd = new ArrayList<>();

        if(userType.equals("PCC") || userType.equals("Admin")) {
            for(User u : paperManager.getAllUsers()) {
                for(Paper p : u.getSubmissions()) {
                    Report r = paperManager.getReportByID(p.getPaperID());
                    srd.add(new SubmissionReportDisplay(p, r));
                }
            }
            vm.put("isAdminOrPCC", true);
        } else {
            for(Paper p : user.getSubmissions()) {
                Report r = paperManager.getReportByID(p.getPaperID());
                srd.add(new SubmissionReportDisplay(p, r));
            }
            vm.put("isAdminOrPCC", false);
        }

        vm.put("papers", srd);
        return new ModelAndView(vm , "manageSubmissions.ftl");
    }
}