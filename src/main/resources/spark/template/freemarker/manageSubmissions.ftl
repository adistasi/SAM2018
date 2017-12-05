<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>SAM 2018 Paper Management</h1>
    
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
            <#if papers?size != 0>
                <#list papers as p>
                    <div>
                        <p>Paper Title: ${p.getPaper().getTitle()}</p>
                        <p>Version Number: ${p.getPaper().getVersion()}</p>
                        <#if isAdminOrPCC == true>
                            <p>Paper Author: ${p.getPaper().getContactAuthor().getFullName()}</p>
                        </#if>

                        <a href="${p.getPaper().getPaperUpload()}" download>Download Paper</a>

                        <#if isAdminOrPCC == false>
                            <a href="/editPaper?pid=${p.getPaper().getPaperID()}">Edit Paper</a>
                        </#if>

                        <p>
                            STATUS:
                            <#if p.getReport()??>
                                <#if p.getReport().getAcceptanceStatus() == "Modify">
                                    Accepted with Modifications
                                <#else>
                                    ${p.getReport().getAcceptanceStatus()}
                                </#if>

                                <a href="/reviewRating?pid=${p.getPaper().getPaperID()}">View Report</a>
                            <#else>
                                Pending
                            </#if>
                        </p>
                        <hr />
                    </div>
                </#list>
            <#else>
                <p style="text-align:center">You haven't uploaded any papers yet!</p>
            </#if>
        </#if>
    </div>
  </div>
</body>
</html>
