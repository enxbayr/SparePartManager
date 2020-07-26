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
                     message: "төхөөрөмжийн нэр оруулна уу"
                 });
            	 jQuery("#type_id").validate({
                     expression: "if (VAL != '0') return true; else return false;",
                     message: "төрлөө сонгоно уу"
                 });
            });
            /* ]]> */
 </script>
<div style="clear: both">
<h1>Төхөөрөмж засах</h1>
	<form method="post" action="">
	<input type="hidden" id="id" name="id" value="${unit.id}" />
		<table width="900" cellpadding="2" cellspacing="0">
			<tr>
				<td colspan="2" align="center" style="color: #D20000;">${error}</td>
			</tr>
			<tr>
				<td width="100">Төрөл:</td>
				<td>
					<select name="type_id" id="type_id">
				       <c:forEach items="${types}" var="st">
				         	<option value="${st.id}" <c:if test="${st.id==unit.type_id}">selected</c:if>>${st.c_name}</option>
				       </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Нэр:</td>
				<td><input type="text" id="c_name" name="c_name" value="<c:out value="${unit.c_name}" />" style="width: 500px;" /></td>
			</tr>
			<tr>
				<td>Тайлбар:</td>
				<td><textarea id="c_desc" name="c_desc" style="width: 500px;">${unit.c_desc}</textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<input type="hidden" name="user_id" value="${op.id}" />
					<input type="hidden" name="dep_id" value="${op.dep_id}" />
					<input type="submit" name="submit" value="Засах" />
				</td>
			</tr>
			
		</table>
	</form>
</div>