package com.omo.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomAuthoritySerializer extends JsonSerializer<List<CustomAuthority>> {

    @Override
    public void serialize(List<CustomAuthority> authorities, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        List<String> roles = new ArrayList<>();
        if (authorities != null) {
            roles = authorities.stream()
                    .map(CustomAuthority::getAuthority)
                    .collect(Collectors.toList());
        }
        gen.writeObject(roles);
    }
}
