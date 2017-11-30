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

    <h1>SAM 2018 Paper Upload</h1>

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
            <a href="/Register">Register</a> |
        </#if>
    </div>

    <div class="body">
        <#if message??>
            <div id="message" class="${messageType}">${message}</div>
        <#else>
            <div id="message" class="info" style="display:none">

            </div>
        </#if>
        <h3 style="text-align:center">Upload a Paper to be considered for the conference</h3>

        <form method="POST" action="/submitPaper" id="submitPaper" class="inputForm">
            <div class="form-group">
                <div style="margin-bottom: 35px;" id="authorsDiv">
                    <label>Authors</label>
                    <input type="text" class="authorField" name="author"/><br />
                </div>
                <input type="hidden" id="authInput" name="authors" />
                <a href="#" onclick="addAuthor()">Add Author</a><br />

                <label>Paper Title</label>
                <input type="text" name="title" required /><br />

                <label>Paper Format</label>
                <input type="text" name="format" required /><br />

                <label>Upload Paper</label>
                <input type="text" name="paperFile" required /><br />
                <!--<input type="file" name="paperFile" /><br />-->
                <button type="submit">Upload</button>
            </div>
        </form>
    </div>

</div>
</body>
</html>
