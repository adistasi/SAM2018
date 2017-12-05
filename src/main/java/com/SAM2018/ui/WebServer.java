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
    public static final String SUBMIT_PAPER_URL = "/submitPaper";
    public static final String LOGIN_URL = "/login";
    public static final String REGISTER_URL = "/register";
    public static final String LOGOUT_URL = "/logout";
    public static final String REQUEST_PAPER_URL = "/requestPaper";
    public static final String MANAGE_REQUESTS_URL = "/manageRequests";
    public static final String MANAGE_PAPERS_URL = "/managePapers";
    public static final String MANAGE_SUBMISSIONS_URL = "/manageSubmissions";
    public static final String EDIT_PAPER_URL = "/editPaper";
    public static final String REVIEW_PAPERS_URL = "/reviewPapers";
    public static final String REVIEW_PAPER_URL = "/reviewPaper";
    public static final String RATE_PAPERS_URL = "/ratePapers";
    public static final String RATE_PAPER_URL = "/ratePaper";
    public static final String REVIEW_RATING_URL = "/reviewRating";
    public static final String REREVIEW_PAPER_URL = "/rereviewPaper";
    public static final String CREATE_NOTIFICATION_URL = "/createNotification";
    public static final String VIEW_NOTIFICATIONS_URL = "/viewNotifications";
    public static final String MARK_AS_READ_URL = "/markAsRead";
    public static final String MANAGE_ACCOUNTS_URL = "/manageAccounts";
    public static final String APPROVE_USER_URL = "/approveUser";
    public static final String DENY_USER_URL = "/denyUser";
    public static final String DELETE_USER_URL = "/deleteUser";
    public static final String MANAGE_DEADLINES_URL = "/manageDeadlines";
    public static final String CREATE_DEADLINE_URL = "/createDeadline";

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

        //Shows & Posts the Submit Paper Page
        get(SUBMIT_PAPER_URL, new GetSubmitPaperRoute(paperManager), templateEngine);
        post(SUBMIT_PAPER_URL, new PostSubmitPaperRoute(paperManager), templateEngine);

        // Shows & Posts the Login Page
        get(LOGIN_URL, new GetLoginRoute(), templateEngine);
        post(LOGIN_URL, new PostLoginRoute(paperManager), templateEngine);

        // Shows & Posts the Registration Page
        get(REGISTER_URL, new GetRegisterRoute(), templateEngine);
        post(REGISTER_URL, new PostRegisterRoute(paperManager),templateEngine);

        //Shows logout Route
        get(LOGOUT_URL, new GetLogoutRoute(), templateEngine);

        //Get & Submit Requests
        get(REQUEST_PAPER_URL, new GetRequestPaperRoute(paperManager), templateEngine);
        post(REQUEST_PAPER_URL,new PostRequestPaperRoute(paperManager),templateEngine);

        //Manage & submit requests
        get(MANAGE_REQUESTS_URL, new GetManageRequestsRoute(paperManager), templateEngine);
        post(MANAGE_REQUESTS_URL, new PostManageRequestsRoute(paperManager), templateEngine);

        //Show "Home Page" for managing Papers
        get(MANAGE_PAPERS_URL, new GetManagePapersRoute(paperManager), templateEngine);

        //Show all submissions by a user
        get(MANAGE_SUBMISSIONS_URL, new GetManageSubmissionsRoute(paperManager), templateEngine);

        //Lets a user edit a paper in the SAM System
        get(EDIT_PAPER_URL, new GetEditPaperRoute(paperManager), templateEngine);
        post(EDIT_PAPER_URL, new PostEditPaperRoute(paperManager), templateEngine);

        //View all Papers (or an individual paper that you can submit)
        get(REVIEW_PAPERS_URL, new GetReviewPapersRoute(paperManager), templateEngine);
        get(REVIEW_PAPER_URL, new GetReviewPaperRoute(paperManager), templateEngine);
        post(REVIEW_PAPER_URL, new PostReviewPaperRoute(paperManager), templateEngine);

        //View all Ratings (or view & submit a rating for an individual paper)
        get(RATE_PAPERS_URL, new GetRatePapersRoute(paperManager), templateEngine);
        get(RATE_PAPER_URL, new GetRatePaperRoute(paperManager), templateEngine);
        post(RATE_PAPER_URL, new PostRatePaperRoute(paperManager), templateEngine);

        //Get a rating and trigger the PCMs needing to re-review with an AJAX call
        get(REVIEW_RATING_URL, new GetReviewRatingRoute(paperManager), templateEngine);
        post(REREVIEW_PAPER_URL, new PostRereviewPaperRoute(paperManager), JsonUtils.json());

        //View & Post Notifications
        get(CREATE_NOTIFICATION_URL, new GetCreateNotificationRoute(paperManager), templateEngine);
        post(CREATE_NOTIFICATION_URL, new PostCreateNotificationRoute(paperManager), templateEngine);

        //See all Notifications and mark them as read
        get(VIEW_NOTIFICATIONS_URL, new GetViewNotificationsRoute(paperManager), templateEngine);
        post(MARK_AS_READ_URL, new PostMarkAsReadRoute(paperManager), JsonUtils.json());

        //Account management controls for Admin Users
        get(MANAGE_ACCOUNTS_URL, new GetManageAccountsRoute(paperManager), templateEngine);
        post(APPROVE_USER_URL, new PostApproveUserRoute(paperManager), JsonUtils.json());
        post(DENY_USER_URL, new PostDenyUserRoute(paperManager), JsonUtils.json());
        post(DELETE_USER_URL, new PostDeleteUserRoute(paperManager), JsonUtils.json());

        //See & Submit Deadlines
        get(MANAGE_DEADLINES_URL, new GetManageDeadlinesRoute(paperManager), templateEngine);
        post(CREATE_DEADLINE_URL, new PostCreateDeadlineRoute(paperManager), templateEngine);
  }
}