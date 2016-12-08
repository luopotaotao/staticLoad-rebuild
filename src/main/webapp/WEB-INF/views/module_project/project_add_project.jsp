<%--
  Created by IntelliJ IDEA.
  User: tt
  Date: 2016/10/12
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="width:100%;max-width:600px;padding:30px 60px;">
    <form class="easyui-form" method="post" data-options="novalidate:true" action="<c:url value="/project/manage/post"/>">
        <div style="margin-bottom:20px;display: none">
            <input class="easyui-textbox" name="id" style="width:100%" data-options="label:'企业编号:'">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="code" style="width:45%"
                   data-options="label:'工程编码:',labelAlign:'right',required:true">
            <input class="easyui-textbox" name="name" style="width:45%"
                   data-options="label:'工程名称:',labelAlign:'right'">
        </div>
        <div style="margin-bottom:20px">
            <select id="project_province_id" class="easyui-combobox" name="province.id" style="width:45%"
                    data-options="label:'所在省份:',
            labelAlign:'right',
            url:'<c:url value="/basic/area/area/0"/>',
            method:'get',
            valueField: 'id',
            textField: 'text',
            onSelect:function(rec){
                var $city = $('#project_add_project_city_id');
                $city.combobox('clear');
                $city.combobox('reload','<c:url value="/basic/area/area/"/>'+rec.id);
            }
            ">

            </select>
            <select id="project_add_project_city_id" class="easyui-combobox" name="city.id" style="width:45%"
                    data-options="label:'所在城市:',labelAlign:'right', method:'get',valueField: 'id',textField: 'text'">
            </select>
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="address" style="width:90%"
                   data-options="label:'具体地址:',labelAlign:'right'">
        </div>
        <div style="margin-bottom:20px;display: none">
            <input id="project_add_project_input_lat" class="easyui-textbox" name="lat" style="width:100%"
                   data-options="label:'纬度:'">
        </div>
        <div style="margin-bottom:20px;display: none">
            <input id="project_add_project_input_lng" class="easyui-textbox" name="lng" style="width:100%"
                   data-options="label:'经度:'">
        </div>
        <div style="margin-bottom:20px">
            <input id="project_add_project_select_coordinate" class="easyui-textbox" style="width:45%;height:32px;">
            <input id="select_constructor" class="easyui-textbox select" name="constructor.id" style="width:45%"
                   data-options="label:'建设单位:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'" url="<c:url value="/basic/company/partial?typ=1"/>">
        </div>
        <div style="margin-bottom:20px">
            <input id="project_add_project_select_builder" class="easyui-textbox select" name="builder.id" style="width:45%"
                   data-options="label:'施工单位:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'" url="<c:url value="/basic/company/partial?typ=2"/>">

            <input id="project_add_project_select_user" class="easyui-textbox select" name="user.id" style="width:45%"
                   data-options="label:'监理单位:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'" url="<c:url value="/basic/company/partial?typ=3"/>">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="note" style="width:90%"
                   data-options="label:'备注信息:',labelAlign:'right',multiline:true,height:120">
        </div>
    </form>
</div>
<div id="project_selectCoordinateDiv" style="display: none">
    <div id="project_map_div"></div>
</div>
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
                },
                center:function(cityName){
                    map.centerAndZoom(cityName, 9);
                }
            }

        });
        function initializeMap() {
            var $div = $('#project_map_div');
            var height = Math.floor($(document).height() * 0.81);
            var width = Math.floor($(document).width() * 0.69);
            $div.height(height).width(width);

            var map = new BMap.Map('project_map_div');
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
<script>
    $(function () {
        $('input.select').textbox({
            onClickButton: function () {
                var _this = this;
                var url = $(this).attr('url');
                selectChild(url, function (data) {
                    var names = [];
                    var ids = [];
                    $.each(data, function (i, item) {
                        ids.push(item.id);
                        names.push(item.name);
                    });
                    $(_this).textbox('setValue', ids);
                    $(_this).textbox('setText', names);
                });
            }
        });

        function selectChild(url, callback) {
            var $doc = $(document);
            var height = screen.availHeight * 0.6, width = screen.availWidth * 0.6;
            var $div = $('<div/>', {'height': height, width: width});
            $div.dialog({
                title: '请选择',
                closed: false,
                cache: true,
                href: url,
                modal: true,
                buttons: [{
                    text: '确定',
                    handler: function () {
                        if (true || $.isFunction(callback)) {
                            var data = $div.find('#grid').datagrid('getChecked');
                            if (data && data.length > 0) {
                                $div.dialog('close');
                                callback(data);
                            } else {
                                $.messager.alert('提示', '请选择数据!');
                            }
                        }
                    }
                }, {
                    text: '取消',
                    handler: function () {
                        $div.dialog('close');
                    }
                }]
            });
        }
        $('#project_add_project_select_coordinate').textbox({
            label: '经度纬度:',
            labelAlign: 'right',
            buttonText: '选择',
            buttonAlign: 'right',
            buttonIcon: 'icon-search',
            prompt: '经纬度',
            editable: false,
            onClickButton: function () {
                var _this = this;
                selectCoordinate(function (data) {
                    var str_arr = [
                        data.lng,
                        data.lat
                    ];
                    $(_this).textbox('setText', str_arr.join(','));
                    $('#project_add_project_input_lat').textbox('setValue', data.lat);
                    $('#project_add_project_input_lng').textbox('setValue', data.lng);
                });
            }
        });
        function selectCoordinate(callback) {
            var currentCity = $('#project_add_project_city_id').combobox('getText');
            $.BaiduMap.center(currentCity);
            var height = Math.floor($(document).height() * 0.93);
            var width = Math.floor($(document).width() * 0.7);
            var $div = $('#project_selectCoordinateDiv');
            $div.dialog({
                title: '请选择',
                closed: false,
                cache: false,
//                href: '<c:url value="/project/manage/selectCoordinate"/>',
                modal: true,
                buttons: [{
                    text: '确定',
                    handler: function () {
                        if (true || $.isFunction(callback)) {
                            var marker = $.BaiduMap.getCoordinate();
                            if (marker) {
                                $div.dialog('close');
                                callback(marker.M);
                                // TODO set 经纬度
                            } else {
                                $.messager.alert('提示', '请点击选择坐标!');
                            }
                        }
                    }
                }, {
                    text: '取消',
                    handler: function () {
                        $div.dialog('close');
                    }
                }]
            });
        }
    });
</script>