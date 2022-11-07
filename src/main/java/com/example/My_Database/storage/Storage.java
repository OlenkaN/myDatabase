package com.example.My_Database.storage;


import com.example.My_Database.Domain.Entity.Database;
import com.example.My_Database.utils.Result;

import java.util.ArrayList;

public interface Storage {
    ArrayList<String> getDBNames();
    Result saveDB(Database db);
    Result dropDB(String dbName);
    Database loadDB(String dbName);
}
