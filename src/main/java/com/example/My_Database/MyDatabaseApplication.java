package com.example.My_Database;

import com.example.My_Database.Domain.Entity.Database;
import com.example.My_Database.Domain.Entity.DatabaseManager;
import com.example.My_Database.Domain.Entity.Row;
import com.example.My_Database.Domain.Entity.Table;
import com.example.My_Database.Domain.Entity.types.Attribute;
import com.example.My_Database.Domain.Entity.types.TimeLnvlAttr;
import com.example.My_Database.Domain.Entity.types.Types;
import com.example.My_Database.Domain.Entity.types.Value;
import com.example.My_Database.storage.MySQL.MySQLClient;
import com.example.My_Database.utils.toGson.Deserializer;
import com.example.My_Database.utils.toGson.Serializer;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
public class MyDatabaseApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyDatabaseApplication.class, args);
//        Table expectedResultAddAttribute = new Table("Init");
//        Attribute[] attributes = {
//              Attribute.getAttribute("User", Types.STRING),
//              Attribute.getAttribute("Time", Types.TIME),
//              Attribute.getAttribute("Class", Types.CHAR),
//              Attribute.getAttribute("Mark", Types.REAL)
//        };
//        HashMap<String, Value> row1 = new HashMap<>();
//        row1.put("User", attributes[0].getValue("Kate"));
//        row1.put("Time", attributes[1].getValue("15:30"));
//        row1.put("Class", attributes[2].getValue("A"));
//        row1.put("Mark", attributes[3].getDefault());
//
//        HashMap<String, Value> row2 = new HashMap<>();
//        row2.put("User", attributes[0].getValue("Kate"));
//        row2.put("Age", attributes[1].getValue("15"));
//        row2.put("Class", attributes[2].getValue("D"));
//        row2.put("Mark", attributes[3].getDefault());
//
//        HashMap<String, Value> row3 = new HashMap<>();
//        row3.put("User", attributes[0].getValue("Kate"));
//        row3.put("Age", attributes[1].getValue("16"));
//        row3.put("Class", attributes[2].getValue("A"));
//        row3.put("Mark", attributes[3].getDefault());
/*        for (Attribute attr : attributes) {
            expectedResultAddAttribute.addAttr(attr);
        }
        expectedResultAddAttribute.addRow(new Row(row1));
        HashMap<String, Table> tableHashMap = new HashMap<>();
        tableHashMap.put(expectedResultAddAttribute.getName(), expectedResultAddAttribute);
        Database database = new Database("Ternopil", tableHashMap);
        MySQLClient mySQLClient= new MySQLClient();
        mySQLClient.saveDB(database);*/
//        expectedResultAddAttribute.addRow(new Row(row2));
//        expectedResultAddAttribute.addRow(new Row(row3));

       /* HashMap<String, Attribute> row1 = new HashMap<>();
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
        db = Database.ReadFromFile("test1");*/

    }

}
