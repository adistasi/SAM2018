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

    <h1>SAM 2018 - Edit Paper Submission</h1>

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
        <#if message??>
            <div id="message" class="${messageType}">${message}</div>
        <#else>
            <div id="message" class="info" style="display:none">

            </div>
        </#if>

        <#if paper??>
            <h4 style="text-align:center">Edit your paper: <em>'${paper.getTitle()}'</em></h4>

            <form method="POST" enctype="multipart/form-data" action="/editPaper" id="submitPaper" class="inputForm">
                <div class="form-group">
                    <input type="hidden" name="pid" value="${paper.getPaperID()}" />

                    <div id="authorsDiv">
                        <label>Authors</label>
                        <#list paper.getAuthors() as a>
                            <input type="text" class="authorField" name="author" value="${a}"/><br />
                        </#list>
                    </div>

                    <input type="hidden" id="authInput" name="authors" value="${paper.getAuthorsAsString()}"/>
                    <a href="#" class="btn-good" style="margin-left:95px; margin-bottom: 25px;" onclick="addAuthor()">Add Author</a><br />

                    <label>Paper Title</label>
                    <input type="text" name="title" value="${paper.getTitle()}" required /><br />

                    <label>Upload Paper</label>
                    <input style="margin-bottom: 15px" type="file" accept=".pdf,.doc,.docx" name="paperFile" /><br />
                    <span style="font-size:9pt; margin-bottom: 15px"><em>Current File: ${paper.getPaperUpload()}</em></span><br />
                    <button class="btn-good" style="margin-top:15px" type="submit">Edit</button>
                </div>
            </form>
        <#else>
            <p style="text-align:center">Please select a valid paper for editing</p>
        </#if>
    </div>

</div>
</body>
</html>
