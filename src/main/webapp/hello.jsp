<%--
  Created by IntelliJ IDEA.
  User: ggx
  Date: 17-8-16
  Time: 下午5:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test</title>

    <script src="/static/js/jquery/jquery-1.11.1.min.js"></script>
    <script src="/static/js/bootstrap/bootstrap.js"></script>
    <script src="/static/js/template-web.js"></script>
    <link rel="stylesheet"  href="/static/css/bootstrap.css">
    <link rel="stylesheet"  href="/static/css/select2-bootstrap.css">
    <link rel="stylesheet"  href="/static/css/select2.css">

    <script src="static/js/bootstrap/select2.full.js"></script>

</head>
<body>

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
            alert(data);
            document.getElementById('content').innerHTML = html1;
        }
    });
</script>

</body>
</html>
