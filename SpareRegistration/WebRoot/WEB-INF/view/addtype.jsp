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
            });
            /* ]]> */
 </script>
<div style="clear: both">
<h1>Төрөл нэмэх</h1>
	<form method="post" action="">
		<table width="900" cellpadding="2" cellspacing="0">
			<tr>
				<td colspan="2" align="center" style="color: #D20000;">${error}</td>
			</tr>
			<tr>
				<td>Төрөл:</td>
				<td>
					<input type="text" id="c_name" name="c_name" style="width: 500px;" />
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="right">
					<input type="hidden" name="user_id" value="${op.id}" />
					<input type="hidden" name="dep_id" value="${op.dep_id}" />
					<input type="submit" name="submit" value="Оруулах" />
				</td>
			</tr>
		</table>
	</form>
</div>