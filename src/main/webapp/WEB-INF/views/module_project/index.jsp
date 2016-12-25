<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>智能无线静荷载试验检测云平台<sec:authentication property="principal.dept.name"/></title>
    <jsp:include page="../layout/common.jsp"></jsp:include>
    <style type="text/css">
        .info_form_hidden {
            display: none;
        }

        .info_form_hidden form {
            padding: 20px;
        }
    </style>
</head>
<body class="easyui-layout">
<div id="project_menu_panel" data-options="region:'west',split:true,tools: [
<sec:authorize access="hasAnyRole('SUPER','ADMIN')">
{
    id:'tools_add_project',
    iconCls:'icon-add',
    handler:function(){
        try{
            $.project.addProject()
        }catch(e){
        }
    }
  },
  </sec:authorize>
  {
    id:'tools_reload_project',
    iconCls:'icon-reload',
    handler:function(){
        $('#tree_menu').tree('reload');
    }
  }],collapsible:false " title="工程列表" style="width: 200px; overflow: hidden;">
    <div class="easyui-panel" style="padding:5px">
        <ul id="tree_menu"></ul>
    </div>
    <div id="project_mm_project" class="easyui-menu" style="width:120px;">
        <sec:authorize access="hasAnyRole('SUPER','ADMIN')">
            <div class="menu_item" onclick="$.project.addProject()" data-options="iconCls:'icon-add'">新建工程</div>
            <div class="menu-sep"></div>
            <div class="menu_item" onclick="$.project.addScheme()" data-options="iconCls:'icon-add'">添加方案</div>
            <div class="menu_item_remove" onclick="$.project.remove()" data-options="iconCls:'icon-remove'">删除工程</div>
            <div class="menu-sep"></div>
        </sec:authorize>
        <div class="menu_item" onclick="$.project.expandNode()">展开</div>
        <div class="menu_item" onclick="">收起</div>
    </div>
    <div id="project_mm_scheme" class="easyui-menu" style="width:120px;">
        <sec:authorize access="hasAnyRole('SUPER','ADMIN')">
            <div class="menu_item" onclick="$.project.addPlan()" data-options="iconCls:'icon-add'">添加计划</div>
            <div class="menu_item_remove" onclick="$.project.remove()" data-options="iconCls:'icon-remove'">删除方案</div>
            <div class="menu-sep"></div>
        </sec:authorize>
        <div class="menu_item" onclick="operate('expand')">展开</div>
        <div class="menu_item" onclick="operate('collapse')">收起</div>
    </div>
    <div id="project_mm_plan" class="easyui-menu" style="width:120px;">
        <sec:authorize access="hasAnyRole('SUPER','ADMIN')">
        <div class="menu_item_remove" onclick="$.project.linkData()" data-options="iconCls:'icon-remove'">关联数据</div>
        <div class="menu_item_remove" onclick="$.project.remove()" data-options="iconCls:'icon-remove'">删除计划</div>
        </sec:authorize>
    </div>
</div>
<div id="tt" class="easyui-panel" data-options="region:'center'" style="width: 1000px;height: 500px">
    <div class="easyui-tabs info_form_hidden" style="width:100%;height:100%;"
         data-options="onSelect:function(title,index){
            if(index==1){
                if(!$(this).data('inited')){
                try{
                    $(this).data('inited',true);
                    $('#project_scheme_list_div').panel('refresh');
                    }catch(e){
                        console.log(e);
                    }
                }
            }
         }">
        <div title="工程详情">
            <form class="easyui-form" method="post" data-options="novalidate:true"
                  action="<c:url value="/project/manage/post"/>">

                <div style="margin-bottom:20px;display: none">
                    <input class="easyui-textbox" name="id" style="width:500px"
                           data-options="label:'工程编号:',required:true">
                </div>
                <div style="margin-bottom:20px">
                    <input class="easyui-textbox" name="code" style="width:250px"
                           data-options="label:'工程编码:',labelAlign:'right',required:true">
                    <input class="easyui-textbox" name="name" style="width:250px"
                           data-options="label:'工程名称:',labelAlign:'right'">
                </div>
                <div style="margin-bottom:20px">
                    <select id="project_province_id" class="easyui-combobox" name="province.id" style="width:250px"
                            data-options="label:'所在省份:',
            labelAlign:'right',
            url:'<c:url value="/basic/area/area/0"/>',
            method:'get',
            valueField: 'id',
            textField: 'text',
            onSelect:function(rec){
                var $city = $('#project_city_id');
                $city.combobox('clear');
                $city.combobox('reload','<c:url value="/basic/area/area/"/>'+rec.id);
            }
            ">

                    </select>
                    <select id="project_city_id" class="easyui-combobox" name="city.id" style="width:250px"
                            data-options="label:'所在城市:',labelAlign:'right', method:'get',valueField: 'id',textField: 'text'">
                    </select>
                </div>
                <div style="margin-bottom:20px">
                    <input class="easyui-textbox" name="address" style="width:500px"
                           data-options="label:'具体地址:',labelAlign:'right'">
                </div>
                <div style="margin-bottom:20px;display: none">
                    <input id="project_input_lat" class="easyui-textbox" name="lat" style="width:100%"
                           data-options="label:'纬度:'">
                </div>
                <div style="margin-bottom:20px;display: none">
                    <input id="project_input_lng" class="easyui-textbox" name="lng" style="width:100%"
                           data-options="label:'经度:'">
                </div>
                <div style="margin-bottom:20px">
                    <input id="project_select_coordinate" class="easyui-textbox" style="width:250px;height:32px;">
                    <a id="view_map" class="easyui-linkbutton" style="width: 165px; height: 30px;margin-left:85px;"
                       data-options="iconCls:'icon-search',
            onClick:function () {
                var id = $('form').eq(0).find('input[name=\'id\']').val();
            top.openModule('<c:url value="/overview/main/index"/>?project_id='+id);
            }
">查看地图</a>
                </div>
                <div style="margin-bottom:20px">
                    <input id="project_select_constructor" class="easyui-textbox select" name="constructor.id"
                           style="width:250px"
                           data-options="label:'建设单位:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'"
                           url="<c:url value="/basic/company/partial"/>">
                    <input id="project_select_builder" class="easyui-textbox select" name="builder.id"
                           style="width:250px"
                           data-options="label:'施工单位:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'"
                           url="<c:url value="/basic/company/partial"/>">

                </div>
                <div style="margin-bottom:20px">
                    <input id="project_select_inspector" class="easyui-textbox select" name="inspector.id"
                           style="width:250px"
                           data-options="label:'监理单位:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'"
                           url="<c:url value="/basic/company/partial"/>">
                </div>
                <div style="margin-bottom:20px">
                    <input class="easyui-textbox" name="note" style="width:500px"
                           data-options="label:'备注信息:',labelAlign:'right',multiline:true,height:120">
                </div>
            </form>
        </div>
        <div id="project_scheme_list_div" title="方案列表" data-options="closable:false" style="overflow:auto">
            <table id="project_scheme_list_dg" class="easyui-datagrid" data-options="method:'get',rownumbers:true">
                <thead>
                <tr>
                    <th data-options="field:'name',align:'center',width:80">方案名称</th>
                    <th data-options="field:'basement_lev',align:'center',width:80,formatter:function(val,row){return val?['甲级','乙级','丙级'][val]:'';}">
                        建筑等级
                    </th>
                    <th data-options="field:'pile_count',align:'center',width:80">总桩数</th>
                    <th data-options="field:'inspectItem',align:'center',width:80,formatter:function(val,row){return val?val.name:'';}">
                        检测项目
                    </th>
                    <th data-options="field:'project',align:'center',width:80,formatter:function(val,row){return '<a href=\'javascript:$.project.clickNode('+val.id+','+row.id+')\'>查看</a>';}">
                        查看
                    </th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <div class="easyui-tabs info_form_hidden" style="width:100%;height:100%;"
         data-options="onSelect:function(title,index){
            if(index==1){
                if(!$(this).data('inited')){
                try{
                    $(this).data('inited',true);
                    $('#project_scheme_plan_list_div').panel('refresh');
                    }catch(e){
                        console.log(e);
                    }
                }
            }
         }">
        <div title="方案详情">
            <form class="easyui-form" method="post" data-options="novalidate:true"
                  action="<c:url value="/project/manage/post"/>">
                <div style="margin-bottom:20px;display: none">
                    <input class="easyui-textbox" name="id" style="width:500px"
                           data-options="label:'编号:',required:true">
                </div>
                <div style="margin-bottom:20px">
                    <input class="easyui-textbox" name="name" style="width:500px"
                           data-options="label:'方案名称:',labelAlign:'right',required:true">
                </div>
                <div style="margin-bottom:20px">
                    <select class="easyui-combobox" data-options="editable:false,labelAlign:'right'" name="basement_lev"
                            label="低级基础设计等级:"
                            style="width:500px">
                        <option value="1">甲级</option>
                        <option value="2">乙级</option>
                        <option value="3">丙级</option>
                    </select>
                </div>
                <div style="margin-bottom:20px">
                    <select class="easyui-combobox" data-options="editable:false,labelAlign:'right'" name="safety_lev"
                            label="建筑安全等级:"
                            style="width:500px">
                        <option value="1">一级</option>
                        <option value="2">二级</option>
                        <option value="3">三级</option>
                    </select>
                </div>
                <div style="margin-bottom:20px">
                    <input class="easyui-numberbox" name="pile_count" style="width:500px"
                           data-options="label:'总桩数:',labelAlign:'right',required:true">
                </div>
                <div style="margin-bottom:20px">
                    <input id="project_scheme_dept" class="easyui-textbox" style="width:500px"
                           data-options="label:'检测单位:',labelAlign:'right',required:true"/>
                </div>
                <div style="margin-bottom:20px">
                    <input class="easyui-textbox" id="approval_file_uuid" name="approval_file.uuid" style="width:500px"
                           data-options="label:'检测方案审批表:',labelAlign:'right',required:true"><a
                        id="approval_file_uuid_download">下载</a>
                </div>
                <div style="margin-bottom:20px">
                    <input class="easyui-textbox" id="inspect_file_uuid" name="inspect_file.uuid" style="width:500px"
                           data-options="label:'检测方案附件:',labelAlign:'right'"><a id="inspect_file_uuid_download">下载</a>
                </div>
                <div style="margin-bottom:20px">
                    <div style="margin-bottom:20px">
                        <select id="inspectItem_id" class="easyui-combobox" name="inspectItem.id" style="width:500px"
                                data-options="label:'检测项目:',
            labelAlign:'right',
            url:'<c:url value="/basic/inspectItem/comboList"/>',
            method:'get',
            valueField: 'id',
            textField: 'name'
            ">
                        </select>
                    </div>
                </div>
            </form>
        </div>
        <div id="project_scheme_plan_list_div" title="计划列表" data-options="closable:false" style="overflow:auto">
            <table id="project_scheme_plan_list_dg" class="easyui-datagrid" data-options="method:'get'">
                <thead>
                <tr>
                    <th data-options="field:'name',align:'center',width:80">计划名称</th>
                    <th data-options="field:'stzh',align:'center',width:80">桩号</th>
                    <th data-options="field:'inspector',align:'center',width:80,formatter:function(val,row){return val?val.realName:'';}">
                        检测负责人
                    </th>
                    <th data-options="field:'equipment',align:'center',width:80,formatter:function(val,row){return val?val.name:'';}">
                        设备编号
                    </th>
                    <th data-options="field:'start_time',align:'center',width:80,formatter:function(val,row){return $.isNumeric(val)?$.DateUtil.format(new Date(val),'yyyy-MM-dd'):'';}">
                        开始日期
                    </th>
                    <th data-options="field:'end_time',align:'center',width:80,formatter:function(val,row){return $.isNumeric(val)?$.DateUtil.format(new Date(val),'yyyy-MM-dd'):'';}">
                        结束日期
                    </th>
                    <th data-options="field:'maxLoad',align:'center',width:80">最大加载值</th>
                    <th data-options="field:'maxOffset',align:'center',width:80">最大位移值</th>
                    <th data-options="field:'project',align:'center',width:80,formatter:function(val,row){return '<a href=\'javascript:$.project.clickNode('+val.id+','+row.inspectScheme.id+','+row.id+')\'>查看</a>';}">
                        查看
                    </th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <div class="easyui-tabs info_form_hidden" style="width:100%;height:100%;"
         data-options="onSelect:function(title,index){
            if(index==1){
                if(!$(this).data('inited')){
                try{
                    $(this).data('inited',true);
                    $('#project_scheme_plan_show_data_div').panel('refresh');
                    }catch(e){
                        console.log(e);
                    }
                }
            }
         }">
        <div title="计划详情">
            <form class="easyui-form" method="post" data-options="novalidate:true"
                  action="<c:url value="/inspect/plan/post"/>">
                <div style="margin-bottom:20px;display: none">
                    <input class="easyui-textbox" name="id" style="width:500px"
                           data-options="label:'编号:',required:true">
                </div>
                <div style="margin-bottom:20px">
                    <input class="easyui-textbox" name="name" style="width:500px"
                           data-options="label:'计划名称:',labelAlign:'right',required:true">
                </div>
                <div style="margin-bottom:20px">
                    <input class="easyui-textbox" name="stzh" style="width:500px"
                           data-options="label:'桩号:',labelAlign:'right',required:true">
                </div>
                <div style="margin-bottom:20px">
                    <input id="project_scheme_plan_inspector" class="easyui-textbox select-inspector"
                           name="inspector.id"
                           style="width:500px"
                           data-options="label:'检测负责人:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'">
                </div>
                <div style="margin-bottom:20px">
                    <input id="project_scheme_plan_equipment" class="easyui-textbox select" name="equipment.id"
                           style="width:500px"
                           data-options="label:'检测设备:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'"
                           url="<c:url value="/inspect/scheme/selectProject"/>"/>
                </div>
                <div style="margin-bottom:20px">
                    <input id="project_scheme_plan_equipment_code" class="easyui-textbox select" name="equipment.code"
                           style="width:500px"
                           data-options="label:'设备编号:',labelAlign:'right',required:false,editable:false">
                </div>
                <div style="margin-bottom:20px">
                    <input name="start_time" style="width:500px" type="text" class="easyui-datebox"
                           data-options="label:'开始日期:',labelAlign:'right',required:true,editable:false">
                </div>
                <div style="margin-bottom:20px">
                    <input name="end_time" style="width:500px" type="text" class="easyui-datebox"
                           data-options="label:'结束日期:',labelAlign:'right',required:true,editable:false">
                </div>
                <div style="margin-bottom:20px">
                    <input id="project_scheme_plan_majorInspector" class="easyui-textbox select"
                           name="majorInspector.id"
                           style="width:500px"
                           data-options="label:'主检人:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'">
                </div>
                <div style="margin-bottom:20px">
                    <input id="project_scheme_plan_assistantInspector" class="easyui-textbox select"
                           name="assistantInspector.id" style="width:500px"
                           data-options="label:'副检人:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'">
                </div>

                <div style="margin-bottom:20px">
                    <div style="margin-bottom:20px">
                        <select id="project_scheme_plan_inspectMethod" class="easyui-combobox" name="inspectMethod"
                                style="width:500px"
                                data-options="label:'检测项目:',
            labelAlign:'right',
            method:'get',
            valueField: 'id',
            textField: 'name'
            ">
                        </select>
                    </div>
                </div>
            </form>
        </div>
        <div id="project_scheme_plan_show_data_div" title="检测数据" data-options="closable:false" style="overflow:auto">
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/resources/jslib/Highcharts-4.1.3/js/highcharts.js"/>"
        charset="utf-8"></script>
<script>
    $(function () {
        //初始化工程编辑内容
        $('#project_select_coordinate').textbox({
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
                    $('#project_input_lat').textbox('setValue', data.lat);
                    $('#project_input_lng').textbox('setValue', data.lng);
                });
            }
        });
        var $tree_menu = $('#tree_menu');
        initUI();
        var project_id = '${project_id}';

        function initUI() {
            $tree_menu.tree({
                url: '<c:url value="/project/manage/tree"/>',
                method: 'get',
                idField: 'id',
                textField: 'name',
                animate: true,
                onSelect: function (node) {
                    var root = getRoot(node);
                    node.prg = root.code;
                    showInfo(node.level, node);

                },
                onLoadSuccess: function (node, data) {
                    if ($.isNumeric(project_id)) {
                        var node = $(this).tree('find', project_id);
                        $(this).tree('select', node.target);
                    }
                },
                onContextMenu: function (e, node) {
                    e.preventDefault();
                    $(this).tree('select', node.target);
                    if (node) {
                        var $mm = null;
                        switch (node.level) {
                                //工程节点
                            case 0:
                                $mm = $('#project_mm_project');
                                break;
                                //方案节点
                            case 1:
                                $mm = $('#project_mm_scheme');
                                break;
                                //计划节点
                            case 2:
                                $mm = $('#project_mm_plan');
                                break;
                        }
                        $mm.menu('show', {
                            left: e.pageX,
                            top: e.pageY
                        });
                    }
                }
            });

        }

        function addProject() {
            showAddDialog({
                title: '添加工程'
            }, '<c:url value="/project/manage/addProject"/>');
        }

        function showProject(data) {
            $('#project_province_id').combobox('select', data.province.id);
            $('#project_city_id').combobox('select', data.city.id);
            $('#project_select_coordinate').textbox('setText', [data.lng, data.lat].join(','));
            setValues('project_select_', ['constructor', 'builder', 'inspector'], data);
            $('#project_scheme_list_dg').datagrid("reload", '<c:url value="/inspect/scheme/queryByProjectId?id="/>' + data.id);

        }

        function addScheme() {
            var node = getNode();
            showAddDialog({
                title: '添加方案',
                params: [{name: 'project.id', value: node.id}]
            }, '<c:url value="/project/manage/addScheme"/>');
        }

        function showScheme(data) {
            <sec:authentication property="principal.dept.name" var="dept_name"/>
            var dept_name = '${dept_name}';
            $('#project_scheme_dept').textbox('setText', dept_name);
            $('#inspectItem_id').combobox('setValue', data.inspectItem ? data.inspectItem.id : null);
            setFileField('approval_file');
            setFileField('inspect_file');
            $('#project_scheme_plan_list_dg').datagrid("reload", '<c:url value="/inspect/plan/queryBySchemeId?id="/>' + data.id);
            function setFileField(field) {

                var file = data[field];
                var name = file ? file.name : '';
                var uuid = file ? file.uuid : '';
                if (name && uuid) {
                    $('#' + field + '_uuid').textbox('setText', name);
                    $('#' + field + '_uuid_download').attr('href', '<c:url value="/file/download/"/>' + uuid);
                    $('#' + field + '_uuid_download').show();
                } else {
                    $('#' + field + '_uuid_download').hide();
                }
            }
        }

        function addPlan() {
            var node = getNode();
            var root = getRoot(node);

            showAddDialog({
                title: '添加计划',
                params: [
                    {name: 'project.id', value: root.id},
                    {name: 'inspectScheme.id', value: node.id}
                ]
            }, '<c:url value="/project/manage/addPlan?"/>inspectItemId=' + node.inspectItem.id);
        }


        function showPlan(plan) {
            $('#project_scheme_plan_show_data_div').panel('refresh', '<c:url value="/inspect/plan/showData/"/>' + plan.prg + '/' + plan.id);
            if ($.isNumeric(plan.start_time)) {
                plan.start_time = new Date(plan.start_time);
            }
            if ($.isNumeric(plan.end_time)) {
                plan.end_time = new Date(plan.end_time);
            }
            setValues('project_scheme_plan_', ['assistantInspector', 'inspector', 'majorInspector', 'equipment', 'inspectMethod'], plan, 'realName');
            $('#project_scheme_plan_equipment_code').textbox('setValue', plan.equipment.code);
        }

        function linkData() {
            var node = getNode();
            var plan_id = node.id;
            var url = '<c:url value="/inspect/plan/selectData/"/>' + plan_id;
            selectChild(url, function (data) {
                $.messager.confirm('请确认','是否要将该组数据关联到此计划中?此操作不可撤销!',function(r){
                    if(r){
                        $.ajax({
                            url: '<c:url value="/inspect/data/linkData/"/>' + plan_id,
                            type: 'post',
                            dataType: 'json',
                            contentType:"application/json",
                            data:JSON.stringify(data[0]),
                        }).done(function (ret) {
                            if(ret.flag){
                                $.messager.alert('系统提示', '关联数据成功!');
                                $('#project_scheme_plan_show_data_div').panel('refresh', '<c:url value="/inspect/plan/showData/"/>' + node.prg + '/' + node.id);
                                return;
                            }
                            $.messager.alert('系统提示', '关联数据失败!');
                        }).fail(function () {
                            $.messager.alert('系统提示', '关联数据失败!');
                        });
                    }
                })

            });
        }

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

        function setValues(prefix, keys, data, alias) {
            $.each(keys, function (i, key) {
                if (data[key]) {
                    var $input = $(['#', prefix, key].join(''));
                    $input.textbox('setValue', data[key].id)
                    $input.textbox('setText', data[key].name || data[key][alias]);
                }
            });
        }

        function expandNode(project_id, scheme_id, plan_id) {

        }

        function showInfo(level, obj) {
            var $forms = $('.info_form_hidden');
            $forms.hide();
            var $target = $forms.eq(level);
            $target.css('display', 'block');
            $target.show();

            try {
                $target.form('clear');
                $target.form('load', obj);
            } catch (e) {
                console.log(e);
            }
            [showProject, showScheme, showPlan][level](obj);
        }

        function openTab(title, url) {
            var $tt = $('#tt');
            if (!$tt.tabs('exists', title)) {
                var $iframe = $('<iframe/>', {src: url, style: 'width:95%;height:95%;', scrolling: 'no'});
                $tt.tabs('add', {
                    title: title,
                    content: $iframe,
                    closable: true,
                    fit: true,
                    plain: true
                });
            } else {
                $tt.tabs('select', title)
            }
        }

        function showAddDialog(options, url, callback) {
            var height = screen.availHeight * 0.6, width = screen.availWidth * 0.6;
            var $div = $('<div/>', {'height': height, width: width});
            $div.dialog({
                title: options.title,
                closed: false,
                cache: true,
                href: url,
                modal: true,
                buttons: [{
                    text: '确定',
                    handler: function () {
                        $.messager.progress();
                        var $form = $div.find('form');
                        $form.form('submit', {
                            onSubmit: function () {
                                var isValid = $form.form('enableValidation').form('validate');
                                if (!isValid) {
                                    $.messager.progress('close');
                                }
                                return isValid;
                            },
                            success: function (ret) {
                                $.messager.progress('close');
                                ret = $.parseJSON(ret);
                                if (ret && ret.flag) {
                                    $div.dialog('close');
                                    $.messager.alert('提示', '保存成功!');
                                    $('#tree_menu').tree('reload');
                                    if ($.isFunction(callback)) {
                                        callback(ret);
                                    }
                                } else {
                                    if (ret.err) {
                                        $.messager.alert('提示', ret.msg || "保存失败!");
                                    }
                                }
                            }
                        });
                    }
                }, {
                    text: '取消',
                    handler: function () {
                        $div.dialog('close');
                    }
                }],
                onLoad: function () {
                    if (options.params) {
                        var p = options.params;
                        p = $.isPlainObject(p) ? [p] : p;
                        $.each(p, function (i, item) {
                            $div.find('input[name="' + item.name + '"]').val(item.value);
                        });
                    }

                }
            });
        }

        function getNode() {
            return $tree_menu.tree('getSelected');
        }

//        function getRoot() {
//            return $tree_menu.tree('getRoot');
//        }
        function getRoot(node) {
            if ($.isNumeric(node.level) && node.level > 0) {
                return getRoot($tree_menu.tree('getParent', node.target));
            } else {
                return node;
            }
        }

        function remove() {
            var node = getNode();
            var msg = ['是否确认删除:', node.text, '?'].join('');
            var url = [
                '<c:url value="/project/manage/delete"/>',
                '<c:url value="/inspect/scheme/delete"/>',
                '<c:url value="/inspect/plan/delete"/>'
            ][node.level];
            $.messager.confirm('提示', msg, function (r) {
                if (r) {
                    $.ajax({
                        url: url,
                        type: 'post',
                        dataType: 'json',
                        data: {ids: [node.id]}
                    }).done(function (ret) {
                        if (ret && ret.flag) {
                            $.messager.alert('提示', '删除成功!');
                            $('#tree_menu').tree('reload');
                        } else {
                            $.messager.alert('提示', '删除失败!');
                        }
                    }).fail(function () {
                        $.messager.alert('提示', '删除失败!');
                    });
                }
            });
        }

        function clickNode(projectId, schemeId, planId) {
            var prg = null, scheme = null, plan = null, node = null;
            var projects = $tree_menu.tree('getRoots');
            if (projects.length > 0) {
                for (var i = 0; i < projects.length; i++) {
                    if (projects[i].id == projectId) {
                        prg = projects[i];
                        break;
                    }
                }
            }
            //只有工程Id的时候说明选择的是工程
            if (schemeId == null) {
                node = prg;
            } else {
                var children = $tree_menu.tree('getChildren', prg.target);
                if (children == null || children.length < 1) {
                    return;
                }
                //因为获取的scheme和plan节点都算在的root的children中,因此需要根据level和id来过滤到底是哪个层级哪个节点
                var level = 1, id = schemeId;
                if (planId != null) {
                    level = 2;
                    id = planId;
                }
                for (var i = 0; i < children.length; i++) {
                    var child = children[i];
                    if (child.id == id && child.level == level) {
                        node = child;
                        break;
                    }
                }
            }
            if (node != null) {
                $(node.target).click();
            }
        }

        $.extend({
            project: {
                addProject: addProject,
                showProject: showProject,
                addScheme: addScheme,
                showScheme: showScheme,
                addPlan: addPlan,
                showPlan: showPlan,
                linkData: linkData,
                expandNode: expandNode,
                remove: remove,
                clickNode: clickNode
            }
        });
    });
</script>

</body>
</html>