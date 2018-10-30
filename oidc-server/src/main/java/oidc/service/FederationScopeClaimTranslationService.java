package oidc.service;

import org.mitre.openid.connect.config.ConfigurationPropertiesBean;
import org.mitre.openid.connect.service.ScopeClaimTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

@Primary
@Service("federationScopeClaimTranslationService")
public class FederationScopeClaimTranslationService implements ScopeClaimTranslationService {

	@Autowired
	private ConfigurationPropertiesBean config;

	private SetMultimap<String, String> scopesToClaims = HashMultimap.create();

	/**
	 * Default constructor; initializes scopesToClaims map
	 */
	public FederationScopeClaimTranslationService() {
		scopesToClaims.put("openid", "sub");
		scopesToClaims.put("openid", "acr");
		scopesToClaims.put("openid", "eduperson_assurance");

		scopesToClaims.put("profile", "name");
		scopesToClaims.put("profile", "preferred_username");
		scopesToClaims.put("profile", "given_name");
		scopesToClaims.put("profile", "family_name");

		scopesToClaims.put("email", "email");
	}

	@PostConstruct
	public final void init() {
		if (config.isClaimEduPersonEntitlementOld()) {
			scopesToClaims.put("eduperson_entitlement", "edu_person_entitlements");
		}
		if (config.isClaimEduPersonEntitlement()) {
			scopesToClaims.put("eduperson_entitlement", "eduperson_entitlement");
		}
		if (config.isClaimEduPersonScopedAffiliationOld()) {
			scopesToClaims.put("eduperson_scoped_affiliation", "edu_person_scoped_affiliations");
		}
		if (config.isClaimEduPersonScopedAffiliation()) {
			scopesToClaims.put("eduperson_scoped_affiliation", "eduperson_scoped_affiliation");
		}
		if (config.isClaimEduPersonUniqueIdOld()) {
			scopesToClaims.put("eduperson_unique_id", "edu_person_unique_id");
		}
		if (config.isClaimEduPersonUniqueId()) {
			scopesToClaims.put("eduperson_unique_id", "eduperson_unique_id");
		}
	}

	/* (non-Javadoc)
	 * @see org.mitre.openid.connect.service.ScopeClaimTranslationService#getClaimsForScope(java.lang.String)
	 */
	@Override
	public Set<String> getClaimsForScope(String scope) {
		if (scopesToClaims.containsKey(scope)) {
			return scopesToClaims.get(scope);
		} else {
			return new HashSet<>();
		}
	}

	/* (non-Javadoc)
	 * @see org.mitre.openid.connect.service.ScopeClaimTranslationService#getClaimsForScopeSet(java.util.Set)
	 */
	@Override
	public Set<String> getClaimsForScopeSet(Set<String> scopes) {
		Set<String> result = new HashSet<>();
		for (String scope : scopes) {
			result.addAll(getClaimsForScope(scope));
		}
		return result;
    }

}