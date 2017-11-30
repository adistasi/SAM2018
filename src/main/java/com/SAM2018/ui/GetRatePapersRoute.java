package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GetRatePapersRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code GET /ratePapers} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    GetRatePapersRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);

        Session session = request.session();
        vm.put("title", "Submit Paper");
        vm.put("username", session.attribute("username"));
        vm.put("ratablePapers", paperManager.getRatablePapers());

        return new ModelAndView(vm , "manageRatings.ftl");
    }
}
