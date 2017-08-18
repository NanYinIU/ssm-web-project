<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
    <script src="/static/js/template-web.js"></script>
    <script type="text/javascript">
        $(function() {
            $(".knob").knob();
        });
    </script>


    <link rel="stylesheet" type="text/css" href="/static/css/theme.css">
    <link rel="stylesheet" type="text/css" href="/static/css/premium.css">
    <%--<script src="/static/js/flatUi/flat-ui.js" type="text/javascript"></script>--%>
    <%--<link rel="stylesheet" type="text/css" href="/static/css/flat-ui.css">--%>

</head>
<body class=" theme-3">

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
                    <span class="glyphicon glyphicon-music padding-right-small" style="position:relative;top: 3px;"></span>当前用户： ${user}

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

        <h1 class="page-title">Dashboard</h1>
        <ul class="breadcrumb">
            <li><a href="/returnIndex">主页</a> </li>
            <li class="active">主界面</li>
        </ul>

    </div>
    <div class="main-content">


        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="panel panel-default">
                    <a href="#widget1container" class="panel-heading" data-toggle="collapse">系统功能概述 </a>
                    <div id="widget1container" class="panel-body collapse in">
                        <h2>系统功能概述</h2>
                        <p>使用spring，springmvc，mybatis，shiro 框架构建一个简单的后台粗粒度管理系统</p>
                        <p>前端使用bootstrap技术</p>
                        <p>bootstrap-table 文档:<a href="http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/">http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/</a></p>
                        <p>项目地址：<a href="https://github.com/NanYinIU/ssm-web-project/">https://github.com/NanYinIU/ssm-web-project/</a></p>
                        <p>8.17 新增简单的角色权限分配 </p>
                        <p> 新增个人日程页面，新增echart报表支持 </p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="panel panel-default">
                    <a href="#widget2container" class="panel-heading" data-toggle="collapse">学习资料 </a>
                    <div id="widget2container" class="panel-body collapse in">
                        <h2>学习资料</h2>
                        <p><a href="http://jinnianshilongnian.iteye.com/blog/2018936">shiro 实用使用教程</a> </p>
                        <p></p>
                        <p></p>
                        <p><a class="btn btn-primary">学习更多 »</a></p>
                    </div>
                </div>
            </div>
        </div>


    </div>
</div>


<script src="/static/js/bootstrap/bootstrap.js"></script>



</body></html>
