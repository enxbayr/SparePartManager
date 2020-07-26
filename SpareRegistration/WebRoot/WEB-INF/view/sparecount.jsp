<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table id="list" width="800" cellpadding="2" cellspacing="0" align="center">
	<tr>
		<th width="20"></th>
		<th>Төхөөрөмж</th>
		<c:forEach items="${statuses}" var="status">
		<th width="50">${status.status}</th>
		</c:forEach>
		<th width="50">Нийт</th>
	</tr>
	<c:forEach items="${sparecount}" var="sp" varStatus="i">
	<tr>
		<td width="20" style="text-align: right;">${pageStart*perPage+i.index+1}.</td>
		<td style="text-align: left;">${sp.sp.unitName}</td>
				<c:forEach items="${statuses}" var="status">
				<td>
					<c:forEach items="${sp.st}" var="st" varStatus="i">
					<c:if test="${status.status==st.status}">${st.count}</c:if>
					</c:forEach>
				</td>
				</c:forEach>
		<td><b>${sp.sp.description}</b></td>
	</tr>
	</c:forEach>
</table>

<div align="center">
<font size="1">
	<c:if test="${pageCountperPage>0}">
	<c:forEach begin="0" end="${pageCountperPage-1}" var="i">
		<c:choose>
			<c:when test="${pageStart==i}">
				<b>${i+1}</b> | 
			</c:when>
			<c:otherwise>
				<a href="/sparecount.html?p=${i}">${i+1}</a> |
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
			<a href="/sparecount.html?p=${pageCountperPage}">${pageCountperPage+1}</a>
		</c:otherwise>
	</c:choose>
	</c:if>
</font>
</div>