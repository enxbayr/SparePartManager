<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table id="list" width="100%" cellpadding="2" cellspacing="0">
	<c:forEach items="${sparelist}" var="sp" varStatus="i">
	<tr>
		<td width="20" style="text-align: right;">${pageStart*perPage+i.index+1}.</td>
		<td style="text-align: left;"><a title="${sp.description }" href="/moving.html?sp=${sp.id }">${sp.unitName}</a></td>
		<td width="70">${sp.unitType}</td>
		<td width="140">${sp.sysName}</td>
		<c:if test="${op.c_role==0}"><td width="100">${sp.depName}</td></c:if>
		<td width="160">${sp.locName}</td>
		<td width="70">${sp.c_date}</td>
		<td width="100">${sp.serial_key}</td>
		<td width="100">${sp.product_num}</td>
		<td width="100">${sp.user_name}</td>
		<td width="100">${sp.statName}</td>
		<td width="100">${sp.asset_id}</td>
		<c:if test="${op.c_role!=3}"><c:if test="${op.c_role!=4}"><td width="20">[<a title="Шилжүүлэг хийх" href="javascript:void(0)" onclick="blur(this);javascript:openNewWindow('Шилжүүлэг','/trspare.html?id=${sp.id}',420, 470, 100, 100);">Ш</a>]</td></c:if></c:if>
		<c:if test="${op.c_role!=3}"><c:if test="${op.c_role!=4}"><td width="20">[<a title="Засах" href="/editspare.html?id=${sp.id}">З</a>]</td></c:if></c:if>
		<c:if test="${op.c_role!=3}"><c:if test="${op.c_role!=4}"><td width="20">[<a href="#" onclick="return remove_spare('Устгах уу?', ${sp.id});">У</a>]</td></c:if></c:if>
	</tr>
	</c:forEach>
</table>