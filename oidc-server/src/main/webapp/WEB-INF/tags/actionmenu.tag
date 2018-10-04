<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<security:authorize access="hasRole('ROLE_ADMIN')">
	<li class="nav-header"><spring:message code="sidebar.administrative.title"/></li>
	<li><a href="manage/#admin/clients" data-toggle="collapse" data-target=".nav-collapse"><spring:message code="sidebar.administrative.manage_clients"/></a></li>
	<li><a href="manage/#admin/scope" data-toggle="collapse" data-target=".nav-collapse"><spring:message code="sidebar.administrative.system_scopes"/></a></li>
	<li class="divider"></li>
</security:authorize>
<li class="nav-header"><spring:message code="sidebar.personal.title"/></li>
<li><a href="manage/#user/services" data-toggle="collapse" data-target=".nav-collapse"><spring:message code="sidebar.personal.services"/></a></li>
<li><a href="manage/#user/profile" data-toggle="collapse" data-target=".nav-collapse"><spring:message code="sidebar.personal.profile_information"/></a></li>