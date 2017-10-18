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
    <#if message??>
        <div id="message" class="${messageType}">"${message}"</div>
    </#if>


    <form class="form-register" method="POST" action="/login">
        <div class="form-group">
            <label for="name">User_Name</label>
            <input type="text"
                   class="form-group"
                   id="name"
                   name="username"
                   placeholder="Enter a user name " required>

            <label for="Password">Password</label>
            <input type="password"
                   class="form-control"
                   id="password"
                   name="password"
                   placeholder="Enter your password" required>

            <button type="submit" class="btn btn-default"> Login</button>
    </form>

</div>
</body>
</html>
