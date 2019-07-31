jQuery.validator.setDefaults({
    debug: true,
    success: "valid"
});

$(document).ready(function () {
    // validate
    $("#addOrModify").validate({
        errorClass: "invalid",
        errorElement: "em",
        // wrapper: "li",
        rules: {
            name: "required",
            email: {
                required: true,
                email: true
            },

        },
        messages: {
            name: "Please specify your name",
            email: {
                required: "We need your email address to contact you",
                email: "Your email address must be in the format of name@domain.com"
            },
        }
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
        sortStable:true,
        // 开启search按钮 去掉字符串前后空格
        showSearchButton:true,
        showSearchClearButton:true,
        trimOnSearch: true,
        search:true,
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

    //每次清空表单信息，因为是修改添加公用的表单
    $('#addOrModifyModal').on('hidden.bs.modal', function (e) {
        $("#userId").val("");
        // 使用bootstrap-select 插件
        $('#sex').selectpicker("val", "");
        $('#status').selectpicker("val", "");
        $('#auth').selectpicker("val", "");
        $('#name').val("");
        $('#email').val("");
    })

});

// boostrap-table自定义数据格式
var responseHandler  = function (rec) {
    rec = JSON.parse(rec);
    console.log(rec);
    return {
        "total": rec.data.total,//总页数
        "rows": rec.data.rows   //数据
    };
}

function standardArrayFormatter(value, row, index) {
    var _return="";
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
    result += "<a class='btn btn-primary btn-sm tip-top' id='detail' href='#' role='button' data-toggle='modal' onclick='showCheckModal("+row.id+")' title='查看' >" +
        "<i class='fas fa-search'></i></a> &nbsp;";
    result += "<a class='btn btn-primary btn-sm tip-top' id='modify' href='#' role='button'  title='修改' data-toggle='modal' onclick='showModifyModal(" + row.id + ")' data-url='/user/user/'+row.id  >" +
        "<i class='fas fa-edit'></i></a> &nbsp;";
    result += "<a class='btn btn-primary btn-sm tip-top' id='delete' href='#' role='button'  data-toggle='modal' onclick='warnModal("+row.id+")' title='删除' ><i" +
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
            var authArr = new Array() ;
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

function showAddOrModifyModal(){
    $('#addOrModifyModal').modal('show');
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
            // 修改菜单
            $('#sex').selectpicker("val", data.sex?data.sex.name:"");
            $('#status').selectpicker("val", data.status?data.status.name:"");
            var authStr = new Array() ;
            for (var i = 0; i < data.auths.length; i++) {
                authStr[i] = data.auths[i].name;
            }
            $('#auth').selectpicker("val", authStr);
            $("#userId").val(data.id);
            $('#name').val(data.name);
            $('#email').val(data.email);
            showAddOrModifyModal();
        },
        error: function (request, status, error) {
            console.log("ajax call went wrong:" + request.responseText);
        }
    });
}

// 打开删除模态框
var warnModal = function(id){
    $("#userId").val(id);
    deleteUser();
}

// 【修改/添加】动作
var modifySave = function(){
    var flag = $("#addOrModify").valid();
    if(!flag){
        //没有通过验证
        return;
    }

    var id = $("#userId").val();
    var type = "POST";
    var url = "/user/user/";
    var isAddAction = true;
    if(id !== ""){
        isAddAction = false;
        //如果id不为空，说明时修改
        type="PUT";
        url = "/user/user/" + id;
    }
    // 数据
    var name = $("#name").val();
    var email = $("#email").val();
    var sex = $("#sex").find("option:selected").attr('id');
    var status = $("#status").find("option:selected").attr('id');
    // var auth = $("#auth").find("option:selected").attr('id');
    var auth = new Array();
    for (var i = 0;i<$("#auth").find("option:selected").length;i++){
        auth[i]=$("#auth").find("option:selected")[i].id;
    }
    var data = {'name':name,'email':email,'sex':sex,'status':status,'auth':auth};
    $.ajax({
        type: type,
        url: url,
        dataType: 'json',
        data: data,
        success: function (res) {
            var data = JSON.parse(res);
            if(data.code === 0){
                $("#addOrModifyModal").modal("hide");
                if(isAddAction){
                    swal({
                        title:"Success!",
                        text:"添加成功！Added successfully!",
                        type: "success",
                        confirmButtonClass: "btn-primary",
                        confirmButtonText: "确认",
                    },function(){
                        $("#table").bootstrapTable('refresh');
                    })
                }else{
                    swal({
                        title:"Success!",
                        text:"修改成功！Modifyed successfully!",
                        type: "success",
                        confirmButtonClass: "btn-primary",
                        confirmButtonText: "确认",
                    },function(){
                        $("#table").bootstrapTable('refresh');
                    })
                }
            }else{
                swal("保存失败，请重试!","Save failed, please try again!");
            }
        },
        error: function (request, status, error) {
            swal("请求出错，请重试!","Request error, please try again!");
        }
    });
}

// 添加动作
var showAddModal = function(){
    showAddOrModifyModal();
}

var deleteUser = function () {
    swal({
        title: "是否删除用户？",
        text: "Whether to delete the user ？",
        type: "warning",
        showCancelButton: true,
        cancelButtonText:'取消',
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
                success:function (e) {
                    var data = JSON.parse(e);
                    if(data.code === 0){
                        swal({
                            title:"Success!",
                            text:"删除成功！Deleted successfully!",
                            type: "success",
                            confirmButtonClass: "btn-primary",
                            confirmButtonText: "确认",
                        },function(){
                            $("#table").bootstrapTable('refresh');
                        })
                    }else{
                        swal("删除失败，请重试!","Deleted failed, please try again!");
                    }
                },
                error: function (request, status, error) {
                    swal("请求出错，请重试!","Request error, please try again!");
                }
            })
        }, 2000);
    });
}

var showAlter = function () {
    swal({
        title:"Success!",
        text:"添加成功！Added successfully!",
        confirmButtonClass: "btn-primary",
        confirmButtonText: "确认！confirm!",
    },function(){
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    })
}

