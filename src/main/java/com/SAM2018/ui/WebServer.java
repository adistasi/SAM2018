package com.SAM2018.ui;

import static spark.Spark.*;
import com.SAM2018.appl.PaperManager;
import spark.TemplateEngine;


/**
 * The server that initializes the set of HTTP request handlers.
 * This defines the <em>web application interface</em> for this
 * guessing game application.
 *
 * <p>
 * There are multiple ways in which you can have the client issue a
 * request and the application generate responses to requests. If your team is
 * not careful when designing your approach, you can quickly create a mess
 * where no one can remember how a particular request is issued or the response
 * gets generated. Aim for consistency in your approach for similar
 * activities or requests.
 * </p>
 *
 * <p>Design choices for how the client makes a request include:
 * <ul>
 *     <li>Request URL</li>
 *     <li>HTTP verb for request (GET, POST, PUT, DELETE and so on)</li>
 *     <li><em>Optional:</em> Inclusion of request parameters</li>
 * </ul>
 * </p>
 *
 * <p>Design choices for generating a response to a request include:
 * <ul>
 *     <li>View templates with conditional elements</li>
 *     <li>Use different view templates based on results of executing the client request</li>
 *     <li>Redirecting to a different application URL</li>
 * </ul>
 * </p>
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class WebServer {
    // Constants

    // Attributes
    private final TemplateEngine templateEngine;
    private final PaperManager paperManager;

    /**
    * The constructor for the Web Server.
    *
    * @param _paperManager The default {@link PaperManager} to manage state)
    * @param _templateEngine The default {@link TemplateEngine} to render views.
    */
    public WebServer(final PaperManager _paperManager, final TemplateEngine _templateEngine) {
        this.paperManager = _paperManager;
        this.templateEngine = _templateEngine;

    }

    /**
    * Initialize all of the HTTP routes that make up this web application.
    *
    * <p>
    * Initialization of the web server includes defining the location for static
    * files, and defining all routes for processing client requests. The method
    * returns after the web server finishes its initialization.
    * </p>
    */
    public void initialize() {
        // Configuration to serve static files
        staticFileLocation("/public");

        // Shows the SAM game Home page.
        get("/", new GetHomeRoute(), templateEngine);

        //Show Submit Paper Page
        get("/submitPaper", new GetSubmitPaperRoute(), templateEngine);
        post("/submitPaper", new PostSubmitPaperRoute(paperManager), templateEngine);

        // Shows the Login Page
        get("/login", new GetLoginRoute(), templateEngine);
        post("/login", new PostLoginRoute(paperManager), templateEngine);

        // Shows the Registration Page
        get("/register", new GetRegisterRoute(), templateEngine);
        post("/register", new PostRegisterRoute(paperManager),templateEngine);

        //Shows logout Route
        get("/logout", new GetLogoutRoute(), templateEngine);

        //Get & Submit Requests
        get("/requestPaper", new GetRequestPaperRoute(paperManager), templateEngine);
        post("/requestPaper",new PostRequestPaperRoute(paperManager),templateEngine);

        //Manage requests
        get("/manageRequests", new GetManageRequestsRoute(paperManager), templateEngine);
        post("/manageRequests", new PostManageRequestsRoute(paperManager), templateEngine);

        get("/managePapers", new GetManagePapersRoute(paperManager), templateEngine);

        get("/manageSubmissions", new GetManageSubmissionsRoute(paperManager), templateEngine);

        //Lets a user edit a paper in the SAM System
        get("/editPaper", new GetEditPaperRoute(paperManager), templateEngine);
        post("/editPaper", new PostEditPaperRoute(paperManager), templateEngine);

        get("/reviewPapers", new GetReviewPapersRoute(paperManager), templateEngine);

        get("/reviewPaper", new GetReviewPaperRoute(paperManager), templateEngine);
        post("/reviewPaper", new PostReviewPaperRoute(paperManager), templateEngine);

        get("/ratePapers", new GetRatePapersRoute(paperManager), templateEngine);

        get("/ratePaper", new GetRatePaperRoute(paperManager), templateEngine);
        post("/ratePaper", new PostRatePaperRoute(paperManager), templateEngine);

  }
}