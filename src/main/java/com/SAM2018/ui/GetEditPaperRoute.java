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
        vm.put("title", "Edit Paper");

        String username = request.session().attribute("username");
        String paperID = request.queryParams("pid");
        if(username != null) {
            vm.put("username", username);

            Paper paper = paperManager.getPaperbyID(Integer.parseInt(paperID));

            if(!paper.getContactAuthor().getUsername().equals(username)) {
                //return error
            } else {
                vm.put("auth1", paper.getAuthors().get(0));
                vm.put("auth2", paper.getAuthors().get(1));
                vm.put("auth3", paper.getAuthors().get(2));

                vm.put("paper", paper);
            }
        } else {
            response.redirect("/login");
            halt();
            return null;
        }

        return new ModelAndView(vm , "editPaper.ftl");
    }
}