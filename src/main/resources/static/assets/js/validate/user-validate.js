$(document).ready(function () {
    // validate
    $("#add").validate({
        errorClass: "is-invalid",
        success: "valid",
        ignore: "",
        validClass: "is-valid",
        errorElement: 'em',
        errorPlacement: function(error, element) {
            var elem = $(element);
            if (elem.hasClass("select2-hidden-accessible")) {
                element = elem.next();
                error.insertAfter(element);
            } else {
                error.insertAfter(element);
            }
        },
        rules: {
            name: "required",
            email: {
                required: true,
                email: true
            },
            auths: "required",
            status: "required",
            sex: "required"
        },
        messages: {
            name: "名称必填",
            email: {
                required: "邮箱字段必填",
                email: "Your email address must be in the format of name@domain.com"
            },
            auths: {
                required: "字段必填",
            },
            status: {
                required: "字段必填",
            },
            sex: {
                required: "字段必填",
            }
        },
    });


})