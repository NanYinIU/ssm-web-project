
$(document).ready(function () {
    $('#table').bootstrapTable({
        url: '/user/users',
        method:'get',
        cache: false,                        //是否使用缓存
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        showToggle:true,                    //是否显示详细视图和列表视图
        pagination: true,                    //启用分页
        pageNumber: 1,                        //初始化加载第一页，默认第一页
        pageSize: 10,                        //每页的记录行数
        pageList: [10,20,50],                //可供选择的每页行数
        sidePagination: "server",
        // dataButtonsToolbar:".buttons-toolbar",
        columns: [{
            checkbox:true,
            visible: true
        },{
            field: 'id',
            title: 'Item ID'
        }, {
            field: 'name',
            title: 'Item Name',
        }, {
            field:'operate',
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
    $('[data-toggle="tooltip"]').tooltip()

});

// $(function () {
//
// })

function actionFormatter(value, row, index) {
    var id = value;
    var result = "";
    result += "<a class='btn btn-primary btn-sm' href='#' role='button' data-toggle='tooltip' title='Disabled tooltip' data-placement='top' ><i class='fas fa-search'></i></a> &nbsp;";
    result += "<a class='btn btn-primary btn-sm' href='#' role='button'>修改</a> &nbsp;";
    result += "<a class='btn btn-primary btn-sm' href='#' role='button'>删除</a>";
    //
    // result += "<a href='javascript:;' class='btn btn-secondary' onclick=\"EditViewById('" + id + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
    // result += "<a href='javascript:;' class='btn btn-success' onclick=\"DeleteByIds('" + id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";

    return result;
}