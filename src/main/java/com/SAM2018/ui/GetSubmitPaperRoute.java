package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Deadline;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The Web Controller for the Submit Paper page.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetSubmitPaperRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /submitPaper} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetSubmitPaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        vm.put("title", "Submit Paper");
        vm.put("userType", paperManager.getUserType(request.session().attribute("username")));

        //If the submission deadline has passed, set "closed" as true in the VM
        Deadline subDead = paperManager.getDeadline("Submission Deadline");
        vm.put("closed", (subDead != null && subDead.hasPassed()));

        return new ModelAndView(vm , "submitPaper.ftl");
    }
}
