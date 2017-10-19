<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>SAM 2018 Home Page</h1>

    <div class="navigation">
        <a href="/">my home</a>
    </div>

    <div class="body">
        <p>Welcome to the world of SAM 2018. </br>
        ${title}
        </p>
    </div>


    <form action="/reviewManagement" method="POST">
    <#if paperForReview??>
    <#list paperForReview as paper>
      <input type="checkbox" name="requestedPaper" value=${paper.getPaperID()}> ${paper.getTitle()}<br>
      </#list>
    <input type="submit" value="Submit Request">
      </#if>
    </form>

</div>
</body>
</html>
