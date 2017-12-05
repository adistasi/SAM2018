package com.SAM2018.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Paper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import static spark.Spark.halt;

/**
 * The Web Controller for the Paper Editing page.
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
        //Validate if users are logged in and then put page information
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String username = request.session().attribute("username");
        vm.put("title", "Edit Paper");
        vm.put("userType", paperManager.getUserType(username));
        vm.put("username", username);
        String paperIDString = request.queryParams("pid");

        int paperID = UIUtils.parseIntInput(paperIDString);
        if(paperID == -2) { //route input validation (-2 means a non-integer was entered)
            response.redirect("/managePapers");
            halt();
            return null;
        }

        //Get the paper and return add it to the page
        Paper paper = paperManager.getPaperbyID(paperID);

        if(paper == null || !paper.getContactAuthor().usernameMatches(username)) { //If there is no paper or an invalid paper, redirect the user
            response.redirect("/manageSubmissions");
            halt();
            return null;
        } else {
            vm.put("paper", paper);
            return new ModelAndView(vm , "editPaper.ftl");
        }
    }
}