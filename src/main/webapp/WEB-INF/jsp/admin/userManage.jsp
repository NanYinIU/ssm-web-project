<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Table</title>
    <link rel="stylesheet" href="/plugins/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/ztree/metro/ztree.css"></head>

<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space30">
        <div ></div>
    </div>
</div>
<script type="text/javascript" src="/plugins/layui/layui.js"></script>
</body>
<script>
    layui.use('element', function() {
        var $ = layui.jquery, element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
        element.on('tab(docDemoTabBrief)', function(data){
            console.log(data);
        });
    })



</script>
</html>