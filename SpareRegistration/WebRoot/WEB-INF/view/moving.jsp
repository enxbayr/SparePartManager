<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
  <script>
  $(function() {
    $( "#datepickerf").datepicker({dateFormat: 'yy-mm-dd'});
    $( "#datepickert").datepicker({dateFormat: 'yy-mm-dd'});
  });
  $(document).ready(function() {
  	$("#toexcel").click(function() {
		alert("Creating xls file...");
		$.post('movingxls.html', {sdate:$( "#datepickerf").datepicker({dateFormat: 'yy-mm-dd'}).val(),
  								edate:$( "#datepickert").datepicker({dateFormat: 'yy-mm-dd'}).val()}, function(data) {
		});
	});
  	$("#timerange").click(function() {
  		$.post('movingl.html', {sdate:$( "#datepickerf").datepicker({dateFormat: 'yy-mm-dd'}).val(),
  								edate:$( "#datepickert").datepicker({dateFormat: 'yy-mm-dd'}).val()}, function(data) {
			$("#movingl").html(data);
		});
		
		$.post('movingp.html', {sdate:$( "#datepickerf").datepicker({dateFormat: 'yy-mm-dd'}).val(),
  								edate:$( "#datepickert").datepicker({dateFormat: 'yy-mm-dd'}).val()}, function(data) {
			$("#movingp").html(data);
		});
  	});
  });
  </script>
<div style="clear: both;">
<h1>Нөөц сэлбэгийн шилжилт</h1>
<p>From: <input type="text" id="datepickerf" size="10" value="${sdate}" /> To: <input type="text" id="datepickert" size="10" value="${edate}" />
<input type="button" id="timerange" value="View" /></p>
<div id="movingl">
<table id="list" width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<th width="20"></th>
		<th>Нэр</th>
		<th>Сериал</th>
		<th>Хаанаас</th>
		<th>Хаашаа</th>
		<th>Өмнөх төлөв</th>
		<th>Дараах төлөв</th>
		<th>Огноо</th>
		<th>Тайлбар</th>
		<th>Гүйцэтгэсэн</th>
		<c:if test="${op.c_role==0}"><th>Хэсэг</th></c:if>
	</tr>			
	<c:forEach items="${moving}" var="mv" varStatus="i">
	<tr>
		<td style="text-align: right;">${pageStart*perPage+i.index+1}.</td>
		<td style="text-align: left;"><a href="/moving.html?sp=${mv.spare_id}">${mv.spareName}</a></td>
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
</div>

<div id="movingp" align="center">
<font size="1">
	<c:if test="${pageCountperPage>0}">
	<c:forEach begin="0" end="${pageCountperPage-1}" var="i">
		<c:choose>
			<c:when test="${pageStart==i}">
				<b>${i+1}</b> | 
			</c:when>
			<c:otherwise>
				<a href="/moving.html?p=${i}&amp;sd=${sdate}&amp;ed=${edate}">${i+1}</a> |
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
			<a href="/moving.html?p=${pageCountperPage}&amp;sd=${sdate}&amp;ed=${edate}">${pageCountperPage+1}</a>
		</c:otherwise>
	</c:choose>
	</c:if>
</font>
</div>
<div style="float: right; padding-right: 20px;"><img src="/images/excel-8.png" width="30" border="0" id="toexcel" title="XLS рүү хөрвүүлэх" style="cursor: pointer;" /></div>
</div>