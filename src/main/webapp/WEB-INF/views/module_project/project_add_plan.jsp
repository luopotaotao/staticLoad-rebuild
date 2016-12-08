<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tt
  Date: 2016/10/12
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="width:100%;max-width:600px;padding:30px 60px;">
    <input id="project_add_plan_dept_id" type="hidden" name="dept.id">
    <form class="easyui-form" method="post" data-options="novalidate:true" action="<c:url value="/inspect/plan/post"/>">
        <div style="margin-bottom:20px;display: none">
            <input class="easyui-textbox" name="id" style="display: none" data-options="label:'编号:'">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="name" style="width:45%" data-options="label:'计划名称:',labelAlign:'right',required:true">
            <input class="easyui-textbox" name="stzh" style="width:45%" data-options="label:'桩号:',labelAlign:'right',required:true">
        </div>
        <div style="margin-bottom:20px;display: none">
            <input class="easyui-textbox select-scheme" name="inspectScheme.id" style="width:45%"
                   data-options="label:'方案id:',labelAlign:'right',required:true,editable:false">
            <input class="easyui-textbox select-scheme" name="project.id" style="width:45%"
                   data-options="label:'工程id:',labelAlign:'right',required:true,editable:false">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox select-user" name="user.id" style="width:45%"
                   data-options="label:'检测负责人:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'">
            <input class="easyui-textbox select-equipment" name="equipment.id" style="width:45%"
                   data-options="label:'检测设备:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'">
        </div>
        <div style="margin-bottom:20px">
            <input name="start_time" style="width:45%" type="text" class="easyui-datebox"
                   data-options="label:'开始日期:',labelAlign:'right',required:true,editable:false">
            <input name="end_time" style="width:45%" type="text" class="easyui-datebox"
                   data-options="label:'结束日期:',labelAlign:'right',required:true,editable:false">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox select-user" name="majorUser.id" style="width:45%"
                   data-options="label:'主检人:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'">
            <input class="easyui-textbox select-user" name="assistantUser.id" style="width:45%"
                   data-options="label:'副检人:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'">
        </div>
        <div style="margin-bottom:20px">
            <div style="margin-bottom:20px">
                <select id="inspect_method" class="easyui-combobox" name="inspectMethods.id" style="width:45%"
                        data-options="label:'检测项目:',
            labelAlign:'right',
            url:'<c:url value="/basic/inspectMethod/${inspectItemId}/comboList"/> ',
            method:'get',
            valueField: 'id',
            textField: 'name'
            ">
                </select>
            </div>
        </div>
    </form>
</div>

<script>
    $(function () {
        $('input.select-equipment').textbox({
            onClickButton: function () {
                var _this = this;
                var dept_id = $('#project_add_plan_dept_id').val();
                var  url="<c:url value="/inspect/plan/selectEquipment/"/>"+dept_id;
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
        $('input.select-user').textbox({
            onClickButton: function () {
                var _this = this;
                var dept_id = $('#project_add_plan_dept_id').val();
                var  url="<c:url value="/inspect/plan/selectUser/"/>"+dept_id;
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
                                console.log(data);
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
    });
</script>