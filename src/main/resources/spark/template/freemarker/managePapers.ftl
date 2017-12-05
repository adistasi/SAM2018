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
    </div>


    <div class="body">
        <#if username??>
            <p>This is the Paper Management section of the SAM2018 Submission Manager.  From here you can submit a new paper and manage your existing submissions.</p>

            <#if userType == "PCC" || userType == "Admin" || userType == "PCM">
                <br />
                <p>From this page, Program Committee Chairs can assign papers to Program Committee Members for review and can themselves rate papers, mark papers as accepted or declined, and generate reports.  Program Committee Members can request to review certain papers and review the papers assigned to them from here.</p>
            </#if>
        </#if>
    </div>
  </div>
</body>
</html>
