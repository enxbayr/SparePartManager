<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
	function remove_unit(message, id) {
		var answer = confirm(message);
	    if (answer){
	       $.post('unit.html', {id:id}, function(data) {
	       window.location.reload();
		});
	    }
	    return false;  
	}
	
$(document).ready(function() {	
	$("#type_id").change(function(){
	 $.post('units.html', { tid: $("#type_id").val()}, function(data) {
			$("#unitl").html(data);
	 });
	 $.post('unitp.html', { tid: $("#type_id").val()}, function(data) {
			$("#unitp").html(data);
	 });
	});
	
});
</script>
<div style="clear: both;">
<h1>Төхөөрөмж</h1>
<c:if test="${op.c_role!=2}"><p style="text-align: right; font-weight: bold;"><a href="/addunit.html">Төхөөрөмж нэмэх</a></p></c:if>

<span style="color: #00D200; text-align: center; width: 900px;">${success}</span>
<span style="color: #D20000; text-align: center; width: 900px;">${error}</span>

<form method="post">
<table id="list" width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<th width="20"></th>
		<th width="150">
			<select name="type_id" id="type_id">
				<option value="0" <c:if test="${tid==0}">selected</c:if>>--- all types ---</option>
				<c:forEach items="${types}" var="st">
				      <option value="${st.id}" <c:if test="${tid==st.id}">selected</c:if>>${st.c_name}</option>
				</c:forEach>
			</select>
		</th>
		<th width="250">Нэр</th>
		<th>Тайлбар</th>
		<th width="20"></th>
		<c:if test="${op.c_role==0}"><th width="20"></th></c:if>
	</tr>
	</table>
	
<div id="unitl">
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
</div>

</form>

<div id="unitp" align="center">
	<font size="1">
	<c:if test="${pageCountperPage>0}"> | 
	<c:forEach begin="0" end="${pageCountperPage-1}" var="i">
		<c:choose>
			<c:when test="${pageStart==i}">
				<b>${i+1}</b> | 
			</c:when>
			<c:otherwise>
				<a href="/unit.html?tid=${tid}&amp;p=${i}">${i+1}</a> |
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
			<a href="/unit.html?tid=${tid}&amp;p=${pageCountperPage}">${pageCountperPage+1}</a>
		</c:otherwise>
	</c:choose>
	</c:if>
	</font>
</div>

</div>