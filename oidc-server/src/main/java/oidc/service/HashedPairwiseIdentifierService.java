package oidc.service;

import org.mitre.openid.connect.service.PairwiseIdentiferService;

public interface HashedPairwiseIdentifierService extends PairwiseIdentiferService {

  String getIdentifier(String unspecifiedNameId, String clientId);

}