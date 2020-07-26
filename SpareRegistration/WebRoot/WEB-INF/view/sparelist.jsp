<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
	function remove_spare(message, id) {
		var answer = confirm(message);
	    if (answer){
	       $.post('spare.html', {id:id}, function(data) {
	       window.location.reload();
		});
	    }
	    return false;  
	}

$(document).ready(function() {
	$("#toexcel").click(function() {
		alert("Creating xls file...");
		$.post('sparexls.html', {sysid: $("#sys_id").val(), depid: $("#dep_id").val(),
	 						locid: $("#loc_id").val(), uid: $("#user_id").val(),
	 						stid: $("#status_id").val(), tid: $("#type_id").val(),
	 						fn: $("#by_name").val(), fs: $("#by_serial").val(),
	 						fr: $("#by_register").val(), fa: $("#by_asset").val()}, function(data) {
		});
	});
	$("select").change(function() {
		$.post('sparel.html', {sysid: $("#sys_id").val(), depid: $("#dep_id").val(),
	 						locid: $("#loc_id").val(), uid: $("#user_id").val(),
	 						stid: $("#status_id").val(), tid: $("#type_id").val(),
	 						fn: $("#by_name").val(), fs: $("#by_serial").val(),
	 						fr: $("#by_register").val(), fa: $("#by_asset").val()}, function(data) {
			$("#sparel").html(data);
		});
		
		$.post('sparep.html', {sysid: $("#sys_id").val(), depid: $("#dep_id").val(),
	 						locid: $("#loc_id").val(), uid: $("#user_id").val(),
	 						stid: $("#status_id").val(), tid: $("#type_id").val(),
	 						fn: $("#by_name").val(), fs: $("#by_serial").val(),
	 						fr: $("#by_register").val(), fa: $("#by_asset").val()}, function(data) {
			$("#sparep").html(data);
		});
	});
	$("input").keyup(function(){
		$.post('sparel.html', {sysid: $("#sys_id").val(), depid: $("#dep_id").val(),
	 						locid: $("#loc_id").val(), uid: $("#user_id").val(),
	 						stid: $("#status_id").val(), tid: $("#type_id").val(),
	 						fn: $("#by_name").val(), fs: $("#by_serial").val(),
	 						fr: $("#by_register").val(), fa: $("#by_asset").val()}, function(data) {
			$("#sparel").html(data);
		});
		
		$.post('sparep.html', {sysid: $("#sys_id").val(), depid: $("#dep_id").val(),
	 						locid: $("#loc_id").val(), uid: $("#user_id").val(),
	 						stid: $("#status_id").val(), tid: $("#type_id").val(),
	 						fn: $("#by_name").val(), fs: $("#by_serial").val(),
	 						fr: $("#by_register").val(), fa: $("#by_asset").val()}, function(data) {
			$("#sparep").html(data);
		});
	});
});

</script>
<div style="clear: both;">
<h1>Нөөц сэлбэг</h1>
<p style="text-align: left; font-weight: bold;"><a href="/sparecount.html">Бүлэглэж харах</a></p>
<c:if test="${op.c_role!=3}"><c:if test="${op.c_role!=4}"><p style="text-align: right; font-weight: bold;"><a href="/addspare.html">Нөөц сэлбэг нэмэх</a></p></c:if></c:if>

<span style="color: #00D200; text-align: center; width: 900px;">${success}</span>
<span style="color: #D20000; text-align: center; width: 900px;">${error}</span>

<form>
<c:if test="${op.c_role!=0}">
	<input type="hidden" name="dep_id" id="dep_id" value="${op.dep_id}">
</c:if>
<table id="list" width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<th rowspan="2" width="20"></th>
		<th>Нэр</th>
		<th width="70">Төрөл</th>
		<th width="140">Систем</th>
		<c:if test="${op.c_role==0}"><th width="100">Хэсэг</th></c:if>
		<th width="160">Байршил</th>
		<th width="70">Огноо</th>
		<th width="100">Сериал.№</th>
		<th width="100">Продакт.№</th>
		<th width="100">Бүртгэсэн</th>
		<th width="100">Төлөв</th>
		<th width="100">Ассет.№</th>
		<c:if test="${op.c_role!=3}"><c:if test="${op.c_role!=4}"><th rowspan="2" width="20"></th></c:if></c:if>
		<c:if test="${op.c_role!=3}"><c:if test="${op.c_role!=4}"><th rowspan="2" width="20"></th></c:if></c:if>
		<c:if test="${op.c_role!=3}"><c:if test="${op.c_role!=4}"><th rowspan="2" width="20"></th></c:if></c:if>
	</tr>
	<tr>
		<th>
			<input type="text" id="by_name" name="by_name" value="${fn}" style="width: 150px;" />
		</th>
		<th>
			<select name="type_id" id="type_id">
				<option value="0" <c:if test="${tid==0}">selected</c:if>>Бүгд</option>
				<c:forEach items="${type}" var="st">
				      <option value="${st.id}" <c:if test="${tid==st.id}">selected</c:if>>${st.c_name}</option>
				</c:forEach>
			</select>
		</th>
		<th>
			<select name="sys_id" id="sys_id">
				<option value="0" <c:if test="${sysid==0}">selected</c:if>>Бүгд</option>
				<c:forEach items="${system}" var="st">
				      <option value="${st.id}" <c:if test="${sysid==st.id}">selected</c:if>>${st.c_name} ${st.c_vendor}</option>
				</c:forEach>
			</select>
		</th>
		<c:if test="${op.c_role==0}"><th>
			<select name="dep_id" id="dep_id">
				<option value="0" <c:if test="${depid==0}">selected</c:if>>Бүгд</option>
				<c:forEach items="${dep}" var="st">
				      <option value="${st.id}" <c:if test="${depid==st.id}">selected</c:if>>${st.c_name}</option>
				</c:forEach>
			</select>
		</th></c:if>
		<th>
			<select name="loc_id" id="loc_id">
				<option value="0" <c:if test="${lid==0}">selected</c:if>>Бүгд</option>
				<c:forEach items="${loc}" var="st">
				      <option value="${st.id}" <c:if test="${lid==st.id}">selected</c:if>>${st.c_building} (${st.c_room}) ${st.c_rack}</option>
				</c:forEach>
			</select>
		</th>
		<th>
		</th>
		<th>
			<input type="text" id="by_serial" name="by_serial" value="${fs}" style="width: 80px;" />
		</th>
		<th>
			<input type="text" id="by_register" name="by_register" value="${fr}" style="width: 80px;" />
		</th>
		<th>
			<select name="user_id" id="user_id">
				<option value="" <c:if test="${uid==''}">selected</c:if>>Бүгд</option>
				<c:forEach items="${user}" var="st">
				      <option value="${st.c_name}" <c:if test="${uid==st.c_name}">selected</c:if>>${st.c_name}</option>
				</c:forEach>
			</select>
		</th>
		<th>
			<select name="status_id" id="status_id">
				<option value="0" <c:if test="${stid==0}">selected</c:if>>Бүгд</option>
				<c:forEach items="${status}" var="st">
				      <option value="${st.id}" <c:if test="${stid==st.id}">selected</c:if>>${st.status}</option>
				</c:forEach>
			</select>
		</th>
		<th>
			<input type="text" id="by_asset" name="by_asset" value="${fa}" style="width: 80px;" />
		</th>
	</tr>
</table>
</form>
<div id="sparel">
<table id="list" width="100%" cellpadding="2" cellspacing="0">
	<c:forEach items="${sparelist}" var="sp" varStatus="i">
	<tr>
		<td width="20" style="text-align: right;">${pageStart*perPage+i.index+1}.</td>
		<td style="text-align: left;"><a title="${sp.description }" href="/moving.html?sp=${sp.id }">${sp.unitName}</a></td>
		<td width="70">${sp.unitType}</td>
		<td width="140">${sp.sysName}</td>
		<c:if test="${op.c_role==0}"><td width="100">${sp.depName}</td></c:if>
		<td width="160">${sp.locName}</td>
		<td width="70">${sp.c_date}</td>
		<td width="100">${sp.serial_key}</td>
		<td width="100">${sp.product_num}</td>
		<td width="100">${sp.user_name}</td>
		<td width="100">${sp.statName}</td>
		<td width="100">${sp.asset_id}</td>
		<c:if test="${op.c_role!=3}"><c:if test="${op.c_role!=4}"><td width="20">[<a title="Шилжүүлэг хийх" href="javascript:void(0)" onclick="blur(this);javascript:openNewWindow('Шилжүүлэг','/trspare.html?id=${sp.id}',420, 470, 100, 100);">Ш</a>]</td></c:if></c:if>
		<c:if test="${op.c_role!=3}"><c:if test="${op.c_role!=4}"><td width="20">[<a title="Засах" href="/editspare.html?id=${sp.id}">З</a>]</td></c:if></c:if>
		<c:if test="${op.c_role!=3}"><c:if test="${op.c_role!=4}"><td width="20">[<a href="#" onclick="return remove_spare('Устгах уу?', ${sp.id});">У</a>]</td></c:if></c:if>
	</tr>
	</c:forEach>
</table>
</div>

<div id="sparep" align="center">
<font size="1">
	<c:if test="${pageCountperPage>0}"> | 
	<c:forEach begin="0" end="${pageCountperPage-1}" var="i">
		<c:choose>
			<c:when test="${pageStart==i}">
				<b>${i+1}</b> | 
			</c:when>
			<c:otherwise>
				<a href="/spare.html?tid=${tid}&amp;sysid=${sysid}&amp;depid=${depid}&amp;locid=${locid}&amp;uid=${uid}&amp;stid=${stid}&amp;fn=${fn}&amp;fs=${fs}&amp;fr=${fr}&amp;fa=${fa}&amp;p=${i}">${i+1}</a> |
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
			<a href="/spare.html?tid=${tid}&amp;sysid=${sysid}&amp;depid=${depid}&amp;locid=${locid}&amp;uid=${uid}&amp;stid=${stid}&amp;fn=${fn}&amp;fs=${fs}&amp;fr=${fr}&amp;fa=${fa}&amp;p=${pageCountperPage}">${pageCountperPage+1}</a>
		</c:otherwise>
	</c:choose>
	</c:if>
</font>
</div>
<div style="float: right; padding-right: 20px;"><img src="/images/excel-8.png" width="30" border="0" id="toexcel" title="XLS рүү хөрвүүлэх" style="cursor: pointer;" /></div>
</div>