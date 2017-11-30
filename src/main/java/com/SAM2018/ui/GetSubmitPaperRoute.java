package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class GetSubmitPaperRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /reviewRating} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetSubmitPaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        vm.put("title", "Submit Paper");
        vm.put("userType", paperManager.getUserType(request.session().attribute("username")));

        return new ModelAndView(vm , "submitPaper.ftl");
    }
}
