$(document).ready(function () {
    // select2 使用bootstrap css样式
    $('select').select2({
        theme: 'bootstrap4',
    }).change(function(){
        $("#add").valid();
    });
    // 设置table属性
    $('#table').bootstrapTable({
        url: '/user/users',
        method: 'get',
        cache: false,                        //是否使用缓存
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        showToggle: true,                    //是否显示详细视图和列表视图
        pagination: true,                    //启用分页
        pageNumber: 1,                        //初始化加载第一页，默认第一页
        pageSize: 10,                        //每页的记录行数
        pageList: [10, 20, 50],                //可供选择的每页行数
        sidePagination: "server",
        sortStable: true,
        // 开启search按钮 去掉字符串前后空格
        showSearchButton: true,
        showSearchClearButton: true,
        trimOnSearch: true,
        // checkboxHeader: true,
        search: true,
        // dataButtonsToolbar:".buttons-toolbar",
        columns: [
            {
                checkbox:true
            },
            {
                title: 'id',
                align: 'center',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                field: 'name',
                title: 'Item Name',
            }, {
                field: 'email',
                title: 'email',
            }, {
                field: 'age',
                title: 'age',
            }, {
                field: 'sex.name',
                title: 'sex',
            }, {
                field: 'status.name',
                title: 'status',
            }, {
                field: 'auths',
                title: 'auth',
                formatter: standardArrayFormatter
            }, {
                field: 'roles.name',
                title: 'roles',
                formatter: standardArrayFormatter
            }, {
                field: 'operate',
                title: '操作',
                width: 180,
                align: 'center',
                valign: 'middle',
                formatter: actionFormatter
            },],
        onDblClickRow: function (row, $element) {
            var id = row.ID;
            // EditViewById(id, 'view');

        },
        responseHandler: responseHandler
        // queryParams: function(params){
        //     var queryData = {};    //如果没有额外的查询参数的话就新建一个空对象，如果有的话就先装你的查询参数
        //     //然后增加这两个
        //     queryData.limit = params.limit;
        //     queryData.offset = params.offset;
        //     return queryData;    //这个就是向服务端传递的参数对象
        // }
    });
});

// 重置密码
var refreshPassword = function () {
    var rows = $("#table").bootstrapTable('getSelections');
    rows.forEach(function (currentValue,index,arr) {
        var id = currentValue.id
        resetPass(id);
    })

}

// boostrap-table自定义数据格式
var responseHandler = function (rec) {
    // rec = JSON.parse(rec);
    // console.log(rec);
    return {
        "total": rec.total,//总页数
        "rows": rec.data   //数据
    };
}
// 多选的table中的显示 以逗号隔开
function standardArrayFormatter(value, row, index) {
    var _return = "";
    if (value != null || value != undefined) {
        for (var i = 0; i < value.length; i++) {
            _return = _return + value[i].name + ",";
        }
        if (_return != null || _return != undefined) {
            _return = _return.substr(0, _return.length - 1);
        }
    }
    return _return;
}
// table中的行里的按钮
function actionFormatter(value, row, index) {
    var id = value;
    var result = "";
    result += "<a class='btn btn-primary btn-sm tip-top' id='detail' href='#' role='button' data-toggle='modal' onclick='openDetailPage(" + row.id + ")' title='查看' >" +
        "<i class='fas fa-search'></i></a> &nbsp;";
    result += "<a class='btn btn-primary btn-sm tip-top' id='modify' href='#' role='button'  title='修改' data-toggle='modal' onclick='showModifyModal(" + row.id + ")' data-url='/user/user/'+row.id  >" +
        "<i class='fas fa-edit'></i></a> &nbsp;";
    result += "<a class='btn btn-primary btn-sm tip-top' id='delete' href='#' role='button'  data-toggle='modal' onclick='warnModal(" + row.id + ")' title='删除' ><i" +
        " class='fas fa-trash'></i></a> &nbsp;";
    return result;
}

function showAddModal() {
    $('#addModal').modal('show');
}

// 打开修改模态框
var showModifyModal = function (id) {
    //获得选中的行数据
    $('#modifyModal').modal('show');
}

// 打开删除模态框
var warnModal = function (id) {
    $("#userId").val(id);
    deleteUser();
}


// 【添加】动作
var addUser = function () {
    var flag = $("#add").valid();
    if (!flag) {
        //没有通过验证
        return;
    }
    var type = "POST";
    var url = "/user/user/";
    // 数据
    var t = $('#add').serializeObject();
    $.ajax({
        type: type,
        url: url,
        dataType: 'json',
        contentType:'application/json',
        data: JSON.stringify(t),
        success: function (res) {
            var data = JSON.parse(res);
            if (data.code === 0) {
                $("#addModal").modal("hide");
                swal({
                    title: "Success!",
                    text: "添加成功！Added successfully!",
                    type: "success",
                    confirmButtonClass: "btn-primary",
                    confirmButtonText: "确认",
                }, function () {
                    $("#table").bootstrapTable('refresh');
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

// 删除
var deleteUser = function () {
    swal({
        title: "是否删除用户？",
        text: "Whether to delete the user ？",
        type: "warning",
        showCancelButton: true,
        cancelButtonText: '取消',
        closeOnConfirm: false,
        confirmButtonText: "确认",
        confirmButtonClass: "btn-danger",
        showLoaderOnConfirm: true
    }, function () {
        setTimeout(function () {
            var id = $("#userId").val();
            var url = "/user/user/" + id;
            $.ajax({
                type: "DELETE",
                url: url,
                dataType: 'json',
                success: function (e) {
                    var data = JSON.parse(e);
                    if (data.code === 0) {
                        swal({
                            title: "Success!",
                            text: data.msg,
                            type: "success",
                            confirmButtonClass: "btn-primary",
                            confirmButtonText: "确认",
                        }, function () {
                            $("#table").bootstrapTable('refresh');
                        })
                    }else if(data.code === 1){
                        swal(data.msg, "Deleted failed, please try again!");
                    } else {
                        swal("删除失败，请重试!", "Deleted failed, please try again!");
                    }
                },
                error: function (request, status, error) {
                    swal("请求出错，请重试!", "Request error, please try again!");
                }
            })
        }, 2000);
    });
}

var modifyUser = function () {
    var flag = $("#modify").valid();
    if (!flag) {
        //没有通过验证
        return;
    }
    var type = "put";
    var url = "/user/user/";
    // 数据
    var t = $('#modify').serializeObject();
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
                    $("#table").bootstrapTable('refresh');
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

function resetPass(ids) {
    // 重置密码操作
    $.ajax({
        type: 'POST',
        url: '/user/user/'+ids+"/password",
        dataType: 'json',
        contentType:'application/json',
        // data: JSON.stringify(t),
        success: function (res) {
            var data = JSON.parse(res);
            if (data.code === 0) {
                swal({
                    title: "Success!",
                    text: "修改成功！Modifyed successfully!",
                    type: "success",
                    confirmButtonClass: "btn-primary",
                    confirmButtonText: "确认",
                }, function () {
                    $("#table").bootstrapTable('refresh');
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

var openDetailPage = function (id) {
    window.location.href="/user/user/"+id;
}


