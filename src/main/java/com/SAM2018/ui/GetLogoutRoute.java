package com.SAM2018.ui;

import spark.*;
import static spark.Spark.halt;


public class GetLogoutRoute implements TemplateViewRoute{
    @Override
    public ModelAndView handle(Request request, Response response) {
        final Session session = request.session();
        session.attribute("username", null);

        response.redirect("/login");
        halt();
        return null;
    }
}
