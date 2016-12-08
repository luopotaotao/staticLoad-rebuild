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
       data-options="singleSelect:false,url:'<c:url value="/project/manage/query"/>',method:'get'">
    <thead>
    <tr>
        <th data-options="field: 'ck', checkbox: true"></th>
        <th data-options="field: 'id', title: 'ID', hidden: true"></th>
        <th data-options="field: 'code', title: '工程编码', width: 80"></th>
        <th data-options="field: 'name', title: '工程名称', width: 80"></th>
        <th data-options="field: 'province_id', title: '所在省份id', width: 80"></th>
        <th data-options="field: 'city_id', title: '所在市id', width: 80"></th>
        <th data-options="field: 'address', title: '具体地址', width: 80"></th>
        <th data-options="field: 'lat', title: '纬度', width: 80"></th>
        <th data-options="field: 'lng', title: '经度', width: 80"></th>
        <th data-options="field: 'constructor_id', title: '建设单位', width: 80"></th>
        <th data-options="field: 'builder_id', title: '施工单位', width: 80"></th>
        <th data-options="field: 'user_id', title: '监理单位', width: 80"></th>
    </tr>
    </thead>
</table>
