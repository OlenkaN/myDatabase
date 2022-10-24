package com.example.My_Database.storage.MySQL;



import com.example.My_Database.Domain.Entity.Database;
import com.example.My_Database.Domain.Entity.Table;
import com.example.My_Database.storage.Storage;
import com.example.My_Database.utils.Result;
import com.example.My_Database.utils.toGson.Deserializer;
import com.example.My_Database.utils.toGson.Serializer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLClient implements Storage {
    private final String url ="jdbc:mysql://localhost:3306";
    private final String username="root";
    private final String password = "password";

    private Connection connection;
    private Statement statement;

    public MySQLClient(){}

    private void open() throws Exception{
        if(connection == null){
            connection = DriverManager.getConnection(url, username, password);
        }
        statement = connection.createStatement();
    }

    private void close() throws Exception {
        statement.close();
        if(connection != null){
            connection.close();
            connection = null;
        }
    }

    @Override
    public ArrayList<String> getDBNames() {
        ArrayList<String> result = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            open();
            ResultSet resultSet = statement.executeQuery("SHOW DATABASES");
            while(resultSet.next()){
                result.add(resultSet.getString("Database"));
            }
            resultSet.close();
        } catch (Exception e){
            e.printStackTrace();;
        }
        return result;
    }

    @Override
    public Result saveDB(Database db) {
       try{
           Class.forName("com.mysql.cj.jdbc.Driver");
           open();
           String createDB = String.format("CREATE DATABASE %s", db.getName());
           statement.executeUpdate(createDB);


           for(Table t: db.list()){
               String fullTableName = String.format("%s.%s", db.getName(), t.getName());
               String createTable = String.format("CREATE TABLE %s(data JSON)", fullTableName);
               statement.executeUpdate(createTable);

               String jsonValue = Serializer.Serialize(t);

               String insertData = String.format("INSERT INTO %s VALUES(\'%s\')", fullTableName, jsonValue);
               statement.executeUpdate(insertData);
           }

           close();
       } catch (Exception e){
           e.printStackTrace();
           return Result.Fail("Failed to save data " + e.getMessage());
       }
       return Result.Success();
    }

    @Override
    public Result dropDB(String dbName) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            open();
            String dropDB = String.format("DROP DATABASE IF EXISTS %s", dbName);
            statement.executeUpdate(dropDB);

            close();
        } catch (Exception e){
            e.printStackTrace();
            return Result.Fail("Failed to drop database " + e.getMessage());
        }
        return Result.Success();
    }

    @Override
    public Database loadDB(String dbName) {
        Database database = new Database(dbName);

        String showTables = String.format("SHOW TABLES FROM %s", dbName);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            open();

            ResultSet tables = statement.executeQuery(showTables);
            String columnName = String.format("Tables_in_%s", dbName);

            ArrayList<String> tableNames = new ArrayList<>();

            while(tables.next()) {
                tableNames.add(tables.getString(columnName));
            }

            tables.close();

            for (String tableName: tableNames) {

                String selectTable = String.format("SELECT data FROM %s.%s", dbName, tableName);
                ResultSet data = statement.executeQuery(selectTable);

                while (data.next()) {
                    Table table = Deserializer.getGson().fromJson(data.getString("data"), Table.class);
                    database.addTable(table);
                }

                data.close();
            }

            close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return database;
    }
}
