<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="text-align: center; font-size: 12px; padding: 20px;">
${depName} хэсэгт доорх нөөц байна
</div>
<table id="list" width="400" cellpadding="2" cellspacing="0" align="center">
	<tr>
		<th width="150">Төлөв</th>
		<th>Нийт</th>
	</tr>
	<c:forEach items="${sparestatus}" var="u" varStatus="i">
	<tr>
		<td>${u.description}</td>
		<td><a href="/spare.html?stid=${u.status_id}">${u.asset_id}</a></td>
	</tr>
	</c:forEach>
</table>