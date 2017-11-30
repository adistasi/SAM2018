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
        <a href="/">Home</a> |
        <a href="/managePapers">Manage Papers</a> |
        <a href="/logout">Logout</a>
    <#else>
        <a href="/login">Login</a> |
        <a href="/register">Register</a>
    </#if>
    </div>


    <div class="body">
    <#if username??>
        <#if ratablePapers??>
            <#list ratablePapers as p>
                <div>
                    <a href="/ratePaper?pid=${p.getPaperID()}">${p.getTitle()} - ${p.getAuthorsAsString()}</a>

                </div>
            </#list>
        <#else>
            <p>You have no pending reviews!</p>
        </#if>
    </#if>
    </div>
</div>
</body>
</html>
