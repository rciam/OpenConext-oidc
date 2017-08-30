package oidc.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.*;
import oidc.AbstractTestIntegration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class FederatedUserInfoTest {

  private ObjectMapper objectMapper = new ObjectMapper();
  private FederatedUserInfo federatedUserInfo;

  @Before
  public void before() throws IOException {
    this.federatedUserInfo = objectMapper.readValue(new ClassPathResource("model/federated_user_info.json").getInputStream(), FederatedUserInfo.class);
  }

  @Test
  public void testFromJson() throws IOException {
    JsonObject jsonObject = federatedUserInfo.toJson();
    assertJsonObject(jsonObject);
  }

  @Test
  public void testJson() throws JsonProcessingException {
    ObjectMapper objectMapperSnakeCase = new ObjectMapper();
    objectMapperSnakeCase.setPropertyNamingStrategy(
        PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    String json = objectMapperSnakeCase.writeValueAsString(federatedUserInfo);

    Gson gson = new GsonBuilder().create();
    JsonElement element = gson.fromJson(json, JsonElement.class);
    JsonObject jsonObject = element.getAsJsonObject();
    assertJsonObject(jsonObject);
  }

  private void assertJsonObject(JsonObject jsonObject) {
    assertEquals(AbstractTestIntegration.SUB, jsonObject.getAsJsonPrimitive("sub").getAsString());
    assertEquals("John Doe", jsonObject.getAsJsonPrimitive("name").getAsString());
    assertEquals("John", jsonObject.getAsJsonPrimitive("given_name").getAsString());
    assertEquals("Doe", jsonObject.getAsJsonPrimitive("family_name").getAsString());
    assertEquals("john.doe@example.org", jsonObject.getAsJsonPrimitive("email").getAsString());
    assertEquals("http://foo.example.org/assurance/loa-1", jsonObject.getAsJsonPrimitive("acr").getAsString());
    assertEquals("0123456789@example.org", jsonObject.getAsJsonPrimitive("edu_person_unique_id").getAsString());
    assertEquals("jdoe@example.org", jsonObject.getAsJsonPrimitive("edu_person_principal_name").getAsString());
    assertEquals("fd9021b35ce0e2bb4fc28d1781e6cbb9eb720fed", jsonObject.getAsJsonPrimitive("edu_person_targeted_id").getAsString());
    assertCommaSeparatedStringEquality(
        new String[]{"student", "faculty"},
        jsonObject.getAsJsonArray("edu_person_scoped_affiliations")
    );
    assertCommaSeparatedStringEquality(
        new String[]{"http://xstor.com/contracts/HEd123", "urn:mace:washington.edu:confocalMicroscope"},
        jsonObject.getAsJsonArray("edu_person_entitlements")
    );
  }

  //The ordering is not constant
  private void assertCommaSeparatedStringEquality(String[] expected, JsonArray actual) {
    List<String> actuals = new ArrayList<>();
    for (int i = 0; i < actual.size(); i++) {
      actuals.add(actual.get(i).getAsJsonPrimitive().getAsString());
    }
    Set<String> expectedSet = new HashSet<>(Arrays.asList(expected));
    Set<String> actualSet = new HashSet<>(actuals);
    assertEquals(expectedSet, actualSet);

  }
}