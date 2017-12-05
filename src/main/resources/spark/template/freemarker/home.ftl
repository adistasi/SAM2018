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
        <#if username??>
            <p>Welcome to the submission management system for the Software Architecture Mining Workshop.</p><br />

            <p>If you are looking to submit, rate, or review Paper submissions, select "Manage Papers" to see more paper related controls.  <#if userType == "Admin">Administrative Users are able to access controls to manage System Deadlines and User Accounts from this page.</#if></p>
        </#if>
    </div>
  </div>
</body>
</html>
