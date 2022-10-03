package com.example.My_Database.Domain.Entity;

import lombok.extern.slf4j.Slf4j;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Slf4j
public class DatabaseManager {
    public static DatabaseManager instance;
    private final HashMap<String, Database> databases = new HashMap<>();

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Collection<Database> list() {
        return databases.values();
    }

    public Database get(String name) {
        return databases.getOrDefault(name, null);
    }

    public boolean add(Database db) {
        if (databases.containsKey(db.getName())) {
            throw new KeyAlreadyExistsException("Duplicate error: database with such name already exists");
        }
        databases.put(db.getName(), db);
        return true;
    }

    public boolean delete(String name) {
        if (!databases.containsKey(name)) {
            throw new NoSuchElementException("Database with such name does not exist");
        }
        databases.remove(name);
        return true;
    }

    public boolean SaveToFile(String filename, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(content);
            writer.close();
        } catch (IOException ex) {
            log.error(ex.getMessage());
            return false;
        }
        return true;
    }
}
