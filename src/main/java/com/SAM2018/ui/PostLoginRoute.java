package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.User;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The Web Controller for the Login POST.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostLoginRoute implements TemplateViewRoute{
    //Attributes
    private final PaperManager paperManager;

    //Constants
    String WRONG_PASSWORD = "Incorrect Password!";
    String WRONG_USERNAME = "Incorrect Username!";

    /**
     * The constructor for the {@code POST /login} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostLoginRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        //Prepare the VM & get username, type, & logged in status
        Map<String, Object> vm = new HashMap<>();
        final Session session = request.session();

        String username = request.queryParams("username");
        String password = request.queryParams("password");

        if(paperManager.userExists(username)){ //Don't let users register with the same username
            User user = paperManager.getUser(username);

            if (user.getPassword().equals(password)){ //password check validation
                session.attribute("username", username);
                response.redirect("/");
                halt();
                return null;
            } else { //Return invalid password error message
                vm.put("title","Login");
                vm.put("messageType", "error");
                vm.put("message", WRONG_PASSWORD);
                return new ModelAndView(vm, "login.ftl");
            }
        } else { //Return taken username error message
            vm.put("title","Login");
            vm.put("messageType", "error");
            vm.put("message", WRONG_USERNAME);
            return new ModelAndView(vm, "login.ftl");
        }
    }
}