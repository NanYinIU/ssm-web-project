<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图标添加form</title>
    <link rel="stylesheet" href="/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/ztree/metro/ztree.css">
</head>

<body style="width: 800px;height: 400px;margin-top: 10px">
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">图标名称</label>
        <div class="layui-input-block">
            <input type="text" name="iconName" required  lay-verify="required" placeholder="请输入图标名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">图标unicode</label>
        <div class="layui-input-block">
            <input type="text" name="iconUnicode" required  lay-verify="required" placeholder="请输入图标名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">图标class</label>
        <div class="layui-input-block">
            <input type="text" name="iconClass" required  lay-verify="required" placeholder="请输入图标名称" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="addIconSubmit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px; width: 100%">
    <legend>提示：用到的请参考layui官网图标样式进行添加</legend>
</fieldset>
<script type="text/javascript" src="/plugins/layui/layui.js"></script>
</body>
<script>
    layui.use(['element', 'table', 'laypage',' layer','form'], function () {
        var $ = layui.jquery;
        var element = layui.element;
        var table = layui.table; //Tab的切换功能，切换事件监听等，需要依赖element模块
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form;

        form.on('submit(addIconSubmit)', function(data){
            layer.msg(JSON.stringify(data.field));
            $.ajax({
                url:'/icon/icon',
                type:'POST',
                data:JSON.stringify(data.field),
                dataType:'json',
                success:function(){
                    layer.alert('添加成功')
                }
            })
            return false;
        });

    })
</script>
</html>