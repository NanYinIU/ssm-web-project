<%--
  Created by IntelliJ IDEA.
  User: ggx
  Date: 17-7-9
  Time: 上午10:45
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>后台登录</title>
    <meta name="author" content="DeathGhost" />
    <link rel="stylesheet" type="text/css" href="/static/css/style.css" />
    <style>
        body{height:100%;background:#16a085;overflow:hidden;}
        canvas{z-index:-1;position:absolute;}
    </style>
    <script src="/static/js/jquery/jquery.js"></script>
    <script src="/static/js/verificationNumbers.js"></script>
    <script src="/static/js/Particleground.js"></script>
    <script>
        $(document).ready(function() {
            //粒子背景特效
            $('body').particleground({
                dotColor: '#5cbdaa',
                lineColor: '#5cbdaa'
            });
            //验证码 js写的
            createCode();

        });
    </script>
</head>
<body>


<dl class="admin_login">
    <dt>
        <sm>管理系统</sm>
        <em>Management System</em>
    </dt>


    <form action="/login" method="post" onsubmit="return validate()">

        <dd class="user_icon">
            <input type="text" name="name" placeholder="账号" class="login_txtbx" maxlength="50"/>
        </dd>
        <dd class="pwd_icon">
            <input type="password" name="password" placeholder="密码" class="login_txtbx" maxlength="32"/>
        </dd>
        <dd class="val_icon">
            <div class="checkcode">
                <input type="text" name="checkcode" id="J_codetext" placeholder="验证码" maxlength="4" class="login_txtbx">
                <canvas class="J_codeimg" id="myCanvas" onclick="createCode()">对不起，您的浏览器不支持canvas，请下载最新版浏览器!</canvas>
            </div>
            <input type="button" value="验证码核验" class="ver_btn" onClick="validate();">
        </dd>
        <dd>
            <input type="submit" value="立即登陆" class="submit_btn"/>
        </dd>
    </form>
    <dd>
        <p>© 2016-2017 版权所有</p>
        <p>Email：admin@abc.com.cn</p>
    </dd>
</dl>


</body>
</html>
