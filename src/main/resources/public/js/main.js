function addAuthor() {
    $("#authorsDiv").append("<input type='text' name='author' class='authorField' /><br />");
}

$(document).on('blur', "input.authorField", function() {
    updateAuthors();
});

function updateAuthors() {
    var authors = document.getElementsByClassName("authorField");

    var authNames = "";

    for(var i = 0; i < authors.length; i++) {
        authNames += authors[i].value;
        authNames += "/";
    }

    document.getElementById("authInput").value = authNames;
}

function addPCM(selectList) {
    var username = selectList.val();
    var name = selectList.find(":selected").text();
    var pid = selectList.parent().data("pid");
    var htmlString = '<input type="checkbox" name="requests" value="' + username + '|||' + pid + '" checked><label for="requests">' + name + '</label><br />';
    selectList.before(htmlString);
}

function rereviewPaper(paperID) {
    jQuery.post('/rereviewPaper', JSON.stringify(paperID), handleMessageResponse, 'json');
}

function markAsRead(notificationID) {
    jQuery.post("/markAsRead", JSON.stringify(notificationID), handleMessageResponse, 'json');
}

function deleteUser(username) {
    jQuery.post("/deleteUser", JSON.stringify(username), handleMessageResponse, 'json');
}

function approveUser(username) {
    jQuery.post("/approveUser", JSON.stringify(username), handleMessageResponse, 'json');
}

function denyUser(username) {
    jQuery.post("/denyUser", JSON.stringify(username), handleMessageResponse, 'json');
}

function handleMessageResponse(message, textStatus, jqXHR) {
    displayMessage(message);
}

function displayMessage(message) {
    $('#message').attr('class', message.type).html(message.text).slideDown(400);
}