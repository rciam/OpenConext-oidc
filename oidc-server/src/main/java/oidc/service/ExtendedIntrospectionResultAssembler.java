package oidc.service;

import oidc.model.FederatedUserInfo;
import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.mitre.oauth2.service.impl.DefaultIntrospectionResultAssembler;
import org.mitre.openid.connect.model.UserInfo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.mitre.openid.connect.config.ConfigurationPropertiesBean;

import java.util.Map;
import java.util.Set;

@Service
@Primary
public class ExtendedIntrospectionResultAssembler extends DefaultIntrospectionResultAssembler {
    
    @Autowired
    private ConfigurationPropertiesBean config;

    @Override
    public Map<String, Object> assembleFrom(OAuth2AccessTokenEntity accessToken, UserInfo userInfo, Set<String>
        authScopes) {
        Map<String, Object> result = super.assembleFrom(accessToken, userInfo, authScopes);
        if (userInfo != null && userInfo instanceof FederatedUserInfo) {
            FederatedUserInfo federatedUserInfo = (FederatedUserInfo) userInfo;
            result.put("iss", config.getIssuer());
            result.put("authenticating_authority", federatedUserInfo.getAuthenticatingAuthority());
            result.put("acr", federatedUserInfo.getAcr());
            result.put("eduperson_assurance", federatedUserInfo.getEduPersonAssurance());
            if (config.isClaimEduPersonEntitlementOld()) {
                result.put("edu_person_entitlements", federatedUserInfo.getEduPersonEntitlements());
            }
            if (config.isClaimEduPersonEntitlement()) {
                result.put("eduperson_entitlement", federatedUserInfo.getEduPersonEntitlements());
            }
            if (config.isClaimEduPersonScopedAffiliationOld()) {
                result.put("edu_person_scoped_affiliations", federatedUserInfo.getEduPersonScopedAffiliations());
            }
            if (config.isClaimEduPersonScopedAffiliation()) {
                result.put("eduperson_scoped_affiliation", federatedUserInfo.getEduPersonScopedAffiliations());
            }
        }
        return result;
    }

}
