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
       data-options="singleSelect:false,url:'<c:url value="/inspect/data/unLinkedKeys"/>',method:'get'">
    <thead>
    <tr>
        <th data-options="field: 'ck', checkbox: true"></th>
        <th data-options="field: 'id', title: 'ID', hidden: true"></th>
        <th data-options="field: 'prg', title: '工程'"></th>
        <th data-options="field: 'stzh', title: '桩号'"></th>
        <%--<th data-options="field: 'gender', title: '详情', formatter: function (val,row) {--%>
            <%--return row.prg+','+row.stzh;--%>
        <%--}--%>
        <%--"></th>--%>
        <!--TODO 点击查看原始数据-->
    </tr>
    </thead>
</table>
