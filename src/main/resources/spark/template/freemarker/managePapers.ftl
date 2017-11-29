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
            <a href="/submitPaper">Submit Paper</a> |
            <a href="/manageSubmissions">Manage Submissions</a> |
            <a href="/requestPaper">Request Reviews</a> |
            <a href="reviewPapers">Review Papers</a> |
            <a href="/manageRequests">Manage Assignments</a> |
            <a href="ratePapers">Rate Papers</a> |
            <a href="/logout">Logout</a>
        <#else>
            <a href="/login">Login</a> |
            <a href="/register">Register</a>
        </#if>
    </div>


    <div class="body">
        <#if username??>
            <p>Welcome to the world of SAM 2018.</p>
        </#if>
    </div>
  </div>
</body>
</html>
