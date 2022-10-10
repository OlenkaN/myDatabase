package com.example.My_Database.Domain.Entity;

import com.example.My_Database.utils.Result;
import com.example.My_Database.utils.toGson.Deserializer;
import com.example.My_Database.utils.toGson.Serializer;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityExistsException;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Slf4j
@Setter
@Getter
@NoArgsConstructor
public class Database {
    private String name;

    private HashMap<String, Table> tables;

    public Database(String name, HashMap<String, Table> tables) {
        this.name = name;
        this.tables = tables;
    }

    public Database(String name) {
        this.name = name;
        this.tables=new HashMap<>();
    }

    public Result update(Database database)
    {
        this.tables= database.getTables();
        return Result.Success();
    }

    public Collection<Table> list() {
        return tables.values();
    }

    public Table get(String tableName) {
        return tables.getOrDefault(tableName, null);
    }

    public Result addTable(Table table) {
        if (tables.containsKey(table.getName())) {
           return Result.Fail(String.format("Table with this name: {} already exist", table.getName()));
        }
        tables.put(table.getName(), table);
        return Result.Success();
    }

    public Result deleteTable(String tableName) {
        if (!tables.containsKey(tableName)) {
           return Result.Fail(String.format("Table with this name: {} not exist", tableName));
        }
        tables.remove(tableName);
        return Result.Success();
    }

    public static boolean SaveToFile(String filename, Database database) {
        try {

            String json = Serializer.Serialize(database);
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(json);
            writer.close();
        } catch (IOException ex) {
            log.error(ex.getMessage());
            return false;
        }
        return true;
    }

    public static Database ReadFromFile(String filename) {
        try {

            // create a reader
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            Database saveTo = Deserializer.getGson().fromJson(reader, Database.class);

            reader.close();
            return saveTo;
        } catch (IOException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }


}
