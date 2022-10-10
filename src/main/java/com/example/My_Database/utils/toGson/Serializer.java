package com.example.My_Database.utils.toGson;

import com.example.My_Database.Domain.Entity.Table;
import com.example.My_Database.Domain.Entity.types.Attribute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serializer {
    private static Gson gson = new GsonBuilder()
          .registerTypeAdapter(Table.class, new TableSerializer())
          .registerTypeAdapter(Attribute.class, new AttributeSerializer())
          .create();

    public static String Serialize(Object obj) {
        return gson.toJson(obj);
    }
}
