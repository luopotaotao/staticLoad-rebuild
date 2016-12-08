<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<div data-options="region:'west',split:true" title="工程分布" style="width: 200px; overflow: hidden;">
    <div class="easyui-panel" style="padding:5px">
        <ul id="tree_menu"></ul>
    </div>
</div>
<div id="tt" class="easyui-panel" data-options="region:'center'">
    <div id="div_map" style="width: 800px;height: 500px"></div>
</div>

<script>
    $(function () {
        var selectedProject = ${selectedProject};
        var tree_data = [{
            "id": 1,
            "text": "基础信息",
            "url": '<c:url value="/project/manage/index"/>'
        }];

        initUI();
        function initUI() {
            $('#tree_menu').tree({
                url: '<c:url value="/overview/main/queryAll"/>',
                method: 'get',
                textField: 'name',
                animate: true,
                onClick: function (node) {
                    removeAllOverlays();
                    if (node.count) {
                        getProjectsAndShow(node.id);
                    }
                },
                formatter: function (node) {
                    if ($.isNumeric(node.count)) {
                        var str_arr = [node.text, ' (', node.count, ')'];
                        return str_arr.join('');
                    } else {
                        return node.text;
                    }

                }
            });

        }

        function getProjectsAndShow(area_id) {
            var url = '<c:url value="/overview/main/"/>' + area_id + '/queryProjects';
            $.getJSON(url, function (ret) {
                showMarkers(ret, function (marker) {
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
                });
            });
        }

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
//        var points = [new BMap.Point(113.272,23.134),
//
//            new BMap.Point(113.299,23.124),
//            new BMap.Point(113.251,23.130),
//            new BMap.Point(113.323,23.089),
//            new BMap.Point(113.317,23.128),
//            new BMap.Point(113.232,23.095)];
//
//        var markers = points.map(function(p){return {point:p};});
//
//        $.Map.showMarkers(markers);
        function initializeMap() {
            var $div = $('#div_map');
            $div.height($(document).height() * 0.92);
            $div.width($(document).width() * 0.85);
            var map = new BMap.Map("div_map");
//            var point = new BMap.Point(113.276, 23.117);
            map.centerAndZoom("西安", 4);
//            map.centerAndZoom(point, 12);                 // 初始化地图，设置中心点坐标和地图级别
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