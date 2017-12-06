<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>SAM 2018 - Create Notification</h1>

    <div class="navigation">
    <#if username??>
        <a href="/managePapers">Manage Papers</a> |
        <#if userType == "Admin">
            <a href="/manageAccounts">Manage Accounts</a> |
            <a href="/manageDeadlines">Manage Deadlines</a> |
        </#if>
        <#if userType == "Admin" || userType =="PCC">
            <a href="/createNotification">Create Notification</a> |
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
        <h4 class="subheader">Create a Notification</h4>
        <hr class="spacer" />
        <form method="POST" action="/createNotification" id="createNotification" class="inputForm">
            <br />
            <label>Recipient:</label>
            <select name="recipient">
                <#list users as u>
                    <option value="${u.getUsername()}">${u.getFirstName()} ${u.getLastName()}</option>
                </#list>
            </select>

            <br />
            <br />
            <label style="vertical-align:top">Message:</label>
            <textarea rows="5" cols="100" name="message"></textarea>

            <br />
            <br />

            <div style="padding-left: 105px"><button type="submit" class="btn-good">Create</button></div>
        </form>
    </div>

</div>
</body>
</html>
