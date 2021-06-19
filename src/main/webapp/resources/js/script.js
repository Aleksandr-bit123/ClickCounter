function mode() {
    $.ajax({
        url: '/content',
        success: function(data) {
            $('#counter').html(data);
        },
        error: function(){
            $('#counter').html("error");
        }
    });
}
setInterval(mode, 1000);
