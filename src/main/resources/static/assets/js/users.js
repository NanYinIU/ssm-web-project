$(document).ready(function () {
    $(".tip-top").tooltip({placement: 'top'});
    $(".tip-right").tooltip({placement: 'right'});
    $(".tip-bottom").tooltip({placement: 'bottom'});
    $(".tip-left").tooltip({placement: 'left'});
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

        }
        // queryParams: function(params){
        //     var queryData = {};    //如果没有额外的查询参数的话就新建一个空对象，如果有的话就先装你的查询参数
        //     //然后增加这两个
        //     queryData.limit = params.limit;
        //     queryData.offset = params.offset;
        //     return queryData;    //这个就是向服务端传递的参数对象
        // }
    });

});

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
    result += "<a class='btn btn-primary btn-sm tip-top' id='detail' href='#' role='button' data-toggle='modal' onclick='checkModal("+row.id+")' title='查看' >" +
        "<i class='fas fa-search'></i></a> &nbsp;";
    result += "<a class='btn btn-primary btn-sm tip-top' id='modify' href='#' role='button'  title='修改' data-toggle='modal' onclick='showModal(" + row.id + ")' data-url='/user/user/'+row.id  >" +
        "<i class='fas fa-edit'></i></a> &nbsp;";
    result += "<a class='btn btn-primary btn-sm tip-top' id='delete' href='#' role='button'  data-toggle='modal' onclick='warnModal()' title='删除' ><i" +
        " class='fas fa-trash'></i></a> &nbsp;";
    return result;
}

var checkModal = function (id) {
    var url = "/user/user/" + id;
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function (res) {
            var data = res.rows;
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

var showModal = function (id) {
    var url = "/user/user/" + id;
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function (res) {
            var data = res.rows;
            // 修改菜单
            $('#sex').selectpicker("val", data.sex.name);
            $('#status').selectpicker("val", data.status.name);
            var authStr = new Array() ;
            for (var i = 0; i < data.auths.length; i++) {
                authStr[i] = data.auths[i].name;
            }
            $('#auth').selectpicker("val", authStr);
            $("#userId").val(data.id);
            $('#addOrModifyModal #name').val(data.name);
            $('#addOrModifyModal #email').val(data.email);
            $('#addOrModifyModal').modal('show');
        },
        error: function (request, status, error) {
            console.log("ajax call went wrong:" + request.responseText);
        }
    });
}

var warnModal = function(){
    $("#warnModal").modal('show')
}

$('#addOrModifyModal').on('hidden.bs.modal', function (e) {
    // do something...
   console.log(".....")
})

var addOrModifySave = function(){
    var id = $("#userId").val();
    var url = "/user/user/" + id;

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
        type: "put",
        url: url,
        dataType: 'json',
        data: data,
        success: function (res) {
            // var data = res.rows;
        },
        error: function (request, status, error) {
            console.log("ajax call went wrong:" + request.responseText);
        }
    });
    $("#addOrModifyModal").modal("hide")
}
