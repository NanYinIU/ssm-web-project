<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>icon管理</title>
    <link rel="stylesheet" href="/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="/plugins/font-awesome/css/font-awesome.min.css">
</head>

<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space30">
        <div class="layui-tab-item layui-show">
            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
                    <button class="layui-btn layui-btn-sm" lay-event="delete">批量删除</button>
                </div>
            </script>
            <script type="text/html" id="barDemo">
                <a class="layui-btn  layui-btn-sm" lay-event="edit">编辑</a>
                <a class="layui-btn  layui-btn-sm layui-btn-danger" lay-event="del">删除</a>
            </script>
            <table class="layui-hide" id="icon" lay-filter="iconFilter"></table>
            <div id="laypage"></div>
        </div>
    </div>
</div>

<input type="hidden" id="iconTableCount">
<script type="text/javascript" src="/plugins/layui/layui.js"></script>

<script type="text/html" id="iconTpl">
   <i class="layui-icon">{{d.iconUnicode}}</i>
</script>

</body>
<script>
    layui.use(['element', 'table', 'laypage'], function () {
        var $ = layui.jquery;
        var element = layui.element;
        var table = layui.table; //Tab的切换功能，切换事件监听等，需要依赖element模块
        var laypage = layui.laypage;
        /*获得分页总页数 开始*/
        $.ajax({
            url:"/icon/count",
            type:'get',
            dataType:'json',
            success:function(data){
                $("#iconTableCount").val(data);
            }
        })
        var pageCount = $("#iconTableCount").val();
        /*获得分页总页数 结束*/
        /* 表格渲染开始 */
        table.render({
             id:'iconTable'
            ,elem: '#icon' //指定原始表格元素选择器（推荐id选择器）
            ,title: '图标表'
            ,toolbar: '#toolbarDemo'
            ,height: 'full-100' //容器高度
            ,cols: [[ //表头
                {type: 'checkbox'}
                ,{type:'numbers'}
                ,{field: 'iconName', title: '图标名称', width: 150}
                ,{field: 'iconUnicode', title: '图标形状', width: 150, sort: true, templet: '#iconTpl'}
                ,{field: 'iconClass', title: '图标class', width: 300}
                ,{fixed: 'right', title:'操作', toolbar:'#barDemo', width:150}
            ]] //设置表头
            , url: '/icon/iconInfo'
        });
        /* 表格渲染结束 */

        /*分页空间渲染*/
        laypage.render({
            elem: 'laypage' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: pageCount //数据总数，从服务端得到
            ,limit:10
            ,limits:[10,20,30,50]
            ,jump: function(obj, first){
                //obj包含了当前分页的所有参数，比如：
                console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                console.log(obj.limit); //得到每页显示的条数

                //首次不执行
                if(!first){
                    //do something
                }
            }
        });
        /*分页空间渲染 结束*/

        //监听表格内工具条
        table.on('tool(iconFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            console.log(data);
            if (layEvent === 'del') { //删除
                layer.confirm('真的删除行么', function (index) {
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
//                    var checkStatus = table.checkStatus('iconTable');
                    layer.close(index);
                    //向服务端发送删除指令
                    $.ajax({
                        url:'/oneLevelBar/'+data.id,
                        type:'POST',
                        data:'{_method:"DELETE",id:"'+data.id+'"}',
                        dataType:'json',
                        success:function(){
                            layer.alert('删除成功')
                        }
                    })
                });
            } else if (layEvent === 'edit') { //编辑
                //同步更新缓存对应的值
                obj.update({
                    username: '123'
                    , title: 'xxx'
                });
            } else if (layEvent === 'add') { //编辑
                //do something
                //打开 iframe
                //同步更新缓存对应的值
                obj.update({
                    username: '123'
                    , title: 'xxx'
                });
            }
        });
        // 监听表格上方工具条
        table.on('toolbar(iconFilter)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    layer.open({
                        type: 2,
                        area: ['820px', '400px'],
                        content: ['/icon/addIconPage','no'] //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                    });
                    break;
                case 'delete':
                    layer.msg('删除');
                    break;
                case 'update':
                    layer.msg('编辑');
                    break;
            };
        });
    });


</script>
</html>