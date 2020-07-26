<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>

<h1>Нөөц сэлбэг шилжүүлэх</h1>
	<form method="post" action="">
		<table width="900" cellpadding="2" cellspacing="0">
			<tr>
				<td colspan="2" align="center" style="color: #D20000;">${error}</td>
			</tr>
			<tr>
				<td>Систем:</td>
				<td><input type="hidden" name="id" id="id" value="${sp.id}" />
					<select name="sys_id" id="sys_id">
				       <c:forEach items="${systems}" var="s">
				         	<option value="${s.id}" <c:if test="${sp.sys_id==s.id}">selected</c:if>>${s.c_name} ${s.c_vendor}</option>
				       </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Төхөөрөмж:</td>
				<td>
					<select name="unit_id" id="unit_id">
				       <c:forEach items="${unit}" var="u">
				         	<option value="${u.id}" <c:if test="${sp.unit_id==u.id}">selected</c:if>>${u.c_name}</option>
				       </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Байршил:</td>
				<td><input type="hidden" name="loc_id" id="loc_id" value="${sp.loc_id}" />
				   ${sp.locName }
				</td>
			</tr>
			<tr>
				<td>Сериал.№:</td>
				<td><input type="text" id="serial_key" name="serial_key" style="width: 500px;" value="${sp.serial_key}" /></td>
			</tr>
			<tr>
				<td>Продакт.№:</td>
				<td><input type="text" id="product_num" name="product_num" style="width: 500px;" value="${sp.product_num}" /></td>
			</tr>
			<tr>
				<td>Тодорхойлолт:</td>
				<td><textarea id="description" name="description" style="width: 500px;">${sp.description}</textarea></td>
			</tr>
			<tr>
				<td>Төлөв:</td>
				<td>
					<select name="status_id" id="status_id">
				       <c:forEach items="${status}" var="st">
				         	<option value="${st.id}" <c:if test="${sp.status_id==st.id}">selected</c:if>>${st.status}</option>
				       </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Ассет.№:</td>
				<td><input type="text" id="asset_id" name="asset_id" style="width: 500px;" value="${sp.asset_id}" /></td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<input type="hidden" name="user_id" value="${op.c_name}" />
					<input type="hidden" name="dep_id" value="${op.dep_id}" />
					<input type="submit" name="submit" value="Засах" />
				</td>
			</tr>
			
		</table>
	</form>
</div>