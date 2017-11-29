package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GetReviewPaperRoute implements TemplateViewRoute{
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /reviewPapers} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetReviewPaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        String username = request.session().attribute("username");
        int pid = Integer.parseInt(request.queryParams("pid"));


        vm.put("title", "Review Paper");
        vm.put("username", username);
        vm.put("paper", paperManager.getPaperbyID(pid));
        return new ModelAndView(vm , "reviewPaper.ftl");
    }
}
