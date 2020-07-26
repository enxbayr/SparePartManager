<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
	function remove_sys(message, id) {
		var answer = confirm(message);
	    if (answer){
	       $.post('system.html', {id:id}, function(data) {
	       window.location.reload();
		});
	    }
	    return false;  
	}
</script>
<div style="clear: both;">
<h1>Систем</h1>
<p style="text-align: right; font-weight: bold;"><a href="/addsystem.html">Систем нэмэх</a></p>

<span style="color: #00D200; text-align: center; width: 900px;">${success}</span>
<span style="color: #D20000; text-align: center; width: 900px;">${error}</span>

<form method="post">
<table id="list" width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<th width="20"></th>
		<th width="150">Систем</th>
		<th width="200">Нийлүүлэгч</th>
		<th width="200">Хэсэг</th>
		<th>Тайлбар</th>
		<th width="20"></th>
		<c:if test="${op.c_role==0}"><th width="20"></th></c:if>
	</tr>
	<c:forEach items="${systems}" var="u" varStatus="i">
	<tr>
		<td style="text-align: right;" width="20">${pageStart*perPage+i.index+1}.</td>
		<td width="150" style="text-align: left;">${u.c_name}</td>
		<td width="250">${u.c_vendor}</td>
		<td width="250">${u.depName}</td>
		<td>${u.c_desc}</td>
		<td width="20">[<a href="/editsystem.html?id=${u.id}">З</a>]</td>
		<td width="20">[<a href="#" onclick="return remove_sys('Устгах уу?', ${u.id});">У</a>]</td>
	</tr>
	</c:forEach>
</table>
</form>

<div align="center">
	<font size="1">
	<c:if test="${pageCountperPage>0}"> | 
	<c:forEach begin="0" end="${pageCountperPage-1}" var="i">
		<c:choose>
			<c:when test="${pageStart==i}">
				<b>${i+1}</b> | 
			</c:when>
			<c:otherwise>
				<a href="/system.html?p=${i}">${i+1}</a> |
			</c:otherwise>
		</c:choose>
	</c:forEach>
	</c:if>
	<c:if test="${(pageCount mod perPage) != 0}">
	<c:choose>
		<c:when test="${pageStart == pageCountperPage}">
			<b>${pageCountperPage + 1}</b>
		</c:when>
		<c:otherwise>
			<a href="/system.html?p=${pageCountperPage}">${pageCountperPage+1}</a>
		</c:otherwise>
	</c:choose>
	</c:if>
	</font>
</div>
</div>