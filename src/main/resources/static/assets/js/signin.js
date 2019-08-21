var translate = function (lang) {
    $.ajax({
        type: 'POST',
        url: '/lang',
        data: {'lang':lang},
        success: function (res) {
            // 成功后刷新页面
            window.location.reload();
        },
        error: function (request, status, error) {

        }
    });
}