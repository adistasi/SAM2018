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
        <#else>
            <div id="message" class="info" style="display:none">
                <!-- keep here for Client-side messages -->
            </div>
        </#if>

        <#if paper??>
            <p>Submit a Review for ${paper.getTitle()}</p>
            <p>By ${paper.getAuthorsAsString()}</p>
            <p>Paper: ${paper.getPaperUpload()}</p>
            <p><em>THIS WILL BE REPLACED WITH DOWNLOAD LINK</em></p>
            <form method="POST" action="/ratePaper" id="submitRating" class="inputForm">
                <div class="form-group">
                    <input type="hidden" name="pid" value="${paper.getPaperID()}" />

                    <label>Score</label>
                    <input type="number" name="score" min="0" max="10" step="0.1" required/><br />

                    <label>Comments</label>
                    <textarea name="comment" placeholder="Enter Comments Here" required/></textarea><br />

                    <label>Accepted?</label><br />
                    <input type="radio" name="approval" value="Accepted" required>Accepted<br />
                    <input type="radio" name="approval" value="Denied" required>Denied<br />
                    <input type="radio" name="approval" value="Modify" required>Accepted with Modifications<br />
                    <button type="submit">Submit</button>
                </div>
            </form>

            <button onclick="rereviewPaper(${paper.getPaperID()})">Have PCMs Re-Review this paper</button>

            <#if reviews??>
                <p>PCM Reviews for this paper:</p>

                <#list reviews as r>
                    <p>Reviewer: ${r.getReviewer().getFirstName()} ${r.getReviewer().getLastName()}</p>
                    <p>Rating: ${r.getRating()}</p>
                    <p>Comments: ${r.getReviewerComments()}</p>
                    <hr />
                </#list>
            </#if>
        <#else>
            <p style="text-align:center">Please s1elect a valid paper to rate</p>
        </#if>
    </div>

</div>
</body>
</html>
