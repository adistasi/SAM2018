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
        <a href="/managePapers">Manage Papers</a> |
        <a href="/logout">Logout</a>
    <#else>
        <a href="/login">Login</a> |
        <a href="/register">Register</a>
    </#if>
    </div>

    <div class="body">
        <#if paper??>
            <h3 style="text-align:center">Edit your paper: ${paper.getTitle()}</h3>

            <form method="POST" action="./editPaper" id="submitPaper" class="inputForm">
                <div class="form-group">'
                    <input type="hidden" name="pid" value="${paper.getPaperID()}" />
                    <label>Authors</label>
                    <input type="text" name="author1" value="${auth1}" /><br />
                    <label></label>
                    <input type="text" name="author2" value="${auth2}" /><br />
                    <label></label>
                    <input style="margin-bottom: 35px;" type="text" name="author3" value="${auth3}" /><br />

                    <label>Paper Title</label>
                    <input type="text" name="title" value="${paper.getTitle()}" /><br />

                    <label>Paper Format</label>
                    <input type="text" name="format" value="${paper.getFormat()}" /><br />

                    <label>Upload Paper</label>
                    <input type="text" name="paperFile" value="${paper.getPaperUpload()}"/><br />
                    <!--<input type="file" name="paperFile" /><br />-->
                    <button type="submit">Edit</button>
                </div>
            </form>
        </#if>
    </div>

</div>
</body>
</html>
