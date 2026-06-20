package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigParser {

    public RenderConfig parseConfig(String jsonString) throws Exception {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(jsonString, RenderConfig.class);

    }


}
