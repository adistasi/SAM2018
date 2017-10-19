<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>SAM 2018 Home Page</h1>

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
        <p>Submitted Review Requests:</p>
        <#if papersRequested??>
            <#list papersRequested as p>
                <p style="text-decoration: underline">"<em>${p.getPaper().getTitle()}</em>" - ${p.getPaper().getAuthorsAsString()}</p>
                <div class="reviews">
                    <#list p.getUsers() as u>
                        <input type="checkbox" name="requests" />
                        <label for="requests">${u.getFirstName()} ${u.getLastName()}</label><br />
                    </#list>
                </div>
             </#list>
        </#if>
    </div>
</div>
</body>
</html>
