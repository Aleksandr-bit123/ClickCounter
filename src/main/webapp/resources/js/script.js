function mode() {
    $.ajax({
        url: '/content',
        success: function(data) {
            $('#counter').html(data);
        }
    });
}
setInterval(mode, 1000);
