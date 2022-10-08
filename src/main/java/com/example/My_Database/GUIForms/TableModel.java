package com.example.My_Database.GUIForms;

import javax.swing.table.AbstractTableModel;
import java.util.HashSet;
import java.util.Set;

public class TableModel extends AbstractTableModel {
    public Set<Integer> changedRows = new HashSet<>();
    private String[] columnNames;
    private String[] attrNames;

    public void setColumnNames(String[] columns){
        this.columnNames = columns;
    }

    private String[][] data;
    public  void setData(String[][] data){
        this.data = data;
        fireTableStructureChanged();
    }

//    public final Object[] longValues = {"", "",  defaultTime,
//            "", "", defaultPrice, Boolean.FALSE};

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public String getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return String.class;
        // return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        return true; //r != 0;
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value.toString();
        fireTableCellUpdated(row, col);
        changedRows.add(row);
    }


}
