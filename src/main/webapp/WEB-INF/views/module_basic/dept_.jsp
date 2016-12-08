<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>智能无线静荷载试验检测云平台</title>
    <jsp:include page="../layout/common.jsp"></jsp:include>
</head>

<body>
<sec:authorize access="hasAnyRole('SUPER','ADMIN')">
    <div class="easyui-panel" style="width:30%">
        <input class="easyui-searchbox"
               data-options="prompt:'请输入公司名称',searcher:function(val){$('#dg').datagrid('load',{name:encodeURIComponent(val)});}"
               style="width:100%">
    </div>

    <table id="dg" style="width:95%"></table>
    <div id="dlg_edit" style="width:100%;max-width:800px;padding:30px 60px;">
        <form id="ff" class="easyui-form" method="post" data-options="novalidate:true"
              action="<c:url value="/basic/dept/post"/>">
            <div style="margin-bottom:20px;display: none">
                <input class="easyui-textbox" name="id" style="width:45%" data-options="label:'记录编号:',required:true">
                <input class="easyui-textbox" name="code" style="width:45%"
                       data-options="label:'机构编号:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="name" style="width:45%"
                       data-options="label:'机构名称:',labelAlign:'right'">
                <select class="easyui-combobox" data-options="editable:false,labelAlign:'right'" name="economy_typ"
                        label="经济性质:"
                        style="width:45%">
                    <option value="1">经济性质1</option>
                    <option value="2">经济性质2</option>
                    <option value="3">经济性质3</option>
                </select>
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="certificate_code" style="width:45%"
                       data-options="label:'电话号码:',labelAlign:'right'">
                <select class="easyui-combobox" data-options="editable:false,labelAlign:'right'" name="register_type"
                        label="注册类型:"
                        style="width:45%">
                    <option value="1">建设单位</option>
                    <option value="2">施工单位</option>
                    <option value="3">监理单位</option>
                </select>
            </div>

            <div style="margin-bottom:20px;display: none">
                <input class="easyui-textbox" id="dept_logo" name="logo" style="width:100%"
                       data-options="label:'logo:',labelAlign:'right'">
            </div>
            <div id="img_div" style="margin-bottom:20px;margin-left:87px;overflow: hidden" class="resumable-browse">
                <img id="img" style="width: 300px;height: 59px"/>
            </div>
            <div class="resumable-progress">
                <table>
                    <tr>
                        <td width="100%">
                            <div class="progress-container">
                                <div class="progress-bar"></div>
                            </div>
                        </td>
                        <td class="progress-text" nowrap="nowrap"></td>
                    </tr>
                </table>
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="note" style="width:90%"
                       data-options="label:'备注信息:',labelAlign:'right',multiline:true,height:120">
            </div>
        </form>
    </div>
    <div id="details"></div>

    <script type="text/javascript">
        $(function () {
            $('#dg').datagrid({
                url: '<c:url value="/basic/dept/query"/>',
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
                    {field: 'name', title: '公司名称', width: 80},
                    {
                        field: 'logo', title: '公司Logo', width: 80, align: 'right',
                        formatter: function (val, row) {
                            return '<img style="width:300px;height:59px;" src="<c:url value="/resources/upload/"/>' + (val || 'blank.png') + '">';
                        }
                    },
                    {field: 'note', title: '备注'},
                    {
                        field: 'null', title: '操作', width: 80, align: 'right',
                        formatter: function (val, row) {
                            var str_arr = [
                                '<a href="javascript:manageTUsers(',
                                row['id'],
                                ');">账号管理</a>',
                                '<a href="javascript:manageTUsers(',
                                row['id'],
                                ');">设备管理</a>',
                                '<a href="javascript:top.switchDept(',
                                row['id'],
                                ');">查看数据</a>'
                            ];
                            return str_arr.join('');
                        }
                    }
                ]],
                columns: [[
                    {field: 'ck', checkbox: true},
                    {field: 'id', title: 'ID', hidden: true},
                    {field: 'code', title: '机构编号', width: 80},
                    {field: 'name', title: '机构名称', width: 80, align: 'right'},
                    {
                        field: 'economy_typ', title: '经济性质', width: 80, align: 'right',
                        formatter: function (val, row) {
                            return {1: '性质1', 2: '性质2', 3: '性质3'}[val];
                        }
                    },
                    {field: 'certificate_code', title: '资质证书编号', width: 80, align: 'right'},
                    {
                        field: 'register_type', title: '注册类型', width: 80, align: 'right',
                        formatter: function (val, row) {
                            return {1: '建设单位', 2: '施工单位', 3: '监理单位'}[val];
                        }
                    },
                    {
                        field: 'logo', title: '公司Logo', width: 80, align: 'right',
                        formatter: function (val, row) {
                            return '<img style="width:300px;height:59px;" src="<c:url value="/resources/upload/"/>' + (val || 'blank.png') + '">';
                        }
                    },
                    {field: 'note', title: '备注'},
                    {
                        field: 'null', title: '操作', width: 80, align: 'right',
                        formatter: function (val, row) {
                            var str_arr = [
                                '<a href="javascript:manageTUsers(',
                                row['id'],
                                ');">人员</a>&nbsp;',
                                '<a href="javascript:manageEquipments(',
                                row['id'],
                                ');">设备</a>',
                                '<a href="javascript:top.switchDept(',
                                row['id'],
                                ');">查看数据</a>'
                            ];
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

            $('#dlg_edit').dialog({
                title: "编辑",
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
                }],
                onClose: function () {
                    r.cancel();
                    $('#ff_user').form('clear');
                },
                onBeforeOpen: function () {
                    initResumable();
                    return true;
                }
            });

            function showEditDialog(data) {
                var $ff = $('#ff'),
                        $img = $('#img'),
                        name = 'blank.png';
                if (data) {
                    $ff.form('load', data);
                    $ff.form({url: 'put'});
                    if (data.logo) {
                        name = data.logo;
                    }
                } else {
                    $ff.form({url: 'post'});
                }
                $img.attr('src', '<c:url value="/resources/upload/"/>' + name);

                $('#dlg_edit').dialog('open');
            }

            function submitForm() {
                $.messager.progress();
                $('#ff').form('submit', {
                    onSubmit: function () {
                        var isValid = $(this).form('validate');
                        if (!isValid) {
                            $.messager.progress('close');
                        }
                        return isValid;
                    },
                    success: function (data) {
                        $.messager.progress('close');
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
                    url: '<c:url value="/basic/dept/delete"/>',
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
                height: $(document).height() * 0.8,
                closed: false,
                cache: false,
                href: href,
                modal: true
            });
        }


        function manageTUsers(id) {
            var href = '<c:url value="/basic/user/index/"/>' + id;
            openDialog('账号管理', href);
        }
        function manageEquipments(id) {
            var href = '<c:url value="/basic/equipment/index/"/>' + id;
            openDialog('设备管理', href);
        }
        var r = null;
        function initResumable() {
            r = $.getResumble({
                url: '<c:url value="/inspect_file/upload"/>',
                fileType: ['png'],
                successHandler: function (uuid) {
                    alert('成功保存文件:' + uuid);
                    $('#dept_logo').textbox('setValue', uuid);
                    $('#img').attr('src', '<c:url value="/resources/upload/"/>' + uuid);
                },
                fileTypeErrorHandler: function () {
                    alert("文件类型错误...");
                }
            });
        }
        $('#btn_save').on('click', function () {
            $.ajax({
                url: '../fileController/save',
                type: 'post',
                dataType: 'json',
                data: {fileName: $('#fileName').val()}
            }).done(function (ret) {
                if (ret.flag) {
                    console.log('保存成功');
                } else {
                    console.log("保存失败");
                }
            }).fail(function () {
                console.log("保存失败");
            });
        })

    </script>
    <script type="text/javascript" src="<c:url value="/resources/jslib/resumable.js"/>" type="text/javascript"
            charset="utf-8"></script>
    <script type="text/javascript" src="<c:url value="/resources/jslib/my_resumble.js"/>" type="text/javascript"
            charset="utf-8"></script>

</sec:authorize>
<sec:authorize access="hasRole('CUSTOM')">
    <div class="easyui-panel" style="width:30%">
        <input class="easyui-searchbox"
               data-options="prompt:'请输入用户名称',searcher:function(val){$('#dg_user').datagrid('load',{name:encodeURIComponent(val)});}"
               style="width:100%">
    </div>

    <table id="dg_user" style="width:100%"></table>
    <div id="dlg_user_edit" style="width:100%;max-width:400px;padding:30px 60px;">
        <form id="ff_user" class="easyui-form" method="post" data-options="novalidate:true"
              action="<c:url value="/basic/dept/post"/>">
            <div style="margin-bottom:20px;display: none">
                <input class="easyui-textbox" name="id" style="width:100%" data-options="label:'企业编号:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="name" style="width:100%"
                       data-options="label:'姓名:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="email" style="width:100%"
                       data-options="label:'Email:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="pwd" style="width:100%"
                       data-options="label:'密码:',required:true">
            </div>

            <div style="margin-bottom:20px">
                <select class="easyui-combobox" data-options="editable:false" name="role"
                        label="角色:" style="width:100%">
                    <c:if test="${sessionScope.sessionInfo.role eq 0}">
                        <option value="1">管理员</option>
                    </c:if>
                    <option value="2">普通用户</option>
                </select>
            </div>
        </form>
    </div>

    <script type="text/javascript">
        $(function () {
            var dept_id = '${dept_id}';
            $('#dg_user').datagrid({
                url: '<c:url value="/basic/user/queryAll"/>',
                method: 'get',
//                title: '人员管理',
//                iconCls: 'icon-save',
//            width: 700,
                height: $('body').height(),
                fitColumns: true,
                singleSelect: false,
                pagination: false,
                fitColumns: true,
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
                        var rows = $('#dg_user').datagrid('getChecked');
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
                        var rows = $('#dg_user').datagrid('getChecked');
                        if (!rows || !rows.length) {
                            $.messager.alert('提示', '请选择要删除的行!');
                            return;
                        }
                        $.messager.confirm('提示', '是否确定删除这' + rows.length + '条数据?', function (r) {
                            if (r) {
                                remove($.map(rows, function (row) {
                                    return row.id;
                                }), function () {
                                    $('#dg_user').datagrid('reload');
                                });
                            }
                        });
                    }
                }],
                columns: [[
                    {field: 'ck', checkbox: true},
                    {field: 'id', title: 'ID', hidden: true},
                    {field: 'name', title: '姓名'},
//                    {field: 'password', title: '密码'},
                    {
                        field: 'role', title: '角色', formatter: function (val, row) {
                        return ['超级管理员', '管理员', '普通用户'][val || 2];
                    }
                    },
                    {field: 'note', title: '备注'}
                ]]
            });

            $('#dlg_user_edit').dialog({
                title: "添加账号",
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
                var $ff = $('#ff_user');
                if (data) {
                    $ff.form('load', data);
                    $ff.form({url: '../basic/user/put'});
                } else {
                    $ff.form({url: '../basic/user/post'});
                }
                $('#user_dept_id').textbox('setValue', dept_id);
                $('#dlg_user_edit').dialog('open');
            }

            function submitForm() {
                $.messager.progress();
                $('#ff_user').form('submit', {
                    onSubmit: function (param) {
                        param['dept.id'] = dept_id;
                        var isValid = $(this).form('validate');
                        if (!isValid) {
                            $.messager.progress('close');
                        }
                        return isValid;
                    },
                    success: function (data) {
                        $.messager.progress('close');
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
                    $('#dg_user').datagrid('reload');
                }
                $('#ff_user').form('clear');
                $('#dlg_user_edit').dialog('close');
            }

            //TODO 删除用户与删除dept冲突
            function remove(ids) {
                $.ajax({
                    url: '<c:url value="/basic/user/delete"/>',
                    data: {ids: ids},
                    type: 'post',
                    dataType: 'json'
                }).done(function (ret) {
                    if (ret && ret.flag) {
                        $.messager.alert('提示', '删除成功!');
                        $('#dg_user').datagrid('reload');
                    } else {
                        $.messager.alert('提示', ret, msg || '删除失败!');
                    }
                }).fail(function () {
                    $.messager.alert('提示', '删除失败!');
                });
            }
        });

    </script>
</sec:authorize>
</body>

</html>