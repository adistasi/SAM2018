<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>SAM 2018 Review Management</h1>

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
        <#if reviews?size != 0>
            <div class="subdiv" style="margin-bottom:50px;">
                <p class="subheader">You have been assigned the following reviews:</p>
                <hr style="margin-bottom:15px"/>
                <#list reviews as r>
                    <div>
                        <a style="margin-bottom: 25px" href="/reviewPaper?pid=${r.getSubject().getPaperID()}">${r.getSubject().getTitle()} - ${r.getSubject().getAuthorsAsString()}</a>
                    </div>
                </#list>
            </div>
        <#else>
            <p style="text-align:center">You have no pending reviews!</p>
        </#if>

        <#if completedReviews?size != 0>
            <div class="subdiv">
                <p class="subheader">You have completed the following reviews:</p>
                <hr />
                <#list completedReviews as cr>
                    <div>
                        <p style="margin-bottom: 25px;">${cr.getSubject().getTitle()} - ${cr.getSubject().getAuthorsAsString()} <em>(Score: ${cr.getRating()})</em></p>
                    </div>
                </#list>
            </div>
        </#if>
    </#if>
    </div>
</div>
</body>
</html>
