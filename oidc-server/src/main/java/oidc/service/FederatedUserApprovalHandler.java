package oidc.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.mitre.oauth2.service.SystemScopeService;
import org.mitre.openid.connect.web.AuthenticationTimeStamper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.Map;

@Primary
@Component("federatedUserApprovalHandler")
public class FederatedUserApprovalHandler implements UserApprovalHandler {
	
  private static final Logger LOG = LoggerFactory.getLogger(FederatedUserApprovalHandler.class);

  @Autowired
  private SystemScopeService systemScopes;
  
  @Override
  public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
    //by default, we don't show the consent screen as EB just did this
	LOG.debug("isApproved");
    return true;
  }

  @Override
  public AuthorizationRequest checkForPreApproval(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
	LOG.info("checkForPreApproval"); // TODO DEBUG
	//TODO if (systemScopes.scopesMatch(??.getAllowedScopes(), authorizationRequest.getScope())) {
		authorizationRequest.setApproved(true);

		setAuthTime(authorizationRequest);
	//}
	return authorizationRequest;
  }

  @Override
  public AuthorizationRequest updateAfterApproval(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
	LOG.debug("updateAfterApproval");  
    return authorizationRequest;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Map<String, Object> getUserApprovalRequest(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
	LOG.debug("getUserApprovalRequest");  
    return Collections.EMPTY_MAP;
  }
  
  /**
   * Get the auth time out of the current session and add it to the
   * auth request in the extensions map.
   * 
   * @param authorizationRequest
   */
  private void setAuthTime(AuthorizationRequest authorizationRequest) {
	  LOG.debug("setAuthTime");
	  // Get the session auth time, if we have it, and store it in the request
	  /*ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	  if (attr != null) {
		  LOG.debug("attr != null"); // TODO REMOVE
		  HttpSession session = attr.getRequest().getSession();
		  if (session != null) {
			  LOG.debug("session != null"); // TODO REMOVE
			  Date authTime = (Date) session.getAttribute(AuthenticationTimeStamper.AUTH_TIMESTAMP);
			  if (authTime != null) {
				  LOG.debug("authTime != null"); // TODO REMOVE
				  String authTimeString = Long.toString(authTime.getTime());
				  authorizationRequest.getExtensions().put(AuthenticationTimeStamper.AUTH_TIMESTAMP, authTimeString);
			  } else {
				  String authTimeString = Long.toString(new Date().getTime());
				  authorizationRequest.getExtensions().put(AuthenticationTimeStamper.AUTH_TIMESTAMP, authTimeString);
			  }
		  }
	  }*/
	  String authTimeString = Long.toString(new Date().getTime());
	  authorizationRequest.getExtensions().put(AuthenticationTimeStamper.AUTH_TIMESTAMP, authTimeString);
  }
}
