package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Submitter;
import com.SAM2018.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class PostRegisterRoute implements TemplateViewRoute {
    private final PaperManager paperManager;

    String PASSWORD_NO_MATCH = "The password is not the same!";

    /**
     * The constructor for the {@code POST /login} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostRegisterRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        String firstName = request.queryParams("fName");
        String lastName = request.queryParams("lName");
        String username = request.queryParams("username");
        String password = request.queryParams("password");
        String validPassword = request.queryParams("validPassword");

        if (password.equals(validPassword)) {
            if(username.contains("|||") || password.contains("|||") || firstName.contains("|||") || lastName.contains("|||")) {
                vm.put("title","Register");
                vm.put("messageType","error");
                vm.put("message", "Account information cannot include the string '|||'");
                return new ModelAndView(vm, "register.ftl");
            } else {
                User user = new Submitter(username, password, firstName, lastName);
                paperManager.addUser(user);

                response.redirect("/login");
                halt();
                return null;
            }
        }
        else{
            vm.put("title","Register");
            vm.put("messageType","error");
            vm.put("message",PASSWORD_NO_MATCH);
            return new ModelAndView(vm, "register.ftl");
        }
    }
}