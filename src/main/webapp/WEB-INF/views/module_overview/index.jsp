<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>智能无线静荷载试验检测云平台</title>
    <jsp:include page="../layout/common.jsp"></jsp:include>

    <script type="text/javascript" src="<c:url value="/resources/jslib/baiduMap/map_api.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="/resources/jslib/baiduMap/AreaRestriction_min.js"/>"></script>

</head>
<body class="easyui-layout">

<div data-options="region:'west',split:true"
        <sec:authorize access="hasRole('CUSTOM')">
             title="工程列表" style="width: 20%; overflow: hidden;"
        </sec:authorize>
        <sec:authorize access="hasAnyRole('SUPER','ADMIN')">
             title="工程分布" style="width: 12%; overflow: hidden;"
        </sec:authorize>
>
    <table id="tree_menu"></table>
</div>
<div id="tt" class="easyui-panel" data-options="region:'center'" style="height: 100%">
    <div id="div_map" style="width: 100%;height: 100%"></div>
</div>

<script>
    $(function () {
        var selectedProject = ${selectedProject};
        var tree_data = [{
            "id": 1,
            "text": "基础信息",
            "url": '<c:url value="/project/manage/index"/>'
        }];

        initTree();
        <sec:authorize access="hasRole('CUSTOM')">
        function initTree() {
            $('#tree_menu').treegrid({
                animate:true,
                collapsible:true,
                fitColumns:true,
                url:'<c:url value="/overview/main/queryProjects"/>',
                method: 'get',
                idField:'id',
                treeField:'text',
                showFooter:true,
                columns:[[
                    {title:'工程名称',field:'text',align:'center'},
                    {title:'工程编码',field:'code',align:'center'},
                    {title:'所在城市',field:'province',align:'center',formatter:function (p,row) {
                        return p.text+row['city'].text;
                    }}
                ]],
                onClickRow:function (row) {
                    centerAndZoom(row.city.text, 10);
                    removeAllOverlays();
                    showMarkers([row], onClickMarker);
                }
            });
        }

        </sec:authorize>
        <sec:authorize access="hasAnyRole('SUPER','ADMIN')">
        function initTree() {
            $('#tree_menu').treegrid({
                animate:true,
                collapsible:true,
                fitColumns:true,
                url:'<c:url value="/overview/main/queryAll"/>',
                method: 'get',
                idField:'id',
                treeField:'text',
                showFooter:true,
                columns:[[
                    {title:'区域',field:'text'},
                    {field:'count',title:'数量',align:'center'}
                ]],
                onClickRow:function (row) {
                    centerAndZoom(row.text, [5, 7, 10][row.level]);
                    removeAllOverlays();
                    if (row.count) {
                        getProjectsAndShow(row.id);
                    }
                }
            });
        }
        function getProjectsAndShow(area_id) {
            var url = '<c:url value="/overview/main/queryProjectsByAreaId/"/>' + area_id;
            $.getJSON(url, function (ret) {
                showMarkers(ret, onClickMarker);
            });
        }

        </sec:authorize>
        var map = initializeMap();
        $.extend({
            Map: {
                instance: map,
                center: center,
                showMarkers: function (markers) {
                    showMarkers(markers, function (marker) {
                        showInfo(point, '<p>:name</p>', {name: JSON.stringify(marker)});
                    });
                },
                showInfo: showInfo
            }

        });

        function initializeMap() {
            var map = new BMap.Map("div_map");
            map.centerAndZoom("全国", 5);
            map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
            map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用
            var controls = [
                new BMap.OverviewMapControl(),//添加默认缩略地图控件
                new BMap.OverviewMapControl({isOpen: true, anchor: BMAP_ANCHOR_TOP_RIGHT}), //右上角，打开
                new BMap.NavigationControl(),//添加默认缩略地图控件
                new BMap.NavigationControl({
                    anchor: BMAP_ANCHOR_BOTTOM_LEFT,
                    type: BMAP_NAVIGATION_CONTROL_PAN
                }),//左下角，仅包含平移按钮
                new BMap.NavigationControl({
                    anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
                    type: BMAP_NAVIGATION_CONTROL_ZOOM
                }),//右下角，仅包含缩放按钮
                new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP, BMAP_HYBRID_MAP]})//2D图，卫星图
            ];
            $.each(controls, function (i, item) {
                map.addControl(item);
            });
            return map;
        }


        function center() {
            map.centerAndZoom("西安", 4);
        }

        function centerAndZoom(city, level) {
            map.centerAndZoom(city, level);
        }

        //显示markers,挂载点击事件,同时设置地图显示范围
        function showMarkers(markers, callback) {
            if ($.isArray(markers)) {
                var max_lat = 0, max_lng = 0,
                        min_lat = 91, min_lng = 181;

                $.each(markers, function (i, item) {
                    max_lat = item.lat > max_lat ? item.lat : max_lat;
                    max_lng = item.lng > max_lng ? item.lng : max_lng;
                    min_lat = item.lat < min_lat ? item.lat : min_lat;
                    min_lng = item.lng < min_lng ? item.lng : min_lng;
                    var img = '<c:url value="/resources/style/images/baidu_map/marker_"/>' + (item.status > 0 ? 'green' : 'gray') + '.png';
                    var myIcon = new BMap.Icon(img, new BMap.Size(35, 35), {
                        anchor: new BMap.Size(17, 35)
                    });
                    var icon = new BMap.Icon('<c:url value="/resources/style/images/baidu_map/marker_pink.png"/>', new BMap.Size(128, 128), {
                        anchor: new BMap.Size(10, 30)
                    });
                    var point = new BMap.Point(item.lng, item.lat);
                    var marker = new BMap.Marker(point, {icon: myIcon});  // 创建标注
                    marker.info = item;
                    map.addOverlay(marker);
                    if ($.isFunction(callback)) {
                        marker.addEventListener("click", function () {
                            callback(marker);
                        });
                    }
                });
            }
        }
        function onClickMarker(marker) {
            var template_arr = [
                '<p>工程名称:{name}</p>',
                '<p>工程编码:{code}</p>',
                '<p>地址:{city}{address}</p>',
                '<div><button onclick="top.openModule(\'<c:url value="/project/manage/index"/>?project_id={id}\')"> 工程详情</button> </div>'];
            var info = marker.info;
            map.centerAndZoom(marker.M, 11);
            showInfo(marker.M, template_arr.join(''), {
                id: info.id || '',
                name: info.name || '',
                code: info.code || '',
                city: info.city ? (info.city.text || '') : '',
                address: info.address || ''
            });
        }
        /**
         * 显示信息窗口
         * @param point 信息窗口点坐标,例如 point = new BMap.Point(116.417854,39.921988)
         * @param template 信息窗口模板,其中要替换的字符串以 :key 的形式 ,例如 <p>:name</p>
         * @param info 要替换的数据json对象,例如 {name:'XXX工程'}
         */
        function showInfo(point, template, info) {
            if ($.isPlainObject(info)) {
                $.each(info, function (key, val) {
                    template = template.replace(new RegExp('{' + key + '}', 'g'), val);
                });
            }
            var infoWindow = new BMap.InfoWindow(template);
            map.openInfoWindow(infoWindow, point);
        }

        function removeAllOverlays() {
            var allOverlay = map.getOverlays();
            $.each(allOverlay, function (i, item) {
                map.removeOverlay(item);
            });
        }

        if (selectedProject && selectedProject.id) {
            showMarkers([selectedProject], function (marker) {
                var template = '<p><a href="javascript:top.openModule(\'<c:url value="/project/manage/index"/>?project_id={id}\');">工程名称:{name}</a></p><p>工程编码:{code}</p><p>地址:{city}{address}</p>';
                var info = marker.info;
                map.centerAndZoom(marker.M, 11);
                showInfo(marker.M, template, {
                    id: info.id || '',
                    name: info.name || '',
                    code: info.code || '',
                    city: info.city ? (info.city.text || '') : '',
                    address: info.address || ''
                });
            });
        }
    });
</script>

</body>
</html>