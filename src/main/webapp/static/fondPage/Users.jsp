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
    <script type="text/javascript">
        $(function() {
            $(".knob").knob();
        });
    </script>


    <link rel="stylesheet" type="text/css" href="/static/css/theme.css">
    <link rel="stylesheet" type="text/css" href="/static/css/premium.css">
    <script src="/static/js/flatUi/flat-ui.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/flat-ui.css">

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
        <a class="" href="index.html"><span class="navbar-brand"><span class="fa fa-database"></span> 管理系统</span></a></div>

    <div class="navbar-collapse collapse" style="height: 1px;">
        <ul id="main-menu" class="nav navbar-nav navbar-right">
            <li class="dropdown hidden-xs">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <span class="glyphicon glyphicon-music padding-right-small" style="position:relative;top: 3px;"></span> ${user}
                    <i class="fa fa-caret-down"></i>
                </a>

                <ul class="dropdown-menu">
                    <li><a href="/selectByName">我的账号</a></li>
                    <li class="divider"></li>
                    <li class="dropdown-header"> <shiro:hasRole name="admin"><a href="/mes/AllMessageDis/1"></shiro:hasRole>管理员面板</a></li>
                    <li><a href="./">用户</a></li>
                    <li><a href="./">安全</a></li>
                    <li><a tabindex="-1" href="./">Payments</a></li>
                    <li class="divider"></li>
                    <li><a tabindex="-1" href="/logout">登出</a></li>
                </ul>
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
            <li ><a href="/mes/AllMessageDis/1"><span class="fa fa-caret-right"></span>用户</a></li>
            <li ><a href="/selectByName"><span class="fa fa-caret-right"></span> 用户信息</a></li>
            <li ><a href="media.html"><span class="fa fa-caret-right"></span> 媒体</a></li>
            <li ><a href="calendar.html"><span class="fa fa-caret-right"></span> Calendar</a></li>
        </ul></li>

        <li data-popover="true" data-content="Items in this group require a <strong><a href='http://portnine.com/bootstrap-themes/aircraft' target='blank'>premium license</a><strong>." rel="popover" data-placement="right"><a href="#" data-target=".premium-menu" class="nav-header collapsed" data-toggle="collapse"><i class="fa fa-fw fa-fighter-jet"></i> 小功能实现<i class="fa fa-collapse"></i></a></li>
        <li><ul class="premium-menu nav nav-list collapse">
            <li ><a href="premium-profile.html"><span class="fa fa-caret-right"></span> Enhanced Profile</a></li>
            <li ><a href="premium-blog.html"><span class="fa fa-caret-right"></span> Blog</a></li>
        </ul></li>

        <li><a href="#" data-target=".accounts-menu" class="nav-header collapsed" data-toggle="collapse"><i class="fa fa-fw fa-briefcase"></i> 用户信息 <span class="label label-info">+3</span></a></li>
        <li><ul class="accounts-menu nav nav-list collapse">
            <li ><a href="sign-in.html"><span class="fa fa-caret-right"></span> Sign In</a></li>
            <li ><a href="sign-up.html"><span class="fa fa-caret-right"></span> Sign Up</a></li>
            <li ><a href="reset-password.html"><span class="fa fa-caret-right"></span> Reset Password</a></li>
        </ul></li>

        <li><a href="#" data-target=".legal-menu" class="nav-header collapsed" data-toggle="collapse"><i class="fa fa-fw fa-legal"></i> 资源管理器<i class="fa fa-collapse"></i></a></li>
        <li><ul class="legal-menu nav nav-list collapse">
            <li ><a href="privacy-policy.html"><span class="fa fa-caret-right"></span> Privacy Policy</a></li>
            <li ><a href="terms-and-conditions.html"><span class="fa fa-caret-right"></span> Terms and Conditions</a></li>
        </ul></li>

        <li><a href="help.html" class="nav-header"><i class="fa fa-fw fa-question-circle"></i> 帮助</a></li>
        <li><a href="faq.html" class="nav-header"><i class="fa fa-fw fa-comment"></i> Faq</a></li>
        <li><a href="http://portnine.com/bootstrap-themes/aircraft" class="nav-header" target="blank"><i class="fa fa-fw fa-heart"></i> Get Premium</a></li>
    </ul>
</div>

<div class="content">
    <div class="header">
        <div class="stats">
            <p class="stat"><span class="label label-info">5</span> Tickets</p>
            <p class="stat"><span class="label label-success">27</span> Tasks</p>
            <p class="stat"><span class="label label-danger">15</span> Overdue</p>
        </div>

        <h1 class="page-title">NanYinIU</h1>
        <ul class="breadcrumb">
            <li><a href="/static/fondPage/index.jsp">主页</a> </li>
            <li class="active">用户列表</li>
        </ul>

    </div>
    <div class="main-content">
        <div class="btn-group">
        <form action="/mes/selectMsgByName/1" method="post">
                <input type="text" name="name" placeholder="输入用户名关键字检索.." class="form-control">
                <div class="btn-toolbar list-toolbar">
                <br><button type="submit" class="btn btn-primary"><i class="fa fa-search-plus"></i> 查询</button> &nbsp;<a class="page-header"><shiro:lacksRole name="admin">非管理员状态 如要对用户列表进行改删 请联系管理员</shiro:lacksRole></a>
                </div>
        </form>
        </div>

        <table class="table">
            <thead>
            <tr>
                <th>#</th>
                <th>姓名</th>
                <th>年龄</th>
                <th>性别</th>
                <th>创建时间</th>
                <th>职位</th>
                <th>所属部门</th>
                <th>编辑</th>
                <th>删除</th>
                <th style="width: 3.5em;"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="users" items="${Users}">
           <tr>

                <td>${users.id}</td>
                <td>${users.name}</td>
                <td>${users.age}</td>
                <td>${users.sex}</td>
                <td>${users.create_time}</td>
                <td>${users.role_describe}</td>
                <td>${users.organization_name}</td>
                <td>

                    <shiro:hasAnyRoles name="admin,manager">  <a href="#firstModal" role="button" data-toggle="modal" id="OnEdit" ></shiro:hasAnyRoles><i class="fa fa-edit"></i></a>
                </td>
                <td >
                    <shiro:hasRole name="admin"> <a href="#myModal" role="button" data-toggle="modal"></shiro:hasRole><i class="fa fa-trash-o"></i></a>
                </td>
            </tr>


            </c:forEach>



            </tbody>
        </table>
        <%--分页 需要修改 要不然 数据量大了页数会很多很多。。。。！！！！标记上先--%>
        <ul class="pagination">
            <li><a href="/mes/AllMessageDis/${page.prePage}">&laquo;</a></li>
            <c:forEach var="x" begin="1" end="${page.pages}" step="1">
            <li><a href="/mes/AllMessageDis/${x}">${x}</a></li>
            </c:forEach>
            <li><a href="/mes/AllMessageDis/${page.nextPage}">&raquo;</a></li>
        </ul>

        <div class="modal small fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3 id="myModalLabel">删除提示</h3>
                    </div>
                    <div class="modal-body">
                        <p class="error-text"><i class="fa fa-warning modal-icon"></i>确定要删除用户?</p>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">取消</button>
                        <button class="btn btn-danger" data-dismiss="modal">删除</button>
                    </div>

                </div>
            </div>
        </div>



            <div class="modal small fade" id="firstModal" tabindex="-1" role="dialog" aria-labelledby="firstModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h3 id="firstModalLabel">更改提示</h3>
                        </div>
                        <div class="modal-body">
                            <%--<p class="error-text"><i class="fa fa-warning modal-icon"></i>确定要删除用户?</p>--%>
                                <div class="container-fluid">
                                    <div class="row-fluid">
                                        <div class="span12">
                                            <form class="form-horizontal">
                                                <div class="control-group">
                                                    <label class="control-label" for="inputEmail">部门：</label>
                                                    <div class="controls">
                                                        <input id="inputEmail" type="hidden" />
                                                    </div>
                                                    <br>
                                                    <div class="row-fluid">
                                                    <select class="form-control selectpicker" title="默认">
                                                        <%-- admin 用户下 能够选择不通的部门 其他用户 只能选择而自己的部门--%>

                                                            <c:forEach var="users" items="${organ}">
                                                            <option>${users.name}</option>
                                                            </c:forEach>

                                                            <%--如果是经理 能够操作 只能选择当前部门下的不同的职位 就是说部门固定
                                                            不能选择--%>

                                                    </select>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="control-group">
                                                    <label class="control-label" for="inputPassword">职位：</label>
                                                    <div class="controls">
                                                        <input id="inputPassword" type="hidden" />
                                                    </div>
                                                    <br>
                                                </div>
                                                <%----%>
                                                    <select class="form-control selectpicker">
                                                <c:forEach var="users" items="${Roles}">
                                                    <option>${users.describe}</option>
                                                </c:forEach>
                                                    </select>
                                                <br>
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <button type="submit" class="btn btn-success">提交</button>
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


        <footer>
            <hr>
            <%--<c:out value="${User[0].name}">${Users[0].name}</c:out>--%>
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

</body>
</html>
