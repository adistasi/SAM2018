package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Notification;
import com.SAM2018.model.Paper;
import com.SAM2018.model.User;
import spark.*;
import java.util.*;

import static spark.Spark.halt;

/**
 * The Web Controller for the Submit Paper POST.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
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
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        Session session = request.session();
        vm.put("title", "Submit Paper");
        vm.put("username", session.attribute("username"));
        vm.put("userType", paperManager.getUserType(request.session().attribute("username")));

        //Get the information from the form
        String rawAuthors = request.queryParams("authors");
        List<String> authors = UIUtils.validateAuthors(rawAuthors);

        if(authors.size() == 0) { //Validate that they inputted authors
            return UIUtils.error(vm, "A paper must have an author", "submitPaper.ftl");
        } else if(rawAuthors.contains("|||") || rawAuthors.contains(",")) { //validate that no protected characters were used
            return UIUtils.error(vm, "An author may not contain the characters '|||' or ','", "submitPaper.ftl");
        }

        String contactAuthorString = session.attribute("username");
        String title = request.queryParams("title");
        String format = request.queryParams("format");
        String file = request.queryParams("paperFile");

        if (UIUtils.validateInputText(title) || UIUtils.validateInputText(format) || UIUtils.validateInputText(file)) { //validate text inputs
            return UIUtils.error(vm, "Paper information cannot be blank or contain the characters '|||", "submitPaper.ftl");
        }

        //Create the Paper, notify the PCC, and save everything
        User contactAuthor = paperManager.getContactAuthorByUsername(contactAuthorString);
        Paper paper = new Paper(paperManager.getPaperCount(), authors, contactAuthor, title, format, 1, file);
        paperManager.addPaper(paper);
        contactAuthor.submitPaper(paper);

        String messageString = "A User (" + contactAuthor.getFullName() + ") has submitted a Paper entitled '" + paper.getTitle() + "'.";
        Notification notification = new Notification(paperManager.getNotificationsSize(), contactAuthor, paperManager.getPCC(), messageString, false, new Date());
        paperManager.addNotification(notification);
        paperManager.savePapers();
        paperManager.saveNotifications();

        response.redirect("/managePapers");
        halt();
        return null;
    }
}
