<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | SAM 2018</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>SAM 2018 Paper Management</h1>

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
            <div class="subdiv">
                <h4 class="subheader">Manage System Deadlines:</h4>
                <hr class="spacer">
                <p>Set Paper Submission Deadline:</p>
                <#if submissionDeadlineDate??>
                    <form method="POST" action="/createDeadline" id="paperDeadline" class="inputForm">
                        <input type="hidden" name="title" value="Submission Deadline" />
                        <input id="submissionDate" name="date" type="date" value="${submissionDeadlineDate}"/>
                        <input id="submissionTime" name="time" type="time" value="${submissionDeadlineTime}"/>
                        <button type="submit" class="btn-good">Update Deadline</button>
                    </form>
                <#else>
                    <form method="POST" action="/createDeadline" id="paperDeadline" class="inputForm">
                        <input type="hidden" name="title" value="Submission Deadline" />
                        <input id="submissionDate" name="date" type="date" />
                        <input id="submissionTime" name="time" type="time" />
                        <button type="submit" class="btn-good">Set Deadline</button>
                    </form>
                </#if>

                <p>Set Review Request Deadline:</p>
                <#if requestDeadlineDate??>
                    <form method="POST" action="/createDeadline" id="requestDeadline" class="inputForm">
                        <input type="hidden" name="title" value="Request Deadline" />
                        <input id="requestDate" name="date" type="date" value="${requestDeadlineDate}"/>
                        <input id="requestTime" name="time" type="time" value="${requestDeadlineTime}"/>
                        <button type="submit" class="btn-good">Update Deadline</button>
                    </form>
                <#else>
                    <form method="POST" action="/createDeadline" id="requestDeadline" class="inputForm">
                        <input type="hidden" name="title" value="Request Deadline" />
                        <input id="requestDate" name="date" type="date" />
                        <input id="requestTime" name="time" type="time" />
                        <button type="submit" class="btn-good">Set Deadline</button>
                    </form>
                </#if>

                <p>Set Review Submission Deadline:</p>
                <#if reviewDeadlineDate??>
                    <form method="POST" action="/createDeadline" id="reviewDeadline" class="inputForm">
                        <input type="hidden" name="title" value="Review Deadline" />
                        <input id="reviewDate" name="date" type="date" value="${reviewDeadlineDate}"/>
                        <input id="reviewTime" name="time" type="time" value="${reviewDeadlineTime}"/>
                        <button type="submit" class="btn-good">Update Deadline</button>
                    </form>
                <#else>
                    <form method="POST" action="/createDeadline" id="reviewDeadline" class="inputForm">
                        <input type="hidden" name="title" value="Review Deadline" />
                        <input id="reviewDate" name="date" type="date" />
                        <input id="reviewTime" name="time" type="time" />
                        <button type="submit" class="btn-good">Set Deadline</button>
                    </form>
                </#if>

                <p>Set Paper Rating Deadline:</p>
                <#if ratingDeadlineDate??>
                    <form method="POST" action="/createDeadline" id="ratingDeadline" class="inputForm">
                        <input type="hidden" name="title" value="Rating Deadline" />
                        <input id="ratingDate" name="date" type="date" value="${ratingDeadlineDate}"/>
                        <input id="ratingTime" name="time" type="time" value="${ratingDeadlineTime}"/>
                        <button type="submit" class="btn-good">Update Deadline</button>
                    </form>
                <#else>
                    <form method="POST" action="/createDeadline" id="ratingDeadline" class="inputForm">
                        <input type="hidden" name="title" value="Rating Deadline" />
                        <input id="ratingDate" name="date" type="date" />
                        <input id="ratingTime" name="time" type="time" />
                        <button type="submit" class="btn-good">Set Deadline</button>
                    </form>
                </#if>
            </div>
        </#if>
    </div>
  </div>
</body>
</html>
