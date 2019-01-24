package oidc.saml;

import org.mitre.openid.connect.config.ConfigurationPropertiesBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("defaultServiceProviderTranslationService")
public class DefaultServiceProviderTranslationService implements ServiceProviderTranslationService{
  @Autowired
  private ConfigurationPropertiesBean config;

  @Override
  public String translateServiceProviderEntityId(String entityId) {
    Assert.notNull(entityId);
    return entityId.replace("@","@@").replaceAll(":","@");
  }

  @Override
  public String translateClientId(String clientId) {
    String baseUrl = config.getIssuer();
    Assert.notNull(clientId);
    return baseUrl + clientId.replaceAll("(?<!@)@(?!@)", ":").replaceAll("@@","@");
  }

}
