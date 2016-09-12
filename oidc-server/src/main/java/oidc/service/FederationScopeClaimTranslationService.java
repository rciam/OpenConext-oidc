package oidc.service;

import org.mitre.openid.connect.service.ScopeClaimTranslationService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Primary
@Service("federationScopeClaimTranslationService")
public class FederationScopeClaimTranslationService implements ScopeClaimTranslationService {

  private final Set<String> claims = new HashSet<>();
  private final Set<String> noClaims = new HashSet<>();

  public FederationScopeClaimTranslationService() {
    claims.add("sub");
    claims.add("acr");

    claims.add("name");
    claims.add("given_name");
    claims.add("family_name");
    // TODO claims.add("zoneinfo");
    // TODO claims.add("locale");

    claims.add("email");
    claims.add("email_verified");  

    claims.add("edu_person_scoped_affiliations");

    claims.add( "edu_person_entitlements");

  }

  @Override
  public Set<String> getClaimsForScope(String scope) {
    return "openid".equals(scope) ? claims : noClaims;
  }

  @Override
  public Set<String> getClaimsForScopeSet(Set<String> scopes) {
    return scopes.contains("openid") ? claims : noClaims;
  }

  protected Set<String> allClaims() {
    return claims;
  }

}
