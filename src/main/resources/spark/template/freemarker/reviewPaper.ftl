<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>SAM 2018 - Review Paper Submission</h1>

    <div class="navigation">
    <#if username??>
        <#if username??>
            <a href="/">Home</a> |
            <a href="/submitPaper">Submit Paper</a> |
            <a href="/manageSubmissions">Manage Submissions</a> |
            <#if userType == "PCM" || userType == "Admin">
                <a href="/requestPaper">Request Reviews</a> |
                <a href="reviewPapers">Review Papers</a> |
            </#if>
            <#if userType == "PCC" || userType == "Admin">
                <a href="/manageRequests">Manage Assignments</a> |
                <a href="ratePapers">Rate Papers</a> |
            </#if>
            <a href="/logout">Logout</a>
        <#else>
            <a href="/login">Login</a> |
            <a href="/register">Register</a>
        </#if>
    <#else>
        <a href="/login">Login</a> |
        <a href="/register">Register</a> |
    </#if>
    </div>

    <div class="body">
        <#if message??>
            <div id="message" class="${messageType}">"${message}"</div>
        </#if>

        <#if paper??>
            <h4>Submit a Review for '<em>${paper.getTitle()}</em>'</h4>
            <p style="margin-left:25px;">By ${paper.getAuthorsAsString()}</p>
            <a style='margin-left:25px' href="${paper.getPaperUpload()}" download>Download Paper</a>
            <form method="POST" action="/reviewPaper" id="submitPaper" class="inputForm">
                <div style="margin-top:25px; margin-bottom: 50px;">
                    <input type="hidden" name="pid" value="${paper.getPaperID()}" />

                    <label>Score</label>
                    <input type="number" name="score" min="0" max="10" step="0.1" required /><br />

                    <label style="vertical-align:top;">Comments</label>
                    <textarea rows="5" cols="100" name="comment" placeholder="Enter Comments Here" required /></textarea><br /><br />

                    <div style="text-align:center"><button class='btn-good' type="submit">Submit</button></div>
                </div>
            </form>

            <#if otherReviews??>
                <h3>A PCC has requested these papers be re-reviewed.  Here are the reviews that were submitted:</h3>
                <div class="subdiv">
                    <#list otherReviews as or>
                        <p>Rating: ${or.getRating()}</p>
                        <p>Comments: ${or.getReviewerComments()}</p>
                        <hr style="margin-left:40px;"/>
                    </#list>
                </div>
            </#if>
        <#else>
            <p style="text-align:center">Please select a valid paper for Review</p>
        </#if>


    </div>

</div>
</body>
</html>
