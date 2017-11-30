package com.SAM2018.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Paper;
import com.SAM2018.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import static spark.Spark.halt;

/**
 * The Web Controller for the Paper Editing page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetEditPaperRoute implements TemplateViewRoute {
    private PaperManager paperManager;

    /**
     * The constructor for the {@code GET /editPaper} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetEditPaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        vm = UIUtils.validateLoggedIn(request, response, vm);
        vm.put("title", "Edit Paper");
        vm.put("userType", paperManager.getUserType(request.session().attribute("username")));

        String username = request.session().attribute("username");
        String paperIDString = request.queryParams("pid");
        vm.put("username", username);

        int paperID = UIUtils.parseIntInput(paperIDString);
        if(paperID == -2) {
            response.redirect("/managePapers");
            halt();
            return null;
        }

        Paper paper = paperManager.getPaperbyID(paperID);

        if(paper == null || !paper.getContactAuthor().getUsername().equals(username)) {
            response.redirect("/manageSubmissions");
            halt();
            return null;
        } else {
            vm.put("paper", paper);
        }

        return new ModelAndView(vm , "editPaper.ftl");
    }
}