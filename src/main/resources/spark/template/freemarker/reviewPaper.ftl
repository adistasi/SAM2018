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
        <p>Papers Available for Review:</p>

        <form action="/requestPaper" method="POST" class="inputForm">
            <#if paperForReview??>
                <div class="paperRequestForm">
                    <#list paperForReview as paper>
                        <input type="checkbox" name="requestedPaper" value=${paper.getPaperID()}> "<em>${paper.getTitle()}</em>" - ${paper.getAuthorsAsString()}<br>
                    </#list>
                </div>
                <div id="requestFormSubmitButton">
                    <button type="submit" class="btn btn-default">Submit Request</button>
                </div>
            </#if>
        </form>
    </div>

</div>
</body>
</html>
