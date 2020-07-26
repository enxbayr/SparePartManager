<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
            /* <![CDATA[ */
            jQuery(function(){
            	 jQuery("#newpass").validate({
                     expression: "if (VAL) return true; else return false;",
                     message: "!!!"
                 });
                 jQuery("#oldpass").validate({
                     expression: "if (VAL) return true; else return false;",
                     message: "!!!"
                 });
                 jQuery("#confirmpass").validate({
                     expression: "if (VAL) return true; else return false;",
                     message: "!!!"
                 });
            });
            /* ]]> */
 </script>
<div style="padding: 10px;">
<h1>Нууц үг солих</h1>
	<c:if test="${success==null }">
	<form method="post" action="">
		<table width="360" cellpadding="5" cellspacing="0" id="sublist" style="font-size: 12px;">
			<tr>
				<td colspan="2" style="color: #D20000; text-align: center; font-weight: normal;">${error}</td>
			</tr>
			<tr>
				<th>Хуучин нууц үг:</th>
				<td><input type="password" style="width: 70px;" id="oldpass" name="oldpass" /></td>
			</tr>
			<tr>
				<th>Шинэ нууц үг:</th>
				<td><input type="password" style="width: 70px;" id="newpass" name="newpass" /></td>
			</tr>
			<tr>
				<th>Шинэ нууц үг(давтах):</th>
				<td><input type="password" style="width: 70px;" id="confirmpass" name="confirmpass" /></td>
			</tr>
			<tr>
				<th></th>
				<td>
					<input type="hidden" name="user_id" value="${op.id}" />
					<input type="hidden" name="dep_id" value="${op.dep_id}" />
					<input type="submit" name="submit" value="Солих" />
				</td>
			</tr>
		</table>
	</form>
	</c:if>
	<c:if test="${success!=null }">
		<p>Нууц үг амжилттай солигдлоо</p>
		<p><a href="javascript:window.close();">Хаах</a></p>
	</c:if>
</div>