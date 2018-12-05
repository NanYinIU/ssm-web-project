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
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
            <ul class="layui-tab-title">
                <li class="layui-this">网站设置</li>
                <li>用户管理</li>
                <li>权限分配</li>
                <li>商品管理</li>
                <li>订单管理</li>
            </ul>
            <div class="layui-tab-content" style="height: 100px;">
                <div class="layui-tab-item layui-show">
                    <table class="layui-hidden" id="treeTable" lay-filter="treeTable"></table>
                </div>
                <div class="layui-tab-item">内容2</div>
                <div class="layui-tab-item">内容3</div>
                <div class="layui-tab-item">内容4</div>
                <div class="layui-tab-item">内容5</div>
            </div>
        </div>

        <%--<div class="layui-col-md11" >--%>
            <%--<div class="layui-card">--%>
                <%--<div class="layui-card-header">--%>
                    <%--Navbar 自定义配置--%>
                <%--</div>--%>
                <%--<div class="layui-card-body">--%>
                <%--&lt;%&ndash;<ul id="ztree"></ul>&ndash;%&gt;--%>
                    <%--<table class="layui-hidden" id="treeTable" lay-filter="treeTable"></table>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>

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
    var editObj=null,ptable=null,treeGrid=null,tableId='treeTable',layer=null;
    layui.config({
        base: '/extend/'
    }).extend({
        treeGrid:'treeGrid'
    }).use(['jquery','treeGrid','layer'], function(){
        var $=layui.jquery;
        treeGrid = layui.treeGrid;//很重要
        layer=layui.layer;
        ptable=treeGrid.render({
            id:tableId
            ,elem: '#'+tableId
            ,idField:'id'
            ,url:'/datas/data2.json'
            ,cellMinWidth: 100
            ,treeId:'id'//树形id字段名称
            ,treeUpId:'pId'//树形父id字段名称
            ,treeShowName:'name'//以树形式显示的字段
            ,cols: [[
                {width:100,title: '操作', align:'center'/*toolbar: '#barDemo'*/
                    ,templet: function(d){
                    var html='';
                    var addBtn='<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="add">添加</a>';
                    var delBtn='<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
                    return addBtn+delBtn;
                }
                }
                ,{field:'name', edit:'text',width:300, title: '水果名称'}
                ,{field:'id',width:100, title: 'id'}
                ,{field:'pId', title: 'pid'}
            ]]
            ,page:false
        });

        treeGrid.on('tool('+tableId+')',function (obj) {
            if(obj.event === 'del'){//删除行
                del(obj);
            }else if(obj.event==="add"){//添加行
                add(obj.data);
            }
        });
    });

    function del(obj) {
        layer.confirm("你确定删除数据吗？如果存在下级节点则一并删除，此操作不能撤销！", {icon: 3, title:'提示'},
                function(index){//确定回调
                    obj.del();
                    layer.close(index);
                },function (index) {//取消回调
                    layer.close(index);
                }
        );
    }


    var i=1000;
    //添加
    function add(pObj) {
        var param={};
        param.name='水果'+Math.random();
        param.id=++i;
        param.pId=pObj?pObj.id:0;
        treeGrid.addRow(tableId,pObj?pObj.LAY_TABLE_INDEX+1:0,param);
    }

    function print() {
        console.log(treeGrid.cache[tableId]);
        var loadIndex=layer.msg("对象已打印，按F12，在控制台查看！", {
            time:3000
            ,offset: 'auto'//顶部
            ,shade: 0
        });
    }


</script>
</html>