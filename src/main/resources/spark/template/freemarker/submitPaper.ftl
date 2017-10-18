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
        <a href="/">my home</a>
    </div>

    <div class="body">
        <#if message??>
            <div id="message" class="${messageType}">${message}</div>
        <#else>
            <div id="message" class="info" style="display:none">

            </div>
        </#if>
        <p>Upload a Paper to be considered for the conference!</p>

        <form method="POST" action="./submitPaper" id="submitPaper">
            <label>Authors:</label>
            <input type="text" name="author1" /><br />
            <input type="text" name="author2" /><br />
            <input type="text" name="author3" /><br />

            <label>Paper Title:</label>
            <input type="text" name="title" /><br />

            <label>Paper Format:</label>
            <input type="text" name="format" /><br />

            <label>Upload Paper</label>
            <input type="text" name="paperFile" />
            <!--<input type="file" name="paperFile" /><br />-->
            <button type="submit">Upload</button>
        </form>
    </div>

</div>
</body>
</html>
