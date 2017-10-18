package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import spark.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PostSubmitPaperRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /submitPaper} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostSubmitPaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        Session session = request.session();
        vm.put("title", "Submit Paper");

        //TODO: VALIDATION
        String auth1 = request.queryParams("author1");
        String auth2 = request.queryParams("author2");
        String auth3 = request.queryParams("author3");
        List<String> authors = paperManager.getAllAuthors(auth1, auth2, auth3);

        String title = request.queryParams("title");
        String format = request.queryParams("format");
        String file = request.queryParams("paperFile");

        paperManager.addPaper(authors, paperManager.getContactAuthorByUsername(auth1), title, format, 1, file);
        paperManager.printPapersData();
        paperManager.savePapers();
        return new ModelAndView(vm , "submitPaper.ftl");
    }
}
