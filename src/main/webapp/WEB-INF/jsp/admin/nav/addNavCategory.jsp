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
            <input type="text" name="name" required  lay-verify="required" placeholder="请输入分类名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">选择分类图标</label>
        <div class="layui-input-block">
            <div id="iconTplDiv"></div>
        </div>
    </div>
    <script type="text/html" id="iconTpl">
        <select name="icon" lay-verify="required" lay-search>
            <option value=""></option>
        {{#  layui.each(d.list, function(index, item){ }}
        <option value={{item.value}}>{{item.name}}</option>
        {{#  }); }}
        </select>
    </script>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">请输入分类介绍</label>
        <div class="layui-input-block">
            <textarea name="comment" placeholder="请输入内容" class="layui-textarea"></textarea>
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
    layui.use(['element', 'table', 'form','laytpl'], function () {
        var $ = layui.jquery;
        var element = layui.element;
        var table = layui.table; //Tab的切换功能，切换事件监听等，需要依赖element模块
        var form = layui.form;
        var laytpl = layui.laytpl;
        // 下拉框
        $.ajax({
            url:'/icon/iconCombo',
            type:'get',
            dataType:'json',
            success:function(data){
                var getTpl = iconTpl.innerHTML
                var iconTplDiv = document.getElementById('iconTplDiv');
                laytpl(getTpl).render(data, function(html){
                    iconTplDiv.innerHTML = html;
                });
                form.render('select')
            }
        })
        form.on('submit(navCategorySubmit)', function(data){
           $.ajax({
               url:'/nav/navbarCategory',
               type:'POST',
               data:data.field,
               dataType:'json',
               success:function(){
                   if(data){
                       /*父页面刷新*/
                       window.parent.location.reload();
                       /*关闭当前页面*/
                       var index=parent.layer.getFrameIndex(window.name);
                       parent.layer.close(index);
                       layer.alert('提交成功')
                   }else{
                       layer.alert('添加时出错，请重试！！')
                   }
               }
           })
            return false;
        });

    })
</script>
</html>