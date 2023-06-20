package com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.config;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

public class ConfigGsonInstant implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String instantStr = json.getAsString();
        return Instant.parse(instantStr);
    }

    @Override
    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        String instantStr = src.toString();
        return new JsonPrimitive(instantStr);
    }
}
