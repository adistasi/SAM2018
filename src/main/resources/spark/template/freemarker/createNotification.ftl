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
        <a href="/managePapers">Manage Papers</a> |
        <#if userType == "Admin">
            <a href="/accountManagement">Manage Accounts</a> |
        </#if>
        <a href ="/viewNotifications">Notifications<#if notificationCount != 0> (<span style="color:red">${notificationCount}</span>)</#if></a> |
        <a href="/logout">Logout</a>
    <#else>
        <a href="/login">Login</a> |
        <a href="/register">Register</a>
    </#if>
    </div>

    <div class="body">
        <#if message??>
            <div id="message" class="${messageType}">"${message}"</div>
        </#if>
        <p>Create a Notification</p>

        <form method="POST" action="/createNotification" id="createNotification" class="inputForm">
            <label>Recipient:</label>
            <select name="recipient">
                <#list users as u>
                    <option value="${u.getUsername()}">${u.getFirstName()} ${u.getLastName()}</option>
                </#list>
            </select>

            <br />
            <br />
            <label>Message:</label>
            <textarea name="message"></textarea>

            <br />
            <br />

            <button type="submit">Create</button>
        </form>
    </div>

</div>
</body>
</html>
