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

    <h1>SAM 2018 PCM Paper Request Page</h1>

    <div class="navigation">
    <#if username??>
        <a href="/">Home</a> |
        <a href="/submitPaper">Submit a Paper</a> |
        <a href="/requestPaper">Request Reviews</a> |
        <a href="/logout">Logout</a> |
    <#else>
        <a href="/login">Login</a> |
        <a href="/register">Register</a> |
    </#if>
    </div>

    <div class="body">
        <#if message??>
            <div id="message" class="${message.type}">${message.text}</div>
        <#else>
            <div id="message" class="info" style="display:none">
                <!-- keep here for Client-side messages -->
            </div>
        </#if>
        <p>Papers Available for Review:</p>

        <#if paper??>
            <p>Submit a Review for ${paper.getTitle()}</p>
            <p>By ${paper.getAuthorsAsString()}</p>
            <p>Paper: ${paper.getPaperUpload()}</p>
            <p><em>THIS WILL BE REPLACED WITH DOWNLOAD LINK</em></p>
            <form method="POST" action="/ratePaper" id="submitRating" class="inputForm">
                <div class="form-group">
                    <input type="hidden" name="pid" value="${paper.getPaperID()}" />

                    <label>Score</label>
                    <input type="number" name="score" min="0" max="10" step="0.1" /><br />

                    <label>Comments</label>
                    <textarea name="comment" placeholder="Enter Comments Here"/></textarea><br />

                    <label>Accepted?</label><br />
                    <input type="radio" name="approval" value="Accepted">Accepted<br />
                    <input type="radio" name="approval" value="Denied">Denied<br />
                    <input type="radio" name="approval" value="Modify">Accepted with Modifications<br />
                    <button type="submit">Submit</button>
                </div>
            </form>

            <button onclick="rereviewPaper(${paper.getPaperID()})">Have PCMs Re-Review this paper</button>
            <!--<a href="/rereviewPaper?pid=${paper.getPaperID()}">Have PCMs Re-Review this paper</a>-->
        </#if>
    </div>

</div>
</body>
</html>
