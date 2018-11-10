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
        $('#tb_departments').bootstrapTable({
            url: '/mes/selectByName',         //请求后台的URL（*）
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
                field: 'id',
                title: '#',
                align: 'center'
            }, {
                field: 'name',
                title: '用户名',
                align: 'center'
            }, {
                field: 'create_time',
                title: '创建时间',
                align: 'center'
            },
                {
                    field: 'age',
                    title: '年龄',
                    align: 'center'
                },
                {
                    field: 'sex',
                    title: '性别',
                    align: 'center'
                },
                {
                    field: 'role_describe',
                    title: '职位',
                    align: 'center'
                },
                {
                    field: 'organization_name',
                    title: '部门',
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
            ByName:$("#ByName").val()
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
        $("#btn_query").click(function () {
        $("#tb_departments").bootstrapTable('refresh',{url:'/mes/selectByName'});
    });


        $("#btn_add").click(function () {
           $("#myModalLabel").text("新增");
           $("#myModal").find(".form-control").val("");
           $('#myModal').modal()

        });









        $("#btn_delete").click(function () {
            var arrselections = $("#tb_departments").bootstrapTable('getSelections');
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
                                       url: "/mes/delectParam",
                                       data:JSON.stringify(arrselections),
                                        dataType: "json",contentType:"application/json;UTF-8",
                                       success: function (data, status) {
                                           if (status == "success") {
                                               toastr.success('提交数据成功');
                                               $("#tb_departments").bootstrapTable('refresh');
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
        $("#btn_submit").click(function () {

            var bootstrapValidator = $("#form123").data('bootstrapValidator');
            //手动触发验证1
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid()){
                postdata.id = '';
                postdata.name = $("#txt_name").val();
                postdata.password = $("#txt_pass").val();
                postdata.salt = "";
                postdata.roleId = '';
                postdata.age = $("#txt_age").val();
                postdata.sex = $("#txt_sex").val();
                postdata.create_time = '';
                postdata.organizationId = '';
                postdata.role_describe = $("#txt_role").val();
                postdata.organization_name = $("#txt_organ").val();
                // alert(JSON.parse(postdata[0]));
                 var voList = [postdata];
                    // alert(JSON.parse(voList));
                $.ajax({
                    type:"post",
                    url:"/mes/insertUserVo",
                    data:
                        JSON.stringify(voList),
                    dataType: "json",contentType:"application/json;UTF-8",
                    success:function (data,status) {
                        if(status=="success"){
                            toastr.success('提交数据成功');
                            $("#tb_departments").bootstrapTable('refresh');
                        }
                    },
                    error:function () {
                        toastr.error('发生错误 提交失败');
                    },
                    complete: function () {

                               }
                });
            }


        });


        $("#btn_edit").click(function () {
            var arrselections = $("#tb_departments").bootstrapTable('getSelections');

            if (arrselections.length > 1) {
                       toastr.warning('只能选择一行进行编辑');

                       return;
                   }
                   if (arrselections.length <= 0) {
                       toastr.warning('请选择有效数据');

                       return;
                   }
            $("#myModalLabel1").text("编辑");
            $("#txt_name1").val(arrselections[0].name);
            // $("#txt_pass1").val(arrselections[0].password);
            $("#txt_sex1").val(arrselections[0].sex);
            $("#txt_age1").val(arrselections[0].age);
            $("#txt_organ1").val(arrselections[0].organization_name);
            $("#txt_role1").val(arrselections[0].role_describe);
            postdata1.id = arrselections[0].id;
            $('#myModal1').modal()

        })

    };

    $("#btn_submit1").click(function () {

        var bootstrapValidator = $("#form1234").data('bootstrapValidator');
        //手动触发验证1
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()){

            postdata1.name = $("#txt_name1").val();
            postdata1.password = $("#txt_pass1").val();
            postdata1.salt = "";
            postdata1.roleId = '';
            postdata1.age = $("#txt_age1").val();
            postdata1.sex = $("#txt_sex1").val();
            postdata1.create_time = '';
            postdata1.organizationId = '';
            postdata1.role_describe = $("#txt_role1").val();
            postdata1.organization_name = $("#txt_organ1").val();
            // alert(JSON.parse(postdata[0]));
            var voList = [postdata1];
            // alert(JSON.parse(voList));
            $.ajax({
                type:"post",
                url:"/mes/UpdateUserVo",
                data:
                    JSON.stringify(voList),
                dataType: "json",contentType:"application/json;UTF-8",
                success:function (data,status) {
                    if(status=="success"){
                        toastr.success('提交数据成功');
                        $("#tb_departments").bootstrapTable('refresh');
                    }
                },
                error:function () {
                    toastr.error('发生错误 提交失败');
                },
                complete: function () {

                }
            });

        }

    });






    return oInit;
};