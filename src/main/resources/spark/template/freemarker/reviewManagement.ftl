<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script type="text/javascript" src="js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="js/main.js"></script>
</head>
<body>
<div class="page">

    <h1>SAM 2018 - Review Management</h1>

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
        <#if message??>
            <div id="message" class="${messageType}">${message}</div>
        </#if>

        <#if papersRequested?size != 0>
            <div class="subdiv">
                <h3 class="subheader">Submitted Review Requests:</h3>

                <form action="/manageRequests" method="POST" class="inputForm">
                    <#list papersRequested as p>
                        <#if p.getReviewExists()>
                            <h4 style="text-decoration: underline">"<em>${p.getPaper().getTitle()}</em>" - ${p.getPaper().getAuthorsAsString()}</h4>
                            <div class="reviews" data-pid="${p.getPaper().getPaperID()}">
                                <#if p.getUsers()??>
                                    <#list p.getUsers() as u>
                                        <input type="checkbox" name="requests" value="${u.getUsername()}|||${p.getPaper().getPaperID()}"/>
                                        <label class='noMaxWidth' for="requests">${u.getFirstName()} ${u.getLastName()}</label><br />
                                    </#list>
                                <#else>
                                    <p>No PCMs have requested to review this paper</p>
                                </#if>

                                <#if pcmUsers??>
                                    <select class="addPCMSToPaper" onchange="addPCM($(this))">
                                        <option selected disabled>Add a PCM to this paper</option>
                                        <#list pcmUsers as pcm>
                                            <option value="${pcm.getUsername()}">${pcm.getFirstName()} ${pcm.getLastName()}</option>
                                        </#list>
                                    </select>
                                </#if>
                            </div>
                        </#if>
                    </#list>

                    <div style="text-align:center; margin-top:25px;margin-left:150px"><button type="submit" class="btn btn-good">Submit</button></div>
                </form>
            </div>
        <#else>
            <p style="text-align:center">No requests have been submitted.</p>
        </#if>
    </div>
</div>
</body>
</html>
