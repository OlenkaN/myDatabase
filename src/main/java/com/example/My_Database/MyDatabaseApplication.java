package com.example.My_Database;

import com.example.My_Database.Domain.Entity.Database;
import com.example.My_Database.Domain.Entity.DatabaseManager;
import com.example.My_Database.Domain.Entity.Row;
import com.example.My_Database.Domain.Entity.Table;
import com.example.My_Database.Domain.Entity.types.Attribute;
import com.example.My_Database.Domain.Entity.types.TimeLnvlAttr;
import com.example.My_Database.Domain.Entity.types.Types;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.HashMap;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MyDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyDatabaseApplication.class, args);
        TimeLnvlAttr timeLnvlAttr= new TimeLnvlAttr("Time", "");
        timeLnvlAttr.getValue("07/06/2013 10:11:59 / 07/06/2014 10:11:59");
        HashMap<String, Attribute> row1 = new HashMap<>();
        row1.put("User", Attribute.getAttribute("User", Types.STRING, "Kate"));
        row1.put("Age", Attribute.getAttribute("Age", Types.INTEGER, "15"));
        row1.put("Class", Attribute.getAttribute("Class", Types.CHAR, "A"));
        Row row = new Row(row1);
        Table table = new Table("school");
        table.addRow(row);
        HashMap<String,Table> tableHashMap=new HashMap<>();
        tableHashMap.put(table.getName(),table);
        Database database= new Database("Ternopil",tableHashMap);
        DatabaseManager databaseManager= new DatabaseManager();
        databaseManager.add(database);
    }

}
