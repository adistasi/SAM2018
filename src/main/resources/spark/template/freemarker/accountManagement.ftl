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

    <h1>SAM 2018 - Manage Accounts</h1>

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
            <div id="message" class="${messageType}">${message}</div>
        <#else>
            <div id="message" class="info" style="display:none">

            </div>
        </#if>
    </div>

    <#if requestedPermissions?size != 0>
        <div class="subdiv">
            <h4 class="subheader">Users that have asked for Elevation:</h4>
            <hr />
            <#list requestedPermissions as prd>
                <p>${prd.getUser().getFullName()} is requesting to be a ${prd.getPermissionLevel()}</p>
                <div class="permButton">
                    <button class="btn-good" onclick="approveUser('${prd.getUser().getUsername()}')">Approve</button>
                    <button class="btn-alert" onclick="denyUser('${prd.getUser().getUsername()}')">Deny</button>
                    <hr />
                </div>
            </#list>
        </div>
    </#if>

    <#if users?size != 0>
        <div class="subdiv" style="margin-top:50px";>
            <h4 class="subheader">Delete Users:</h4>
            <hr class="spacer"/>
            <ul id="deleteAccounts">
                <#list users as u>
                    <li class="deleteLI">
                        ${u.getFullName()}
                        <button class="delUser" onclick="deleteUser('${u.getUsername()}')">Delete</button>
                    </li>
                </#list>
            </ul>
        </div>
    </#if>

</div>
</body>
</html>
