package oidc.service;

import java.util.Date;

import org.mitre.openid.connect.web.AuthenticationTimeStamper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Primary
@Component("federatedUserApprovalHandler")
public class FederatedUserApprovalHandler implements UserApprovalHandler {

	private static final Logger LOG = LoggerFactory.getLogger(FederatedUserApprovalHandler.class);

	@Override
	public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
		//by default, we don't show the consent screen as EB just did this
		LOG.debug("isApproved");
		return true;
	}

	@Override
	public AuthorizationRequest checkForPreApproval(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
		LOG.info("checkForPreApproval");
		authorizationRequest.setApproved(true);
		setAuthTime(authorizationRequest);
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
		String authTimeString = Long.toString(new Date().getTime());
		authorizationRequest.getExtensions().put(AuthenticationTimeStamper.AUTH_TIMESTAMP, authTimeString);
	}
}
