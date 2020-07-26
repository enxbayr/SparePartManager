<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
            /* <![CDATA[ */
            jQuery(function(){
            	 jQuery("#loginName").validate({
                     expression: "if (VAL) return true; else return false;",
                 });
                 jQuery("#loginPass").validate({
                      expression: "if (VAL) return true; else return false;",
                 });	 
            });
            /* ]]> */
 </script>

<div style="padding: 20px;">
	<h1>
		НӨӨЦ СЭЛБЭГИЙН БҮРТГЭЛ
	</h1>
	<div class="sub_menu" style="width: 900px; height: 400px; margin-bottom: 20px; font-size: 12px;">
		<form action="index.html" method="post">
			<table width="90%" align="center" cellpadding="0" cellspacing="10" style="padding-top: 100px;">
				<tr>
					<td colspan="2" align="center" style="color: red; font-size: 11px;">${error}</td>
				</tr>
				<tr>
					<td width="50%" align="right">
						Нэвтрэх нэр:
					</td>
					<td align="left">
						<input type="text" style="width: 100px;" id="loginName" name="loginName" />
					</td>
				</tr>
				<tr>
					<td width="50%" align="right">
						Нууц үг:
					</td>
					<td align="left">
						<input type="password" style="width: 100px;" id="loginPass" name="loginPass" />
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input type="submit" style="width: 100px;" name="Submit" value="Нэвтрэх" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
