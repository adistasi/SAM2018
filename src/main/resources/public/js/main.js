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