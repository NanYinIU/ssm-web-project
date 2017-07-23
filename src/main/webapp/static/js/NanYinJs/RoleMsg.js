$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_roles').bootstrapTable({
            url: '/role/roleTable',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
             dataType: 'json',
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
            queryParamsType:'',                 //// 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
              search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            // strictSearch: true,
            // showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            // height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            // uniqueId: "",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,
            locale:'zh-CN',                      //是否显示父子表
            paginationLoop:true,                 //分页条循环
            columns: [{
                checkbox: true
            }, {
                field: 'role_id',
                title: '#',
                align: 'center'
            }, {
                field: 'role_name',
                title: '角色名',
                align: 'center'
            }, {
                field: 'describe',
                title: '角色描述',
                align: 'center'
            },
            ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageNumber: params.pageNumber,
            pageSize: params.pageSize,
            name:$("#name").val()
            // statu: $("#txt_search_statu").val()
        };
        return temp;
    };
    return oTableInit;
};

var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};
    var postdata1 = {};
    oInit.Init = function () {
        //初始化页面上面的按钮事件
        $("#but_query").click(function () {
        $("#tb_roles").bootstrapTable('refresh',{url:'/role/roleTable'});
    });




        $("#but_delete").click(function () {
            var arrselections = $("#tb_roles").bootstrapTable('getSelections');
            // alert(JSON.stringify(arrselections));
               if (arrselections.length <= 0) {
                   toastr.warning('请选择有效数据');
                   return;
               }

                bootbox.confirm({
                title: "删除选中数据？",
                message: "确定删除用户信息？删除后不可恢复！.",
                buttons: {
                    cancel: {
                        label: '<i class="fa fa-times"></i> 取消'
                    },
                    confirm: {
                        label: '<i class="fa fa-check"></i> 确定'
                    }
                },
                callback: function (result) {
                    if (!result){

                    }else {

                        $.ajax({
                                       type: "post",
                                       url: "/role/delectRole",
                                       data:JSON.stringify(arrselections),
                                        dataType: "json",contentType:"application/json;UTF-8",
                                       success: function (data, status) {
                                           if (status == "success") {
                                               toastr.success('提交数据成功');
                                               $("#tb_roles").bootstrapTable('refresh');
                                           }
                                       },
                                       error: function () {
                                           toastr.error('Error');
                                       },
                                       complete: function () {

                                       }

                                   });
                    }

                }
            });


        });

        $("#but_edit").click(function () {
            var arrselections = $("#tb_roles").bootstrapTable('getSelections');

            if (arrselections.length > 1) {
                toastr.warning('只能选择一行进行编辑');

                return;
            }
            if (arrselections.length <= 0) {
                toastr.warning('请选择有效数据');

                return;
            }
            $("#myModalLabel").text("编辑");
            $("#txt_roleName").val(arrselections[0].role_name);
            $("#txt_describe").val(arrselections[0].describe);
            postdata.role_id = arrselections[0].role_id;
            $('#myModal').modal()

        })

        $("#r_submit").click(function () {

                postdata.role_name = $("#txt_roleName").val();
                postdata.describe= $("#txt_describe").val();
                // alert(JSON.parse(postdata[0]));
                 var role = [postdata];
                     // alert(JSON.stringify(role));
                $.ajax({
                    type: "post",
                    url: "/role/UpdateRole",
                    data:JSON.stringify(role),
                    dataType: "json",contentType:"application/json;UTF-8",
                    success: function (data, status) {
                        if (status == "success") {
                            toastr.success('提交数据成功');
                            $("#tb_roles").bootstrapTable('refresh');
                        }
                    },
                    error: function () {
                        toastr.error('Error');
                    },
                    complete: function () {

                    }
                })

        });




    };




    return oInit;
};