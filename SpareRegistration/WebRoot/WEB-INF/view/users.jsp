<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" type="text/css" href="/css/jquery.validate.css" />
<script src="/js/jquery.validate.js"></script>
<script type="text/javascript">
            /* <![CDATA[ */
            jQuery(function(){
            	 jQuery("#c_name").validate({
                     expression: "if (VAL) return true; else return false;",
                     message: "!!!"
                 });
                  jQuery("#c_pass").validate({
                     expression: "if (VAL) return true; else return false;",
                     message: "!!!"
                 });
            });
           
            /* ]]> */
  </script>
<script type="text/javascript">
	function remove_adm(message, id) {
		var answer = confirm(message);
	    if (answer){
	       $.post('remuser.html', {id:id}, function(data) {
	       window.location.reload();
		});
	    }
	    return false;  
	}
</script>

<div style="clear: both;">
<h1>Хэрэглэгчид</h1>
<span style="color: #00D200; text-align: center; width: 900px;">${success}</span>
<span style="color: #D20000; text-align: center; width: 900px;">${error}</span>
<c:if test="${adm!=null}">
<form method="post" style="padding: 10px; background-color: #EEE;">
	<input type="hidden" name="id" value="${adm.id}" />
	Нэвтрэх нэр: <input type="hidden" name="login_name" value="${adm.c_name}" /><b>${adm.c_name}</b>&nbsp;&nbsp;
	Нэвтрэх эрх: 
	<select name="c_role">
		<c:if test="${op.c_role==0 }">
		<option value="0" <c:if test="${adm.c_role==0}">selected</c:if>>superadmin</option>
		</c:if>
		<option value="1" <c:if test="${adm.c_role==1}">selected</c:if>>admin</option>
		<option value="2" <c:if test="${adm.c_role==2}">selected</c:if>>shift</option>
		<option value="4" <c:if test="${adm.c_role==4}">selected</c:if>>superviewer</option>
		<option value="3" <c:if test="${adm.c_role==3}">selected</c:if>>viewer</option>
	</select>&nbsp;&nbsp;
	<c:if test="${op.c_role==0 }">
	Хэсэг:
	<select name="dep_id">
		<c:forEach items="${departments}" var="dep">
			<option value="${dep.id }" <c:if test="${adm.dep_id==dep.id}">selected</c:if>>${dep.c_name}</option>
		</c:forEach>
	</select>&nbsp;&nbsp;
	</c:if>
	<c:if test="${op.c_role!=0}"><input type="hidden" name="dep_id" value="${op.dep_id}"/></c:if>
	<input type="submit" name="submit" value="Засах" />
</form>
</c:if>
<form method="post">
<table width="100%" cellpadding="2" cellspacing="0" id="list">
	<tr>
		<th width="20"></th>
		<th>Нэвтрэх нэр</th>
		<th width="100">Хандах эрх</th>
		<th>Хэсэг</th>
		<th>Үүссэн огноо</th>
		<th width="60"></th>
		<th width="20"></th>
		<th width="20"></th>
	</tr>
<c:forEach items="${adminlist}" var="a" varStatus="i">
<tr>
	<td style="text-align: right;">${i.index+1}.</td>
	<td style="text-align: left;">${a.c_name}</td>
	<td><c:if test="${a.c_role==0}">superadmin</c:if><c:if test="${a.c_role==1}">admin</c:if>
	<c:if test="${a.c_role==2}">shift</c:if><c:if test="${a.c_role==4}">superviewer</c:if><c:if test="${a.c_role==3}">viewer</c:if></td>
	<td>${a.dep_name}</td>
	<td>${a.c_date}</td>
	<td>[<a title="Нууц үг солих" href="javascript:void(0)" onclick="blur(this);javascript:openNewWindow('Нууц_үг_солих','/resetpass.html?id=${a.id}',360, 300, 100, 100);">нууц үг</a>]</td>
	<td>[<a href="/users.html?id=${a.id}">З</a>]</td>
	<td width="20">[<a href="#" onclick="return remove_adm('Устгах уу?', ${a.id});">У</a>]</td>
</tr>
</c:forEach>
</table>
</form>
<form method="post" style="padding: 10px; background-color: #d5ffdb;">
	Нэвтрэх нэр: <input type="text" name="c_name" id="c_name" value="" />&nbsp;&nbsp;
	Нууц үг: <input type="password" name="c_pass" id="c_pass" value="" />&nbsp;&nbsp;
	Нэвтрэх эрх: 
	<select name="c_role">
		<c:if test="${op.c_role==0 }">
		<option value="0" selected>superadmin</option>
		</c:if>
		<option value="1" selected>admin</option>
		<option value="2">shift</option>
		<option value="4">superviewer</option>
		<option value="3">viewer</option>
	</select>&nbsp;&nbsp;
	<c:if test="${op.c_role==0 }">
	Хэсэг:
	<select name="dep_id">
		<c:forEach items="${departments}" var="dep">
			<option value="${dep.id }">${dep.c_name}</option>
		</c:forEach>
		
	</select>&nbsp;&nbsp;
	</c:if>
	<c:if test="${op.c_role!=0 }"><input type="hidden" name="dep_id" value="${op.dep_id }"/></c:if>
	<input type="submit" name="submit" value="Нэмэх" />
</form>
</div>