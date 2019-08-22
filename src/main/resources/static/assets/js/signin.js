function translates(lang) {
    console.log("test");
    $.ajax({
        type: 'post',
        url: '/lang',
        dataType: 'json',
        data: {'lang':lang},
        success: function (res) {
            // 成功后刷新页面
            window.location.reload();
        },
        error: function (request, status, error) {

        }
    });
}