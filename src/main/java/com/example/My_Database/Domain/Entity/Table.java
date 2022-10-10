package com.example.My_Database.Domain.Entity;

import com.example.My_Database.Domain.Entity.types.Attribute;
import com.example.My_Database.Domain.Entity.types.Value;
import com.example.My_Database.utils.Result;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Setter
@Getter
@NoArgsConstructor
public class Table {
    private String name;
    private ArrayList<Row> rows;
    private Columns columns;

    public Table(String tableName) {
        this.name = tableName;
        this.rows = new ArrayList<>();
        this.columns = new Columns();

    }

    public Table(String tableName, ArrayList<Row> rows, Columns columns) {
        this.name = tableName;
        this.rows = rows;
        this.columns = columns;
    }

    public Result addRow(Row row) {
        rows.add(row);
        return Result.Success();
    }

    public Table projection(ArrayList<String> nameOfColumn) {
        ArrayList<Row> newRows = new ArrayList<>();
        Columns newColumns = new Columns();
        if (columns.columnsExistInTable(nameOfColumn)) {
            for (var row : rows) {
                Row r = new Row();
                for (var name : nameOfColumn) {
                    r.getValueHashMap().put(name, row.getValueHashMap().get(name));
                    newColumns.addAttr(columns.getAttr(name));
                }
                newRows.add(r);
            }
            deleteDuplicateRows(newRows);
        }
        return new Table("Projection", newRows, newColumns);
    }

    public Result addEmptyRow() {
        HashMap<String, Value> defaultValues = new HashMap<>();
        for (Attribute attr : columns.listAttributes()) {
            defaultValues.put(attr.getName(), attr.getDefault());
        }
        addRow(new Row(defaultValues));
        return Result.Success();
    }

    public Result deleteRow(int index) {
        if (index < 0 || index >= this.rows.size()) {
            return Result.Fail("Index out of bound for deleting row");
        }
        rows.remove(index);
        return Result.Success();
    }

    public Result deleteRow(int index, List<Row> rows) {
        if (index < 0 || index >= this.rows.size()) {
            return Result.Fail("Index out of bound for deleting row");
        }
        rows.remove(index);
        return Result.Success();
    }

    private void addAttrToRows(Attribute attribute) {
        for (Row row : rows) {
            row.getValueHashMap().put(attribute.getName(), attribute.getDefault());
        }
    }

    private void removeAttrFromRows(String key) {
        for (Row row : rows) {
            row.getValueHashMap().remove(key);
        }
    }

    public Result addAttr(Attribute attr) {
        Result result = columns.addAttr(attr);
        if (result.isSuccessful()) {
            addAttrToRows(attr);
        }
        return result;
    }

    public Result deleteAttr(String key) {
        Result result = columns.deleteAttr(key);
        if (result.isSuccessful()) {
            removeAttrFromRows(key);
        }
        return result;
    }

    public Collection<Attribute> listColumns() {
        return columns.listAttributes();
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
