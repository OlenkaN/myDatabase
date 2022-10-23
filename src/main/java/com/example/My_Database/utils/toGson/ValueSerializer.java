package com.example.My_Database.utils.toGson;

import com.example.My_Database.Domain.Entity.types.TimeAttr;
import com.example.My_Database.Domain.Entity.types.Value;
import com.google.gson.*;

import java.lang.reflect.Type;


public class ValueSerializer implements JsonSerializer<Value> {


    @Override
    public JsonElement serialize(Value value, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("value", value.getVal().toString());
        return result;
    }
}