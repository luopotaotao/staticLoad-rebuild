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
<div id="dlg_edit" style="width:100%;max-width:600px;padding:10px 60px;">
    <form id="ff" class="easyui-form" method="post" data-options="novalidate:true" action="<c:url value="/inspect/plan/post"/>">
        <div style="margin-bottom:20px;display: none">
            <input class="easyui-textbox" name="id" style="width:100%" data-options="label:'编号:',required:true">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="name" style="width:100%" data-options="label:'计划名称:',labelAlign:'right',required:true">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox select-scheme" name="inspectScheme.id" style="width:100%"
                   data-options="label:'方案名称:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'" url="<c:url value="/inspect/plan/selectScheme"/>/">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox select-user" name="user.id" style="width:100%"
                   data-options="label:'检测负责人:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox select" name="equipment.id" style="width:100%"
                   data-options="label:'检测设备:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'" url="<c:url value="/inspect/scheme/selectProject"/>">
        </div>
        <div style="margin-bottom:20px">
            <input name="start_time" style="width:100%" type="text" class="easyui-datebox"
                   data-options="label:'开始日期:',labelAlign:'right',required:true,editable:false">
        </div>
        <div style="margin-bottom:20px">
            <input name="end_time" style="width:100%" type="text" class="easyui-datebox"
                   data-options="label:'结束日期:',labelAlign:'right',required:true,editable:false">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox select" name="majorUser.id" style="width:100%"
                   data-options="label:'主检人:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox select" name="assistantUser.id" style="width:100%"
                   data-options="label:'副检人:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'">
        </div>

        <div style="margin-bottom:20px">
            <div style="margin-bottom:20px">
                <select id="inspect_method" class="easyui-combobox" name="inspectMethods" style="width:100%"
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
<div id="details"></div>
<div id="data_details">
    <iframe id="data_details_iframe" style="width: 98%;height: 98%"></iframe>
</div>

<script type="text/javascript">
    $(function () {
        $('#dg').datagrid({
            url: '<c:url value="/inspect/plan/query"/>',
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
                {field: 'name', title: '计划名称'},
                {
                    field: 'inspectScheme', title: '方案名称', formatter: function (val) {
                    return val?val.name:'';
                }
                },
                {
                    field: 'user', title: '检测负责人', width: 80, align: 'center',
                    formatter: function (val, row) {
                        return val?val.name:'';
                    }
                },
                {
                    field: 'equipment', title: '检测设备', width: 80, align: 'center',
                    formatter: function (val, row) {
                        return val?val.name:'';
                    }
                },

                {field: 'start_time', title: '开始时间', width: 80,formatter:function (val) {
                    return $.isNumeric(val)?$.DateUtil.format(new Date(val),'yyyy/MM/dd'):'';
                }},
                {field: 'end_time', title: '结束时间', width: 80, align: 'center',formatter:function (val) {
                    return $.isNumeric(val)?$.DateUtil.format(new Date(val),'yyyy/MM/dd'):'';
                }},
                {
                    field: 'majorUser', title: '主检人', width: 80, align: 'center',
                    formatter: function (val, row) {
                        return val?val.name:'';
                    }
                },
                {
                    field: 'assistantUser', title: '副检人', width: 80, align: 'center',
                    formatter: function (val, row) {
                        return val?val.name:'';
                    }
                },
                {field: 'note', title: '备注', align: 'center'},
                {
                    field: 'null', title: '数据操作', width: 80, align: 'center',
                    formatter: function (val, row) {
                        var str_arr = ['<a href="javascript:showData(',row.id,')">查看</a>',
                                '&nbsp',
                            '<a href="javascript:linkData(',row.id,')">关联</a>'];
                        return str_arr.join('');
                    }
                }
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
        $('input.select-scheme').textbox({
            onClickButton: function () {
                var _this = this;
                var url = $(this).attr('url');
                selectChild(url, function (data) {
                    var item = data[0];
                    $(_this).textbox('setValue', item.id);
                    $(_this).textbox('setText', item.name);
                    var url_method = '<c:url value="/basic/inspectMethod/'+item.inspectItem.id+'/comboList"/>';
                    var url_user = '<c:url value="/inspect/plan/selectUser/"/>'+item.inspectItem.id;
                    $('#inspect_method').combobox('reload',url);
                    $('input.select-user').attr('url',url_user);
                });
            }
        });
        $('input.select-user').textbox({
            onClickButton: function () {
                var _this = this;
                var url = $(_this).attr('url');
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



        $('#dlg_edit').dialog({
            title: "添加计划",
            closed: true,
            modal: true,
            height:520,
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
                onSubmit: function (param) {
                    var isValid = $(this).form('validate');
                    if (!isValid) {
                        $.messager.progress('close');	// hide progress bar while the form is invalid
                    }
                    var methods = $('#inspect_method').combobox('getValues');
                    delete param.inspectMethods;
                    if($.isArray(methods)&&methods.length>0){
                        param.inspectMethods = [];
                        $.each(methods,function(i,val){
                            param.inspectMethods.push({id:val});
                        });
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
                url: '<c:url value="/inspect/plan/delete"/>',
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
    function showData(plan_id) {
        var url = '<c:url value="/inspect/plan/showData/"/>'+plan_id;
        var $iframe = $('#data_details_iframe');
        $iframe.attr('src',url);
        $('#data_details').dialog({
            title: '详情',
            width: $('body').width() * 0.9,
            height: $(document).height() * 0.6,
            closed: false,
            modal: true
        });
    }
    function linkData(plan_id) {
        var url = '<c:url value="/inspect/plan/selectData/"/>'+plan_id;
        selectChild(url, function (data) {
            $.ajax({
                url:'<c:url value="/inspect/data/linkData/"/>'+plan_id,
                type:'post',
                dataType:'json',
                data:JSON.stringify(data),
                contentType: "application/json"
            }).done(function (ret) {
                console.log(ret);
            }).fail(function () {
                $.messager.alert('提示','关联数据失败!');
            });
        });
    }

</script>
</body>
</html>