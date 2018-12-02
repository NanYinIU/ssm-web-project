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
        <div class="layui-col-md3" >
            <div class="layui-card">
                <div class="layui-card-header">
                    <div class="layui-btn-group">
                        <button class="layui-btn layui-btn-primary layui-btn-sm">
                            <i class="layui-icon">添加 &#xe654;</i>
                        </button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm">
                            <i class="layui-icon">编辑 &#xe642;</i>
                        </button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm">
                            <i class="layui-icon">删除 &#xe640;</i>
                        </button>
                    </div>
                </div>
                <div class="layui-card-body">
                <ul id="ztree" class="ztree"></ul>
                </div>
            </div>
        </div>
        <div class="layui-col-md8" >
            <div class="layui-card">
                <div class="layui-card-header">
                    <div class="layui-btn-group">
                        <button class="layui-btn layui-btn-primary layui-btn-sm">
                            <i class="layui-icon">添加 &#xe654;</i>
                        </button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm">
                            <i class="layui-icon">编辑 &#xe642;</i>
                        </button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm">
                            <i class="layui-icon">删除 &#xe640;</i>
                        </button>
                    </div>
                </div>
                <div class="layui-card-body">
                    <table class="layui-table" lay-even lay-skin="nob">
                        <colgroup>
                            <col width="150">
                            <col width="200">
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>昵称</th>
                            <th>加入时间</th>
                            <th>签名</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>贤心</td>
                            <td>2016-11-29</td>
                            <td>人生就像是一场修行</td>
                        </tr>
                        <tr>
                            <td>许闲心</td>
                            <td>2016-11-28</td>
                            <td>于千万人之中遇见你所遇见的人，于千万年之中，时间的无涯的荒野里…</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                </div>
        </div>

    </div>
</div>
<script type="text/javascript" src="/plugins/layui/layui.js"></script>
</body>
<script>
    var message;
    layui.config({
        base: '../js/'
    }).use(['treeselect','layer'], function() {
        var treeslect = layui.treeslect,
                $ = layui.jquery,
                layer = layui.layer;
        //主入口
        var zNodes = [{
            id: 1,
            pId: 0,
            name: "父节点1",
            open: true
        }, {
            id: 11,
            pId: 1,
            name: "父节点11"
        }, {
            id: 111,
            pId: 11,
            name: "叶子节点111"
        }, {
            id: 112,
            pId: 11,
            name: "叶子节点112"
        }, {
            id: 113,
            pId: 11,
            name: "叶子节点113"
        }, {
            id: 114,
            pId: 11,
            name: "叶子节点114"
        }, {
            id: 12,
            pId: 1,
            name: "父节点12"
        }, {
            id: 121,
            pId: 12,
            name: "叶子节点121"
        }, {
            id: 122,
            pId: 12,
            name: "叶子节点122"
        }, {
            id: 123,
            pId: 12,
            name: "叶子节点123"
        }, {
            id: 124,
            pId: 12,
            name: "叶子节点124"
        }, {
            id: 13,
            pId: 1,
            name: "父节点13",
            isParent: true
        }, {
            id: 2,
            pId: 0,
            name: "父节点2"
        }, {
            id: 21,
            pId: 2,
            name: "父节点21",
            open: true
        }, {
            id: 211,
            pId: 21,
            name: "叶子节点211"
        }, {
            id: 212,
            pId: 21,
            name: "叶子节点212"
        }, {
            id: 213,
            pId: 21,
            name: "叶子节点213"
        }, {
            id: 214,
            pId: 21,
            name: "叶子节点214"
        }, {
            id: 22,
            pId: 2,
            name: "父节点22"
        }, {
            id: 221,
            pId: 22,
            name: "叶子节点221"
        }, {
            id: 222,
            pId: 22,
            name: "叶子节点222"
        }, {
            id: 223,
            pId: 22,
            name: "叶子节点223"
        }, {
            id: 224,
            pId: 22,
            name: "叶子节点224"
        }, {
            id: 23,
            pId: 2,
            name: "父节点23"
        }, {
            id: 231,
            pId: 23,
            name: "叶子节点231"
        }, {
            id: 232,
            pId: 23,
            name: "叶子节点232"
        }, {
            id: 233,
            pId: 23,
            name: "叶子节点233"
        }, {
            id: 234,
            pId: 23,
            name: "叶子节点234"
        }, {
            id: 3,
            pId: 0,
            name: "父节点3",
            isParent: true
        }];

        var setting = {
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            edit: {
                enable: true
            },
            callback: {
                onClick: function(e, treeId, treeNode) {
                    console.log(treeNode);
                }
            }
        };


        $(document).ready(function() {
            $.fn.zTree.init($("#ztree"), setting, zNodes);
        });

        var newCount = 1;

        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
                return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId +
                    "' title='add node' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_" + treeNode.tId);
            if (btn) {
                btn.bind("click", function() {
                    var zTree = $.fn.zTree.getZTreeObj("ztree");
                    zTree.addNodes(treeNode, {
                        id: (100 + newCount),
                        pId: treeNode.id,
                        name: "new node" + (newCount++)
                    });
                    return false;
                });
            }
        };

        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_" + treeNode.tId).unbind().remove();
        };
    });
</script>
</html>