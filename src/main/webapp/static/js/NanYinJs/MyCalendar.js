
$(document).ready(function() {
    artDialog.notice = function (options) {
        var opt = options || {},
            api, aConfig, hide, wrap, top,
            duration = 800;

        var config = {
            id: 'Notice',
            left: '100%',
            top: '100%',
            fixed: true,
            drag: false,
            resize: false,
            follow: null,
            lock: false,
            init: function(here){
                api = this;
                aConfig = api.config;
                wrap = api.DOM.wrap;
                top = parseInt(wrap[0].style.top);
                hide = top + wrap[0].offsetHeight;

                wrap.css('top', hide + 'px')
                    .animate({top: top + 'px'}, duration, function () {
                        opt.init && opt.init.call(api, here);
                    });
            },
            close: function(here){
                wrap.animate({top: hide + 'px'}, duration, function () {
                    opt.close && opt.close.call(this, here);
                    aConfig.close = $.noop;
                    api.close();
                });

                return false;
            }
        };

        for (var i in opt) {
            if (config[i] === undefined) config[i] = opt[i];
        };

        return artDialog(config);
    };
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    $('#calendar').fullCalendar({
        theme: true,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        //fullcalendar本地化
        //timeFormat:{agenda: 'h:mm TT{ - h:mm TT}'}, //默认是{agenda: ‘h:mm{ - h:mm}}, 影响的是添加的具体的日程上显示的时间格式.
        monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
        dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
        today: ["今天"],
        firstDay: 1,
        buttonText: {
            today: '今天',
            month: '月',
            week: '周',
            day: '日',
            prev: '上一月',
            next: '下一月'
        },
        allDaySlot:false,
        selectable: true,
        selectHelper: true,
        aspectRatio:2.7,
        editable: false,
        allDayDefault:false,
        viewDisplay: function(view) {
            var viewStart = $.fullCalendar.formatDate(view.start,"yyyy-MM-dd HH:mm:ss");
            var viewEnd = $.fullCalendar.formatDate(view.end,"yyyy-MM-dd HH:mm:ss");
            $("#calendar").fullCalendar('removeEvents');
            $.getJSON('http://localhost:8080/pnote/schedule/listevents.action',{start:viewStart,end:viewEnd},function(data) {
                for(var i=0;i<data.length;i++) {
                    var obj = new Object();
                    obj.id = data[i].id;
                    obj.title = data[i].title;
                    obj.description = data[i].description;
                    obj.color = data[i].color;
                    obj.remindertime = $.fullCalendar.parseDate(data[i].remindertime);
                    obj.messagenotice = data[i].messagenotice;
                    obj.description = data[i].description;
                    obj.start = $.fullCalendar.parseDate(data[i].start);
                    obj.end = $.fullCalendar.parseDate(data[i].end);
                    $("#calendar").fullCalendar('renderEvent',obj,true);
                }
            }); //把从后台取出的数据进行封装以后在页面上以fullCalendar的方式进行显示
        },
        eventMouseover: function(event, jsEvent, view){
            showDetail(event, jsEvent);
        },
        eventMouseout: function(event, jsEvent, view){
            $('#tip').remove();
        },
        //日程点击：添加日程
        dayClick: function(date, allDay, jsEvent, view) {
            var obj =new Object();
            art.dialog.open('newschedule.html',{
                title: '添加日程',
                lock: true,
                width:300,
                height:400,
                fixed: true, //固定定位
                //background: '#600', // 背景色
                opacity: 0.6,   // 透明度
                // 在open()方法中，init会等待iframe加载完毕后执行
                init: function () {
                    var iframe = this.iframe.contentWindow;
                    //var top = art.dialog.top;// 引用顶层页面window对象
                    var start = iframe.document.getElementById('form-start');
                    start.value = $.fullCalendar.formatDate(date,"yyyy-MM-dd HH:mm:ss");
                },
                okVal:'提交日程',
                ok: function () {
                    var iframe = this.iframe.contentWindow;
                    if (!iframe.document.body) {
                        alert('iframe还没加载完毕呢');
                        return false;
                    };

                    var start = iframe.document.getElementById('form-start').value;
                    var end = iframe.document.getElementById('form-end').value;
                    var remindertime = iframe.document.getElementById('form-remindertime').value;
                    obj.title = iframe.document.getElementById('form-title').value;
                    obj.description = iframe.document.getElementById('form-description').value;
                    obj.start = $.fullCalendar.parseDate(start);
                    obj.end = $.fullCalendar.parseDate(end);
                    obj.color = iframe.document.getElementById('form-color').value;
                    if (obj.title== '') {
                        alert("标题不能为空");
                        return false;
                    }else if(start== '') {
                        alert("开始日期不能为空");
                        return false;
                    }else if(end == '') {
                        alert("结束日期不能为空");
                        return false;
                    }else if(iframe.document.getElementById('form-messagenotice').checked) {
                        if(remindertime == '') {
                            alert("短信提醒时间不能为空");
                            return false;
                        }
                        obj.messagenotice = 1;
                        obj.remindertime = $.fullCalendar.parseDate(remindertime);
                    }else {
                        obj.messagenotice = 0;
                        obj.remindertime = null;
                    }

                    $.post("http://localhost:8080/pnote/schedule/addevents",{//把刚输入的日程计划信息传到后台，保存到数据库
                            title: obj.title,
                            start:start,
                            end:end,
                            description:obj.description,
                            remindertime:remindertime,
                            color:obj.color,
                            messagenotice:obj.messagenotice
                        },
                        function (data, textStatus){
                            obj.id = data[0].id;
                        }, "json"
                    );
                    $('#calendar').fullCalendar('renderEvent', obj);  //核心的更新代码
                    $('#calendar').fullCalendar('unselect');
                    art.dialog.notice({
                        title: '笔记之家',
                        width: 150,// 必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致artDialog收缩
                        content: '日程已添加至后台！',
                        icon: 'face-smile',
                        time: 3
                    });
                    return true;
                },
                cancel: true
            });
        },
        eventClick:function(calEvent, jsEvent, view){
            art.dialog.open('newschedule.html', {
                title: '更新日程',
                lock: true,
                width:300,
                height:400,
                //background: '#600', // 背景色
                opacity: 0.6,   // 透明度
                // 在open()方法中，init会等待iframe加载完毕后执行
                init: function () {
                    var iframe = this.iframe.contentWindow;
                    //var top = art.dialog.top;// 引用顶层页面window对象
                    iframe.document.getElementById('form-start').value = $.fullCalendar.formatDate(calEvent.start,"yyyy-MM-dd HH:mm:ss");
                    iframe.document.getElementById('form-end').value = $.fullCalendar.formatDate(calEvent.end,"yyyy-MM-dd HH:mm:ss");
                    iframe.document.getElementById('form-description').value = calEvent.description;
                    iframe.document.getElementById('form-title').value = calEvent.title;
                    if(calEvent.messagenotice == 1) {
                        iframe.document.getElementById('form-messagenotice').checked = true;
                        iframe.document.getElementById('form-remindertime').value =  $.fullCalendar.formatDate(calEvent.remindertime,"yyyy-MM-dd HH:mm:ss");
                        iframe.document.getElementById('showtxt').style.display='block';
                    }else {
                    }
                    iframe.document.getElementById('form-color').value = calEvent.color;
                },
                okVal:'修改日程',
                ok: function () {
                    var iframe = this.iframe.contentWindow;
                    if (!iframe.document.body) {
                        alert('iframe还没加载完毕呢')
                        return false;
                    };

                    var start = iframe.document.getElementById('form-start').value;
                    var end = iframe.document.getElementById('form-end').value;
                    var remindertime = iframe.document.getElementById('form-remindertime').value;
                    calEvent.title = iframe.document.getElementById('form-title').value;
                    calEvent.description = iframe.document.getElementById('form-description').value;
                    calEvent.start = $.fullCalendar.parseDate(start);
                    calEvent.end = $.fullCalendar.parseDate(end);
                    calEvent.color = iframe.document.getElementById('form-color').value;
                    if (calEvent.title== '') {
                        alert("标题不能为空");
                        return false;
                    }else if(start== '') {
                        alert("开始日期不能为空");
                        return false;
                    }else if(end == '') {
                        alert("结束日期不能为空");
                        return false;
                    }else if(iframe.document.getElementById('form-messagenotice').checked) {
                        if(remindertime == '') {
                            alert("短信提醒时间不能为空");
                            return false;
                        }
                        calEvent.messagenotice = 1;
                        calEvent.remindertime = $.fullCalendar.parseDate(remindertime);
                    }else {
                        calEvent.messagenotice = 0;
                        calEvent.remindertime = null;
                    }
                    $.post("http://localhost:8080/pnote/schedule/updateevents",{//把要更新的日程计划信息传到后台，保存到数据库
                            id:calEvent.id,
                            title: calEvent.title,
                            start:start,
                            end:end,
                            description:calEvent.description,
                            color:calEvent.color,
                            remindertime:remindertime,
                            messagenotice:calEvent.messagenotice
                        }
                    );
                    $('#calendar').fullCalendar('updateEvent', calEvent);
                    //弹出提示
                    art.dialog.notice({
                        title: '笔记之家',
                        width: 150,// 必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致artDialog收缩
                        content: '日程已更新！',
                        icon: 'face-smile',
                        time: 3
                    });
                    return true;
                },
                cancel: true,
                //删除日程，保存到数据库
                button: [{
                    name: '删除日程',
                    callback: function () {
                        //this.content('你同意了').time(2);
                        $.post("http://localhost:8080/pnote/schedule/deleteevents",{
                            id:calEvent.id
                        });
                        $('#calendar').fullCalendar('removeEvents',calEvent.id);
                        art.dialog.notice({
                            title: '笔记之家',
                            width: 150,// 必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致artDialog收缩
                            content: '日程已删除！',
                            icon: 'face-smile',
                            time: 3
                        });
                        return true;
                    }
                }]
            });
        }
    });
});

function showDetail(obj, e){
    var str;
    if(obj.messagenotice == 1)  str = "短信提醒时间："+$.fullCalendar.formatDate(obj.remindertime,"yyyy-MM-dd HH:mm:ss");
    else {str = "短信提醒未启动";}
    var eInfo = '<div id="tip"><ul>';
    eInfo += '<li class="clock">' + '开始：'+$.fullCalendar.formatDate(obj.start,"yyyy-MM-dd HH:mm:ss") +'</br>结束：'+$.fullCalendar.formatDate(obj.end,"yyyy-MM-dd HH:mm:ss")+ '</li>';
    eInfo += '<li class="message">' +'日志：'+ obj.description + '<br/> </li>';
    //eInfo += '<li>分类：' + obj.title + '</li>';
    eInfo += '<li class="postmessage">' + str + '<br/> </li>';
    eInfo += '</ul></div>';
    $(eInfo).appendTo($('body'));
    $('#tip').css({"opacity":"0.4", "display":"none", "left":(e.pageX + 20) + "px", "top":(e.pageY + 10) + "px"}).fadeTo(600, 0.9);
    //鼠标移动效果
    $('.fc-event-inner').mousemove(function(e){
        $('#tip').css({'top': (e.pageY + 10), 'left': (e.pageX + 20)});
    });
}
