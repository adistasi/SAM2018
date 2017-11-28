<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>SAM 2018 Paper Upload</h1>

    <div class="navigation">
        <#if username??>
            <a href="/">Home</a> |
            <a href="/submitPaper">Submit a Paper</a> |
            <a href="/requestPaper">Request Reviews</a> |
            <a href="/logout">Logout</a> |
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

        <form method="POST" action="./submitPaper" id="submitPaper" class="inputForm">
            <div class="form-group">
                <label>Authors</label>
                <input type="text" name="author1" /><br />
                <label></label>
                <input type="text" name="author2" /><br />
                <label></label>
                <input style="margin-bottom: 35px;" type="text" name="author3" /><br />

                <label>Paper Title</label>
                <input type="text" name="title" /><br />

                <label>Paper Format</label>
                <input type="text" name="format" /><br />

                <label>Upload Paper</label>
                <input type="text" name="paperFile" /><br />
                <!--<input type="file" name="paperFile" /><br />-->
                <button type="submit">Upload</button>
            </div>
        </form>
    </div>

</div>
</body>
</html>
