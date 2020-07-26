<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table id="list" width="100%" cellpadding="2" cellspacing="0">
	<c:forEach items="${unitlist}" var="u" varStatus="i">
	<tr>
		<td width="20" style="text-align: right;">${pageStart*perPage+i.index+1}.</td>
		<td width="150" style="text-align: left;">${u.type_name}</td>
		<td width="250" style="text-align: left;">${u.c_name}</td>
		<td style="text-align: left;">${u.c_desc}</td>
		<td width="20">[<a href="/editunit.html?id=${u.id}">З</a>]</td>
		<c:if test="${op.c_role==0}"><td width="20">[<a href="#" onclick="return remove_unit('Устгах уу?', ${u.id});">У</a>]</td></c:if>
	</tr>
	</c:forEach>
</table>