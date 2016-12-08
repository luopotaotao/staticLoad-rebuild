<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>智能无线静荷载试验检测云平台</title>
    <jsp:include page="../layout/common.jsp"></jsp:include>
</head>
<body class="easyui-layout">
<div data-options="region:'west',split:true" title="数据分组" style="width: 200px; overflow: hidden;">
    <div class="easyui-panel" style="padding:5px">
        <ul id="tree_menu"></ul>
    </div>
</div>
<div id="tt" class="easyui-tabs" data-options="region:'center'">
    <div title="原始记录">
        <table id="tb_source" class="easyui-datagrid" title="原始记录"
               data-options="
       singleSelect:true,
       rownumbers:true,
       <!--url:'source_data.json',-->
       method:'get',
       fitColumns:true
    ">
            <thead>
            <tr>
                <th data-options="field:'prg'">工程</th>
                <th data-options="field:'stzh'">桩号</th>
                <th data-options="field:'devnb',align:'center'">设备编号</th>
                <th data-options="field:'prs',align:'center'">压力</th>
                <%--<th data-options="field:'avg_prs',align:'center'">平均压力</th>--%>
                <th data-options="field:'hzjc',align:'center'">荷载</th>
                <%--<th data-options="field:'avg_hzjc',align:'center'">平均荷载</th>--%>
                <th data-options="field:'wyjc',align:'center'">位移</th>
                <%--<th data-options="field:'avg_wyjc',align:'center'">平均位移</th>--%>
                <th data-options="field:'gps',align:'center',formatter:function(obj,row){return row.lat+','+row.lng}">
                    GPS信息
                </th>
                <th data-options="field:'devstr',align:'center'">设备代号</th>
                <th data-options="field:'time',align:'center'">数据上传时间</th>
                <%--<th data-options="field:'delay',align:'center'">时间间隔</th>--%>
                <th data-options="field:'qjx',align:'center'">倾角数据</th>
                <%--<th data-options="field:'avg_qjx',align:'center'">平均倾角数据</th>--%>
                <th data-options="field:'ndsj',align:'center'">挠度数据</th>
                <%--<th data-options="field:'avg_ndsj',align:'center'">平均挠度数据</th>--%>
                <th data-options="field:'devst',align:'center'">设备状态</th>
            </tr>
            </thead>
        </table>
    </div>
    <div title="统计数据">
        <table id="tb_load"></table>
    </div>


</div>

<script>
    $(function () {
        init();
        function init() {
            $('#tree_menu').tree({
                url: '<c:url value="/inspect/data/keys"/>',
                method: 'get',
                formatter: function (node) {
                    return '工程:' + node.prg + ';桩号:' + node.stzh;
                },
                onClick: function (node) {
                    loadData(node.prg, node.stzh);
                }
            })
        }

        function loadData(prg, stzh) {
            var fields = [
                {key: 'prs', title: '1'},
                {key: 'hzjc', title: '2'},
                {key: 'wyjc', title: '3'},
                {key: 'qjx', title: '4'},
                {key: 'ndsj', title: '5'}
            ];
            $.get('<c:url value="/inspect/data/query/"/>' + prg + '/' + stzh, function (data) {
                var ret = filterData(data, fields);
                var source = ret.source;
                var statistic = ret.statistic;
                console.log(ret.statistic.columns);
                $('#tb_source').datagrid({'data': source});
                $('#tb_load').datagrid({
                    rownumbers: true,
                    columns: statistic.columns,
                    data: statistic.data,
                    fitColumns: true
                });
            }, 'json');
        }

        /**
         * 处理原始数据,生成{source: data, statistic: statisticData};
         * @param data
         * @param fields
         * @returns {{source: *, statistic: ({columns, data}|*)}}
         */
        function filterData(data, fields) {
            if (data) {
                var statistic = {};
                var rows = data.rows;
                if (rows && rows.length > 0) {
                    $.each(rows, function (i, item) {
                        if (!statistic.hasOwnProperty(item.setprs)) {
                            statistic[item.setprs] = [];
                        }
                        var avg_vals = {};
                        $.each(fields, function (j, field) {
                            var key = field.key;
                            var avg_val = avg(item[key]);
                            item['avg_' + key.toUpperCase()] = avg_val
                            avg_vals[item.setprs + key] = avg_val;
                        });
                        statistic[item.setprs].push(avg_vals);
                        var delay = null;
                        switch (i) {
                            case 0:
                                delay = 0;
                                break;
                            case item.length - 1:
                                delay = null;
                                break;
                            default:
                                if (item.setprs == rows[i - 1].setprs) {
                                    delay = Math.round((new Date(item.Time).getTime() - new Date(rows[i - 1].Time).getTime()) / (1000 * 60));
                                } else {
                                    delay = 0;
                                }
                        }
                        item.Delay = delay;
                    });
                }
            }
            return {source: data, statistic: generateStatisticInfo(statistic, fields)};
        }

        function avg(arr) {
            var sum = arr.reduce(function (a, b) {
                return parseFloat(a) + parseFloat(b);
            });
            return sum / arr.length;
        }

        /**
         *
         * @param s
         * @param fields
         * @returns {{columns: *[], data: Array}}
         */
        function generateStatisticInfo(s, fields) {
            var columns = [[], []];
            var ret = [];
            $.each(s, function (key, arr) {
                columns[0].push({title: key, colspan: fields.length});
                $.each(fields, function (i, field) {
                    columns[1].push({title: field.title, field: key + field.key, width: 80});
                })

                for (var i = 0; i < arr.length; i++) {
                    ret[i] = ret[i] || {};
                    $.extend(ret[i], arr[i]);
                }
            });
            return {columns: columns, data: ret}
        }

    });
</script>

</body>
</html>