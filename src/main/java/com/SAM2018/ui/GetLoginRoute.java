package com.SAM2018.ui;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

/**
 * The Web Controller for the Login page.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetLoginRoute implements TemplateViewRoute{

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Login");

        if(request.session().attribute("username") != null) { //If the user is logged in, return their username
            vm.put("username", request.session().attribute("username"));
        }

        return new ModelAndView(vm, "login.ftl");
    }
}
