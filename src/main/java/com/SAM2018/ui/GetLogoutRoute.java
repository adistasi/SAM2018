package com.SAM2018.ui;

import spark.*;
import static spark.Spark.halt;

/**
 * The Web Controller for the Logout Route.
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetLogoutRoute implements TemplateViewRoute{
    @Override
    public ModelAndView handle(Request request, Response response) {
        //Unset the username variable and redirect to the login page
        final Session session = request.session();
        session.attribute("username", null);

        response.redirect("/login");
        halt();
        return null;
    }
}
