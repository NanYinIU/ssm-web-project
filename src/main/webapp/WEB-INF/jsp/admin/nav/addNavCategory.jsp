<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>navBar管理</title>
    <link rel="stylesheet" href="/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/ztree/metro/ztree.css">
</head>

<body style="width: 800px;height: 400px;margin-top: 10px">
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">分类名称</label>
        <div class="layui-input-block">
            <input type="text" name="title" required  lay-verify="required" placeholder="请输入分类名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">选择分类图标</label>
        <div class="layui-input-block">
            <select name="icon" lay-verify="required">
                <option value=""></option>
                <option value="0">北京</option>
                <option value="1">上海</option>
                <option value="2">广州</option>
                <option value="3">深圳</option>
                <option value="4">杭州</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">请输入分类介绍</label>
        <div class="layui-input-block">
            <textarea name="desc" placeholder="请输入内容" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="navCategorySubmit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="/plugins/layui/layui.js"></script>
</body>
<script>
    layui.use(['element', 'table', 'form'], function () {
        var $ = layui.jquery;
        var element = layui.element;
        var table = layui.table; //Tab的切换功能，切换事件监听等，需要依赖element模块
        var form = layui.form;
        form.on('submit(navCategorySubmit)', function(data){
            layer.msg(JSON.stringify(data.field));
//            $.ajax({
//                url:'/icon/icon',
//                type:'POST',
//                data:'{icon:"'+data.id+'"}',
//                dataType:'json',
//                success:function(){
//                    layer.alert('添加成功')
//                }
//            })
            return false;
        });

    })
</script>
</html>