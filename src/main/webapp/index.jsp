
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->

    <script src="/static/js/jquery/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="/static/js/echarts.js" type="text/javascript"></script>
<body>
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
function loadOneColumn() {
    var myChart = echarts.init(document.getElementById('main'));
    // 显示标题，图例和空的坐标轴
    myChart.setOption({
        color: ['#ff7d27', '#47b73d', '#fcc36e', '#57a2fd', "#228b22"],//饼图颜色
        title: {
            text: '各部门人员分布情况',
            subtext: '及时统计',
            x:'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data: []
        },
        toolbox: {
            show: true,
            feature: {
                mark: { show: true },
                dataView: { show: true, readOnly: false },
                magicType: {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore: { show: true },
                saveAsImage: { show: true }
            }
        },
        series: [{
            name: '人员统计',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: []
        }]
    });
    myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
    var names = [];    //类别数组（用于存放饼图的类别）
    var brower = [];
    $.ajax({
        type: 'get',
        url: '/organization/resEchart',//请求数据的地址
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            $.each(result.list, function (index, item) {
                names.push(item.name);    //挨个取出类别并填入类别数组
                brower.push({
                    name: item.name,
                    value: item.count
                });
            });
            myChart.hideLoading();    //隐藏加载动画
            myChart.setOption({        //加载数据图表
                legend: {
                    data: names
                },
                series: [{
                    data: brower
                }]
            });
        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            myChart.hideLoading();
        }
    });
};
loadOneColumn();
</script>

</body>
</html>
