<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>智能无线静荷载试验检测云平台</title>
    <jsp:include page="../layout/common.jsp"></jsp:include>

</head>
<body>

<div class="easyui-panel" style="width:30%">
    <input class="easyui-searchbox"
           data-options="prompt:'请输入项目名称',searcher:function(val){$('#dg').datagrid('load',{name:encodeURIComponent(val)});}"
           style="width:100%">
</div>

<table id="dg" style="width:100%"></table>
<div id="dlg_edit" style="width:100%;max-width:600px;padding:30px 60px;">
    <form id="ff" class="easyui-form" method="post" data-options="novalidate:true" action="<c:url value="/inspect/scheme/post"/>">
        <div style="margin-bottom:20px;display: none">
            <input class="easyui-textbox" name="id" style="width:100%" data-options="label:'编号:',required:true">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="name" style="width:100%" data-options="label:'方案名称:',required:true">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox select" name="project.id" style="width:100%"
                   data-options="label:'工程名称:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'" url="<c:url value="/inspect/scheme/selectProject"/>">
        </div>
        <div style="margin-bottom:20px">
            <select class="easyui-combobox" data-options="editable:false,labelAlign:'right'" name="basement_lev"
                    label="低级基础设计等级:"
                    style="width:100%">
                <option value="1">甲级</option>
                <option value="2">乙级</option>
                <option value="3">丙级</option>
            </select>
        </div>
        <div style="margin-bottom:20px">
            <select class="easyui-combobox" data-options="editable:false,labelAlign:'right'" name="safety_lev"
                    label="建筑安全等级:"
                    style="width:100%">
                <option value="1">一级</option>
                <option value="2">二级</option>
                <option value="3">三级</option>
            </select>
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-numberbox" name="pile_count" style="width:100%"
                   data-options="label:'总桩数:',labelAlign:'right',required:true">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox select" name="dept.id" style="width:100%"
                   data-options="label:'检测单位:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'" url="<c:url value="/inspect/scheme/selectDept"/>">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="code" style="width:100%"
                   data-options="label:'检测方案审批表:',labelAlign:'right',required:true">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="name" style="width:100%"
                   data-options="label:'检测方案附件:',labelAlign:'right'">
        </div>
        <div style="margin-bottom:20px">
            <div style="margin-bottom:20px">
                <select id="inspectItem_id" class="easyui-combobox" name="inspectItem.id" style="width:100%"
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
<div id="details"></div>

<script type="text/javascript">
    $(function () {
        $('#dg').datagrid({
            url: '<c:url value="/inspect/scheme/query"/>',
            method: 'get',
            iconCls: 'icon-save',
//            width: 700,
            height: $('body').height(),
            fitColumns: true,
            singleSelect: false,
            pagination: true,
            pageSize: 10,
            toolbar: [{
                text: '添加',
                iconCls: 'icon-add',
                handler: function () {
                    showEditDialog();
                }
            }, {
                text: '编辑',
                iconCls: 'icon-edit',
                handler: function () {
                    var rows = $('#dg').datagrid('getChecked');
                    if (!rows || !rows.length) {
                        $.messager.alert('提示', '请选择要编辑的行!');
                        return;
                    }
                    if (rows.length > 1) {
                        $.messager.alert('提示', '只能选择一行进行编辑!');
                        return;
                    } else {
                        showEditDialog(rows[0]);
                    }
                }
            }, {
                text: '删除',
                iconCls: 'icon-remove',
                handler: function () {
                    var rows = $('#dg').datagrid('getChecked');
                    if (!rows || !rows.length) {
                        $.messager.alert('提示', '请选择要删除的行!');
                        return;
                    }
                    $.messager.confirm('提示', '是否确定删除这' + rows.length + '条数据?', function (r) {
                        if (r) {
                            remove($.map(rows, function (row) {
                                return row.id;
                            }), function () {
                                $('#dg').datagrid('reload');
                            });
                        }
                    });
                }
            }],
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: 'ID', hidden: true},
                {field: 'name', title: '方案名称'},
                {
                    field: 'project', title: '工程名称', formatter: function (val) {
                    return val.name
                }
                },
                {
                    field: 'basement_lev', title: '低级基础设计等级', width: 80, align: 'right',
                    formatter: function (val, row) {
                        return {1: '甲级', 2: '乙级', 3: '丙级'}[val];
                    }
                },
                {
                    field: 'safety_lev', title: '建筑安全等级', width: 80, align: 'right',
                    formatter: function (val, row) {
                        return {1: '一级', 2: '二级', 3: '三级'}[val];
                    }
                },

                {field: 'pile_count', title: '总桩数', width: 80},
                {field: 'dept', title: '检测单位', width: 80, align: 'right',formatter:function(val){return val.name;}}
            ]],
            onHeaderContextMenu: function (e, field) {
                e.preventDefault();
                if (!$.cmenu) {
                    createColumnMenu();
                }
                $.cmenu.menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            }
        });

        function createColumnMenu() {
            $.cmenu = $('<div/>').appendTo('body');
            $.cmenu.menu({
                onClick: function (item) {
                    if (item.iconCls == 'icon-ok') {
                        $('#dg').datagrid('hideColumn', item.name);
                        $.cmenu.menu('setIcon', {
                            target: item.target,
                            iconCls: 'icon-empty'
                        });
                    } else {
                        $('#dg').datagrid('showColumn', item.name);
                        $.cmenu.menu('setIcon', {
                            target: item.target,
                            iconCls: 'icon-ok'
                        });
                    }
                }
            });
            var fields = $('#dg').datagrid('getColumnFields');
            for (var i = 0; i < fields.length; i++) {
                var field = fields[i];
                var col = $('#dg').datagrid('getColumnOption', field);
                $.cmenu.menu('appendItem', {
                    text: col.title,
                    name: field,
                    iconCls: 'icon-ok'
                });
            }
        }

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

        $('#dlg_edit').dialog({
            title: "添加单位",
            closed: true,
            modal: true,
            draggable: false,
            iconCls: 'icon-add',
            buttons: [{
                text: '保存',
                iconCls: 'icon-ok',
                handler: submitForm
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: closeEditDialog
            }]
        })

        function showEditDialog(data) {
            var $ff = $('#ff');
            if (data) {
                $ff.form('load', data);
                $ff.form({url: 'put'});
            } else {
                $ff.form({url: 'post'});
            }
            $('#dlg_edit').dialog('open');
        }

        function submitForm() {
            $.messager.progress();	// display the progress bar
            $('#ff').form('submit', {
                onSubmit: function () {
                    var isValid = $(this).form('validate');
                    if (!isValid) {
                        $.messager.progress('close');	// hide progress bar while the form is invalid
                    }
                    return isValid;	// return false will stop the form submission
                },
                success: function (data) {
                    $.messager.progress('close');	// hide progress bar while submit successfully
//                    {
//                        "success": true,
//                            "message": "Message sent successfully."
//                    }
                    data = $.parseJSON(data);
                    if (data.flag) {
                        $.messager.alert('提示', '保存成功!');
                        closeEditDialog(true);
                    } else {
                        $.messager.alert('提示', data.message || '保存失败,请检查网络连接或者权限!');
                    }
                }
            });
        }

        function closeEditDialog(needRefresh) {
            if (needRefresh) {
                $('#dg').datagrid('reload');
            }
            $('#ff').form('clear');
            $('#dlg_edit').dialog('close');
        }

        function remove(ids) {
            $.ajax({
                url: '<c:url value="/inspect/scheme/delete"/>',
                data: {ids: ids},
                type: 'post',
                dataType: 'json'
            }).done(function (ret) {
                if (ret && ret.flag) {
                    $.messager.alert('提示', '删除成功!');
                    $('#dg').datagrid('reload');
                } else {
                    $.messager.alert('提示', ret, msg || '删除失败!');
                }
            }).fail(function () {
                $.messager.alert('提示', '删除失败!');
            });
        }


    });
    function openDialog(title, href) {
        $('#details').dialog({
            title: title,
            width: $('body').width() * 0.8,
            height: $('body').height() * 0.8,
            closed: false,
            cache: false,
            href: href,
            modal: true
        });
    }


</script>
</body>
</html>