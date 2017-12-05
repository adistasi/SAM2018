<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>SAM 2018 Login Page</h1>
    <div class="navigation">
            <a href="/">Home</a> |
            <a href="/register">Register</a>
    </div>

    <#if message??>
        <div id="message" class="${messageType}">"${message}"</div>
    </#if>


    <form class="inputForm" method="POST" action="/login">
        <div class="form-group">
            <div id="username">
                <label for="name">Username</label>
                <input type="text" class="form-control" id="name" name="username" placeholder="Enter a user name" required><br />
            </div>

            <div id="password">
                <label for="Password">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Enter your password" required><br />
            </div>

            <button type="submit" class="btn btn-good">Login</button>
    </form>

</div>
</body>
</html>
