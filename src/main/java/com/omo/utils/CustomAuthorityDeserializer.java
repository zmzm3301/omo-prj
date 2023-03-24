package com.omo.utils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomAuthorityDeserializer extends JsonDeserializer<List<CustomAuthority>> {

    @Override
    public List<CustomAuthority> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        List<String> roles = p.readValueAs(new TypeReference<List<String>>() {});
        return roles.stream()
                .map(CustomAuthority::new)
                .collect(Collectors.toList());
    }
}