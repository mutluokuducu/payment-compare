package com.paymentology.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;

public class JsonUtils {

  private static final ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.registerModule(new JsonOrgModule());
  }

  private JsonUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static String objectToJson(Object jsonObject) {
    String json;
    if (jsonObject == null) {
      json = "Null Object";
    } else {
      ObjectMapper mapper = new ObjectMapper();
      try {
        json = mapper.writeValueAsString(jsonObject);
      } catch (Exception e) {
        json = "Object could not be converted to Json Format";
      }
    }
    return json;
  }
}