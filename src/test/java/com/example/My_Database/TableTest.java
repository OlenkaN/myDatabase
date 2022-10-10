package com.example.My_Database;


import com.example.My_Database.Domain.Entity.Columns;
import com.example.My_Database.Domain.Entity.Row;
import com.example.My_Database.Domain.Entity.Table;
import com.example.My_Database.Domain.Entity.types.*;
import com.example.My_Database.utils.Result;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;


public class TableTest {
    private static Table table;
    private static Table expectedResultProjection;
    private static Table expectedResultAddAttribute;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addAttr_noDuplicate_tableWithDefaultColumn() {
        initialise();
        initialiseResultAddAttr();

        Result result = table.addAttr(Attribute.getAttribute("Mark", Types.REAL));

        String expected = new Gson().toJson(expectedResultAddAttribute);
        String actual = new Gson().toJson(table);
        assertEquals(expected, actual);
        assertTrue(result.isSuccessful());
    }

    @Test
    public void addAttr_duplicate_failed() {
        initialise();

        Result result = table.addAttr(Attribute.getAttribute("User", Types.STRING));


        String actual = new Gson().toJson(table);
        initialise();
        String expected = new Gson().toJson(table);
        assertEquals(expected, actual);
        assertFalse(result.isSuccessful());
    }

    @Test
    public void projection_columnIsNOTPresent_EmptyTable() {

        ArrayList<String> columnsToProject = new ArrayList<>(Arrays.asList("NOT",
              "EXIST"));
        initialise();

        Table actualResult = table.projection(columnsToProject);

        String expected = new Gson().toJson(new Table("Projection", new ArrayList<>(), new Columns()));
        String actual = new Gson().toJson(actualResult);
        assertEquals(expected, actual);
    }

    @Test
    public void projection_columnIsPresent_Successful() {
        ArrayList<String> columnsToProject = new ArrayList<>(Arrays.asList("User",
              "Age"));
        initialise();
        initialiseResult();

        Table actualResult = table.projection(columnsToProject);

        String expected = new Gson().toJson(expectedResultProjection);
        String actual = new Gson().toJson(actualResult);
        assertEquals(expected, actual);
    }

    @Test
    public void ValidateAttributes() {
        Attribute[] attributes = {
              new IntegerAttr("attr1"),
              new StringAttr("attr2"),
              new RealAttr("attr3"),
              new CharAttr("attr4"),
              new TimeAttr("attr5"),
              new TimeLnvlAttr("attr6")
        };
        System.out.println(attributes[5].getDefault());
        assertEquals(attributes[0].validate("10"), true);
        assertEquals(attributes[0].validate("10a"), false);
        assertEquals(attributes[1].validate("10blabla"), true);
        assertEquals(attributes[2].validate("10.0"), true);
        assertEquals(attributes[2].validate("10a"), false);
        assertEquals(attributes[3].validate("a"), true);
        assertEquals(attributes[3].validate("10a"), false);
        assertEquals(attributes[4].validate("12:44"), true);
        assertEquals(attributes[4].validate("33:56"), false);
        assertEquals(attributes[5].validate("2007-03-01T13:00:00Z/2008-05-11T15:30:00Z"), true);
        assertEquals(attributes[5].validate("2022-10-12T14:29:18Z/2022-10-11T14:29:18Z"), false);
    }

    public void initialise() {
        table = new Table("Init");
        Attribute[] attributes = {
              Attribute.getAttribute("User", Types.STRING),
              Attribute.getAttribute("Age", Types.INTEGER),
              Attribute.getAttribute("Class", Types.CHAR)};
        HashMap<String, Value> row1 = new HashMap<>();
        row1.put("User", attributes[0].getValue("Kate"));
        row1.put("Age", attributes[1].getValue("15"));
        row1.put("Class", attributes[2].getValue("A"));

        HashMap<String, Value> row2 = new HashMap<>();
        row2.put("User", attributes[0].getValue("Kate"));
        row2.put("Age", attributes[1].getValue("15"));
        row2.put("Class", attributes[2].getValue("D"));

        HashMap<String, Value> row3 = new HashMap<>();
        row3.put("User", attributes[0].getValue("Kate"));
        row3.put("Age", attributes[1].getValue("16"));
        row3.put("Class", attributes[2].getValue("A"));
        for (Attribute attr : attributes) {
            table.addAttr(attr);
        }
        table.addRow(new Row(row1));
        table.addRow(new Row(row2));
        table.addRow(new Row(row3));
    }

    public void initialiseResult() {
        expectedResultProjection = new Table("Projection");
        Attribute[] attributes = {
              Attribute.getAttribute("User",Types.STRING),
              Attribute.getAttribute("Age",Types.INTEGER),
        };
        HashMap<String, Value> row1 = new HashMap<>();
        row1.put("User", attributes[0].getValue("Kate"));
        row1.put("Age", attributes[1].getValue("15"));

        HashMap<String, Value> row3 = new HashMap<>();
        row3.put("User", attributes[0].getValue("Kate"));
        row3.put("Age", attributes[1].getValue("16"));

        for (Attribute attr : attributes) {
            expectedResultProjection.addAttr(attr);
        }
        expectedResultProjection.addRow(new Row(row1));
        expectedResultProjection.addRow(new Row(row3));
    }

    public void initialiseResultAddAttr() {
        expectedResultAddAttribute = new Table("Init");
        Attribute[] attributes = {
              Attribute.getAttribute("User", Types.STRING),
              Attribute.getAttribute("Age", Types.INTEGER),
              Attribute.getAttribute("Class", Types.CHAR),
              Attribute.getAttribute("Mark", Types.REAL)
        };
        HashMap<String, Value> row1 = new HashMap<>();
        row1.put("User", attributes[0].getValue("Kate"));
        row1.put("Age", attributes[1].getValue("15"));
        row1.put("Class", attributes[2].getValue("A"));
        row1.put("Mark", attributes[3].getDefault());

        HashMap<String, Value> row2 = new HashMap<>();
        row2.put("User", attributes[0].getValue("Kate"));
        row2.put("Age", attributes[1].getValue("15"));
        row2.put("Class", attributes[2].getValue("D"));
        row2.put("Mark", attributes[3].getDefault());

        HashMap<String, Value> row3 = new HashMap<>();
        row3.put("User", attributes[0].getValue("Kate"));
        row3.put("Age", attributes[1].getValue("16"));
        row3.put("Class", attributes[2].getValue("A"));
        row3.put("Mark", attributes[3].getDefault());
        for (Attribute attr : attributes) {
            expectedResultAddAttribute.addAttr(attr);
        }
        expectedResultAddAttribute.addRow(new Row(row1));
        expectedResultAddAttribute.addRow(new Row(row2));
        expectedResultAddAttribute.addRow(new Row(row3));
    }
}
