package oidc.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.mitre.openid.connect.model.DefaultUserInfo;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_info")
public class FederatedUserInfo extends DefaultUserInfo {
	
  private static final long serialVersionUID = 7368566484262998945L;

  private String unspecifiedNameId;
  private String authenticatingAuthority;
  
  /* The Authentication Context Class that the authentication performed satisfied. */
  private String acr;
  
  private String eduPersonUniqueId;
  private String eduPersonPrincipalName;
  private String eduPersonTargetedId;

  private Set<String> eduPersonScopedAffiliations = new HashSet<>();
  private Set<String> eduPersonEntitlements = new HashSet<>();

  @Basic
  @Column(name = "unspecified_name_id")
  public String getUnspecifiedNameId() {
    return unspecifiedNameId;
  }

  public void setUnspecifiedNameId(String unspecifiedNameId) {
    this.unspecifiedNameId = unspecifiedNameId;
  }

  @Basic
  @Column(name = "authenticating_authority")
  public String getAuthenticatingAuthority() {
    return authenticatingAuthority;
  }

  public void setAuthenticatingAuthority(String authenticatingAuthority) {
    this.authenticatingAuthority = authenticatingAuthority;
  }
  
  /**
   * Returns a String specifying the Authentication Context Class Reference 
   * value that identifies the Authentication Context Class that the 
   * authentication performed satisfied.
   * 
   * @return a String specifying the Authentication Context Class Reference 
   *         value that identifies the Authentication Context Class that the 
   *         authentication performed satisfied
   * @see http://openid.net/specs/openid-connect-core-1_0.html#IDToken
   */
  @Basic
  @Column(name = "acr")
  public String getAcr() {
    return this.acr;
  }

  /**
   * Sets the Authentication Context Class Reference value that identifies the
   * Authentication Context Class that the authentication performed satisfied.
   * 
   * @param acr
   *            the Authentication Context Class Reference value to set
   * 
   * @see http://openid.net/specs/openid-connect-core-1_0.html#IDToken
   */
  public void setAcr(String acr) {
    this.acr = acr;
  }

  @Basic
  @Column(name = "edu_person_unique_id")
  public String getEduPersonUniqueId() {
    return eduPersonUniqueId;
  }

  public void setEduPersonUniqueId(String eduPersonUniqueId) {
    this.eduPersonUniqueId = eduPersonUniqueId;
  }
  
  @Basic
  @Column(name = "edu_person_principal_name")
  public String getEduPersonPrincipalName() {
    return eduPersonPrincipalName;
  }

  public void setEduPersonPrincipalName(String eduPersonPrincipalName) {
    this.eduPersonPrincipalName = eduPersonPrincipalName;
  }

  @Basic
  @Column(name = "edu_person_targeted_id")
  public String getEduPersonTargetedId() {
    return eduPersonTargetedId;
  }

  public void setEduPersonTargetedId(String eduPersonTargetedId) {
    this.eduPersonTargetedId = eduPersonTargetedId;
  }

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "user_edu_person_scoped_affiliation",
      joinColumns = @JoinColumn(name = "user_id")
  )
  @Column(name = "edu_person_scoped_affiliation")
  public Set<String> getEduPersonScopedAffiliations() {
    return eduPersonScopedAffiliations;
  }

  public void setEduPersonScopedAffiliations(Set<String> eduPersonScopedAffiliations) {
    this.eduPersonScopedAffiliations = eduPersonScopedAffiliations;
  }

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "user_edu_person_entitlement",
      joinColumns = @JoinColumn(name = "user_id")
  )
  @Column(name = "edu_person_entitlement")
  public Set<String> getEduPersonEntitlements() {
    return eduPersonEntitlements;
  }

  public void setEduPersonEntitlements(Set<String> eduPersonEntitlements) {
    this.eduPersonEntitlements = eduPersonEntitlements;
  }

  @Override
  public JsonObject toJson() {
    JsonObject obj = super.toJson();
    addProperty(obj, this.acr, "acr");
    addProperty(obj, this.eduPersonUniqueId, "edu_person_unique_id");
    addProperty(obj, this.eduPersonPrincipalName, "edu_person_principal_name");
    addProperty(obj, this.eduPersonTargetedId, "edu_person_targeted_id");

    addListProperty(obj, this.eduPersonScopedAffiliations, "edu_person_scoped_affiliations");
    addListProperty(obj, this.eduPersonEntitlements, "edu_person_entitlements");
    return obj;
  }

  private void addListProperty(JsonObject obj, Set<String> set, String name) {
    if (!CollectionUtils.isEmpty(set)) {
      JsonArray jsonArray = new JsonArray();
      for (String value : set) {
        jsonArray.add(new JsonPrimitive(value));
      }
      obj.add(name, jsonArray);
    }
  }

  private void addProperty(JsonObject obj, String property, String name) {
    if (StringUtils.isNotEmpty(property)) {
      obj.addProperty(name, property);
    }
  }

  @Override
  public String toString() {
    return "FederatedUserInfo{" +
        "unspecifiedNameId='" + unspecifiedNameId + '\'' +
        ", authenticatingAuthority='" + authenticatingAuthority + '\'' +
        ", acr='" + acr + '\'' +
        ", eduPersonUniqueId='" + eduPersonUniqueId + '\'' +
        ", eduPersonPrincipalName='" + eduPersonPrincipalName + '\'' +
        ", eduPersonTargetedId='" + eduPersonTargetedId + '\'' +
        ", eduPersonScopedAffiliations=" + eduPersonScopedAffiliations +
        ", eduPersonEntitlements=" + eduPersonEntitlements +
        '}';
  }

  public String hashed() {
    return this.toString();
  }
}
