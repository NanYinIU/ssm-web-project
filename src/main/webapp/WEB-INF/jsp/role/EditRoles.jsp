<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: ggx
  Date: 17-7-9
  Time: 下午1:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en"><head>
    <meta charset="utf-8">
    <title>Bootstrap Admin</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/static/css/bootstrap.css">
    <link rel="stylesheet" href="/static/css/font-awesome.css">

    <script src="/static/js/jquery/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="/static/js/jquery/jquery.knob.js" type="text/javascript"></script>

    <script src="/static/js/bootstrap/bootstrap.js"></script>
    <script src="/static/js/bootstrap/bootstrap-table.js"></script>
    <link href="/static/css/bootstrap-table.css" rel="stylesheet" />
    <script src="/static/js/bootstrap/bootstrap-table-zh-CN.js"></script>

    <link href="/static/css/toastr.css" rel="stylesheet" />
    <script src="/static/js/bootstrap/toastr.min.js"></script>
    <script src="/static/js/bootstrap/bootbox.min.js"></script>

    <link href="/static/css/bootstrapValidator.min.css" rel="stylesheet" />
    <script src="/static/js/bootstrap/bootstrapValidator.min.js"></script>

    <script src="/static/js/NanYinJs/RoleMsg.js"></script>
    <script src="/static/js/NanYinJs/roleValidator.js"></script>
    <link rel="stylesheet"  href="/static/css/select2-bootstrap.css">
    <link rel="stylesheet"  href="/static/css/select2.css">
    <script src="/static/js/bootstrap/select2.full.js"></script>
    <script src="/static/js/template-web.js"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/theme.css">
    <link rel="stylesheet" type="text/css" href="/static/css/premium.css">
    <%--<script src="/static/js/flatUi/flat-ui.js" type="text/javascript"></script>--%>
    <%--<link rel="stylesheet" type="text/css" href="/static/css/flat-ui.css">--%>

</head>
<body class=" theme-blue">

<!-- Demo page code -->

<script type="text/javascript">
    $(function() {
        var match = document.cookie.match(new RegExp('color=([^;]+)'));
        if(match) var color = match[1];
        if(color) {
            $('body').removeClass(function (index, css) {
                return (css.match (/\btheme-\S+/g) || []).join(' ')
            })
            $('body').addClass('theme-' + color);
        }

        $('[data-popover="true"]').popover({html: true});

    });
</script>
<style type="text/css">
    #line-chart {
        height:300px;
        width:800px;
        margin: 0px auto;
        margin-top: 1em;
    }
    .navbar-default .navbar-brand, .navbar-default .navbar-brand:hover {
        color: #fff;
    }
</style>

<script type="text/javascript">
    $(function() {
        var uls = $('.sidebar-nav > ul > *').clone();
        uls.addClass('visible-xs');
        $('#main-menu').append(uls.clone());
    });
</script>



<div class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="" href="index.html"><span class="navbar-brand"><span class="fa fa-database"></span> 管理系统</span></a></div>

    <div class="navbar-collapse collapse" style="height: 1px;">
        <ul id="main-menu" class="nav navbar-nav navbar-right">
            <li class="dropdown hidden-xs">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <span class="glyphicon glyphicon-music padding-right-small" style="position:relative;top: 3px;"></span> 当前用户：${user}
                </a>


            </li>
        </ul>

    </div>
</div>


<div class="sidebar-nav">
    <ul>
        <li><a href="#" data-target=".dashboard-menu" class="nav-header" data-toggle="collapse"><i class="fa fa-fw fa-dashboard"></i> 面板<i class="fa fa-collapse"></i></a></li>
        <li><ul class="dashboard-menu nav nav-list collapse in">
            <li><a href="/returnIndex"><span class="fa fa-caret-right"></span> 主界面</a></li>
            <li ><a href="/mes/AllMessageDis"><span class="fa fa-caret-right"></span> 用户</a></li>
            <shiro:hasAnyRoles name="admin,manager,dirver,member"><li ><a href="/selectByName"><span class="fa fa-caret-right"></span> 用户信息</a></li></shiro:hasAnyRoles>
        </ul></li>

        <li data-popover="true" rel="popover" data-placement="right"><a href="#" data-target=".premium-menu" class="nav-header collapsed" data-toggle="collapse"><i class="fa fa-fw fa-github"></i> 部门和职位信息<i class="fa fa-collapse"></i></a></li>
        <li><ul class="premium-menu nav nav-list collapse">
            <li ><a href="/org/returnOrgPage"><span class="fa fa-caret-right"></span>部门管理</a></li>
            <li ><a href="/role/rolePage"><span class="fa fa-caret-right"></span> 职位管理</a></li>
        </ul></li>


        <li><a href="#" data-target=".legal-menu" class="nav-header collapsed" data-toggle="collapse"><i class="fa fa-fw fa-legal"></i> 资源管理器<i class="fa fa-collapse"></i></a></li>
        <li><ul class="legal-menu nav nav-list collapse">
            <shiro:hasAnyRoles name="admin,manager"> <li ><a href="/other/returnDruidPage"><span class="fa fa-caret-right"></span>Druid监控</a></li></shiro:hasAnyRoles>
            <li ><a href="#"><span class="fa fa-caret-right"></span> 页面资源管理</a></li>
        </ul></li>
        <li><a href="/cal/returnCal" class="nav-header"><i class="fa fa-fw fa-bank"></i> 个人日程</a></li>
        <li><a href="/logout" class="nav-header"><i class="fa fa-fw fa-sign-out"></i> 安全登出</a></li>
        <shiro:hasAnyRoles name="admin,manager"> <li><a href="/log/returnLog" class="nav-header"><i class="fa fa-fw fa-cloud"></i> 登录日志</a></li></shiro:hasAnyRoles>
    </ul>
</div>
<div class="content">
    <div class="header">
        <div id="content"></div>

        <script id="test" type="text/html">
            <div class="stats">
                <p class="stat"><b>您还有</b> <span class="label label-info">{{count}}</span><b>个事件未处理</b></p>
                <p class="stat"><b>查看</b> <span class="label label-danger"><a href="/cal/returnCal"><font color="#f0f8ff"> 个人日程</font></a></span></p>
            </div>
        </script>

        <script>
            var data = '' ;
            $.ajax({
                type: "post",
                url: "/cal/count",
                dataType: "json",contentType:"application/json;UTF-8",
                success:function (json) {
                    data =json
                    var html1 = template('test', data);

                    document.getElementById('content').innerHTML = html1;
                }
            });
        </script>

        <h1 class="page-title">NanYinIU</h1>
        <ul class="breadcrumb">
            <li><a href="/static/fondPage/index.jsp">主页</a> </li>
            <li class="active">角色管理面板</li>
        </ul>

    </div>
    <div class="main-content">
        <%--表格部分了--%>

            <div class="panel-body" style="padding-bottom:0px;">
                <div class="panel panel-default">
                    <div class="panel-heading">查询条件</div>
                    <div class="panel-body">
                        <form id="formSearch" class="form-horizontal">
                            <div class="form-group" style="margin-top:15px">
                                <label class="control-label col-sm-1" for="name">用户名：</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" placeholder="输入用户名关键字查询- -" id="name">
                                </div>
                                <div class="col-sm-2" style="text-align:left;">
                                    <button type="button" style="margin-left:50px" id="but_query" class="btn btn-primary">查询</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div id="toolbar" class="btn-group">
                    <shiro:hasPermission name="role:add">
                        <button id="but_add" type="button" class="btn btn-danger">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>增加
                        </button>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="role:update">
                    <button id="but_edit" type="button" class="btn btn-success">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
                    </button>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="role:delect">
                    <button id="but_delete" type="button" class="btn btn-info">
                       <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                    </button>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="role:addPermission">
                        <button id="but_addPermission" type="button" class="btn btn-primary">
                            <span class="glyphicon glyphicon-leaf" aria-hidden="true"></span>更改权限
                        </button>
                    </shiro:hasPermission>

                </div>


                <%--表格--%>
                <table id="tb_roles"></table>



                <%-- 修改开始 --%>
                <div class="modal small fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h3 id="myModalLabel">删除提示</h3>
                            </div>
                            <div class="modal-body">
                                <%--<p class="error-text"><i class="fa fa-warning modal-Icon"></i>确定要删除用户?</p>--%>
                                <div class="container-fluid">
                                    <div class="row-fluid">
                                        <div class="span12">

                                            <form class="form-horizontal" id="form1" name="form1">
                                                <%--表单开始--%>
                                                    <div class="form-group">
                                                        <label class="control-label" >角色名:</label>
                                                        <div class="controls">
                                                            <input  id="txt_roleName" type="text" class="form-control"/>
                                                        </div>
                                                    </div>



                                                    <div class="form-group">
                                                    <label class="control-label">角色描述:</label>
                                                    <div class="controls">
                                                        <input id="txt_describe" type="text" class="form-control"/>
                                                    </div>
                                                    </div>


                                                    <div class="form-group">
                                                    <div class="controls">
                                                        <button id="r_submit" name="r_submit" type="submit" class="btn btn-success">提交</button>
                                                    </div>
                                                </div>

                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">取消</button>
                            </div>

                        </div>
                    </div>
                </div>
                <%--修改结束--%>

                <%--权限增加开始--%>
                <div class="modal large fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h3 id="myModalLabel1">删除提示</h3>
                            </div>
                            <div class="modal-body">

                                            <form id="form2" name="form2">
                                                <%--表单开始--%>
                                                <div class="form-group">
                                                    <div class="controls">
                                                    <label class="control-label">请选择/变更权限</label>
                                                        <select id="sel_menu2" multiple="multiple" ></select>
                                                        <style> select{width:500px; padding:20px 0;}</style>

                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <div class="controls">
                                                        <button id="AP_submit" name="AP_submit" type="submit" class="btn btn-success">提交</button>
                                                    </div>
                                                </div>


                                                    <script type="text/javascript">
                                                        $("#sel_menu2").select2({
                                                            tags: true,
                                                            placeholder:{
                                                                id:'-1',
                                                                text:'选择权限名称'
                                                            },
                                                            multiple: true,
                                                            ajax: {
                                                                url: "/per/AllPermission",
                                                                processResults: function (data, page) {
                                                                    console.log(data);
                                                                    var parsed = data;
                                                                    var arr = [];
                                                                    for (var x in parsed) {
                                                                        arr.push(parsed[x]); //这个应该是个json对象
                                                                    }
                                                                    console.log(arr);
                                                                    return {
                                                                        results: arr
                                                                    };
                                                                }
                                                            }
                                                        });


                                                    </script>
                                            </form>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">取消</button>
                            </div>

                        </div>
                    </div>
                </div>
                <%--权限增加结束--%>

                <%-- 增加开始 --%>
                <div class="modal small fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h3 id="myModalLabel2">增加提示</h3>
                            </div>
                            <div class="modal-body">
                                <div class="container-fluid">
                                    <div class="row-fluid">
                                        <div class="span12">

                                            <form class="form-horizontal" id="form3" name="form3">
                                                <%--表单开始--%>
                                                <div class="form-group">
                                                    <label class="control-label" >角色名:</label>
                                                    <div class="controls">
                                                        <input  id="add_roleName" name="add_roleName" type="text" class="form-control"/>
                                                    </div>
                                                </div>



                                                <div class="form-group">
                                                    <label class="control-label">角色描述:</label>
                                                    <div class="controls">
                                                        <input id="add_describe" name="add_describe" type="text" class="form-control"/>
                                                    </div>
                                                </div>


                                                <div class="form-group">
                                                    <div class="controls">
                                                        <button id="add_submit" name="add_submit" type="submit" class="btn btn-success">提交</button>
                                                    </div>
                                                </div>

                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">取消</button>
                            </div>

                        </div>
                    </div>
                </div>
                <%--增加结束--%>


    </div>
    </div>


<%--<script src="/static/js/bootstrap/bootstrap.js"></script>--%>
</div>
</body>
</html>
