// $(document).ready(function () {
//     $('select').select2({
//         theme: 'bootstrap4',
//     })
// })

function showModifyModal(id) {
    $('#modifyModal').modal('show');
}

function saveUser(id) {
    var flag = $("#add").valid();
    if (!flag) {
        //没有通过验证
        return;
    }
    var type = "PUT";
    var url = "/user/user/"+id;
    // 数据
    var t = $('#modify').serialize();

    $.ajax({
        type: type,
        url: url,
        dataType: 'json',
        contentType:'application/json',
        data: JSON.stringify(t),
        success: function (res) {
            var data = JSON.parse(res);
            if (data.code === 0) {
                $("#modifyModal").modal("hide");
                    swal({
                        title: "Success!",
                        text: "修改成功！Modifyed successfully!",
                        type: "success",
                        confirmButtonClass: "btn-primary",
                        confirmButtonText: "确认",
                    }, function () {

                    })
            } else {
                swal("保存失败，请重试!", "Save failed, please try again!");
            }
        },
        error: function (request, status, error) {
            swal("请求出错，请重试!", "Request error, please try again!");
        }
    });
}
