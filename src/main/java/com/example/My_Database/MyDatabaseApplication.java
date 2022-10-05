package com.example.My_Database;

import com.example.My_Database.Domain.Entity.Database;
import com.example.My_Database.Domain.Entity.DatabaseManager;
import com.example.My_Database.Domain.Entity.Row;
import com.example.My_Database.Domain.Entity.Table;
import com.example.My_Database.Domain.Entity.types.Attribute;
import com.example.My_Database.Domain.Entity.types.TimeLnvlAttr;
import com.example.My_Database.Domain.Entity.types.Types;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MyDatabaseApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyDatabaseApplication.class, args);
        HashMap<String, Attribute> row1 = new HashMap<>();
        row1.put("User", Attribute.getAttribute("User", Types.STRING, "Kate"));
        row1.put("Age", Attribute.getAttribute("Age", Types.INTEGER, "15"));
        row1.put("Class", Attribute.getAttribute("Class", Types.CHAR, "A"));
        HashMap<String, Attribute> row2 = new HashMap<>();
        row2.put("User", Attribute.getAttribute("User", Types.STRING, "Kate"));
        row2.put("Age", Attribute.getAttribute("Age", Types.INTEGER, "15"));
        row2.put("Class", Attribute.getAttribute("Class", Types.CHAR, "D"));
        HashMap<String, Attribute> row3 = new HashMap<>();
        row3.put("User", Attribute.getAttribute("User", Types.STRING, "Kate"));
        row3.put("Age", Attribute.getAttribute("Age", Types.INTEGER, "16"));
        row3.put("Class", Attribute.getAttribute("Class", Types.CHAR, "A"));
        Row row = new Row(row1);
        Row row2r = new Row(row2);
        Row row3r = new Row(row3);
        Table table = new Table("school");
        table.addRow(row);
        table.addRow(row2r);
        table.addRow(row3r);
        ArrayList<String> names = new ArrayList<>();
        names.add("User");
        names.add("Class");
        Table result = table.projection(names);
        HashMap<String, Table> tableHashMap = new HashMap<>();
        tableHashMap.put(table.getName(), table);
        Database database = new Database("Ternopil", tableHashMap);
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.add(database);
        Database.SaveToFile("test1", database);
        Database db = new Database();
        db = Database.ReadFromFile("test1");

    }

}
