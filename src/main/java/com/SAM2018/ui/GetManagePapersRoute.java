package com.SAM2018.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.SAM2018.appl.PaperManager;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import static spark.Spark.halt;

/**
 * The Web Controller for the Paper Management page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetManagePapersRoute implements TemplateViewRoute {
    private PaperManager paperManager;

    /**
     * The constructor for the {@code GET /managePaper} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetManagePapersRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Paper Management");

        if(request.session().attribute("username") != null) {
            vm.put("username", request.session().attribute("username"));
        } else {
            response.redirect("/login");
            halt();
            return null;
        }

        return new ModelAndView(vm , "managePapers.ftl");
    }
}