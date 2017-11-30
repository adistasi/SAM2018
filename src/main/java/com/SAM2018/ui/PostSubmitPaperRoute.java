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
        vm = UIUtils.validateLoggedIn(request, response, vm);
        Session session = request.session();

        vm.put("title", "Submit Paper");
        vm.put("username", session.attribute("username"));
        vm.put("userType", paperManager.getUserType(request.session().attribute("username")));

        String rawAuthors = request.queryParams("authors");
        List<String> authors = paperManager.validateAuthors(rawAuthors);

        if(authors.size() == 0) {
            return UIUtils.error(vm, "A paper must have an author", "submitPaper.ftl");
        } else if(rawAuthors.contains("|||")) {
            return UIUtils.error(vm, "An author may not contain the characters '|||'", "submitPaper.ftl");
        }

        String contactAuthor = session.attribute("username");
        String title = request.queryParams("title");
        String format = request.queryParams("format");
        String file = request.queryParams("paperFile");

        if (UIUtils.validateInputText(title) || UIUtils.validateInputText(format) || UIUtils.validateInputText(file)) {
            return UIUtils.error(vm, "Paper information cannot be blank or contain the characters '|||", "submitPaper.ftl");
        }

        paperManager.addPaper(authors, paperManager.getContactAuthorByUsername(contactAuthor), title, format, 1, file);
        return new ModelAndView(vm , "submitPaper.ftl");
    }
}
