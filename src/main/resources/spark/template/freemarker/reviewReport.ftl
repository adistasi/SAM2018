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
        <a href="/">Home</a> |
        <a href="/submitPaper">Submit a Paper</a> |
        <a href="/requestPaper">Request Reviews</a> |
        <a href="/logout">Logout</a> |
    <#else>
        <a href="/login">Login</a> |
        <a href="/register">Register</a> |
    </#if>
    </div>

    <div class="body">
        <#if report??>
            <p>Report for ${report.getSubject().getTitle()}</p>
            <p>Overall Rating: ${report.getPccReview().getRating()}</p>
            <p>PCC Comments: ${report.getPccReview().getReviewerComments()}</p>
            <br />
            <br />

            <#list report.getPcmReviews() as r>
                <p>Reviewer Ratings:</p>
                <p>Rating: ${r.getRating()}</p>
                <p>Comments: ${r.getReviewerComments()}</p>
                <hr />
            </#list>
        </#if>
    </div>

</div>
</body>
</html>
