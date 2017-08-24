<%@attribute name="pageName" required="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags"%>
<div class="mod-header">
    <p class="title">
        <a href="http://snf-761523.vm.okeanos.grnet.gr:8080/oidc/">
            <img style="width:100px; height:60px;" src="resources/images/download.jpg"/>
            ${config.topbarTitle}
        </a>
    </p>

    <div class="meta">
        <!--<security:authorize access="hasRole('ROLE_ADMIN')">-->
        <div class="name">
            <a href="manage/#user/profile"><span><spring:message code="openconext.header.welcome"/> ${ userInfo.name }</span></a>
        </div>
        <p>

        </p>
        <!--</security:authorize>-->
        <ul class="language">
            <li>
                <a id="header_lang_en" href="#" class="${pageContext.response.locale == "en" || pageContext.response.locale == null ? "selected" : ""}"><spring:message code="openconext.header.lang_en"/></a>
            </li>
            <li>
                <a id="header_lang_nl" href="#" class="${pageContext.response.locale == "nl" ? "selected" : ""}"><spring:message code="openconext.header.lang_nl"/></a>
            </li>
        </ul>
        <ul class="links">
            <li>
            <security:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')">
                <a class="btn btn-primary btn-large" href="saml/logout"><i class="icon-lock icon-white"></i>
                    <spring:message code="sidebar.personal.logout"/></a> 
            </security:authorize>
            <a href="<spring:message code="openconext.footer.git_link"/>" target="_blank">
               <img src="resources/images/github.png"/>
            </a>
            </li>
        </ul>
    </div>
</div>
<div class="mod-navigation">
    <security:authorize access="hasRole('ROLE_USER')">
        <ul>
            <security:authorize access="hasRole('ROLE_ADMIN')">
                <li><a href="manage/#admin/clients" data-toggle="collapse" data-target=".nav-collapse"><spring:message
                            code="sidebar.administrative.manage_clients"/></a></li>
                <li><a href="manage/#admin/scope" data-toggle="collapse" data-target=".nav-collapse"><spring:message
                            code="sidebar.administrative.system_scopes"/></a></li>
            </security:authorize>
            <li><a href="manage/#user/profile" data-toggle="collapse" data-target=".nav-collapse"><spring:message
                        code="sidebar.personal.profile_information"/></a></li>        
        </ul>
    </security:authorize>
</div>
