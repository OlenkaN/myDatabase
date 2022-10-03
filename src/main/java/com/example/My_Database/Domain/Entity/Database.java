package com.example.My_Database.Domain.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityExistsException;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Setter
@Getter
public class Database {
    private String name;
    private HashMap<String, Table> tables;

    public Database(String name, HashMap<String, Table> tables) {
        this.name = name;
        this.tables = tables;
    }

    public Collection<Table> list() {
        return tables.values();
    }

    public Table get(String tableName) {
        return tables.getOrDefault(tableName, null);
    }

    public boolean addTable(Table table) {
        if (tables.containsKey(table.getName())) {
            throw new EntityExistsException(String.format("Table with this name: {} already exist", table.getName()));
        }
        tables.put(table.getName(), table);
        return true;
    }

    public boolean deleteTable(String tableName) {
        if (!tables.containsKey(tableName)) {
            throw new NoSuchElementException(String.format("Table with this name: {} not exist", tableName));
        }
        tables.remove(tableName);
        return true;
    }



}
