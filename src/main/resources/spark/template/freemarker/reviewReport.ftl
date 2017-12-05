<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>SAM 2018 PCM Paper Request Page</h1>

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
        <#if report??>
            <div class="subdiv" style="margin-bottom:50px">
                <h4 class="subheader">Report for ${report.getSubject().getTitle()}</h4>
                <hr class="spacer" />
                <p>Overall Rating: ${report.getPccReview().getRating()}</p>
                <p>PCC Comments: ${report.getPccReview().getReviewerComments()}</p>
            </div>

            <div class="subdiv">
                <h4 class="subheader">Reviewer Ratings:</h4>
                <hr class="spacer" />
                <#list report.getPcmReviews() as r>
                    <p>Rating: ${r.getRating()}</p>
                    <p>Comments: ${r.getReviewerComments()}</p>
                    <hr style="margin-left:40px; margin-bottom: 25px;"/>
                </#list>
            </div>
        <#else>
            <p style="text-align:center">Please select a valid report</p>
        </#if>
    </div>

</div>
</body>
</html>
