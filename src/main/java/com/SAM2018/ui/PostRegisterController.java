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

public class PostRegisterController implements TemplateViewRoute {

    String PASSWORD_NO_MATCH = "The password is not the same!";

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        String firstName = request.queryParams("firstName");
        String lastName = request.queryParams("lastName");
        String username = request.queryParams("username");
        String password = request.queryParams("password");
        String validPassword = request.queryParams("validPassword");

        if (password == validPassword) {
            //TODO - How to add a new user without specifying the role?
            User user = new Submitter(username, password, firstName, lastName);
            PaperManager.addUser(user);
            // TODO - Redirect to?
            return null;
            //return new ModelAndView(vm, "");
        }
        else{
            vm.put("title","Register");
            vm.put("messageType","error");
            vm.put("message",PASSWORD_NO_MATCH);
            return new ModelAndView(vm, "register.ftl");
        }
    }
}