package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Notification;
import com.SAM2018.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The Web Controller for the Register POST.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostRegisterRoute implements TemplateViewRoute {
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /register} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostRegisterRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        vm.put("title","Register");

        //Pull in form data
        String firstName = request.queryParams("fName");
        String lastName = request.queryParams("lName");
        String username = request.queryParams("username");
        String password = request.queryParams("password");
        String validPassword = request.queryParams("validPassword");

        if (password.equals(validPassword)) { //Password confirmation must match
            //Validate input text for protected characters and taken usernames
            if(UIUtils.validateInputText(username) || UIUtils.validateInputText(password) || UIUtils.validateInputText(firstName) || UIUtils.validateInputText(lastName)) {
                return UIUtils.error(vm, "Account information cannot include the string '|||'", "register.ftl");
            } else {
                if(paperManager.getUser(username) != null || username.equals("System"))
                    return UIUtils.error(vm, "That username is already taken", "register.ftl");

                //Create the user as a submitter and create a permission request (notifying the Admin) if they input one
                User user = new User(username, password, firstName, lastName);
                paperManager.addUser(user);

                String requestedType = request.queryParams("usertype");
                if(requestedType != null) {
                    paperManager.requestPermissions(user, requestedType);
                    Notification note = new Notification(paperManager.getNotificationsSize(), user, paperManager.getAdmin(), "A user (" + user.getFullName() + ") has registered and requested " + requestedType + " status", false);
                    paperManager.addNotification(note);
                    paperManager.saveNotifications();
                }
                paperManager.saveUsers();

                response.redirect("/login");
                halt();
                return null;
            }
        } else{
            return UIUtils.error(vm, "Invalid Password", "register.ftl");
        }
    }
}