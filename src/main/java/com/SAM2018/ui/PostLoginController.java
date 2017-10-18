package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class PostLoginController implements TemplateViewRoute{

    String WRONG_PASSWORD = "Incorrect Password!";
    String WRONG_USERNAME = "Incorrect Username!";

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        String username = request.queryParams("username");
        String password = request.queryParams("password");
        if(PaperManager.userExists(username)){
            User user = PaperManager.getUser(username);
            if (user.getPassword() == password){
                return new ModelAndView(vm, "home.ftl");
            }
            else {
                vm.put("title","Login");
                vm.put("messageType", "error");
                vm.put("message", WRONG_PASSWORD);
                return new ModelAndView(vm, "login.ftl");
            }
        }
        else {
            vm.put("title","Login");
            vm.put("messageType", "error");
            vm.put("message", WRONG_USERNAME);
            return new ModelAndView(vm, "login.ftl");
        }
    }
}