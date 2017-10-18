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

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        String username = request.queryParams("username");
        String password = request.queryParams("password");
        if(PaperManager.userExists(username)){
            User user = PaperManager.getUser(username);
            if (user.getPassword() == password){
                return null;
                // TODO- specify which page it will redirect to

            }
            else {
                vm.put("title","Login");
                vm.put("messageType", "error");
                vm.put("message", "Wrong Password!");
                return new ModelAndView(vm, "login.ftl");
            }
        }
        else {
            vm.put("title","Login");
            vm.put("messageType", "error");
            vm.put("message", "Wrong Username!");
            return new ModelAndView(vm, "login.ftl");
        }
    }
}
