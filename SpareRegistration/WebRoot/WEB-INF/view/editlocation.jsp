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
            	 jQuery("#c_building").validate({
                     expression: "if (VAL) return true; else return false;",
                     message: "!!!"
                 });
            	 jQuery("#c_room").validate({
                     expression: "if (VAL) return true; else return false;",
                     message: "!!!"
                 });
            	 jQuery("#c_rack").validate({
                     expression: "if (VAL) return true; else return false;",
                     message: "!!!"
                 });
            });
            /* ]]> */
</script>
<div style="clear: both">
<h1>Байршил засах</h1>
	<form method="post" action="">
	<input type="hidden" id="id" name="id" value="${location.id}" />
		<table width="900" cellpadding="2" cellspacing="0">
			<tr>
				<td colspan="2" align="center" style="color: #D20000;">${error}</td>
			</tr>
			<tr>
				<td>Байр:</td>
				<td>
					<input type="text" id="c_building" name="c_building" value="${location.c_building}" style="width: 500px;" />
				</td>
			</tr>
			<tr>
				<td>Өрөө:</td>
				<td>
					<input type="text" id="c_room" name="c_room" value="${location.c_room}" style="width: 500px;" />
				</td>
			</tr>
			<tr>
				<td>Шүүгээ:</td>
				<td>
					<input type="text" id="c_rack" name="c_rack" value="${location.c_rack}" style="width: 500px;" />
				</td>
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