package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Paper;
import spark.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PostEditPaperRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /submitPaper} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostEditPaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        Session session = request.session();
        vm.put("title", "Submit Paper");
        vm.put("username", session.attribute("username"));

        String pid = request.queryParams("pid");

        Paper paper = paperManager.getPaperbyID(Integer.parseInt(pid));

        String rawAuthors = request.queryParams("authors");
        List<String> authors = paperManager.validateAuthors(rawAuthors);

        if(authors.size() == 0) {
            return error(vm, "A paper must have an author");
        } else if(rawAuthors.contains("|||")) {
            return error(vm, "An author may not contain the characters '|||'");
        }

        String contactAuthor = request.session().attribute("username");
        String title = request.queryParams("title");
        String format = request.queryParams("format");
        String file = request.queryParams("paperFile");

        if (title.contains("|||") || format.contains("|||") || file.contains("|||")) {
            return error(vm, "Paper information may not contain the characters '|||");
        }

        if(title.equals("") || format.equals("") || file.equals("")) {
            return error(vm, "Paper information cannot be blank");
        }

        paper.updatePaper(authors, title, format, file);
        paperManager.savePapers();

        return new ModelAndView(vm , "submitPaper.ftl");
    }

    private ModelAndView error(final Map<String, Object> vm, final String message) {
        vm.put("message", message);
        vm.put("messageType", "error");
        return new ModelAndView(vm, "submitPaper.ftl");
    }
}
