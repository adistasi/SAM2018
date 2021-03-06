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

    <h1>SAM 2018 - Notifications</h1>

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
                <!-- keep here for Client-side messages -->
            </div>
        </#if>

        <#if notifications?size != 0>
            <div class="subdiv">
                <h4 class="subheader">Your Unread Notifications:</h4>
                <hr class="spacer">
                <#list notifications as n>
                    <div>
                        <p><#if n.getCreator()??>FROM: ${n.getCreator().getFirstName()} ${n.getCreator().getLastName()}<#else>SYSTEM GENERATED MESSAGE</#if></p>
                        <p>DATE: ${n.getDateGenerated()?string('MM/dd/yyyy HH:mm')}</p>
                        <p>MESSAGE: ${n.getMessage()}</p>
                        <div class="btnDiv"><button class='btn-good' onclick="markAsRead(${n.getID()})">Mark as Read</button></div>
                        <hr style="padding-left:40px;"/>
                    </div>
                </#list>
            </div>
        <#else>
            <p>You have no new Notifications</p>
        </#if>

        <#if readNotifications?size != 0>
            <div class="subdiv">
                <h4 class="subheader">Your Read Notifications:</h4>
                <hr class="spacer">
                <#list readNotifications as rn>
                    <div>
                        <p>FROM: <#if rn.getCreator()??>${rn.getCreator().getFirstName()} ${rn.getCreator().getLastName()}<#else>SYSTEM GENERATED MESSAGE</#if></p>
                        <p>DATE: ${rn.getDateGenerated()?string('MM/dd/yyyy HH:mm')}</p>
                        <p>MESSAGE: ${rn.getMessage()}</p>
                        <hr />
                    </div>
                </#list>
            </div>
        <#else>
            <p>You have no read Notifications</p>
        </#if>
    </div>

</div>
</body>
</html>
