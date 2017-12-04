package com.SAM2018.ui;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static spark.Spark.halt;

/**
 * A collection of static functions that offer functionality used consistently throughout the Route classes
 * Functionality includes Validation of if the user is logged in, returning an error message, and validating form inputs
 */
public class UIUtils {
    /**
     * A method to check if the user is logged in and set their username value in the ViewModel if they are
     * @param request The HTTP Request
     * @param response THE HTTP Response
     * @param vm The ViewModel Map
     * @return vm The ViewModel with the username attribute included (or null if they aren't logged in)
     */
    public static Map<String, Object> validateLoggedIn(Request request, Response response, Map<String, Object> vm) {
        if(request.session().attribute("username") != null) { //If there is a username attribute in the session, put it in the ViewModel
            vm.put("username", request.session().attribute("username"));
            return vm;
        } else { //else redirect to the login page
            response.redirect("/login");
            halt();
            return null;
        }
    }

    /**
     * A method to return an error to the ViewModel
     * @param vm The ViewModel Map
     * @param message The Error message text
     * @param viewname The View that we're returning to
     * @return new ModelAndView The ViewModel for the specified page
     */
    public static ModelAndView error(Map<String, Object> vm, String message, String viewname) {
        vm.put("message", message);
        vm.put("messageType", "error");
        return new ModelAndView(vm, viewname);
    }

    /**
     * A method to validate input text for protected characters and that it isn't null
     * @param input The text to be validated
     * @return Whether or not the text is valid (has length and doesn't contain '|||')
     */
    public static boolean validateInputText(String input) {
        return (input.contains("|||") || input.equals(""));
    }

    /**
     * A method to parse integer input and ensure it's a valid int
     * @param valueToParse The integer value to evaluate
     * @return The Integer value if it's valid (or -2 if it isn't)
     */
    public static int parseIntInput(String valueToParse) {
        try { //Try and parse the int, returning -2 if it fails
            return Integer.parseInt(valueToParse);
        } catch (Exception e) {
            return -2;
        }
    }

    /**
     * A method to parse double input and ensure it's a valid double
     * @param valueToParse The double value to parse
     * @return The double value if it's valid (or -2.0 if it isn't)
     */
    public static double parseDoubleInput(String valueToParse) {
        try { //Try and parse the double, return -2 if it fails
            return Double.parseDouble(valueToParse);
        } catch (Exception e) {
            return -2.0;
        }
    }

    /**
     * A method to validate the inputted strings for Author names for a paper
     * @param _authors A string containing every author name delimited by '/'
     * @return A List of Strings containing the author names
     */
    public static List<String> validateAuthors(String _authors) {
        List<String> authors = new ArrayList<>();
        String[] authorsArr = _authors.split("/");
        List<String> authorsRaw = Arrays.asList(authorsArr);

        for(String auth : authorsRaw) { //loop through each inputted author name
            if(auth != null && !auth.equals("")) //If the author name exists, add it to the list
                authors.add(auth);
        }

        return authors;
    }

    /**
     * Helper function to get the file name of a submitted Part from a form
     * @param _part The part representing the file that was uploaded
     * @return The filename without any of the other path information
     */
    public static String getSubmittedFileName(Part _part)  {
        for(String content : _part.getHeader("content-disposition").split(";")) { //Loop through each part of the file information
            if(content.trim().startsWith("filename")) { //Get just the filename, remove quotes, and substring away anything that isn't [fileName].[extension]
                String fileName = content.substring(content.indexOf("=")+1).trim().replace("\"", "");

                return fileName.substring(fileName.lastIndexOf('/')+1).substring(fileName.lastIndexOf('\\')+1);
            }
        }

        return null;
    }
}
