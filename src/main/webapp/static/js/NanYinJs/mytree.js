$(function () {

    init();
});

function init() {
    builePlanTree();
}


//ZTREE
function builePlanTree() {
    var setting = {
        view: {
            showIcon: true//设置 zTree 是否显示节点的图标。
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0
            }
        },
        edit:{
            drag:{
                isMove:true,
                prev:true,
                autoOpenTime: 0
            },
            enable:true,
            editNameSelectAll:true,
            showRemoveBtn : true,
            showRenameBtn : true,
            removeTitle : "remove",
            renameTitle : "rename",
        },
        view:{
            // addHoverDom:addHoverDom,
            // removeHoverDom:removeHoverDom,
            selectedMulti:true
            ,showIcon:true,
            showLine:true,
        },
        callback: {
            beforeRename:beforeRename,
            beforeRemove:beforeRemove,
            beforeDrop: BeforeDrop
        }
    }
    var zNodes;
    var zTree;
    $(function() {
        $.ajax({
            async: true,
            cache: false,
            type: 'POST',
            dataType: "json",
            contentType:"application/json;UTF-8",
            url: "/organization/selectOrganizationVo",//请求的action路径
            error: function () {//请求失败处理函数
                alert('请求失败');
            },
            success: function (data) { //请求成功后处理函数。
                // alert(data);
                zNodes = data; //把后台封装好的简单Json格式赋给treeNodes
                 $.fn.zTree.init($("#planTree"), setting, zNodes);
            }
        });
    })

    
    function beforeRename(treeId, treeNode, newName, isCancel) {
       if(newName.length == 0){
           setTimeout(function () {
               zTree.cancelEditName();
               alert("节点名称不能为空")
           },0)
           return false;
       }else {
           var data = {"id":treeNode.id,"name":newName};
           $.ajax({
               url:"/org/updateOrgName",
               type:"post",
               async:true,
               dataType:"json",contentType: "application/json; charset=utf-8",
               data:JSON.stringify(data),
               success:function(data,status){
                   if(status == "success"){
                       toastr.success('提交数据成功');

                   }
               },
               error: function () {
                               toastr.error('Error');
                           },
           });
           return true;
       }

    }


    function beforeRemove(treeId, treeNode, newName, isCancel) {

            var data = {"id":treeNode.id,"name":treeNode.name};
            $.ajax({
                url:"/org/delectOrgNode",
                type:"post",
                async:true,
                dataType:"json",contentType: "application/json; charset=utf-8",
                data:JSON.stringify(data),
                success:function(data,status){
                    if(status == "success"){
                        toastr.success('提交数据成功');
                    }
                },
                error: function () {
                    toastr.error('Error');
                },
            });
            return true;
        }

    /**
     * @return {boolean}
     */
    function BeforeDrop(treeId, treeNodes, targetNode, moveType) {
        // 原来的id
        var id = treeNodes[0].id;
        var name = treeNodes[0].name;
        //目标的id 也就是需要改为id的pid的值
        var targetPid = targetNode.id;
        var data = {"id":id,"name":name,"pId":targetPid};
        $.ajax({
            url:"/organization/updateNode",
            type:"post",
            async:true,
            dataType:"json",contentType: "application/json; charset=utf-8",
            data:JSON.stringify(data),
            success:function(data,status){
                if(status == "success"){
                    toastr.success('提交数据成功');
                }
            },
            error: function () {
                toastr.error('Error');
            }
        });
        return true ;
    }



}