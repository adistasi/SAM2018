package com.SAM2018.ui;

import com.SAM2018.appl.PaperManager;
import com.SAM2018.model.Message;
import com.SAM2018.model.Report;
import com.SAM2018.model.Review;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class PostRereviewPaperRoute implements Route {
    //Attributes
    private final PaperManager paperManager;

    /**
     * The constructor for the {@code POST /rereviewPaper} route handler
     * @param _paperManager The {@link PaperManager} for the application.
     */
    PostRereviewPaperRoute(final PaperManager _paperManager) {
        Objects.requireNonNull(_paperManager, "PaperManager must not be null");

        this.paperManager = _paperManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, Object> vm = new HashMap<>();
        vm = UIUtils.validateLoggedIn(request, response, vm);
        String userType = paperManager.getUserType(request.session().attribute("username"));
        vm.put("userType", userType);

        if(!(userType.equals("PCC") || userType.equals("Admin"))) {
            response.redirect("/managePapers");
            halt();
            return null;
        }


        String rawPid = request.body();
        int pid = Integer.parseInt(rawPid);
        Report report = paperManager.getReportByID(pid);

        List<Review> rereviews = report.getPcmReviews();

        for(Review r : rereviews) {
            r.setNeedsRereviewed(true);
        }

        paperManager.saveReviews();

        return new Message("INSERT ACTUAL MESSAGE TEXT HERE", "info");
    }
}
