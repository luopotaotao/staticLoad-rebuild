<%--
  Created by IntelliJ IDEA.
  User: taotao
  Date: 2016/9/30
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="grid" class="easyui-datagrid" style="width:100%;height:100%"
       data-options="singleSelect:false,url:'<c:url value="/inspect/scheme/query"/>',method:'get'">
    <thead>
    <tr>
        <th data-options="field: 'ck', checkbox: true"></th>
        <th data-options="field: 'id', title: 'ID', hidden: true"></th>
        <th data-options="field: 'name', title: '方案名称'"></th>
        <th data-options="field: 'project', title: '工程名称', formatter: function (val) {
        return val.name
        }
        "></th>
        <th data-options="field: 'basement_lev', title: '低级基础设计等级', width: 80, align: 'right',
        formatter: function (val, row) {
        return {1: '甲级', 2: '乙级', 3: '丙级'}[val];
        }
        "></th>
        <th data-options="field: 'safety_lev', title: '建筑安全等级', width: 80, align: 'right',
        formatter: function (val, row) {
        return {1: '一级', 2: '二级', 3: '三级'}[val];
        }
        "></th>
        <th data-options="field: 'pile_count', title: '总桩数', width: 80"></th>
        <th data-options="field: 'dept', title: '检测单位', width: 80, align: 'right',formatter:function(val){return val.name;}"></th>
    </tr>
    </thead>
</table>
