<%--
  Created by IntelliJ IDEA.
  User: ggx
  Date: 17-8-15
  Time: 上午11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>calendar</title>


    <script src="/static/js/jquery/jquery-ui.min.css"></script>

    <link href='/static/css/fullcalendar.min.css' rel='stylesheet' />
    <link href='/static/css/fullcalendar.print.min.css' rel='stylesheet' media="print" />
    <script src="/static/js/jquery/moment.min.js"></script>
    <script src="/static/js/jquery/jquery-1.11.1.min.js"></script>
    <script src="/static/js/jquery/fullcalendar.min.js"></script>


    <script src="/static/js/bootstrap/bootstrap.js"></script>
    <link href="/static/css/bootstrap.css" rel="stylesheet" />
    <link href="/static/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
    <script src="/static/js/bootstrap/bootstrap-datetimepicker.min.js"></script>
    <script src="/static/js/bootstrap/toastr.min.js"></script>
    <script>

        $(document).ready(function() {
            var date = new Date();
            var username = $("#user").val();
            $('#calendar').fullCalendar({

                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'month,agendaWeek,agendaDay,listMonth'
                },
                defaultDate: date,
                navLinks: true, // can click day/week names to navigate views
                editable: true,
                eventLimit: true, // allow "more" link when too many events
                //agenda视图下是否显示all-day
                allDaySlot: true,
//agenda视图下all-day的显示文本
                allDayText: '全天',
                events:'/cal/allEvent',
                dayClick:function (date) {
                    console.log('dayClick触发的时间为：', date.format());
                    $("#addStart").val(date.format('YYYY-MM-DD HH:mm:ss'));
                    $("#myModalLabel").text("新建事件");
                    $('#myModal').modal()
                },
                eventClick:function (event) {
//
                    $("#myModalLabel2").text("修改事件");
                    $("#hiddenId").val(event.id);
                    $("#txt2_title").val(event.title);
                    $("#updateStart").val(event.start.format('YYYY-MM-DD HH:mm:ss'));
                    $("#updateEnd").val(event.end.format('YYYY-MM-DD HH:mm:ss'));
                    $('#myModal2').modal()

                },
                //#region 鼠标放上去显示信息
                eventMouseover: function (calEvent, jsEvent, view) {
                    var isFinish ;
                    if(calEvent.color == '#FFEBAC'){
                        isFinish = '已完成';
                    }else {
                        isFinish = '未完成'
                    }
                    $(this).attr('title', calEvent.title +"    "+ isFinish);
                    $(this).css('font-weight', 'normal');

                },



            });

        });

    </script>
    <style>

        body {
            margin: 40px 10px;
            padding: 0;
            font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
            font-size: 14px;
        }

        #calendar {
            max-width: 900px;
            margin: 0 auto;
        }

    </style>

</head>

<body>

    <input type="hidden" value="${user}" id="user"/>
    <div id='calendar'></div>

    <%-- 添加开始 --%>
    <div class="modal small fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="myModalLabel">新建事件</h3>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row-fluid">
                            <div class="span12">

                                <form class="form-horizontal" method="post" id="form1" name="form1">
                                    <%--表单开始--%>
                                    <div class="form-group">
                                        <label class="control-label" >日程内容：</label>
                                        <div class="controls">
                                            <input  id="txt_title" name="txt_title" type="text" class="form-control"/>
                                        </div>
                                    </div>


                                        <div class="form-group">
                                            <%--<label class="control-label">开始时间:</label>--%>
                                            <div class="form-group">
                                                <label for="new_input1" class="col-md-2 control-label">开始时间：</label>
                                                <div class="input-group date form_datetime col-md-8 " data-date="2017-08-16T05:25:07Z" data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="new_input1">
                                                    <input class="form-control" size="16" type="text" id="addStart" name="addStart" value="" readonly>
                                                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                                    <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                                </div>
                                                <input type="hidden" id="new_input1" value="" /><br/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="form-group">
                                                <label for="new_input2" class="col-md-2 control-label">结束时间：</label>
                                                <div class="input-group date form_datetime col-md-8" data-date="2017-08-16T05:25:07Z" data-date-format="yyyy-mm-dd  HH:ii:ss" data-link-field="new_input2">
                                                    <input class="form-control" size="16" type="text" id="addEnd" name="addEnd" value="" readonly>
                                                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                                    <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                                </div>
                                                <input type="hidden" id="new_input2" value="" /><br/>
                                            </div>
                                        </div>
                                    <div class="form-group">
                                        <div class="controls">
                                            <button id="NewEvent" name="NewEvent" type="submit" class="btn btn-success">提交</button>
                                        </div>
                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">取消</button>
                </div>
            </div>
        </div>
    </div>
        <%--添加的js--%>
        <script type="text/javascript">
            var postdata = {};
            $("#NewEvent").click(function () {
                postdata.title = $('#txt_title').val();
                postdata.start = $('#addStart').val();
                postdata.end = $('#addEnd').val();
                var calendar = [postdata];
                $.ajax({
                    type:"post",
                    url:"/cal/insertEvent",
                    data:
                        JSON.stringify(calendar),
                    dataType: "json",contentType:"application/json;UTF-8",
                    success:function (data,statues) {
                        if(statues == "success"){
                            toastr.success('提交数据成功');
                        }
                    },
                    error:function () {
                        toastr.error('发生错误 提交失败');
                    }
                })
            })
        </script>
        <%--添加的js over--%>
    <%--添加结束--%>

    <%--修改开始--%>
    <div class="modal small fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="myModalLabel2">新建事件</h3>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row-fluid">
                            <div class="span12">

                                <form class="form-horizontal" id="form2" name="form1">
                                    <%--表单开始--%>
                                    <input type="hidden" id="hiddenId" name="hiddenId"/>
                                    <div class="form-group">
                                        <label class="control-label" >日程内容：</label>
                                        <div class="controls">
                                            <input id="txt2_title" type="text" class="form-control"/>
                                        </div>
                                    </div>
                                        <div class="form-group">
                                        <%--<label class="control-label">开始时间:</label>--%>
                                        <div class="form-group">
                                            <label for="dtp_input1" class="col-md-2 control-label">开始时间：</label>
                                            <div class="input-group date form_datetime col-md-8 " data-date="2017-08-16T05:25:07Z" data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="dtp_input1">
                                                <input class="form-control" size="16" type="text" id="updateStart" value="" >
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                            </div>
                                            <input type="hidden" id="dtp_input1" value="" /><br/>
                                        </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="form-group">
                                                <label for="dtp_input2" class="col-md-2 control-label">结束时间：</label>
                                                <div class="input-group date form_datetime col-md-8" data-date="2017-08-16T05:25:07Z" data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="dtp_input2">
                                                    <input class="form-control" size="16" id="updateEnd" type="text" value="" >
                                                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                                    <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                                </div>
                                                <input type="hidden" id="dtp_input2" value="" /><br/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="switch">
                                                &nbsp;&nbsp;<input type="checkbox" name="ck" value="1" id="className"/> <b>&nbsp; &nbsp;是否完成</b>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                        <div class="controls">
                                            <button id="updateEvent2" name="updateEvent2" type="submit" class="btn btn-success">提交</button>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <button id="DelectEvent2" name="DelectEvent2" type="submit" class="btn btn-danger">删除事件</button>
                                        </div>
                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">取消</button>
                </div>
            </div>
        </div>
    </div>
        <%--修改删除js开始--%>
        <script type="text/javascript">
            var update = {};
            $("#updateEvent2").click(function () {
                update.id = $('#hiddenId').val();
                update.title = $('#txt2_title').val();
                update.start = $('#updateStart').val();
                update.end = $('#updateEnd').val();
                update.className = $("input:checkbox:checked").val();
                var up = [update];
                $.ajax({
                    type:"post",
                    url:"/cal/updateEvent",
                    data:
                        JSON.stringify(up),
                    dataType: "json",contentType:"application/json;UTF-8",
                    success:function (data,statues) {
                        if(statues == "success"){
                            toastr.success('提交数据成功');
                        }
                    },
                    error:function () {
                        toastr.error('发生错误 提交失败');
                    }
                })
            })
        </script>

        <script type="text/javascript">

            $("#DelectEvent2").click(function () {
                var Hid = $('#hiddenId').val();
                Ddate = {"id":Hid};
                $.ajax({
                    type:"post",
                    url:"/cal/delectEvent",
                    data:
                        JSON.stringify(Ddate),
                    dataType: "json",contentType:"application/json;UTF-8",
                    success:function (data,statues) {
                        if(statues == "success"){
                            toastr.success('提交数据成功');
                        }
                    },
                    error:function () {
                        toastr.error('发生错误 提交失败');
                    }
                })
            })
        </script>
        <%--修改删除js结束--%>
    <%--修改结束--%>
    <script type="text/javascript">
        $('.form_datetime').datetimepicker({
            //language:  'fr',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1
        });
    </script>

</body>
</html>
