package com.SAM2018.ui;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import static spark.Spark.halt;

/**
 * The Web Controller for the Home page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetHomeRoute implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");

        if(request.session().attribute("username") != null) {
            vm.put("username", request.session().attribute("username"));
        } else {
            response.redirect("/login");
            halt();
            return null;
        }

        return new ModelAndView(vm , "home.ftl");
    }
}