<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<link rel="stylesheet" type="text/css" href="/css/jquery.validate.css" />
<script src="/js/jquery.validate.js"></script>
<script type="text/javascript">
            /* <![CDATA[ */
            jQuery(function(){
            	 jQuery("#c_name").validate({
                     expression: "if (VAL) return true; else return false;",
                     message: "!!!"
                 });
            	 jQuery("#c_vendor").validate({
                     expression: "if (VAL) return true; else return false;",
                     message: "!!!"
                 });
            });
            /* ]]> */
</script>
<div style="clear: both">
<h1>Систем засах</h1>
	<form method="post" action="">
	<input type="hidden" id="id" name="id" value="${systems.id}" />
		<table width="900" cellpadding="2" cellspacing="0">
			<tr>
				<td colspan="2" align="center" style="color: #D20000;">${error}</td>
			</tr>
			<tr>
				<td width="100">Систем:</td>
				<td>
					<input type="text" id="c_name" name="c_name" value="${systems.c_name}" style="width: 500px;" />
				</td>
			</tr>
			<tr>
				<td>Нийлүүлэгч:</td>
				<td>
					<input type="text" id="c_vendor" name="c_vendor" value="${systems.c_vendor}" style="width: 500px;" />
				</td>
			</tr>
			<tr>
				<td>Тайлбар:</td>
				<td>
					<textarea id="c_desc" name="c_desc" style="width: 500px;">${systems.c_desc}</textarea>
				</td>
			</tr>
			<c:if test="${op.c_role==0 }">
			<tr>
				<td>Хэсэг:</td>
				<td>
					<select name="dep_id" id="dep_id">
				       <c:forEach items="${deps}" var="d">
				         	<option value="${d.id}" <c:if test="${d.id==systems.dep_id }">selected</c:if>>${d.c_name}</option>
				       </c:forEach>
					</select>
				</td>
			</tr>
			</c:if>
			<tr>
				<td colspan="2" align="right">
					<input type="hidden" name="user_id" value="${op.id}" />
					<c:if test="${op.c_role!=0 }"><input type="hidden" name="dep_id" value="${op.dep_id}" /></c:if>
					<input type="submit" name="submit" value="Засах" />
				</td>
			</tr>
		</table>
	</form>
</div>