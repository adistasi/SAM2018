<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
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
            <form action="/manageRequests" method="POST" class="inputForm">
                <#list papersRequested as p>
                    <p style="text-decoration: underline">"<em>${p.getPaper().getTitle()}</em>" - ${p.getPaper().getAuthorsAsString()}</p>
                    <div class="reviews">
                        <#if p.getUsers()??>
                            <#list p.getUsers() as u>
                                <input type="checkbox" name="requests" value="${u.getUsername()}|||${p.getPaper().getPaperID()}"/>
                                <label for="requests">${u.getFirstName()} ${u.getLastName()}</label><br />
                            </#list>
                        <#else>
                            <p>No PCMs have requested to review this paper</p>
                        </#if>
                    </div>
                 </#list>

                <button type="submit" class="btn btn-default">Submit</button>
            </form>
        <#else>
            <p>No requests have been submitted.</p>
        </#if>
    </div>
</div>
</body>
</html>
