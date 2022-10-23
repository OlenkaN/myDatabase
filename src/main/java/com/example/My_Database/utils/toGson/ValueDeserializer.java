package com.example.My_Database.utils.toGson;

import com.example.My_Database.Domain.Entity.types.TimeAttr;
import com.example.My_Database.Domain.Entity.types.Value;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ValueDeserializer implements JsonDeserializer<Value> {
    @Override
    public Value deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
          throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        String value = jsonObject.getAsJsonPrimitive("value").getAsString();
        return new Value(value);
    }

    public static String toString(JsonElement json)
          throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        String value = jsonObject.getAsJsonPrimitive("value").getAsString();
        return new Value(value).toString();
    }


}
