package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Notification;
import com.SAM2018.model.Paper;
import spark.*;
import java.util.*;

import static spark.Spark.halt;

/**
 * The Web Controller for the Edit Paper POST.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostEditPaperRoute implements TemplateViewRoute {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /editPaper} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostEditPaperRoute(final PaperManager _paperManager) {
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

        //Read in the PaperID from the Route & Validate it
        String pid = request.queryParams("pid");
        int paperID = UIUtils.parseIntInput(pid);
        if(paperID == -2) { //-2 means it wasn't a valid int
            response.redirect("/manageSubmissions");
            halt();
            return null;
        }

        //Get the paper & redirect the user if they aren't the contact Author for the paper
        Paper paper = paperManager.getPaperbyID(paperID);
        if(!paper.getContactAuthor().getUsername().equals(session.attribute("username"))) {
            response.redirect("/manageSubmissions");
            halt();
            return null;
        }

        //Get all the information from the form & update the paper
        String rawAuthors = request.queryParams("authors");
        List<String> authors = UIUtils.validateAuthors(rawAuthors);

        if(authors.size() == 0) { //Validation that they must put an author
            vm.put("paper", paper);
            return UIUtils.error(vm, "A paper must have an author", "submitPaper.ftl");
        } else if(rawAuthors.contains("|||") || rawAuthors.contains(",")) { //Protected character validation (these are used as delimiters)
            vm.put("paper", paper);
            return UIUtils.error(vm, "An author may not contain the characters '|||' or ','", "editPaper.ftl");
        }

        String title = request.queryParams("title");
        String format = request.queryParams("format");
        String file = request.queryParams("paperFile");

        if (UIUtils.validateInputText(title) || UIUtils.validateInputText(format) || UIUtils.validateInputText(file)) { //Text validate for text inputs
            vm.put("paper", paper);
            return UIUtils.error(vm, "Paper information cannot be blank or contain the characters '|||", "editPaper.ftl");
        }

        //Update the paper, send a notification, and save everything
        paper.updatePaper(authors, title, format, file);

        String messageString = "A User (" + paper.getContactAuthor().getFullName() + ") has edited their paper entitled '" + paper.getTitle() + "'.";
        Notification notification = new Notification(paperManager.getNotificationsSize(), paper.getContactAuthor(), paperManager.getPCC(), messageString, false, new Date());
        paperManager.addNotification(notification);
        paperManager.savePapers();
        paperManager.saveNotifications();

        response.redirect("/managePapers");
        halt();
        return null;
    }
}
