package com.example.My_Database.Domain.Entity;

import com.example.My_Database.Domain.Entity.types.Attribute;
import com.example.My_Database.Domain.Entity.types.Value;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
public class Table {
    private String name;
    private List<Row> rows;

    public Table(String tableName) {
        this.name = tableName;
        this.rows = new ArrayList<>();
    }

    public boolean addRow(Row row) {
        rows.add(row);
        return true;
    }

/*    public Boolean addEmptyRow() {
        HashMap<String, Value> defaultValues = new HashMap<>();
        for(Attribute attr: rows.get(0).getAttributeHashMap().values()){
            defaultValues.put(attr.getName(), attr.getDefault());
        }
        addRow(new Row(defaultValues));
        return true;
    }*/

    public boolean deleteRow(int index) {
        if (index < 0 || index >= this.rows.size()) {
            throw new ArrayIndexOutOfBoundsException(String.format("Index out of bound for deleting row"));
        }
        rows.remove(index);
        return true;
    }

    private void addAttrToRows(Attribute attribute) {
        for (Row row : rows) {
            row.getAttributeHashMap().put(attribute.name, attribute);
        }
    }

    private void removeAttrFromRows(String key) {
        for (Row row : rows) {
            row.getAttributeHashMap().remove(key);
        }
    }


}
