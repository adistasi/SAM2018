package com.SAM2018.ui;

import static spark.Spark.*;
import com.SAM2018.appl.PaperManager;
import spark.TemplateEngine;


/**
 * The server that initializes the set of HTTP request handlers.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
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
    */
    public void initialize() {
        // Configuration to serve static files
        externalStaticFileLocation(System.getProperty("user.dir") + "\\src\\main\\resources\\public");

        // Shows the SAM game Home page.
        get("/", new GetHomeRoute(paperManager), templateEngine);

        //Show Submit Paper Page
        get("/submitPaper", new GetSubmitPaperRoute(paperManager), templateEngine);
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

        get("/reviewRating", new GetReviewRatingRoute(paperManager), templateEngine);

        post("/rereviewPaper", new PostRereviewPaperRoute(paperManager), JsonUtils.json());

        get("/createNotification", new GetCreateNotificationRoute(paperManager), templateEngine);
        post("/createNotification", new PostCreateNotificationRoute(paperManager), templateEngine);

        get("/viewNotifications", new GetViewNotificationsRoute(paperManager), templateEngine);
        post("/markAsRead", new PostMarkAsReadRoute(paperManager), JsonUtils.json());

        get("/manageAccounts", new GetManageAccountsRoute(paperManager), templateEngine);
        post("/approveUser", new PostApproveUserRoute(paperManager), JsonUtils.json());
        post("/denyUser", new PostDenyUserRoute(paperManager), JsonUtils.json());
        post("/deleteUser", new PostDeleteUserRoute(paperManager), JsonUtils.json());

        get("/manageDeadlines", new GetManageDeadlinesRoute(paperManager), templateEngine);
        post("/createDeadline", new PostCreateDeadlineRoute(paperManager), templateEngine);
  }
}