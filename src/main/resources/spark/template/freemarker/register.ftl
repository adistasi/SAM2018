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
            <a href="/submitPaper">Submit a Paper</a> |
            <a href="/requestPaper">Request Reviews</a> |
            <a href="/logout">Logout</a> |
        <#else>
            <a href="/home">Home</a> |
            <a href="/login">Login</a>
        </#if>
    </div>

    <#if message??>
        <div id="message" class="${messageType}">"${message}"</div>
    </#if>

    <form class="inputForm" method="POST" action="/register">
        <div class="form-group">
            <label for="name">Username</label>
            <input type="text" class="form-control" id="name" name="username" placeholder="Enter a user name " required><br />

            <label for="fName">First Name</label>
            <input type="text" class="form-control" id="fName" name="fName" placeholder="Enter your First Name" required><br />

            <label for="lName">Last Name</label>
            <input type="text" class="form-control" id="lName" name="lName" placeholder="Enter your Last Name" required><br />

            <label>Request Role</label>
            <input type="radio" name="usertype" value="PCM">PCM<br>
            <input type="radio" name="usertype" value="PCC">PCC<br>

            <label for="Password">Password</label>
            <input type="password" lass="form-control" id="password" name="password" placeholder="Enter your password" required><br />
            <label for="validPassword">Verify Password</label>
            <input type="password" class="form-control" id="validPassword" name="validPassword" placeholder="Re-enter password" required><br />

        <button type="submit" class="btn btn-default"> Register</button>
    </form>

</div>
</body>
</html>
