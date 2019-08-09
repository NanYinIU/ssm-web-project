$(document).ready(function () {
    // select
    $('select').select2({
        theme: 'bootstrap4',
    }).change(function(){
        $("#add").valid();
    });
    // validate
    $("#add").validate({
        errorClass: "is-invalid",
        success: "valid",
        ignore: "",
        validClass: "is-valid",
        errorElement: 'em',
        errorPlacement: function(error, element) {
            var elem = $(element);
            if (elem.hasClass("select2-hidden-accessible")) {
                element = elem.next();
                error.insertAfter(element);
            } else {
                error.insertAfter(element);
            }
        },
        rules: {
            name: "required",
            email: {
                required: true,
                email: true
            },
            auths: "required",
            status: "required",
            sex: "required"
        },
        messages: {
            name: "名称必填",
            email: {
                required: "邮箱字段必填",
                email: "Your email address must be in the format of name@domain.com"
            },
            auths: {
                required: "字段必填",
            },
            status: {
                required: "字段必填",
            },
            sex: {
                required: "字段必填",
            }
        },
    });

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
        search: true,
        // dataButtonsToolbar:".buttons-toolbar",
        columns: [
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

// boostrap-table自定义数据格式
var responseHandler = function (rec) {
    rec = JSON.parse(rec);
    console.log(rec);
    return {
        "total": rec.data.total,//总页数
        "rows": rec.data.rows   //数据
    };
}

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

function actionFormatter(value, row, index) {
    var id = value;
    var result = "";
    result += "<a class='btn btn-primary btn-sm tip-top' id='detail' href='#' role='button' data-toggle='modal' onclick='showCheckModal(" + row.id + ")' title='查看' >" +
        "<i class='fas fa-search'></i></a> &nbsp;";
    result += "<a class='btn btn-primary btn-sm tip-top' id='modify' href='#' role='button'  title='修改' data-toggle='modal' onclick='showModifyModal(" + row.id + ")' data-url='/user/user/'+row.id  >" +
        "<i class='fas fa-edit'></i></a> &nbsp;";
    result += "<a class='btn btn-primary btn-sm tip-top' id='delete' href='#' role='button'  data-toggle='modal' onclick='warnModal(" + row.id + ")' title='删除' ><i" +
        " class='fas fa-trash'></i></a> &nbsp;";
    return result;
}

// 打开查看模态框
var showCheckModal = function (id) {
    var url = "/user/user/" + id;
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function (res) {
            var data = JSON.parse(res).data;
            var authArr = new Array();
            for (var i = 0; i < data.auths.length; i++) {
                authArr[i] = data.auths[i].name;
            }
            authStr = authArr.join(",");
            // 查看页面
            $("#check-name").html(data.name);
            $("#check-email").html(data.email);
            $("#check-sex").html(data.sex.name);
            $("#check-status").html(data.status.name);
            $("#check-auth").html(authStr);
            $("#checkModal").modal('show')

        },
        error: function (request, status, error) {
            console.log("ajax call went wrong:" + request.responseText);
        }
    });
}

function showAddModal() {
    $('#addModal').modal('show');
}

// 打开修改模态框
var showModifyModal = function (id) {
    var url = "/user/user/" + id;
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function (res) {
            res = JSON.parse(res);
            var data = res.data;
            // 修改菜单 先赋值
            $("#modify-sex").select2("val", [data.sex.name]);
            $("#modify-status").select2("val", [data.status.name]);
            var authStr = new Array();
            for (var i = 0; i < data.auths.length; i++) {
                authStr[i] = data.auths[i].name;
            }
            $('#modify-auth').select2("val", authStr);
            $("#userId").val(data.id);
            $('#modify-name').val(data.name);
            $('#modify-email').val(data.email);
            $("#modifyModal").modal('show');
        },
        error: function (request, status, error) {
            console.log("ajax call went wrong:" + request.responseText);
        }
    });
}

// 打开删除模态框
var warnModal = function (id) {
    $("#userId").val(id);
    deleteUser();
}

$.fn.serializeObject = function()   {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        var value = this.value;
        var paths = this.name.split(".");
        var len = paths.length;
        var obj = o;
        $.each(paths,function(i,e){
            if(i == len-1){
                if (obj[e]) {
                    if (!obj[e].push) {
                        obj[e] = [obj[e]];
                    }
                    obj[e].push(value || '');
                } else {
                    obj[e] = value || '';
                }
            }else{
                if(!obj[e]){
                    obj[e] = {};
                }
            }
            obj = o[e];
        });
    });
    return o;
}

// 【修改】动作
var addUser = function () {
    var flag = $("#add").valid();
    if (!flag) {
        //没有通过验证
        return;
    }
    var type = "POST";
    var url = "/user/user/";
    var isAddAction = true;
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
                if (isAddAction) {
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
                    swal({
                        title: "Success!",
                        text: "修改成功！Modifyed successfully!",
                        type: "success",
                        confirmButtonClass: "btn-primary",
                        confirmButtonText: "确认",
                    }, function () {
                        $("#table").bootstrapTable('refresh');
                    })
                }
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
                            text: "删除成功！Deleted successfully!",
                            type: "success",
                            confirmButtonClass: "btn-primary",
                            confirmButtonText: "确认",
                        }, function () {
                            $("#table").bootstrapTable('refresh');
                        })
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



