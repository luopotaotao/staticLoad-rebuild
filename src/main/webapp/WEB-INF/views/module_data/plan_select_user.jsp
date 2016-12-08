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
       data-options="singleSelect:false,url:'<c:url value="/basic/user/${dept_id}/query"/>',method:'get'">
    <thead>
    <tr>
        <th data-options="field: 'ck', checkbox: true"></th>
        <th data-options="field: 'id', title: 'ID', hidden: true"></th>
        <th data-options="field: 'name', title: '姓名'"></th>
        <th data-options="field: 'gender', title: '性别', formatter: function (val) {
        return val == 1 ? '男' : '女';
        }
        "></th>
        <th data-options="field: 'note', title: '备注', width: 80"></th>
    </tr>
    </thead>
</table>
