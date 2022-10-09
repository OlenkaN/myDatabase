package com.example.My_Database.utils.toGson;

import com.example.My_Database.Domain.Entity.Table;
import com.example.My_Database.Domain.Entity.types.Attribute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Deserializer {

    private static Gson gson = new GsonBuilder()
          .registerTypeAdapter(Table.class, new TableDeserializer())
          .registerTypeAdapter(Attribute.class, new AttributeDeserializer())
         //   .registerTypeAdapter(RowDeserializer.class, new RowDeserializer(attributes))
            .create();


    public static Gson getGson() {
        return gson;
    }
}


