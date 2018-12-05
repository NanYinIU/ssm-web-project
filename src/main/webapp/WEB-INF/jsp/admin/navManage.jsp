<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>navBar管理</title>
    <link rel="stylesheet" href="/plugins/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/ztree/metro/ztree.css"></head>

<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space30">
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
            <ul class="layui-tab-title">
                <li class="layui-this">一级navBar管理</li>
                <li >侧边栏NavBar管理</li>
            </ul>
            <div class="layui-tab-content" style="height: 100px;">
                <div class="layui-tab-item layui-show">
                    <script type="text/html" id="barDemo">
                        <a class="layui-btn  layui-btn-sm layui-btn-normal" lay-event="add">添加</a>
                        <a class="layui-btn  layui-btn-sm" lay-event="edit">编辑</a>
                        <a class="layui-btn  layui-btn-sm layui-btn-danger " lay-event="del">删除</a>
                    </script>
                    <table id="demo" lay-filter="test"></table>
                    <%--<div id="testLayPage"></div>--%>
                </div>
                <div class="layui-tab-item">
                    <table class="layui-hidden" id="treeTable" lay-filter="treeTable"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/plugins/layui/layui.js"></script>
<script type="text/html" id="iconTpl">
    <i class="layui-icon">{{d.icon}}</i>
</script>
</body>
<script>
    layui.use(['element','table','laypage'], function() {
        var $ = layui.jquery, element = layui.element,table=layui.table; //Tab的切换功能，切换事件监听等，需要依赖element模块
        var laypage = layui.laypage;
        element.on('tab(docDemoTabBrief)', function(data){
            console.log(data);
        });
        table.render({
            elem: '#demo' //指定原始表格元素选择器（推荐id选择器）
            ,height: 'full-100' //容器高度
            ,cols: [[ //表头
                {field: 'id', title: 'ID', width:50, sort: true, fixed: 'left'}
                ,{field: 'name', title: '名称', width:150}
                ,{field: 'icon', title: '图标', width:150, sort: true,templet:'#iconTpl'}
                ,{field: 'comment', title: '描述', width:300}
            ]] //设置表头
            ,url:'/nav/navbarCategoryTable'
//            ,defaultToolbar: ['filter', 'print', 'exports']
            ,toolbar:'#barDemo'

        });
        //监听工具条
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'del'){ //删除
                layer.confirm('真的删除行么', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    //向服务端发送删除指令
                });
            } else if(layEvent === 'edit'){ //编辑
                //do something

                //同步更新缓存对应的值
                obj.update({
                    username: '123'
                    ,title: 'xxx'
                });
            }
        });

//        laypage.render({
//            elem: 'testLayPage' //注意，这里的 test1 是 ID，不用加 # 号
//            ,count: 50 //数据总数，从服务端得到
//        });
    })


    /* edit treeTable */
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