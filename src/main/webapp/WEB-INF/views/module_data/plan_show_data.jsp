<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="<c:url value="/resources/jslib/DateUtil.js"/>"></script>
<div class="easyui-layout" style="width: 100%;height: 97%;border: none;">
    <div class="easyui-tabs" data-options="region:'center',closable:false,collapsible:false,tools:'#tab-tools'"
         style="width: 100%;height: 97%; overflow: hidden;">

        <div title="汇总数据">
            <table id="tb_all" class="easyui-datagrid" style="height: 98%;width: 100%;"
                   data-options="
       singleSelect:true,
       rownumbers:true,
       method:'get',
       fitColumns:true,
    ">
                <thead>
                <tr>
                    <th data-options="field:'devnb',align:'center',width:80">设备编号</th>
                    <th data-options="field:'prs_str',align:'center',width:80">压力</th>
                    <th data-options="field:'avgPrs',align:'center',width:80">平均压力</th>
                    <th data-options="field:'hzjc_str',align:'center',width:80">荷载</th>
                    <th data-options="field:'avgHzjc',align:'center',width:80">平均荷载</th>
                    <th data-options="field:'wyjc_str',align:'center',width:80">位移</th>
                    <th data-options="field:'avgWyjc',align:'center',width:80">平均位移</th>
                    <th data-options="field:'gps',align:'center',formatter:function(obj,row){return row.lat+','+row.lng},width:100">
                        GPS信息
                    </th>
                    <th data-options="field:'devstr',align:'center',width:80">设备代号</th>
                    <th data-options="field:'time',align:'center',width:120">数据上传时间</th>
                    <th data-options="field:'interval',align:'center',width:30">时间间隔</th>
                    <th data-options="field:'qjx_str',align:'center',width:80">倾角数据</th>
                    <th data-options="field:'avgQjx',align:'center',width:80">平均倾角数据</th>
                    <th data-options="field:'ndsj_str',align:'center',width:80">挠度数据</th>
                    <th data-options="field:'avgNdsj',align:'center',width:80">平均挠度数据</th>
                    <th data-options="field:'devst',align:'center',width:80">设备状态</th>
                </tr>
                </thead>
            </table>
        </div>

        <div title="加载数据">
            <table id="tb_source_press" class="easyui-datagrid" style="height: 98%;width: 100%;"
                   data-options="
       singleSelect:true,
       rownumbers:true,
       method:'get',
       fitColumns:true,
    ">
                <thead>
                <tr>
                    <th data-options="field:'devnb',align:'center',width:80">设备编号</th>
                    <th data-options="field:'prs_str',align:'center',width:80">压力</th>
                    <th data-options="field:'hzjc_str',align:'center',width:80">荷载</th>
                    <th data-options="field:'wyjc_str',align:'center',width:80">位移</th>
                    <th data-options="field:'gps',align:'center',formatter:function(obj,row){return row.lat+','+row.lng},width:100">
                        GPS信息
                    </th>
                    <th data-options="field:'devstr',align:'center',width:80">设备代号</th>
                    <th data-options="field:'time',align:'center',width:120">数据上传时间</th>
                    <th data-options="field:'interval',align:'center',width:30">时间间隔</th>
                    <th data-options="field:'qjx_str',align:'center',width:80">倾角数据</th>
                    <th data-options="field:'ndsj_str',align:'center',width:80">挠度数据</th>
                    <th data-options="field:'devst',align:'center',width:80">设备状态</th>
                </tr>
                </thead>
            </table>
        </div>
        <div title="卸载数据">
            <table id="tb_source_release" class="easyui-datagrid" style="height: 98%;width: 100%;"
                   data-options="
       singleSelect:true,
       rownumbers:true,
       method:'get',
       fitColumns:true,
    ">
                <thead>
                <tr>
                    <th data-options="field:'devnb',align:'center',width:80">设备编号</th>
                    <th data-options="field:'prs_str',align:'center',width:80">压力</th>
                    <th data-options="field:'hzjc_str',align:'center',width:80">荷载</th>
                    <th data-options="field:'wyjc_str',align:'center',width:80">位移</th>
                    <th data-options="field:'gps',align:'center',formatter:function(obj,row){return row.lat+','+row.lng},width:100">
                        GPS信息
                    </th>
                    <th data-options="field:'devstr',align:'center',width:80">设备代号</th>
                    <th data-options="field:'time',align:'center',width:120">数据上传时间</th>
                    <th data-options="field:'interval',align:'center',width:30">时间间隔</th>
                    <th data-options="field:'qjx_str',align:'center',width:80">倾角数据</th>
                    <th data-options="field:'ndsj_str',align:'center',width:80">挠度数据</th>
                    <th data-options="field:'devst',align:'center',width:80">设备状态</th>
                </tr>
                </thead>
            </table>
        </div>

        <div title="s-lgt曲线">
            <div id="slgt_chart" style="width: 50%; height: 400px;"></div>
        </div>
        <div title="s-lgQ曲线">
            <div id="slgQ_chart" style="width: 50%; height: 400px;"></div>
        </div>
        <div title="Q-s曲线">
            <div id="qs_chart" style="width: 50%; height: 400px"></div>
        </div>

        <div title="实时数据">
            <div id="current_data" style="width: 50%; height: 400px;padding: 20px">



                <form id="current_data_form" class="easyui-form">
                    <input class="easyui-textbox" name="devnb" style="width:45%"
                           data-options="label:'设备编号:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="prs_str" style="width:45%"
                           data-options="label:'压力:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="avgPrs" style="width:45%"
                           data-options="label:'平均压力:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="hzjc_str" style="width:45%"
                           data-options="label:'荷载:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="avgHzjc" style="width:45%"
                           data-options="label:'平均荷载:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="wyjc_str" style="width:45%"
                           data-options="label:'位移:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="avgWyjc" style="width:45%"
                           data-options="label:'平均位移:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="gps" style="width:45%"
                           data-options="label:'GPS信息:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="devstr" style="width:45%"
                           data-options="label:'设备代号:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="time" style="width:45%"
                           data-options="label:'数据上传时间:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="interval" style="width:45%"
                           data-options="label:'时间间隔:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="qjx_str" style="width:45%"
                           data-options="label:'倾角数据:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="avgQjx" style="width:45%"
                           data-options="label:'平均倾角数据:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="ndsj_str" style="width:45%"
                           data-options="label:'挠度数据:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="avgNdsj" style="width:45%"
                           data-options="label:'平均挠度数据:',labelAlign:'right',editable:false">
                    <input class="easyui-textbox" name="devst" style="width:45%"
                           data-options="label:'设备状态:',labelAlign:'right',editable:false">
                </form>
                <div style="margin-left: 49px">
                    <label id="current_data_msg" style="width:45%"></label>
                    <a id="refresh_data" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="margin-left: 128px">刷新</a>
                </div>
            </div>
        </div>
        <div title="现场视频">
            <div id="camera" style="width: 50%; height: 400px"></div>
        </div>

    </div>
    <div id="tab-tools">
        <a id="plan_data_export_btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-redo'">导出</a>
    </div>
    <script>

        $(function () {
            init();
            function init() {
                var prg = '${prg}', stzh = '${stzh}';
                if (prg && stzh) {
                    loadData(prg, stzh);
                    getCurrentData(prg, stzh);
                    $('#refresh_data').bind('click',function () {
                        getCurrentData(prg, stzh);
                    });
                }
                $('#plan_data_export_btn').bind('click',function () {
                    exportData(prg,stzh);
                });
            }

            function loadData(prg, stzh) {
                $.get('<c:url value="/inspect/data/query/"/>' + prg + '/' + stzh, function (data) {
                    $('#tb_all').datagrid({'data': data.source_press});
                    $('#tb_source_press').datagrid({'data': data.source_press});
                    $('#tb_source_release').datagrid({'data': data.source_release});

                    initChart0(data.chart0);
                    initChart1(data.chart1);
                    initChart2(data.chart2);

                }, 'json');
            }
            function exportData(prg,stzh) {
                alert("待完成导出!");
            }
            function initChart0(data) {
                var tick = data.tick;
                var tickPositions = $.map(tick, function (val, key) {
                    return key;
                }).sort();
                $('#qs_chart').highcharts({
                    legend: {
                        enabled: false
                    },
                    credits: {
                        enabled: false
                    },
                    chart: {
                        marginLeft: 120
                    },

                    title: {
                        text: 'Q-s曲线'
                    },
                    xAxis: {
                        opposite: true,
                        lineWidth: 2,
                        min: 0,
                        title: {
                            text: 'Q (kN)',
                            align: 'high',
                            y: 70,
                            rotation: 0,
                            x: -20
                        },
                        tickPositions: tickPositions,
                        labels: {
                            formatter: function () {
                                return tick[this.value];
                            }
                        }
                    },
                    yAxis: {
                        reversed: true,
                        lineWidth: 2,
                        min: 0,
                        tickWidth: 1,
                        labels: {
                            step: 1,
                            formatter: function () {
                                return this.value.toFixed(2);
                            }
                        },
                        title: {
                            text: 's (mm)',
                            align: 'low',
                            x: 70,
                            rotation: 0,
                            y: -20
                        }
                    }, plotOptions: {
                        series: {
                            marker: {
                                enabled: true
                            }
                        }
                    },
                    series: [{
                        data: data.data
                    }]
                });
            }

            function initChart1(data) {

                var tick = data.tick;
                var tickPositions = $.map(tick, function (val, key) {
                    return key;
                }).sort();
                var opts = {
                    dataLabels: {
                        enabled: true,
                        align: 'left',
                        style: {
                            fontWeight: 'bold'
                        },
                        verticalAlign: 'middle',
                        overflow: true,
                        crop: false,
                        formatter: function () {
                            return this.series.name + ' kN';
                        }
                    }
                }

                $.each(data.data, function (i, item) {
                    var index = item.data.length - 1;
                    var last_p = item.data[index];
                    $.extend(last_p, opts);
                })

                $('#slgt_chart').highcharts({
                    legend: {
                        enabled: false
                    },
                    credits: {
                        enabled: false
                    },
                    chart: {
                        marginLeft: 120
                    },

                    title: {
                        text: 's-lgt曲线'
                    },

                    tooltip: {
                        pointFormat: '{series.name} kN: <b>{point.y}</b><br/>',
                        valueSuffix: ' mm',
                        shared: true
                    },

                    xAxis: {
                        opposite: true,
                        lineWidth: 2,
                        min: 0,
                        title: {
                            text: 't (min)',
                            align: 'high',
                            y: 70,
                            rotation: 0,
                            x: -20
                        },
                        tickPositions: tickPositions,
                        labels: {
                            formatter: function () {
                                return tick[this.value];
                            }
                        }
                    },

                    yAxis: {
                        reversed: true,
                        lineWidth: 2,
                        min: 0,
                        tickWidth: 1,
                        labels: {
                            step: 1,
                            formatter: function () {
                                return this.value.toFixed(2);
                            }
                        },
                        title: {
                            text: 's (mm)',
                            align: 'low',
                            x: 70,
                            rotation: 0,
                            y: -20
                        }
                    }, plotOptions: {
                        series: {
                            marker: {
                                enabled: true
                            }
                        }
                    },
                    series: data.data
                });
            }

            function initChart2(data) {
                var tick = data.tick;
                var tickPositions = $.map(tick, function (val, key) {
                    return key;
                }).sort();
                $('#slgQ_chart').highcharts({
                    legend: {
                        enabled: false
                    },
                    credits: {
                        enabled: false
                    },
                    chart: {
                        marginLeft: 120
                    },
                    title: {
                        text: 's-lgQ曲线'
                    },
                    xAxis: {
                        opposite: true,
                        lineWidth: 2,
                        min: 0,
                        title: {
                            text: 'Q (kN)',
                            align: 'high',
                            y: 70,
                            rotation: 0,
                            x: -20
                        },
                        tickPositions: tickPositions,
                        labels: {
                            formatter: function () {
                                return tick[this.value];
                            }
                        }
                    },
                    yAxis: {
                        reversed: true,
                        lineWidth: 2,
                        min: 0,
                        tickWidth: 1,
                        labels: {
                            step: 1,
                            formatter: function () {
                                return this.value.toFixed(2);
                            }
                        },
                        title: {
                            text: 's (mm)',
                            align: 'low',
                            x: 70,
                            rotation: 0,
                            y: -20
                        }
                    }, plotOptions: {
                        series: {
                            marker: {
                                enabled: true
                            }
                        }
                    },
                    series: [{
                        type: 'line',
                        data: data.data
                    }]

                });
            }

            function getCurrentData(prg, stzh) {
                var time = new Date();
                $.get('<c:url value="/inspect/data/loadLatestData/"/>' + prg + '/' + stzh, function (ret) {
                    if ($.isPlainObject(ret)) {
                        $('#current_data_msg').text($.DateUtil.format(time,'刷新时间:yyyy-MM-dd/hh:mm:ss'));
                        $('#current_data_form').show();
                        $('#current_data_form').form('load', ret);
                    } else {
                        $('#current_data_msg').show($.DateUtil.format(time,'刷新时间:yyyy-MM-dd/hh:mm:ss,未取到数据!'));
                        $('#current_data_form').hide();
                        $('#current_data_form').form('clear');
                    }
                }, 'json');
            }
        });
    </script>
</div>