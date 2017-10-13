package com.SAM2018.ui;

import spark.ResponseTransformer;

import com.google.gson.Gson;

/**
 * A Utility class for parsing and formatting JSON data.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class JsonUtils {

  private static final Gson GSON = new Gson();

  public static <T> T fromJson(final String json, final Class<T> clazz) {
    return GSON.fromJson(json, clazz);
  }

  public static String toJson(Object object) {
    return GSON.toJson(object);
  }

  public static ResponseTransformer json() {
    return JsonUtils::toJson;
  }

}
