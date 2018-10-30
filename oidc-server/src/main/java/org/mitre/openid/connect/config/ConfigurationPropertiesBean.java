package org.mitre.openid.connect.config;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;



/**
 * Bean to hold configuration information that must be injected into various parts
 * of our application. Set all of the properties here, and autowire a reference
 * to this bean if you need access to any configuration properties.
 *
 * @author AANGANES
 *
 */
public class ConfigurationPropertiesBean {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationPropertiesBean.class);

	private String issuer;

	private String topbarTitle;

	private String logoImageUrl;

	private Long regTokenLifeTime;

	private Long rqpTokenLifeTime;

	private int maxRefreshTokenLifeTime;

	private int maxAccessTokenLifeTime;

	private int maxIdTokenLifeTime;

	private int defaultRefreshTokenLifeTime;

	private int defaultAccessTokenLifeTime;

	private int defaultIdTokenLifeTime;

	private boolean forceHttps = false; // by default we just log a warning for HTTPS deployment

	private Locale locale = Locale.ENGLISH; // we default to the english translation

	private List<String> languageNamespaces = Lists.newArrayList("messages");

	private boolean dualClient = false;

	private boolean heartMode = false;

	private boolean claimEduPersonEntitlement = true;

	private boolean claimEduPersonEntitlementOld = true;

	private boolean claimEduPersonScopedAffiliation = true;

	private boolean claimEduPersonScopedAffiliationOld = true;

	private boolean claimEduPersonUniqueId = true;

	private boolean claimEduPersonUniqueIdOld = true;

	private List<String> adminEntitlements = Lists.newArrayList("");

	private List<String> adminSubs = Lists.newArrayList("");

	private boolean adminDevelopInstance = false;

	public ConfigurationPropertiesBean() {

	}

	/**
	 * Endpoints protected by TLS must have https scheme in the URI.
	 * @throws HttpsUrlRequiredException
	 */
	@PostConstruct
	public void checkConfigConsistency() {
		if (!StringUtils.startsWithIgnoreCase(issuer, "https")) {
			if (this.forceHttps) {
				logger.error("Configured issuer url is not using https scheme. Server will be shut down!");
				throw new BeanCreationException("Issuer is not using https scheme as required: " + issuer);
			}
			else {
				logger.warn("\n\n**\n** WARNING: Configured issuer url is not using https scheme.\n**\n\n");
			}
		}

		if (languageNamespaces == null || languageNamespaces.isEmpty()) {
			logger.error("No configured language namespaces! Text rendering will fail!");
		}
	}

	/**
	 * @return the issuer baseUrl
	 */
	public String getIssuer() {
		return issuer;
	}

	/**
	 * @param iss the issuer to set
	 */
	public void setIssuer(String iss) {
		issuer = iss;
	}

	/**
	 * @return the topbarTitle
	 */
	public String getTopbarTitle() {
		return topbarTitle;
	}

	/**
	 * @param topbarTitle the topbarTitle to set
	 */
	public void setTopbarTitle(String topbarTitle) {
		this.topbarTitle = topbarTitle;
	}

	/**
	 * @return the logoImageUrl
	 */
	public String getLogoImageUrl() {
		return logoImageUrl;
	}

	/**
	 * @param logoImageUrl the logoImageUrl to set
	 */
	public void setLogoImageUrl(String logoImageUrl) {
		this.logoImageUrl = logoImageUrl;
	}

	/**
	 * @return the regTokenLifeTime
	 */
	public Long getRegTokenLifeTime() {
		return regTokenLifeTime;
	}

	/**
	 * @param regTokenLifeTime the registration token lifetime to set in seconds
	 */
	public void setRegTokenLifeTime(Long regTokenLifeTime) {
		this.regTokenLifeTime = regTokenLifeTime;
	}

	/**
	 * @return the maxRefreshTokenLifeTime
	 */
	public int getMaxRefreshTokenLifeTime() {
		return maxRefreshTokenLifeTime;
	}

	/**
	 * @param maxRefreshTokenLifeTime the refresh token lifetime to set in seconds
	 */
	public void setMaxRefreshTokenLifeTime(int maxRefreshTokenLifeTime) {
		this.maxRefreshTokenLifeTime = maxRefreshTokenLifeTime;
	}

	/**
	 * @return the maxAccessTokenLifeTime
	 */
	public int getMaxAccessTokenLifeTime() {
		return maxAccessTokenLifeTime;
	}

	/**
	 * @param maxAccessTokenLifeTime the access token lifetime to set in seconds
	 */
	public void setMaxAccessTokenLifeTime(int maxAccessTokenLifeTime) {
		this.maxAccessTokenLifeTime = maxAccessTokenLifeTime;
	}

	/**
	 * @return the maxIdTokenLifeTime
	 */
	public int getMaxIdTokenLifeTime() {
		return maxIdTokenLifeTime;
	}

	/**
	 * @param maxIdTokenLifeTime the id token lifetime to set in seconds
	 */
	public void setMaxIdTokenLifeTime(int maxIdTokenLifeTime) {
		this.maxIdTokenLifeTime = maxIdTokenLifeTime;
	}

	/**
	 * @return the defaultRefreshTokenLifeTime
	 */
	public int getDefaultRefreshTokenLifeTime() {
		return defaultRefreshTokenLifeTime;
	}

	/**
	 * @param defaultRefreshTokenLifeTime the refresh token lifetime to set in seconds
	 */
	public void setDefaultRefreshTokenLifeTime(int defaultRefreshTokenLifeTime) {
		this.defaultRefreshTokenLifeTime = defaultRefreshTokenLifeTime;
	}

	/**
	 * @return the defaultAccessTokenLifeTime
	 */
	public int getDefaultAccessTokenLifeTime() {
		return defaultAccessTokenLifeTime;
	}

	/**
	 * @param defaultAccessTokenLifeTime the access token lifetime to set in seconds
	 */
	public void setDefaultAccessTokenLifeTime(int defaultAccessTokenLifeTime) {
		this.defaultAccessTokenLifeTime = defaultAccessTokenLifeTime;
	}

	/**
	 * @return the defaultIdTokenLifeTime
	 */
	public int getDefaultIdTokenLifeTime() {
		return defaultIdTokenLifeTime;
	}

	/**
	 * @param defaultIdTokenLifeTime the id token lifetime to set in seconds
	 */
	public void setDefaultIdTokenLifeTime(int defaultIdTokenLifeTime) {
		this.defaultIdTokenLifeTime = defaultIdTokenLifeTime;
	}

	/**
	 * @return the rqpTokenLifeTime
	 */
	public Long getRqpTokenLifeTime() {
		return rqpTokenLifeTime;
	}

	/**
	 * @param rqpTokenLifeTime the rqpTokenLifeTime to set
	 */
	public void setRqpTokenLifeTime(Long rqpTokenLifeTime) {
		this.rqpTokenLifeTime = rqpTokenLifeTime;
	}

	public boolean isForceHttps() {
		return forceHttps;
	}

	public void setForceHttps(boolean forceHttps) {
		this.forceHttps = forceHttps;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @return the languageNamespaces
	 */
	public List<String> getLanguageNamespaces() {
		return languageNamespaces;
	}

	/**
	 * @param languageNamespaces the languageNamespaces to set
	 */
	public void setLanguageNamespaces(List<String> languageNamespaces) {
		this.languageNamespaces = languageNamespaces;
	}

	/**
	 * @return true if dual client is configured, otherwise false
	 */
	public boolean isDualClient() {
		if (isHeartMode()) {
			return false; // HEART mode is incompatible with dual client mode
		} else {
			return dualClient;
		}
	}

	/**
	 * @param dualClient the dual client configuration
	 */
	public void setDualClient(boolean dualClient) {
		this.dualClient = dualClient;
	}

	/**
	 * Get the list of namespaces as a JSON string, for injection into the JavaScript UI
	 * @return
	 */
	public String getLanguageNamespacesString() {
		return new Gson().toJson(getLanguageNamespaces());
	}

	/**
	 * Get the default namespace (first in the nonempty list)
	 */
	public String getDefaultLanguageNamespace() {
		return getLanguageNamespaces().get(0);
	}

	/**
	 * @return the heartMode
	 */
	public boolean isHeartMode() {
		return heartMode;
	}

	/**
	 * @param heartMode the heartMode to set
	 */
	public void setHeartMode(boolean heartMode) {
		this.heartMode = heartMode;
	}

	/**
	 * @return the claimEduPersonEntitlement
	 */
	public boolean isClaimEduPersonEntitlement() {
		return claimEduPersonEntitlement;
	}

	/**
	 * @param claimEduPersonEntitlement the claimEduPersonEntitlement to set
	 */
	public void setClaimEduPersonEntitlement(boolean claimEduPersonEntitlement) {
		this.claimEduPersonEntitlement = claimEduPersonEntitlement;
	}

	/**
	 * @return the claimEduPersonEntitlementOld
	 */
	public boolean isClaimEduPersonEntitlementOld() {
		return claimEduPersonEntitlementOld;
	}

	/**
	 * @param claimEduPersonEntitlementOld the claimEduPersonEntitlementOld to set
	 */
	public void setClaimEduPersonEntitlementOld(boolean claimEduPersonEntitlementOld) {
		this.claimEduPersonEntitlementOld = claimEduPersonEntitlementOld;
	}

	/**
	 * @return the claimEduPersonScopedAffiliation
	 */
	public boolean isClaimEduPersonScopedAffiliation() {
		return claimEduPersonScopedAffiliation;
	}

	/**
	 * @param claimEduPersonScopedAffiliation the claimEduPersonScopedAffiliation to set
	 */
	public void setClaimEduPersonScopedAffiliation(boolean claimEduPersonScopedAffiliation) {
		this.claimEduPersonScopedAffiliation = claimEduPersonScopedAffiliation;
	}

	/**
	 * @return the claimEduPersonScopedAffiliationOld
	 */
	public boolean isClaimEduPersonScopedAffiliationOld() {
		return claimEduPersonScopedAffiliationOld;
	}

	/**
	 * @param claimEduPersonScopedAffiliationOld the claimEduPersonScopedAffiliationOld to set
	 */
	public void setClaimEduPersonScopedAffiliationOld(boolean claimEduPersonScopedAffiliationOld) {
		this.claimEduPersonScopedAffiliationOld = claimEduPersonScopedAffiliationOld;
	}

	/**
	 * @return the claimEduPersonUniqueId
	 */
	public boolean isClaimEduPersonUniqueId() {
		return claimEduPersonUniqueId;
	}

	/**
	 * @param claimEduPersonUniqueId the claimEduPersonUniqueId to set
	 */
	public void setClaimEduPersonUniqueId(boolean claimEduPersonUniqueId) {
		this.claimEduPersonUniqueId = claimEduPersonUniqueId;
	}

	/**
	 * @return the claimEduPersonUniqueIdOld
	 */
	public boolean isClaimEduPersonUniqueIdOld() {
		return claimEduPersonUniqueIdOld;
	}

	/**
	 * @param claimEduPersonUniqueIdOld the claimEduPersonUniqueIdOld to set
	 */
	public void setClaimEduPersonUniqueIdOld(boolean claimEduPersonUniqueIdOld) {
		this.claimEduPersonUniqueIdOld = claimEduPersonUniqueIdOld;
	}

	/**
	 * @return the adminEntitlements
	 */
	public List<String> getAdminEntitlements() {
		return adminEntitlements;
	}

	/**
	 * @param adminEntitlements the adminEntitlements to set
	 */
	public void setAdminEntitlements(List<String> adminEntitlements) {
		this.adminEntitlements = adminEntitlements;
	}

	/**
	 * @return the adminSubs
	 */
	public List<String> getAdminSubs() {
		return adminSubs;
	}

	/**
	 * @param adminSubs the adminSubs to set
	 */
	public void setAdminSubs(List<String> adminSubs) {
		this.adminSubs = adminSubs;
	}
	/**
	 * @return the adminDevelopInstance
	 */
	public boolean isAdminDevelopInstance() {
		return adminDevelopInstance;
	}

	/**
	 * @param adminDevelopInstance the adminDevelopInstance to set
	 */
	public void setAdminDevelopInstance(boolean adminDevelopInstance) {
		this.adminDevelopInstance = adminDevelopInstance;
	}
}
