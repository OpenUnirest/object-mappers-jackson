package unirest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public class JacksonObjectMapper implements ObjectMapper {
    private final com.fasterxml.jackson.databind.ObjectMapper om;

    public JacksonObjectMapper() {
        this(new com.fasterxml.jackson.databind.ObjectMapper());
        om.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
    }

    public JacksonObjectMapper(com.fasterxml.jackson.databind.ObjectMapper om){
        this.om = om;
    }

    @Override
    public <T> T readValue(String value, Class<T> valueType) {
        try {
            return om.readValue(value, valueType);
        } catch (IOException e) {
            throw new UnirestException(e);
        }
    }

    @Override
    public <T> T readValue(String value, GenericType<T> genericType) {
        try {
            return om.readValue(value,  om.constructType(genericType.getType()));
        } catch (IOException e) {
            throw new UnirestException(e);
        }
    }

    @Override
    public String writeValue(Object value) {
        try {
            return om.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new UnirestException(e);
        }
    }
}
