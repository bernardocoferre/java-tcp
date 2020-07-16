$(document).ready(function () {

    $('#delete-all').click(function () {
        $.ajax({
            url: "/api/messages",
            type: "DELETE",
            contentType: "application/json"
        }).done(function (data) {
            location.reload(true);
        });
    });

});