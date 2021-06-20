function mode() {
    $.ajax({
        url: '/content',
        success: function(data) {
            $('#counter').html(data);
        },
        error: function(){
            $('#counter').html("connection<br>error");
        }
    });
}
setInterval(mode, 1000);
