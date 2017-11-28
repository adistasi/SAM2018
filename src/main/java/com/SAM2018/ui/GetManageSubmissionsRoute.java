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
 * The Web Controller for the Submitted Paper Management page.
 *
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
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Manage Submissions");

        String username = request.session().attribute("username");

        if(username != null) {

            vm.put("username", username);

            User user = paperManager.getUser(username);
            vm.put("papers", user.getSubmissions());

        } else {
            response.redirect("/login");
            halt();
            return null;
        }

        return new ModelAndView(vm , "manageSubmissions.ftl");
    }
}