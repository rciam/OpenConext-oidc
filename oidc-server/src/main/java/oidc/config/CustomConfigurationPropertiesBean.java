package oidc.config;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.util.StringUtils;
import org.springframework.context.annotation.Primary;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import org.mitre.openid.connect.config.ConfigurationPropertiesBean;



/**
 * Bean to hold configuration information that must be injected into various parts
 * of our application. Set all of the properties here, and autowire a reference
 * to this bean if you need access to any configuration properties.
 *
 * @author AANGANES
 *
 */
@Primary
public class CustomConfigurationPropertiesBean extends ConfigurationPropertiesBean {

	private int maxRefreshTokenLifeTime;

	private int maxAccessTokenLifeTime;

	private int maxIdTokenLifeTime;

	private int defaultRefreshTokenLifeTime;

	private int defaultAccessTokenLifeTime;

	private int defaultIdTokenLifeTime;

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

}