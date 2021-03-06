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
        <#if closed != true>
            <#if hasRequested?? && !hasRequested>
                <form action="/requestPaper" method="POST" class="inputForm">
                    <#if paperForReview?size != 0>
                        <p>Papers Available for Review:</p>
                        <div class="paperRequestForm">
                            <#list paperForReview as paper>
                                <input type="checkbox" name="requestedPaper" value=${paper.getPaperID()}> "<em>${paper.getTitle()}</em>" - ${paper.getAuthorsAsString()}<br>
                            </#list>
                        </div>
                        <div id="requestFormSubmitButton">
                            <button type="submit" class="btn btn-good">Submit Request</button>
                        </div>
                    <#else>
                        <p style="text-align:center">No papers are available for review.</p>
                    </#if>
                </form>
            <#else>
                <p style="text-align:center">You have already made a request to review papers</p>
            </#if>
        <#else>
            <p style="text-align:center">The Request Deadline has passed</p>
        </#if>
    </div>

</div>
</body>
</html>
