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

            <div class="layui-row" style="margin-top: 10px">
                <form class="layui-form" action="">
                    <div class="layui-form-item">
                        <div class="layui-col-md3">
                            <input type="text" name="name" id="name" autocomplete="off"
                                   placeholder="名称/图标class" class="layui-input">
                        </div>
                        <div class="layui-col-md2" style="margin-left: 10px">
                            <div class="layui-input-inline " style="width: 90px">
                                <a class="layui-btn" id="searchEmailCompany" data-type="reload" lay-submit lay-filter="iconsSearch">
                                    <i class="layui-icon" style="font-size: 20px; " ></i> 搜索
                                </a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
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
    layui.use(['element', 'table', 'laypage','form'], function () {
        var $ = layui.jquery;
        var element = layui.element;
        var table = layui.table; //Tab的切换功能，切换事件监听等，需要依赖element模块
        var laypage = layui.laypage;
        var form = layui.form
        /*获得分页总页数 开始*/
        $.ajax({
            url:"/icon/icon/count",
            type:'get',
            dataType:'json',
            success:function(data){
                $("#iconTableCount").val(data);
            }
        })

        /*获得分页总页数 结束*/
        /* 表格渲染开始 */
        var tableId = table.render({
             id:'iconTable'
            ,elem: '#icon' //指定原始表格元素选择器（推荐id选择器）
            ,title: '图标表'
            ,toolbar: '#toolbarDemo'
            ,height: 'full-150' //容器高度
            ,cols: [[ //表头
                {type: 'checkbox'}
                ,{type:'numbers'}
                ,{field: 'iconName', title: '图标名称', width: 150}
                ,{field: 'iconUnicode', title: '图标形状', width: 150, sort: true, templet: '#iconTpl'}
                ,{field: 'iconClass', title: '图标class', width: 300}
                ,{fixed: 'right', title:'操作', toolbar:'#barDemo', width:150}
            ]] //设置表头
            ,url: '/icon/icons'
            ,page: true
            ,where:{name:$("#name").val()}
        });
        /* 表格渲染结束 */
        // 查询条件，并重载
        form.on('submit(iconsSearch)', function(data){
            tableId.reload({
                // url: '/icon/icons',
                where: { //设定异步数据接口的额外参数，任意设
                    name: $("#name").val()
                }
            })
        })

        //监听表格内工具条
        table.on('tool(iconFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            // console.log(data);
            if (layEvent === 'del') { //删除
                layer.confirm('真的删除行么', function (index) {
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    //向服务端发送删除指令
                    $.ajax({
                        url:'/icon/icon/'+data.id,
                        type:'DELETE',
                        data:'{"_method":"DELETE",id:"'+data.id+'"}',
                        dataType:'json',
                        success:function(){
                            layer.alert('删除成功')
                        }
                    })
                });
            } else if (layEvent === 'edit') { //编辑
                layer.open({
                    title : "编辑图标",
                    type: 2,
                    area: ['820px', '400px'],
                    content: ['/icon/iconManage/modifyIconPage','no'], //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                    success:function (layero, index) {
                        var body = layui.layer.getChildFrame('body', index);
                        if(data){
                            // 取到弹出层里的元素，并把编辑的内容放进去
                            body.find("#id").val(data.id);  //将选中的数据的id传到编辑页面的隐藏域，便于根据ID修改数据
                            body.find("#iconName").val(data.iconName);  //将选中的数据的id传到编辑页面的隐藏域，便于根据ID修改数据
                            body.find("#iconUnicode").val(data.iconUnicode);  //密码
                            body.find("#iconClass").val(data.iconClass);  //登录时间
                        }
                    }
                });
            }
        });
        // 监听表格上方工具条
        table.on('toolbar(iconFilter)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    layer.open({
                        title : "添加图标",
                        type: 2,
                        area: ['820px', '400px'],
                        content: ['/icon/iconManage/addIconPage','no'] //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                    });
                    break;
                case 'delete':
                    layer.confirm('真的删除行么', function (index) {
                        var DeleteData = [];
                        var ids;
                        var checkStatus = table.checkStatus('iconTable');
                        data = checkStatus.data;
                        for(var x = 0;x<data.length;x++){
                            DeleteData[x] = data[x].id;
                        }
                        var requestData =  '{_method:"DELETE",ids:"'+DeleteData.join(",")+'"}'
                        $.ajax({
                            url:'/icon/icon/batch/'+DeleteData.join(","),
                            type:'DELETE',
                            data:requestData,
                            dataType:'json',
                            success:function(){
                                layer.alert('删除成功')
                                /*todo*/
                                for (var i = 0; i < DeleteData.length; i++) {
                                    $("tr[data-index='" + DeleteData[i] + "']").remove();
                                }
                            }
                        })
                        layer.close(index);
                    });
                    break;
                case 'update':
                    layer.msg('编辑');
                    break;
            };
        });
    });


</script>
</html>