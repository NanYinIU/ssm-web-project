<%--
  Created by IntelliJ IDEA.
  User: ggx
  Date: 17-7-9
  Time: 下午1:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <title>ztreeDemo</title>
    <link rel="stylesheet" href="/static/css/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="/static/css/metroStyle.css" type="text/css">
    <!-- zTree  这里使用的 all.js = core + excheck + exedit ( 不包括 exhide )-->

    <script src="/static/js/jquery/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="/static/js/zTree/jquery.ztree.all.min.js"></script>
    <script src="/static/js/zTree/jquery.ztree.core.min.js"></script>
    <script src="/static/js/zTree/jquery.ztree.excheck.min.js"></script>
    <script src="/static/js/zTree/jquery.ztree.exhide.min.js"></script>
    <script src="/static/js/zTree/jquery.ztree.exedit.min.js"></script>

    <!--my zTree  这里引入的是下面所示的js代码文件 -->
    <script src ="/static/js/NanYinJs/mytree.js"></script>
    <style>
        /*按钮*/
        .icon_div {
            display: inline-block;
            height: 25px;
            width: 35px;
            background: url(http://c.csdnimg.cn/public/common/toolbar/images/f_icon.png) no-repeat 12px -127px;
        }

        .icon_div a {
            display: inline-block;
            width: 27px;
            height: 20px;
            cursor: pointer;
        }

        /*end--按钮*/

        /*ztree表格*/
        .ztree {
            padding: 0;
            border: 2px solid #CDD6D5;
        }

        .ztree li a {
            vertical-align: middle;
            height: 30px;
        }

        .ztree li > a {
            width: 100%;
        }

        .ztree li > a,
        .ztree li a.curSelectedNode {
            padding-top: 0px;
            background: none;
            height: auto;
            border: none;
            cursor: default;
            opacity: 1;
        }

        .ztree li ul {
            padding-left: 0px
        }

        .ztree div.diy span {
            line-height: 30px;
            vertical-align: middle;
        }

        .ztree div.diy {
            height: 100%;
            width: 20%;
            line-height: 30px;
            border-top: 1px dotted #ccc;
            border-left: 1px solid #eeeeee;
            text-align: center;
            display: inline-block;
            box-sizing: border-box;
            color: #6c6c6c;
            font-family: "SimSun";
            font-size: 12px;
            overflow: hidden;
        }

        .ztree div.diy:first-child {
            text-align: left;
            text-indent: 10px;
            border-left: none;
        }

        .ztree .head {
            background: #5787EB;
        }

        .ztree .head div.diy {
            border-top: none;
            border-right: 1px solid #CDD2D4;
            color: #fff;
            font-family: "Microsoft YaHei";
            font-size: 14px;
        }

        /*end--ztree表格*/
    </style>
</head>
<body>
<div class="content_wrap">
    <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 pt10">
        <div class="well sidebar-nav ">
            <ul id="planTree" class="ztree"></ul>
        </div>
    </div>
</div>

</body>
</html>
