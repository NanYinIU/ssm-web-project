
$('#myModal2').on('hidden.bs.modal', function() {
    $('#form3').data('bootstrapValidator').destroy();
    $('#form3').data('bootstrapValidator', null);
    formValidator();
});
$(document).ready(function() {
    /**
     * 下面是进行插件初始化
     * 你只需传入相应的键值对
     * */
    formValidator();

});
function formValidator() {

    $('#form3').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {/*输入框不同状态，显示图片的样式*/
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {/*验证*/
            add_roleName: {/*键名username和input name值对应*/
                message: 'The username is not valid',
                validators: {
                    notEmpty: {/*非空提示*/
                        message: '角色名不能为空'
                    },
                    stringLength: {/*长度提示*/
                        min: 3,
                        max: 30,
                        message: '用户名长度必须在3到30之间'
                    }/*最后一个没有逗号*/,
                    remote:{
                        url: '/role/validRole',
                        message: '该角色名已存在',
                        delay :  2000,
                        type:'POST'
                        // data: function(validator) {
                        //     return {
                        //         txt_name1:$('#txt_name1').val()
                        //     };
                        // },
                    }
                }
            },
            add_describe: {
                message:'描述无效',
                validators: {
                    notEmpty: {
                        message: '角色描述不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 30,
                        message: '角色描述长度必须在2到30之间'
                    }
                }
            }


        }
    });

}


