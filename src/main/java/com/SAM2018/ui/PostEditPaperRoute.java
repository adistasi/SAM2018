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

        try { //Configure for multipart form data
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/src/main/resources/public/SubmittedPapers"));

            //Read in the PaperID from the Route & Validate it
            Part pidPart = request.raw().getPart("pid");
            String pid = IOUtils.toString(pidPart.getInputStream());
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

            //Pull in Authors and format it as a list
            Part rawAuthorsPart = request.raw().getPart("authors");
            String rawAuthors = IOUtils.toString(rawAuthorsPart.getInputStream());
            List<String> authors = UIUtils.validateAuthors(rawAuthors);

            //Get Paper Title
            Part titlePart = request.raw().getPart("title");
            String title = IOUtils.toString(titlePart.getInputStream());

            //Get Uploaded File Name (will be empty string if they don't upload a file)
            Part filePart = request.raw().getPart("paperFile");
            String fileName = UIUtils.getSubmittedFileName(filePart);

            if(authors.size() == 0) { //Validate that they inputted authors
                return UIUtils.error(vm, "A paper must have an author", "submitPaper.ftl");
            } else if(rawAuthors.contains("|||") || rawAuthors.contains(",")) { //validate that no protected characters were used
                return UIUtils.error(vm, "An author may not contain the characters '|||' or ','", "submitPaper.ftl");
            }

            if (UIUtils.validateInputText(title)) { //validate text inputs
                return UIUtils.error(vm, "Paper information cannot be blank or contain the characters '|||", "submitPaper.ftl");
            }

            if(fileName.length() > 0) { //Only update the format and paper name if they've uploaded a new paper
                try(InputStream in = filePart.getInputStream()) {
                    OutputStream out = new FileOutputStream("" + Application.path  +"\\src\\main\\resources\\public\\SubmittedPapers\\" + fileName);
                    IOUtils.copy(in, out);
                    out.close();
                }

                //Get format from file extension
                String[] rawFormat = fileName.split("\\.");
                String format = rawFormat[rawFormat.length-1];

                //Update the paper, send a notification, and save everything
                paper.updatePaper(authors, title, format, "SubmittedPapers/" + fileName);
            } else { //Otherwise just update the authors & title
                paper.updatePaper(authors, title, paper.getFormat(), paper.getPaperUpload());
            }

            //Send notification and save Paper
            String messageString = "A User (" + paper.getContactAuthor().getFullName() + ") has edited their paper entitled '" + paper.getTitle() + "'.";
            Notification notification = new Notification(paperManager.getNotificationsSize(), paper.getContactAuthor(), paperManager.getPCC(), messageString, false, new Date());
            paperManager.addNotification(notification);

            String messageString2 = "Your Paper '" + paper.getTitle()  +"' was successfully edited.";
            Notification not2 = new Notification(paperManager.getNotificationsSize(), null, paper.getContactAuthor(), messageString2, false, new Date());
            paperManager.addNotification(not2);

            paperManager.savePapers();
            paperManager.saveNotifications();
        } catch(Exception e) {
            e.printStackTrace();
        }

        response.redirect("/managePapers");
        halt();
        return null;
    }
}
