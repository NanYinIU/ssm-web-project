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
            addHoverDom:addHoverDom,
            removeHoverDom:removeHoverDom,
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
    // 添加子部门操作
    var newCount = 0;
    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='添加' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_"+treeNode.tId);
        //var isAdded=false;
        if (btn) btn.bind("click", function(){
            newCount++;
            var organization = {};
            organization.name = "新部门" + newCount;
            organization.pId = treeNode.id;
            alert(JSON.stringify(organization));
            $.ajax({
                type : "POST",
                async : false,
                url : "/org/insertOrg",
                data :
                    // {
                    //     name : "新部门" + newCount,
                    //     pId:treeNode.id
                    // }
                     organization
                    ,
                success:function(result){
                    if(""!=result ){
                        var zTree = $.fn.zTree.getZTreeObj("planTree");
                        zTree.addNodes(treeNode, {id:result, pId:treeNode.id, name:"新部门" + (newCount)});
                        return false;
                    }else{
                        alert("无法添加新部门，请联系管理员！");
                    }
                }
            });
            return false;
        });
    };

// 用于当鼠标移出节点时，隐藏用户自定义控件
    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_"+treeNode.tId).unbind().remove();
    };



}