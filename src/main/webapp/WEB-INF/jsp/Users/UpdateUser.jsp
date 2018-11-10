<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="/static/css/toastr.css" rel="stylesheet" />
    <script src="/static/js/bootstrap/toastr.min.js"></script>
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

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="../assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">


<!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
<!--[if IE 7 ]> <body class="ie ie7 "> <![endif]-->
<!--[if IE 8 ]> <body class="ie ie8 "> <![endif]-->
<!--[if IE 9 ]> <body class="ie ie9 "> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->

<!--<![endif]-->

<div class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="" href="/returnIndex"><span class="navbar-brand"><span class="fa fa-database"></span> 管理系统</span></a></div>

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
            <li><a href="/static/fondPage/index.jsp">主页</a>  </li>
            <li class="active">信息更改</li>
        </ul>

    </div>
    <div class="main-content">





        <ul class="nav nav-tabs">
            <li class="active"><a href="#home" data-toggle="tab">个人信息</a></li>
            <%--<li><a href="#profile" data-toggle="tab">密码修改</a></li>--%>
        </ul>

        <div class="row">
            <div class="col-md-4">
                <br>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane active in" id="home">
                        <form id="tab">
                            <div class="form-group">
                            <input type="hidden" value="${userMessage.id}" name="id" id="id" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>用户名:</label>
                               <p> <b>&nbsp;&nbsp;&nbsp;${userMessage.name}</b></p>
                                <input type="hidden" value="${userMessage.name}" name="name" id="name" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>密码</label>
                                <input type="password" name="password" id="password" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>年龄</label>
                                <input type="text" value="${userMessage.age}" name="age" id="age" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>性别</label>
                                <input type="text" value="${userMessage.sex}" name="sex" id="sex" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>创建时间：</label>
                                <a>${userMessage.create_time}</a>
                            </div>

                        </form>
                    </div>

                </div>
<br>
                <div class="btn-toolbar list-toolbar">
                    <button id="btn_save"  name="btn_save" class="btn btn-primary"><i class="fa fa-save"></i> 保存</button>
                </div>
            </div>
        </div>

        <script>
            $("#btn_save").click(function () {
                var data = {"id":$('#id').val(),"name":$('#name').val(),"password":$('#password').val(),"sex":$('#sex').val(),"age":$("#age").val()};
                var userdata = [data];
               $.ajax({
                   type:"post",
                   dataType: "json",contentType:"application/json;UTF-8",
                   url:"/updateThisUser",
                   data:JSON.stringify(userdata),
                   success:function (data,status) {
                       if(status=="success"){
                           toastr.success('保存信息成功');
                       }
                   },
                   error:function () {
                       toastr.error('发生错误 提交失败');
                   },
                   complete: function () {

                   }
               })
            })
        </script>



            <footer>
                <hr>
                <!-- Purchase a site license to remove this link from the footer: http://www.portnine.com/bootstrap-themes -->
            </footer>
        </div>
    </div>


    <script src="/static/js/bootstrap/bootstrap.js"></script>
    <script type="text/javascript">
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
        });
    </script>

</div>
</div>
</body>
</html>
