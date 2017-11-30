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
                <div class="form-group">
                    <input type="hidden" name="pid" value="${paper.getPaperID()}" />

                    <div style="margin-bottom: 35px;" id="authorsDiv">
                        <label>Authors</label>
                        <#list paper.getAuthors() as a>
                            <input type="text" class="authorField" name="author" value="${a}"/><br />
                        </#list>
                    </div>

                    <input type="hidden" id="authInput" name="authors" value="${paper.getAuthorsAsString()}"/>
                    <a href="#" onclick="addAuthor()">Add Author</a><br />

                    <label>Paper Title</label>
                    <input type="text" name="title" value="${paper.getTitle()}" required /><br />

                    <label>Paper Format</label>
                    <input type="text" name="format" value="${paper.getFormat()}" required /><br />

                    <label>Upload Paper</label>
                    <input type="text" name="paperFile" value="${paper.getPaperUpload()}" required /><br />
                    <!--<input type="file" name="paperFile" /><br />-->
                    <button type="submit">Edit</button>
                </div>
            </form>
        </#if>
    </div>

</div>
</body>
</html>
