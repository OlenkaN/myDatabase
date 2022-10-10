package com.example.My_Database.utils.toGson;

import com.example.My_Database.Domain.Entity.types.Attribute;
import com.example.My_Database.Domain.Entity.types.Types;
import com.google.gson.*;

import java.lang.reflect.Type;

public class AttributeDeserializer implements JsonDeserializer<Attribute> {
    @Override
    public Attribute deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String name = jsonObject.getAsJsonPrimitive("name").getAsString();
        String attrType = jsonObject.getAsJsonPrimitive("type").getAsString();
        String value = jsonObject.getAsJsonPrimitive("value").getAsString();
        return Attribute.getAttribute(name, Types.valueOf(attrType), value);
    }
}
