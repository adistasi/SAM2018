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
        vm = UIUtils.validateLoggedIn(request, response, vm);
        Session session = request.session();

        vm.put("title", "Submit Paper");
        vm.put("username", session.attribute("username"));
        vm.put("userType", paperManager.getUserType(request.session().attribute("username")));

        String pid = request.queryParams("pid");
        Paper paper = paperManager.getPaperbyID(Integer.parseInt(pid));

        String rawAuthors = request.queryParams("authors");
        List<String> authors = paperManager.validateAuthors(rawAuthors);

        if(authors.size() == 0) {
            vm.put("paper", paper);
            return UIUtils.error(vm, "A paper must have an author", "submitPaper.ftl");
        } else if(rawAuthors.contains("|||")) {
            vm.put("paper", paper);
            return UIUtils.error(vm, "An author may not contain the characters '|||'", "editPaper.ftl");
        }

        String title = request.queryParams("title");
        String format = request.queryParams("format");
        String file = request.queryParams("paperFile");

        if (UIUtils.validateInputText(title) || UIUtils.validateInputText(format) || UIUtils.validateInputText(file)) {
            vm.put("paper", paper);
            return UIUtils.error(vm, "Paper information cannot be blank or contain the characters '|||", "editPaper.ftl");
        }

        paper.updatePaper(authors, title, format, file);
        paperManager.savePapers();

        return new ModelAndView(vm , "editPaper.ftl");
    }
}
