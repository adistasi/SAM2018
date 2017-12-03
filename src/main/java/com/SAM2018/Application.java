package com.SAM2018;

import java.util.*;
import java.util.logging.Logger;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.*;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import com.SAM2018.ui.WebServer;


/**
 * The entry point for the SAM2018 web application.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public final class Application {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    /**
    * Entry point for the SAM2018 web application.
    * @param args Command line arguments; none expected.
    */
    public static void main(String[] args) {

        //Create PaperManager
        final PaperManager paperManager = new PaperManager();

        paperManager.loadApplication();

        Map<String, Deadline> deadlines = paperManager.getDeadlines();
        Deadline subDead = deadlines.get("Submission Deadline");
        Deadline reqDead = deadlines.get("Request Deadline");
        Deadline revDead = deadlines.get("Review Deadline");
        Deadline ratDead = deadlines.get("Rating Deadline");
        Timer timer = paperManager.getTimer();

        if(subDead != null) {
            timer.schedule(new TimerTask() {
                public void run() {
                    paperManager.enforceSubmissionDeadline();
                }
            }, subDead.getDate());
        }

        if(reqDead != null) {
            timer.schedule(new TimerTask() {
                public void run() {
                    paperManager.enforceRequestDeadline();
                }
            }, reqDead.getDate(), 86400000);
        }

        if(revDead != null) {
            timer.schedule(new TimerTask() {
                public void run() {
                    paperManager.enforceReviewDeadline();
                }
            }, revDead.getDate(), 86400000);
        }

        if(ratDead != null) {
            timer.schedule(new TimerTask() {
                public void run() {
                    paperManager.enforceRatingDeadline();
                }
            }, ratDead.getDate(), 86400000);
        }

        // The application uses FreeMarker templates to generate the HTML
        // responses sent back to the client. This will be the engine processing
        // the templates and associated data.
        final TemplateEngine templateEngine = new FreeMarkerEngine();

        // inject the game center and freemarker engine into web server
        final WebServer webServer = new WebServer(paperManager, templateEngine);

        // inject web server into application
        final Application app = new Application(webServer);

        // start the application up
        app.initialize();
    }

    // Attributes
    private final WebServer webServer;

    // Constructor
    private Application(final WebServer webServer) {
    this.webServer = webServer;
    }

    // Private methods
    private void initialize() {
        LOG.fine("SAM2018 is initializing.");

        // configure Spark and startup the Jetty web server
        webServer.initialize();

        // other applications might have additional services to configure
        LOG.fine("SAM2018 initialization complete.");
    }

}