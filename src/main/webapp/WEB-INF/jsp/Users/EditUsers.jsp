<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="sihro" uri="http://shiro.apache.org/tags" %>

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

    <script src="/static/js/jquery/jquery-1.11.1.min.js"></script>
    <script src="/static/js/bootstrap/bootstrap.js"></script>
    <link href="/static/css/bootstrap.css" rel="stylesheet" />
    <script src="/static/js/bootstrap/bootstrap-table.js"></script>
    <link href="/static/css/bootstrap-table.css" rel="stylesheet" />
    <script src="/static/js/bootstrap/bootstrap-table-zh-CN.js"></script>

    <link href="/static/css/toastr.css" rel="stylesheet" />
    <script src="/static/js/bootstrap/toastr.min.js"></script>
    <script src="/static/js/bootstrap/bootbox.min.js"></script>

    <link href="/static/css/bootstrapValidator.min.css" rel="stylesheet" />
    <script src="/static/js/bootstrap/bootstrapValidator.min.js"></script>
    <script src="/static/js/template-web.js"></script>
    <script src="/static/js/NanYinJs/UserMsg.js"></script>
    <script src="/static/js/NanYinJs/MyValidator.js"></script>
    <script type="text/javascript">
        $(function() {
            $(".knob").knob();
        });
    </script>


    <link rel="stylesheet" type="text/css" href="/static/css/theme.css">
    <link rel="stylesheet" type="text/css" href="/static/css/premium.css">

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
<%--<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>--%>

<div class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
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
            <li class="active">管理人员面板</li>
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
                            <label class="control-label col-sm-2" for="ByName">输入用户名关键字查询：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" placeholder="Search.." id="ByName">
                            </div>
                            <div class="col-sm-2" style="text-align:left;">
                                <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-success">查询</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

                <div id="toolbar" class="btn-group">
                    
                    <shiro:hasPermission name="user:insert"><button id="btn_add" type="button" class="btn btn-danger">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                    </button></shiro:hasPermission>
                    
                    <sihro:hasPermission name="user:update"><button id="btn_edit" type="button" class="btn btn-warning">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
                    </button></sihro:hasPermission>
                    <shiro:hasPermission name="user:delect"><button id="btn_delete" type="button" class="btn btn-info">
                       <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                    </button></shiro:hasPermission>
                </div>


                <%--表格--%>
                <table id="tb_departments"></table>




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

                                            <form class="form-horizontal" id="form123" name="form123">
                                                <%--表单开始--%>

                                                    <div class="form-group">
                                                        <label class="control-label" >用户名:</label>
                                                        <div class="controls">
                                                            <input name="txt_name" id="txt_name" type="text" class="form-control"/>
                                                        </div>
                                                    </div>



                                                    <div class="form-group">
                                                    <label class="control-label">密码:</label>
                                                    <div class="controls">
                                                        <input name="txt_pass" id="txt_pass" type="text" class="form-control"/>
                                                    </div>
                                                    </div>


                                                    <div class="form-group">
                                                    <label class="control-label">再次输入密码:</label>
                                                    <div class="controls">
                                                        <input name="txt_passSec" id="txt_passSec" type="text" class="form-control"/>
                                                    </div>
                                                    </div>


                                                    <div class="form-group">
                                                    <label class="control-label" >性别:</label>
                                                    <div class="controls">
                                                        <input name="txt_sex" id="txt_sex" type="text" class="form-control"/>
                                                    </div>
                                                    </div>


                                                    <div class="form-group">
                                                    <label class="control-label" >年龄:</label>
                                                    <div class="controls">
                                                        <input name="txt_age" id="txt_age" type="text" class="form-control"/>
                                                    </div>
                                                    </div>


                                                    <div class="form-group">

                                                    <label class="control-label" >部门：</label>
                                                    <div class="controls">
                                                        <input  type="hidden" />
                                                    </div>

                                                        <select name="txt_organ" id="txt_organ" class="form-control selectpicker" data-live-search="true" title="默认">
                                                            <c:forEach var="users" items="${organ}">
                                                                <option>${users.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                         </div>


                                                    <div class="form-group">
                                                    <label class="control-label" >职位：</label>
                                                    <div class="controls">
                                                        <input type="hidden" />
                                                    </div>

                                                <%----%>
                                                <select name="txt_role" id="txt_role" class="form-control selectpicker" data-live-search="true">
                                                    <c:forEach var="users" items="${Roles}">
                                                        <option>${users.describe}</option>
                                                    </c:forEach>
                                                </select>
                                                <br>
                                                    </div>
                                                <div class="form-group">
                                                    <div class="controls">
                                                        <button id="btn_submit" type="submit" class="btn btn-success">提交</button>
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





                <div class="modal small fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h3 id="myModalLabel1">删除提示</h3>
                            </div>
                            <div class="modal-body">
                                <%--<p class="error-text"><i class="fa fa-warning modal-Icon"></i>确定要删除用户?</p>--%>
                                <div class="container-fluid">
                                    <div class="row-fluid">
                                        <div class="span12">

                                            <form class="form-horizontal" id="form1234" name="form1234">
                                                <%--表单开始--%>

                                                <div class="form-group">
                                                    <label class="control-label" >用户名:</label>
                                                    <div class="controls">
                                                        <input name="txt_name1" id="txt_name1" type="text" class="form-control"/>
                                                    </div>
                                                </div>



                                                <div class="form-group">
                                                    <label class="control-label">密码:</label>
                                                    <div class="controls">
                                                        <input name="txt_pass1" id="txt_pass1" type="password" class="form-control"/>
                                                    </div>
                                                </div>


                                                <div class="form-group">
                                                    <label class="control-label">再次输入密码:</label>
                                                    <div class="controls">
                                                        <input name="txt_passSec1" id="txt_passSec1" type="password" class="form-control"/>
                                                    </div>
                                                </div>


                                                <div class="form-group">
                                                    <label class="control-label" >性别:</label>
                                                    <div class="controls">
                                                        <input name="txt_sex1" id="txt_sex1" type="text" class="form-control"/>
                                                    </div>
                                                </div>


                                                <div class="form-group">
                                                    <label class="control-label" >年龄:</label>
                                                    <div class="controls">
                                                        <input name="txt_age1" id="txt_age1" type="text" class="form-control"/>
                                                    </div>
                                                </div>


                                                <div class="form-group">

                                                    <label class="control-label" >部门：</label>
                                                    <div class="controls">
                                                        <input  type="hidden" />
                                                    </div>

                                                    <select name="txt_organ1" id="txt_organ1" class="form-control selectpicker" data-live-search="true" title="默认">
                                                        <c:forEach var="users" items="${organ}">
                                                            <option>${users.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>


                                                <div class="form-group">
                                                    <label class="control-label" >职位：</label>
                                                    <div class="controls">
                                                        <input type="hidden" />
                                                    </div>

                                                    <%----%>
                                                    <select name="txt_role1" id="txt_role1" class="form-control selectpicker" data-live-search="true">
                                                        <c:forEach var="users" items="${Roles}">
                                                            <option>${users.describe}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <br>
                                                </div>

                                                <div class="form-group">
                                                    <div class="controls">
                                                        <button id="btn_submit1" type="submit" class="btn btn-success">提交</button>
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

            </div>



    </div>
</div>


<script src="/static/js/bootstrap/bootstrap.js"></script>

</body>
</html>
