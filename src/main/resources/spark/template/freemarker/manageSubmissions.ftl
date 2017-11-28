<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>SAM 2018 Paper Management</h1>
    
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
            <#if papers??>
                <#list papers as p>
                    <div>
                        <p>${p.getTitle()}</p>
                        <p>${p.getVersion()}</p>
                        <a href="/editPaper?pid=${p.getPaperID()}">Edit Paper</a>
                        <hr />
                    </div>
                </#list>
            <#else>
                <p>You haven't uploaded any papers yet!</p>
            </#if>
        </#if>
    </div>
  </div>
</body>
</html>
