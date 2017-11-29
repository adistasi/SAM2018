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