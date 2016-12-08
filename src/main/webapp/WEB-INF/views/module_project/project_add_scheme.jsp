<%--
  Created by IntelliJ IDEA.
  User: tt
  Date: 2016/10/12
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="<c:url value="/resources/jslib/resumable.js"/>" type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/jslib/my_resumble.js"/>" type="text/javascript"
        charset="utf-8"></script>
<div style="width:100%;max-width:600px;padding:30px 60px;">
    <form id="ff" class="easyui-form" method="post" data-options="novalidate:true"
          action="<c:url value="/inspect/scheme/post"/>">
        <div style="margin-bottom:20px;display: none">
            <input class="easyui-textbox" name="id" style="width:100%" data-options="label:'编号:'">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="name" style="width:100%" data-options="label:'方案名称:',required:true">
        </div>
        <div style="margin-bottom:20px;display: none">
            <input name="project.id" style="width:100%">
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
        <div id="approval_file_id_div" style="margin-bottom:20px">
            <input class="easyui-textbox" id="approval_file_id" name="approval_file.uuid" style="width:100%"
                   data-options="label:'检测方案审批表:',labelAlign:'right',required:true,editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'">
        </div>
        <div id="inspect_file_id_div" style="margin-bottom:20px">
            <input class="easyui-textbox" id="inspect_file_id" name="inspect_file.uuid" style="width:100%"
                   data-options="label:'检测方案附件:',labelAlign:'right',editable:false,buttonText:'选择',
                   buttonIcon:'icon-search'">
        </div>
        <div style="margin-bottom:20px">
            <div style="margin-bottom:20px">
                <select id="inspectItem_id" class="easyui-combobox" name="inspectItem.id" style="width:100%"
                        data-options="label:'检测项目:',
                        editable:false,
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

<script>
    $(function () {
//        initResumable();
        initResumable('#approval_file_id');
        initResumable('#inspect_file_id');

        function initResumable(input) {

            var r = $.getResumble({
                url: '<c:url value="/file/upload"/>',
                fileType: ['doc', 'docx', 'ppt', 'xls', 'xlsx'],
                selectors:[input+'_div'],
                successHandler: function (ret) {
                    console.log('****************************');
                    console.log(input);
                    console.log(JSON.stringify(ret));
                    $(input).textbox('setValue', ret.uuid);
                    $(input).textbox('setText', ret.fileName);
                    console.log('approval_file_id:'+$('#approval_file_id').textbox('getText')+':'+$('#approval_file_id').textbox('getValue'))
                    console.log('inspect_file_id:'+$('#inspect_file_id').textbox('getText')+':'+$('#inspect_file_id').textbox('getValue'))
                },
                fileTypeErrorHandler: function () {
                    alert("文件类型错误...");
                }
            });
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
    });
</script>