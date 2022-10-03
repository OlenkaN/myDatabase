package com.example.My_Database.Domain.Entity;

import com.example.My_Database.Domain.Entity.types.Attribute;
import com.example.My_Database.Domain.Entity.types.Value;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Setter
@Getter
public class Table {
    private String name;
    private List<Row> rows;

    public Table(String tableName) {
        this.name = tableName;
        this.rows = new ArrayList<>();
    }

    public Table(String tableName, List<Row> rows) {
        this.name = tableName;
        this.rows = rows;
    }

    public boolean addRow(Row row) {
        rows.add(row);
        return true;
    }

    public Table projection(ArrayList<String> nameOfColumn) {
        List<Row> newRows = new ArrayList<>();
        for (var row : rows) {
            Row r = new Row();
            for (var name : nameOfColumn) {
                if (row.getAttributeHashMap().containsKey(name)) {
                    r.getAttributeHashMap().put(name, row.getAttributeHashMap().get(name));
                } else {
                    log.info("No Such column");
                }
            }
            newRows.add(r);
        }
        deleteDuplicateRows(newRows);
        return new Table("Projection", newRows);
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

    public boolean deleteRow(int index, List<Row> rows) {
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

    public void deleteDuplicateRows(List<Row> rows) {
        ArrayList<Row> newRows = new ArrayList<>();
        HashSet<Integer> rowsToDelete = new HashSet<>();
        int ind = 0;
        for (Row row : rows) {
            for (Row existed : newRows) {
                if (row.EqualTo(existed)) {
                    rowsToDelete.add(ind - rowsToDelete.size());
                    break;
                }
            }
            newRows.add(row);
            ind++;
        }

        for (Integer rowInd : rowsToDelete) {
            deleteRow(rowInd, rows);
        }

    }


}
