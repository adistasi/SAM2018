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
        vm = UIUtils.validateLoggedIn(request, response, vm);
        vm.put("title", "Submit Paper");

        return new ModelAndView(vm , "submitPaper.ftl");
    }
}
