<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div style="padding: 10px;">
<h1>Нөөц сэлбэг шилжүүлэх</h1>
	<form method="post" action="">
	<input type="hidden" name="spare_id" id="spare_id" value="${sp.id}" />
		<table width="360" cellpadding="5" cellspacing="0" id="sublist" style="font-size: 12px;">
			<tr>
				<td colspan="2" align="center" style="color: #D20000;">${error}</td>
			</tr>
			<tr>
				<th>Систем:</th>
				<td>${sp.sysName}</td>
			</tr>
			<tr>
				<th>Нэгж:</th>
				<td>${sp.unitName}</td>
			</tr>
			<tr>
				<th>Тайлбар:</th>
				<td>${sp.description}</td>
			</tr>
			<tr>
				<th>Байршил -с:
				<input type="hidden" name="loc_from" id="loc_from" value="${sp.loc_id}" /></th>
				<td>${sp.locName }</td>
			</tr>
			<tr>
				<th>Байршил руу:</th>
				<td>
					<select name="loc_to" id="loc_to">
				       <c:forEach items="${location}" var="l">
				         	<option value="${l.id}">${l.c_building} (${l.c_room}) ${l.c_rack}</option>
				       </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>Төлөв -с:<input type="hidden" name="status_from_id" id="status_from_id" value="${sp.loc_id}" /></th>
				<td>${sp.statName}</td>
			</tr>
			<tr>
				<th>Төлөв руу:</th>
				<td>
					<select name="status_to_id" id="status_to_id">
				       <c:forEach items="${status}" var="st">
				         	<option value="${st.id}">${st.status}</option>
				       </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>Тайлбар:</th>
				<td>
					<textarea rows="3" id="comment" name="comment" style="width: 200px;"></textarea>
				</td>
			</tr>
			<tr>
				<th></th>
				<td>
					<input type="hidden" name="user_name" value="${op.c_name}" />
					<input type="hidden" name="dep_id" value="${op.dep_id}" />
					<input type="submit" name="submit" value="Шилжүүлэх" />
				</td>
			</tr>
		</table>
	</form>
</div>