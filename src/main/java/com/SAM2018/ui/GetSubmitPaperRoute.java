package com.SAM2018.ui;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

public class GetSubmitPaperRoute implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Submit Paper");

        if(request.session().attribute("username") != null) {
            vm.put("username", request.session().attribute("username"));
        } else {
            response.redirect("/login");
            halt();
            return null;
        }

        return new ModelAndView(vm , "submitPaper.ftl");
    }
}
