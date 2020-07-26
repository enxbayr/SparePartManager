<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
	function remove_type(message, id) {
		var answer = confirm(message);
	    if (answer){
	       $.post('type.html', {id:id}, function(data) {
	       window.location.reload();
		});
	    }
	    return false;  
	}
</script>
<div style="clear: both;">
<h1>Төрөл</h1>
<p style="text-align: right; font-weight: bold;"><a href="/addtype.html">Төрөл нэмэх</a></p>

<span style="color: #00D200; text-align: center; width: 900px;">${success}</span>
<span style="color: #D20000; text-align: center; width: 900px;">${error}</span>

<form method="post">
<table id="list" width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<th width="20"></th>
		<th>Төрөл</th>
		<th width="20"></th>
		<th width="20"></th>
	</tr>
	<c:forEach items="${type}" var="u" varStatus="i">
	<tr>
		<td style="text-align: right;">${pageStart*perPage+i.index+1}.</td>
		<td style="text-align: left;">${u.c_name}</td>
		<td width="20">[<a href="/edittype.html?id=${u.id}">З</a>]</td>
		<td width="20">[<a href="#" onclick="return remove_type('Устгах уу?', ${u.id});">У</a>]</td>
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
				<a href="/type.html?p=${i}">${i+1}</a> |
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
			<a href="/type.html?p=${pageCountperPage}">${pageCountperPage+1}</a>
		</c:otherwise>
	</c:choose>
	</c:if>
	</font>
</div>
</div>