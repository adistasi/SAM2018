<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>SAM 2018 - Review Management</h1>

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
        <a href="/register">Register</a>
    </#if>
    </div>


    <div class="body">
    <#if username??>
        <#if ratablePapers?size != 0>
            <div class="subdiv" style="margin-bottom:50px;">
                <h4 class="subheader">The following Papers need rated:</h4>
                <hr style="margin-bottom:25px;" />
                <#list ratablePapers as p>
                    <div>
                        <a href="/ratePaper?pid=${p.getPaperID()}">${p.getTitle()} - ${p.getAuthorsAsString()}</a>

                    </div>
                </#list>
            </div>
        <#else>
            <p style="text-align:center">There are no papers in need of being rated.</p>
        </#if>

        <#if generatedReports?size != 0>
            <div class="subdiv">
                <h4 class="subheader">Reports have been generated for the following papers:</h4>
                <hr style="margin-bottom:25px" />
                <#list generatedReports as gr>
                    <p>${gr.getSubject().getTitle()} - ${gr.getSubject().getAuthorsAsString()} <em>(Rated ${gr.getPccReview().getRating()} and ${gr.getAcceptanceStatus()})</em></p>
                </#list>
            </div>
        </#if>
    </#if>
    </div>
</div>
</body>
</html>
