package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Message;
import com.SAM2018.model.PCC;
import com.SAM2018.model.User;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.*;

import static spark.Spark.halt;


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
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));

        if(!userType.equals("Admin")) {
            response.redirect("/manageAccounts");
            halt();
            return null;
        }

        String uid = request.body().substring(1, request.body().length() - 1);
        User user = paperManager.getUser(uid);
        if(paperManager.getCountPCC() == 1 && user instanceof PCC)
            return new Message("There must be at least one PCC in the System at all times", "error");

        paperManager.deleteUser(uid);

        return new Message("User has been deleted", "info");
    }
}
