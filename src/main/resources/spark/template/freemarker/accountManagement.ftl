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

    <h1>SAM 2018 Home Page</h1>

    <div class="navigation">
        <#if username??>
            <a href="/managePapers">Manage Papers</a> |
            <#if userType == "Admin">
                <a href="/manageAccounts">Manage Accounts</a> |
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
            <div id="message" class="${messageType}">${message}</div>
        <#else>
            <div id="message" class="info" style="display:none">

            </div>
        </#if>
    </div>

    <#if requestedPermissions?size != 0>
        <div>
            <p>Users that have asked for Elevation:</p>
            <#list requestedPermissions as prd>
                <p>${prd.getUser().getFullName()} is requesting to be a ${prd.getPermissionLevel()}</p>
                <button onclick="approveUser('${prd.getUser().getUsername()}')">Approve</button>
                <button onclick="denyUser('${prd.getUser().getUsername()}')">Deny</button>
                <hr />
            </#list>
        </div>
    </#if>

    <#if users?size != 0>
        <div>
            <p>Delete Users:</p>
            <#list users as u>
                <button onclick="deleteUser('${u.getUsername()}')">${u.getFullName()}</button>
            </#list>
        </div>
    </#if>

</div>
</body>
</html>
