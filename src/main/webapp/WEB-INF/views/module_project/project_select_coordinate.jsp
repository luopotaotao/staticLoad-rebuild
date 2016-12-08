<%--
  Created by IntelliJ IDEA.
  User: tt
  Date: 2016/9/30
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="map_div"></div>

<script type="text/javascript" src="<c:url value="/resources/jslib/baiduMap/map_api.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/jslib/baiduMap/AreaRestriction_min.js"/>"></script>
<script>
    $(window).on('resize', function () {
        $('#div_map').width(document.body.clientWidth).height(document.body.clientHeight);
    });
    $(function () {
        var map = initializeMap();
        $.extend({
            BaiduMap: {
                getCoordinate: function () {
                    var markers = map.getOverlays();
                    return markers && markers.length > 0 ? markers[0] : null;
                }
            }

        });
        function initializeMap() {
            $('#map_div').empty();
            var id = Math.random()+'';
            var $div = $('<div/>',{id:id});
            $div.appendTo($('#map_div'));
            var height = Math.floor($(document).height() * 0.81);
            var width = Math.floor($(document).width() * 0.69);
            $div.height(height).width(width);

            var map = new BMap.Map(id);
            map.centerAndZoom("银川", 5);
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
            map.addEventListener("click", function (e) {
                var allOverlay = map.getOverlays();
                $.each(allOverlay, function (i, item) {
                    map.removeOverlay(item);
                });
                var marker = new BMap.Marker(e.point);  // 创建标注

                map.addOverlay(marker);
                marker.setAnimation(BMAP_ANIMATION_BOUNCE);
            });
            return map;
        }
    })
    ;
</script>
