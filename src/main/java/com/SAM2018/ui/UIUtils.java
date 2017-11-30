package com.SAM2018.ui;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.Map;
import static spark.Spark.halt;

public class UIUtils {
    public static Map<String, Object> validateLoggedIn(Request request, Response response, Map<String, Object> vm) {
        if(request.session().attribute("username") != null) {
            vm.put("username", request.session().attribute("username"));
            return vm;
        } else {
            response.redirect("/login");
            halt();
            return null;
        }
    }

    public static ModelAndView error(Map<String, Object> vm, String message, String viewname) {
        vm.put("message", message);
        vm.put("messageType", "error");
        return new ModelAndView(vm, viewname);
    }

    public static boolean validateInputText(String input) {
        return (input.contains("|||") || input.equals(""));
    }

    public static int parseIntInput(String valueToParse) {
        try {
            return Integer.parseInt(valueToParse);
        } catch (Exception e) {
            return -2;
        }
    }

    public static double parseDoubleInput(String valueToParse) {
        try {
            return Double.parseDouble(valueToParse);
        } catch (Exception e) {
            return -2.0;
        }
    }
}
