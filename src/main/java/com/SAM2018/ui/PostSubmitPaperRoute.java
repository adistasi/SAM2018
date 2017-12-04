package com.SAM2018.ui;

import com.SAM2018.Application;
import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Notification;
import com.SAM2018.model.Paper;
import com.SAM2018.model.User;
import spark.*;
import spark.utils.IOUtils;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

        try { //Configure for multipart data
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/SubmittedPapers"));

            //Get Author data and format into a List
            Part rawAuthorsPart = request.raw().getPart("authors");
            String rawAuthors = IOUtils.toString(rawAuthorsPart.getInputStream());
            List<String> authors = UIUtils.validateAuthors(rawAuthors);

            //Get contact author from session
            String contactAuthorString = session.attribute("username");

            //Get Title
            Part titlePart = request.raw().getPart("title");
            String title = IOUtils.toString(titlePart.getInputStream());

            //Get file name & upload the file to the "SubmittedPapers directory"
            Part filePart = request.raw().getPart("paperFile");
            String fileName = UIUtils.getSubmittedFileName(filePart);
            try(InputStream in = filePart.getInputStream()) {
                OutputStream out = new FileOutputStream("" + Application.path  +"\\SubmittedPapers\\" + fileName);
                IOUtils.copy(in, out);
                out.close();
            }

            //Get format from extension on filename
            String[] rawFormat = fileName.split("\\.");
            String format = rawFormat[rawFormat.length-1];

            if(authors.size() == 0) { //Validate that they inputted authors
                return UIUtils.error(vm, "A paper must have an author", "submitPaper.ftl");
            } else if(rawAuthors.contains("|||") || rawAuthors.contains(",")) { //validate that no protected characters were used
                return UIUtils.error(vm, "An author may not contain the characters '|||' or ','", "submitPaper.ftl");
            }

            if (UIUtils.validateInputText(title)) { //validate text inputs
                return UIUtils.error(vm, "Paper information cannot be blank or contain the characters '|||", "submitPaper.ftl");
            }

            //Create the Paper, notify the PCC, and save everything
            User contactAuthor = paperManager.getContactAuthorByUsername(contactAuthorString);
            Paper paper = new Paper(paperManager.getPaperCount(), authors, contactAuthor, title, format, 1, Application.path + "/" + fileName);
            paperManager.addPaper(paper);
            contactAuthor.submitPaper(paper);

            String messageString = "A User (" + contactAuthor.getFullName() + ") has submitted a Paper entitled '" + paper.getTitle() + "'.";
            Notification notification = new Notification(paperManager.getNotificationsSize(), contactAuthor, paperManager.getPCC(), messageString, false, new Date());
            paperManager.addNotification(notification);
            paperManager.savePapers();
            paperManager.saveNotifications();
        } catch(Exception e) { //General error handling
            e.printStackTrace();
        }

        response.redirect("/managePapers");
        halt();
        return null;
    }
}
