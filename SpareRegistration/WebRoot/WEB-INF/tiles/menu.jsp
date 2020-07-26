<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/js/common.js"></script>
<div id="mainmenu" style="height: 40px; border-top: 5px solid #00bef5; background-color: #00c6ff; padding-left: 5px;">
	<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/index.html" <c:if test="${page=='index'}">class="selected"</c:if>>Нүүр</a></div>
<c:choose>
	 <c:when test="${op.c_role==0 }">
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/spare.html" <c:if test="${page=='spare'}">class="selected"</c:if>>Нөөц сэлбэг</a></div>
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/unit.html" <c:if test="${page=='unit'}">class="selected"</c:if>>Төхөөрөмж</a></div>
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/system.html" <c:if test="${page=='system'}">class="selected"</c:if>>Систем</a></div>
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/location.html" <c:if test="${page=='loc'}">class="selected"</c:if>>Байршил</a></div>
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/moving.html" <c:if test="${page=='moving'}">class="selected"</c:if>>Шилжилт</a></div>
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/type.html" <c:if test="${page=='type'}">class="selected"</c:if>>Төрөл</a></div>
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/department.html" <c:if test="${page=='dep'}">class="selected"</c:if>>Хэсэг</a></div>
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/users.html" <c:if test="${page=='users'}">class="selected"</c:if>>Хэрэглэгч</a></div>	
	 </c:when>
	 <c:when test="${op.c_role==1 }">
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/spare.html" <c:if test="${page=='spare'}">class="selected"</c:if>>Нөөц сэлбэг</a></div>
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/unit.html" <c:if test="${page=='unit'}">class="selected"</c:if>>Төхөөрөмж</a></div>
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/system.html" <c:if test="${page=='system'}">class="selected"</c:if>>Систем</a></div>
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/location.html" <c:if test="${page=='loc'}">class="selected"</c:if>>Байршил</a></div>
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/moving.html" <c:if test="${page=='moving'}">class="selected"</c:if>>Шилжилт</a></div>
		<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/users.html" <c:if test="${page=='users'}">class="selected"</c:if>>Хэрэглэгч</a></div>
	 </c:when>
	 <c:otherwise>
	 	<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/spare.html" <c:if test="${page=='spare'}">class="selected"</c:if>>Нөөц сэлбэг</a></div>
	 	<div style="float: left; padding: 1px; margin-top: 10px;"><a href="/moving.html" <c:if test="${page=='moving'}">class="selected"</c:if>>Шилжилт</a></div>
	 </c:otherwise>
</c:choose>

<div class="logout" style="float: right; padding: 1px; margin-top: 10px; padding-right: 15px;"><b><a href="/logout.html">ГАРАХ</a></b></div>
<div style="float: right; padding: 1px; margin-top: 10px;"><a title="Нууц үг солих" href="javascript:void(0)" onclick="blur(this);javascript:openNewWindow('Нууц_үг_солих','/changepass.html',420, 300, 100, 100);">Нууц үг солих</a></div>
<div style="float: right; padding: 1px; margin-top: 10px; padding-right: 5px; color: white;"><b>${op.c_name}</b></div>
</div>