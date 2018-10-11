package oidc.service;

import org.mitre.openid.connect.service.ScopeClaimTranslationService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

@Primary
@Service("federationScopeClaimTranslationService")
public class FederationScopeClaimTranslationService implements ScopeClaimTranslationService {

  private SetMultimap<String, String> scopesToClaims = HashMultimap.create();

	/**
	 * Default constructor; initializes scopesToClaims map
	 */
	public FederationScopeClaimTranslationService() {
		scopesToClaims.put("openid", "sub");

		scopesToClaims.put("profile", "name");
		scopesToClaims.put("profile", "preferred_username");
		scopesToClaims.put("profile", "given_name");
		scopesToClaims.put("profile", "family_name");
		scopesToClaims.put("profile", "middle_name");
		scopesToClaims.put("profile", "nickname");
		scopesToClaims.put("profile", "profile");
		scopesToClaims.put("profile", "picture");
		scopesToClaims.put("profile", "website");
		scopesToClaims.put("profile", "gender");
		scopesToClaims.put("profile", "zoneinfo");
		scopesToClaims.put("profile", "locale");
		scopesToClaims.put("profile", "updated_at");
		scopesToClaims.put("profile", "birthdate");

		scopesToClaims.put("email", "email");
		scopesToClaims.put("email", "email_verified");

		scopesToClaims.put("phone", "phone_number");
		scopesToClaims.put("phone", "phone_number_verified");

		scopesToClaims.put("address", "address");
		
		scopesToClaims.put("refeds_edu", "name");
		scopesToClaims.put("refeds_edu", "given_name");
		scopesToClaims.put("refeds_edu", "family_name");
		scopesToClaims.put("refeds_edu", "email");
		scopesToClaims.put("refeds_edu", "sub");
		scopesToClaims.put("refeds_edu", "eduperson_unique_id");
		scopesToClaims.put("refeds_edu", "acr");
		scopesToClaims.put("refeds_edu", "eduperson_assurance");
		scopesToClaims.put("refeds_edu", "edu_person_scoped_affiliations");
		scopesToClaims.put("refeds_edu", "eduperson_scoped_affiliation");
		scopesToClaims.put("refeds_edu", "edu_person_entitlements");
		scopesToClaims.put("refeds_edu", "eduperson_entitlement");
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