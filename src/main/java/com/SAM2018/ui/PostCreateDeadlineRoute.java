package com.SAM2018.ui;

import java.text.SimpleDateFormat;
import java.util.*;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Deadline;
import spark.*;

import static spark.Spark.halt;

/**
 * The Web Controller for the Create Deadline POST
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostCreateDeadlineRoute implements TemplateViewRoute {
    private PaperManager paperManager;

    /**
     * The constructor for the {@code POST /createDeadline} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostCreateDeadlineRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        vm.put("title", "Deadline Management");
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);
        vm.put("notificationCount", paperManager.getUnreadNotificationCount(request.session().attribute("username")));

        if(!userType.equals("Admin")) { //Redirect any non-admin users
            response.redirect("/manageDeadlines");
            halt();
            return null;
        }

        //Bring in each query Parameter from the form
        String title = request.queryParams("title");
        String date = request.queryParams("date");
        String time = request.queryParams("time");
        String dateTime = date + " " + time;

        try { //Format the date into a java.util.Date format
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
            Date dateGenerated = sdf.parse(dateTime);

            Deadline deadline = new Deadline(title, dateGenerated);
            paperManager.addDeadline(title, deadline);
            paperManager.saveDeadlines();

            //Get the timer, unset it, and reapply a new Timer for the Application with the updated deadline information
            Timer timer = paperManager.getTimer();
            timer.cancel();
            Timer timer2 = new Timer();
            for( Deadline d : paperManager.getDeadlines().values()) { //Loop through every deadline and apply a timer method should one exist
                if(d.getTitle().equals("Submission Deadline")) { //Don't set the submission deadline timer to repeat it's method call
                    timer2.schedule(new TimerTask() {
                        public void run() {paperManager.enforceSubmissionDeadline();
                        }
                    }, d.getDate());
                } else {
                    timer2.schedule(new TimerTask() {
                        public void run() { //Set all other deadlines to re-send notifications daily
                            if (d.getTitle().equals("Request Deadline")) {
                                paperManager.enforceRequestDeadline();
                            } else if (d.getTitle().equals("Review Deadline")) {
                                paperManager.enforceReviewDeadline();
                            } else if (d.getTitle().equals("Rating Deadline")) {
                                paperManager.enforceRatingDeadline();
                            }
                        }
                    }, d.getDate(), 86400000);
                }
            }

            paperManager.setTimer(timer2);
        } catch (Exception e){ //Error handling
            e.printStackTrace();
        }

        response.redirect("/manageDeadlines");
        halt();
        return null;
    }
}