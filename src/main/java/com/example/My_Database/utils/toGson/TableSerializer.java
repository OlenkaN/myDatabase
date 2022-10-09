package com.example.My_Database.utils.toGson;

import com.example.My_Database.Domain.Entity.Table;
import com.example.My_Database.Domain.Entity.types.Attribute;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TableSerializer implements JsonSerializer<Table> {

    @Override
    public JsonElement serialize(Table table, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("name", table.getName());
        JsonArray attributesJsonArray = new JsonArray();
        AttributeSerializer attributeSerializer = new AttributeSerializer();
        for (Attribute attribute : table.listColumns()) {
            attributesJsonArray.add(attributeSerializer.serialize(attribute, Attribute.class, jsonSerializationContext));
        }
        result.add("attributes", attributesJsonArray);
        result.add("rows", jsonSerializationContext.serialize(table.getRows()));
        return result;
    }
}
