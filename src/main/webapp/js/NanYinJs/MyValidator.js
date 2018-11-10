
$('#myModal').on('hidden.bs.modal', function() {
    $('#form123').data('bootstrapValidator').destroy();
    $('#form123').data('bootstrapValidator', null);
    formValidator();
});
$(document).ready(function() {
    /**
     * 下面是进行插件初始化
     * 你只需传入相应的键值对
     * */
    formValidator();

    var newdata = {};
        newdata.name = $("#txt_name1").val();
        var txt = [newdata]
});
function formValidator() {

    $('#form123').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {/*输入框不同状态，显示图片的样式*/
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {/*验证*/
            txt_name: {/*键名username和input name值对应*/
                message: 'The username is not valid',
                validators: {
                    notEmpty: {/*非空提示*/
                        message: '用户名不能为空'
                    },
                    stringLength: {/*长度提示*/
                        min: 3,
                        max: 30,
                        message: '用户名长度必须在3到30之间'
                    }/*最后一个没有逗号*/,
                    remote:{
                        url: '/mes/validNamed',
                        message: '用户已存在',
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
            txt_pass: {
                message:'密码无效',
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 3,
                        max: 30,
                        message: '用户名长度必须在3到30之间'
                    }
                }
            },
            txt_passSec: {
                message: '密码确认验证失败',
                validators: {
                    notEmpty: {
                        message: '密码确认不能为空'
                    },
                    identical: {
                        field: 'txt_pass',
                        message: '两次密码不相同'
                    }
                }
            },





        }
    });
    $('#form1234').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {/*输入框不同状态，显示图片的样式*/
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },

        fields: {/*验证*/
            txt_name1: {/*键名username和input name值对应*/
                message: 'The username is not valid',
                validators: {
                    notEmpty: {/*非空提示*/
                        message: '用户名不能为空'
                    },
                    stringLength: {/*长度提示*/
                        min: 3,
                        max: 30,
                        message: '用户名长度必须在3到30之间'
                    },
                    threshold :  2 ,
                    remote:{
                        url: '/mes/validName',
                        message: '用户已存在',
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
            txt_pass1: {
                message:'密码无效',
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 3,
                        max: 30,
                        message: '用户名长度必须在3到30之间'
                    }
                }
            },
            txt_passSec1: {
                message: '密码确认验证失败',
                validators: {
                    notEmpty: {
                        message: '密码确认不能为空'
                    },
                    identical: {
                        field: 'txt_pass1',
                        message: '两次密码不相同'
                    }
                }
            },





        }
    });
}


