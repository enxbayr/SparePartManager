<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<table id="list" width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<th width="20"></th>
		<th>Нэр</th>
		<th>Сериал</th>
		<th>Хаанаас</th>
		<th>Хаашаа</th>
		<th>Өмнөх статус</th>
		<th>Дараах статус</th>
		<th>Огноо</th>
		<th>Тайлбар</th>
		<th>Гүйцэтгэсэн</th>
		<c:if test="${op.c_role==0}"><th>Хэсэг</th></c:if>
	</tr>			
	<c:forEach items="${moving}" var="mv" varStatus="i">
	<tr>
		<td style="text-align: right;">${pageStart*perPage+i.index+1}.</td>
		<td><a  href="/moving.html?sp=${mv.spare_id}">${mv.spareName}</a></td>
		<td>${mv.serialKey}</td>
		<td>${mv.locFrom}</td>
		<td>${mv.locTo}</td>
		<td>${mv.statFrom}</td>
		<td>${mv.statTo}</td>
		<td>${fn:substring(mv.c_date,0,19)}</td>
		<td>${mv.comment}</td>
		<td>${mv.user_name}</td>
		<c:if test="${op.c_role==0}"><td>${mv.depName}</td></c:if>
	</tr>
	</c:forEach>
</table>