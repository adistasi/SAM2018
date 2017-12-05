package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Message;
import com.SAM2018.model.PCC;
import com.SAM2018.model.PCM;
import com.SAM2018.model.User;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.*;

import static spark.Spark.halt;

/**
 * The Web Controller for the Delete User POST (AJAX Method)
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostDeleteUserRoute implements Route {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /deleteUser} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostDeleteUserRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));

        if(!userType.equals("Admin")) { //Redirect any non-Admin users
            response.redirect("/manageAccounts");
            halt();
            return null;
        }

        //Parse in the UID & validate it, deleting the user if it's valid
        String uid = request.body().substring(1, request.body().length() - 1);
        User user = paperManager.getUser(uid);

        if(paperManager.getCountPCC() == 1 && user instanceof PCC) //The last PCC cannot be deleted
            return new Message("There must be at least one PCC in the System at all times", "error");

        if(user instanceof PCM && ((PCM) user).hasReviews())
            return new Message("You cannot delete a PCM who has pending Reviews", "error");

        paperManager.deleteUser(uid);

        return new Message("User has been deleted", "info");
    }
}
