package com.example.My_Database.GUIForms;


import com.example.My_Database.Domain.Entity.Database;
import com.example.My_Database.Domain.Entity.DatabaseManager;
import com.example.My_Database.Domain.Entity.Row;
import com.example.My_Database.Domain.Entity.Table;
import com.example.My_Database.Domain.Entity.types.Attribute;
import com.example.My_Database.Domain.Entity.types.Types;
import com.example.My_Database.Domain.Entity.types.Value;
import com.example.My_Database.Services.TableHandler;
import com.example.My_Database.utils.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class MainViewWindow {
    public JFrame frame;
    private JPanel mainPanel;
    private JPanel createDBPanel;
    private JButton loadDatabaseButton;
    private JButton createDatabaseButton;
    private JButton createTableButton;
    private JButton editDatabaseButton;
    private JButton backToMainFromCreateDB;
    private JPanel editDBPanel;
    private JButton backToMainFromEditDBButton;
    private JButton showDatabaseButton;
    private JPanel showDBPanel;
    private JButton backToMainFromShowDBButton;
    private JPanel createTablePanel;
    private JButton createButton;
    private JButton addAttributeButton;
    private JScrollPane attributesPane;
    private JList tablesList;
    private JList attributesList;
    private JScrollPane tablesScrollPane;
    private JScrollPane attributesScrollPane;
    private JLabel existedTablesLabel;
    private JLabel existedAttributesName;
    private JButton saveToDiskDatabaseButton;
    private JButton add;
    private JButton editTableButton;
    private JButton deleteTableButton;
    private JButton addTableButton;
    private JList<String> tablesListInEdit;
    private JPanel editTablePanel;
    private JTable tableView;
    private JButton addRowButton;
    private JButton deleteAttributeButton;
    private JButton deleteRowButton;
    private JButton addAttributeButtonInEditTable;
    private JButton saveEditedTableButton;
    private JButton projectionButton;
    private JList<String> tablesToShow;
    private JButton showTable;
    private JPanel showTablePanel;
    private JTable tableToShowView;
    private JButton backToMainFromShowTableButton;
    private JButton backShowDBFromShowTable;
    private JButton BackToOriginal;
    private DatabaseManager dbManager;
    private Database activeDB;
    private Table activeTable;
    private TableHandler tableHandler;
    private final String[] typesList = new String[]{
          "INTEGER",
          "REAL",
          "STRING",
          "CHAR",
          "TIME",
          "TIME_LNVL",
    };

    public MainViewWindow() {
        initialize();
        buttonsProcessing();
    }

    private void initialize() {
        dbManager = new DatabaseManager();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new CardLayout(0, 0));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height *= 0.5;
        screenSize.width *= 0.5;
        frame.setSize(screenSize);
        addPanelsToTheFrame();
        changePanels();
    }

    private void addPanelsToTheFrame() {
        frame.getContentPane().add(mainPanel, "name_1");
        frame.getContentPane().add(createDBPanel, "name_2");
        frame.getContentPane().add(editDBPanel, "name_3");
        frame.getContentPane().add(showDBPanel, "name_4");
        frame.getContentPane().add(createTablePanel, "name_5");
        frame.getContentPane().add(editTablePanel, "name_6");
        frame.getContentPane().add(showTablePanel, "name_7");
    }

    private void buttonsProcessing() {
        createDatabaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = JOptionPane.showInputDialog("Input name for new database");
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Name can not be empty");
                } else {
                    Database newDb = new Database(name);
                    Result result = dbManager.add(newDb);
                    if (result.isSuccessful()) {
                        JOptionPane.showMessageDialog(frame, "Database `" + name + "` is created");
                        changeActivePanel(createDBPanel, mainPanel);
                        activeDB = newDb;
                        updateTablesList();
                    } else {
                        JOptionPane.showMessageDialog(frame, result.getMsg());
                    }
                }
            }
        });

        editDatabaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Collection<Database> existedDatabases = dbManager.list();
                if (existedDatabases.size() == 0) {
                    JOptionPane.showMessageDialog(frame, "No database exists. Please create or load database first");
                }
                String[] choices = new String[existedDatabases.size() + 1];
                int ind = 0;
                for (Database db : existedDatabases) {
                    choices[ind++] = db.getName();
                }
                String name = (String) JOptionPane.showInputDialog(null, "",
                      "Select a database to edit", JOptionPane.QUESTION_MESSAGE, null, // Use
                      // default
                      // icon
                      choices, // Array of choices
                      choices[1]); // Initial choice
                activeDB = dbManager.get(name);
                updateTablesListInEditDB();
                if (activeDB != null) {
                    JOptionPane.showMessageDialog(frame, "Database `" + name + "` is loaded");
                    changeActivePanel(editDBPanel, mainPanel);
                } else {
                    JOptionPane.showMessageDialog(frame, "Canceled");
                }
            }
        });


        showDatabaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Collection<Database> existedDatabases = dbManager.list();
                if (existedDatabases.size() == 0) {
                    JOptionPane.showMessageDialog(frame, "No database exists. Please create or load database first");
                }
                String[] choices = new String[existedDatabases.size() + 1];
                int ind = 0;
                for (Database db : existedDatabases) {
                    choices[ind++] = db.getName();
                }
                String name = (String) JOptionPane.showInputDialog(null, "",
                      "Select a database", JOptionPane.QUESTION_MESSAGE, null, // Use
                      // default
                      // icon
                      choices, // Array of choices
                      choices[1]); // Initial choice
                activeDB = dbManager.get(name);
                updateTablesListInShowDB();
                if (activeDB != null) {
                    // JOptionPane.showMessageDialog(frame, "Database `" + name + "` is loaded");
                    changeActivePanel(showDBPanel, mainPanel);
                } else {
                    JOptionPane.showMessageDialog(frame, "Canceled");
                }
            }
        });

        showTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String selectedTable = tablesToShow.getSelectedValue();
                if (selectedTable == null) {
                    JOptionPane.showMessageDialog(frame, "Firstly select table to show");
                } else {
                    activeTable = activeDB.get(selectedTable);
                    if (activeTable != null) {
                        changeActivePanel(showTablePanel, showDBPanel);
                        tableHandler = new TableHandler(activeTable);
                        setTablesToShowModel();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to show selected table");
                    }
                }
            }
        });

        addAttributeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = JOptionPane.showInputDialog("Input name for the attribute");
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Name can not be empty");
                } else {
                    String type = (String) JOptionPane.showInputDialog(null, "",
                          "Select a type", JOptionPane.QUESTION_MESSAGE, null, // Use
                          // default
                          // icon
                          typesList, // Array of choices
                          typesList[1]);
                    Attribute attr = Attribute.getAttribute(name, Types.valueOf(type));
                    Result result = activeTable.addAttr(attr);
                    if (result.isSuccessful()) {
                        JOptionPane.showMessageDialog(frame, "Attribute `" + name + "` added");
                        updateListAttributes();
                    } else {
                        JOptionPane.showMessageDialog(frame, result.getMsg());
                    }
                }
            }
        });

        createTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = JOptionPane.showInputDialog("Input name for new table");
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Name can not be empty");
                } else {
                    Table newTable = new Table(name);
                    Result result = activeDB.addTable(newTable);
                    if (result.isSuccessful()) {
                        JOptionPane.showMessageDialog(frame, "Table `" + name + "` created");
                        changeActivePanel(createTablePanel, createDBPanel);
                        activeTable = newTable;
                        updateTablesList();
                    } else {
                        JOptionPane.showMessageDialog(frame, result.getMsg());
                    }
                }
            }
        });


        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                changeActivePanel(createDBPanel, createTablePanel);
                activeTable = null;
                updateListAttributes();
            }
        });


        addTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = JOptionPane.showInputDialog("Input name for new table");
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Name can not be empty");
                } else {
                    Table newTable = new Table(name);
                    Result result = activeDB.addTable(newTable);
                    if (result.isSuccessful()) {
                        JOptionPane.showMessageDialog(frame, "Table `" + name + "` created");
                        updateTablesListInEditDB();
                    } else {
                        JOptionPane.showMessageDialog(frame, result.getMsg());
                    }
                }
            }
        });

        deleteTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String selectedTable = tablesListInEdit.getSelectedValue();
                if (selectedTable == null) {
                    JOptionPane.showMessageDialog(frame, "Firstly select table to delete");
                } else {
                    Result result = activeDB.deleteTable(selectedTable);
                    if (result.isSuccessful()) {
                        updateTablesListInEditDB();
                    } else {
                        JOptionPane.showMessageDialog(frame, result.getMsg());
                    }
                }
            }
        });

        editTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String selectedTable = tablesListInEdit.getSelectedValue();
                if (selectedTable == null) {
                    JOptionPane.showMessageDialog(frame, "Firstly select table to edit");
                } else {
                    activeTable = activeDB.get(selectedTable);
                    if (activeTable != null) {
                        changeActivePanel(editTablePanel, editDBPanel);
                        tableHandler = new TableHandler(activeTable);
                        setTableModel();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to edit selected table");
                    }
                }
            }
        });

        addRowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tableHandler.insertRow();
                HashMap<String, Value> values = new HashMap<>();

                for (Attribute attr : activeTable.getColumns().listAttributes()) {
                    String value = JOptionPane.showInputDialog("Input correct value for column :" + attr.name + ": " + attr.getType());
                    boolean result = attr.validate(value);
                    if (!result) {
                        JOptionPane.showMessageDialog(frame, "Not valid value, will set default value ");
                        value = attr.getDefault().toString();
                    }
                    values.put(attr.getName(), attr.getValue(value));
                }
                Result res = activeTable.addRow(new Row(values));
                if (!res.isSuccessful()) {
                    JOptionPane.showMessageDialog(frame, "Failed to add row to this table");
                }
                tableHandler = new TableHandler(activeTable);
                setTableModel();

            }
        });

        deleteRowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = tableView.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Firstly select any row to delete");
                    return;
                }
                Result res = activeTable.deleteRow(selectedRow);
                if (!res.isSuccessful()) {
                    JOptionPane.showMessageDialog(frame, "Row isn't deleted");
                } else {
                    tableHandler = new TableHandler(activeTable);
                    setTableModel();
                    //tableHandler.deleteRow(selectedRow);
                }
            }
        });


        deleteAttributeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String[] attributes = new String[activeTable.listColumns().size()];
                int i = 0;
                for (Attribute attr : activeTable.listColumns()) {
                    attributes[i++] = attr.getName();
                }
                String attr = (String) JOptionPane.showInputDialog(null, "",
                      "Select attribute to delete", JOptionPane.QUESTION_MESSAGE, null, // Use
                      // default
                      // icon
                      attributes, // Array of choices
                      typesList[1]);
                Result res = activeTable.deleteAttr(attr);
                if (!res.isSuccessful()) {
                    JOptionPane.showMessageDialog(frame, "Cannot delete this attr");
                } else {
                    tableHandler = new TableHandler(activeTable);
                    setTableModel();

                }
            }
        });

        addAttributeButtonInEditTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = JOptionPane.showInputDialog("Input name for the attribute");
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Name can not be empty");
                } else {
                    String type = (String) JOptionPane.showInputDialog(null, "",
                          "Select a type", JOptionPane.QUESTION_MESSAGE, null, // Use
                          // default
                          // icon
                          typesList, // Array of choices
                          typesList[1]);
                    Attribute attr = Attribute.getAttribute(name, Types.valueOf(type));
                    Result result = activeTable.addAttr(attr);
                    if (result.isSuccessful()) {
                        tableHandler = new TableHandler(activeTable);
                        setTableModel();
                    } else {
                        JOptionPane.showMessageDialog(frame, result.getMsg());
                    }
                }
            }
        });

        saveEditedTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Boolean res = tableHandler.update(activeTable);
                if (!res) {
                    JOptionPane.showMessageDialog(frame, "Fail update");
                    tableHandler = new TableHandler(activeTable);
                    setTableModel();
                } else {
                    changeActivePanel(editDBPanel, editTablePanel);
                }
            }
        });


        saveEditedTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                boolean res = tableHandler.update(activeTable);
                if (!res) {
                    JOptionPane.showMessageDialog(frame, "Error in saving table");
                    tableHandler = new TableHandler(activeTable);
                    setTableModel();
                } else {
                    changeActivePanel(editDBPanel, editTablePanel);
                }
            }
        });

        projectionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!tableHandler.table.changedRows.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Save changes before deleting duplicate rows");
                    return;
                }
                JList<String> jlist = new JList<>(activeTable.getColumns().listColumnNames());
                JOptionPane.showMessageDialog(null, jlist, "Choose..", JOptionPane.PLAIN_MESSAGE);
                tableHandler = new TableHandler(activeTable.projection((ArrayList<String>) jlist.getSelectedValuesList()));
                setTableModel();
            }
        });


        loadDatabaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String filename = JOptionPane.showInputDialog("Name of the file to load database from");
                if (filename.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Filename can not be empty");
                } else {
                    Result res = dbManager.add(Objects.requireNonNull(Database.ReadFromFile(filename)));

                    // InteractionResult res = dbManager.SaveToFile(filename, Serializer.Serialize(dbManager.get(dbToSave)));
                    if (!res.isSuccessful()) {
                        JOptionPane.showMessageDialog(frame, res.isSuccessful());
                    }
                }
            }
        });

        saveToDiskDatabaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String[] choices = new String[dbManager.list().size() + 1];
                int ind = 0;
                for (Database db : dbManager.list()) {
                    choices[ind++] = db.getName();
                }
                String dbToSave = (String) JOptionPane.showInputDialog(null, "",
                      "Select a database to save", JOptionPane.QUESTION_MESSAGE, null, // Use
                      // default
                      // icon
                      choices, // Array of choices
                      choices[1]); // Initial choice
                String filename = JOptionPane.showInputDialog("Name of the file to save database to");
                boolean res = Database.SaveToFile(filename, dbManager.get(dbToSave));
                if (!res) {
                    JOptionPane.showMessageDialog(frame, "Can not save db");
                }
            }
        });


    }

    private void setTableModel() {
        tableView.setModel(tableHandler.table);
        tableHandler.initColumnSizes(tableView);
        tableView.setFillsViewportHeight(true);
        mainPanel.setOpaque(true);
        tableView.setVisible(true);
        tableView.setBorder(BorderFactory.createEmptyBorder());
        //  tableHandler.setUpDaysColumn(tableView.getColumnModel().getColumn(0));
    }

    private void setTablesToShowModel() {
        tableToShowView.setModel(tableHandler.table);
        tableHandler.initColumnSizes(tableToShowView);
        tableToShowView.setFillsViewportHeight(true);
        mainPanel.setOpaque(true);
        tableToShowView.setVisible(true);
        tableToShowView.setBorder(BorderFactory.createEmptyBorder());
        //  tableHandler.setUpDaysColumn(tableView.getColumnModel().getColumn(0));
    }


    private void updateListAttributes() {
        DefaultListModel<String> model = new DefaultListModel<>();
        attributesList.setModel(model);
        if (activeTable == null) {
            return;
        }
        Collection<Attribute> attributesArrayList = activeTable.listColumns();
        Attribute[] attributes = attributesArrayList.toArray(new Attribute[0]);
        for (int i = 0; i < attributes.length; i++) {
            model.add(i, attributes[i].getName() + " : " + attributes[i].getType().name());
        }
    }

    private void updateTablesList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        Set<String> tablesNames = activeDB.getTables().keySet();
        String[] tables = tablesNames.toArray(new String[0]);
        for (int i = 0; i < tables.length; i++) {
            model.add(i, tables[i]);
        }
        tablesList.setModel(model);
    }

    private void updateTablesListInEditDB() {
        DefaultListModel<String> model = new DefaultListModel<>();
        Set<String> tablesNames = activeDB.getTables().keySet();
        String[] tables = tablesNames.toArray(new String[0]);
        for (int i = 0; i < tables.length; i++) {
            model.add(i, tables[i]);
        }
        tablesListInEdit.setModel(model);
    }

    private void updateTablesListInShowDB() {
        DefaultListModel<String> model = new DefaultListModel<>();
        Set<String> tablesNames = activeDB.getTables().keySet();
        String[] tables = tablesNames.toArray(new String[0]);
        for (int i = 0; i < tables.length; i++) {
            model.add(i, tables[i]);
        }
        tablesToShow.setModel(model);
    }

    private void changeActivePanel(JPanel newPanel, JPanel previousPanel) {
        newPanel.setVisible(true);
        previousPanel.setVisible(false);
    }

    private void changePanels() {
        // createDatabaseButton.addActionListener(e -> changeActivePanel(createDBPanel, mainPanel));
        //editDatabaseButton.addActionListener(e -> changeActivePanel(editDBPanel, mainPanel));
        backToMainFromEditDBButton.addActionListener(e -> changeActivePanel(mainPanel, editDBPanel));
        backToMainFromCreateDB.addActionListener(e -> changeActivePanel(mainPanel, createDBPanel));
        backToMainFromShowDBButton.addActionListener(e -> changeActivePanel(mainPanel, showDBPanel));
        backToMainFromShowTableButton.addActionListener(e -> changeActivePanel(mainPanel, showTablePanel));
        backShowDBFromShowTable.addActionListener(e -> changeActivePanel(showDBPanel, showTablePanel));
        BackToOriginal.addActionListener(e -> {
            tableHandler = new TableHandler(activeTable);
            setTableModel();
        });
        //  saveEditedTableButton.addActionListener(e -> changeActivePanel(editDBPanel, editTablePanel));
        // PanelsProcessing(createDatabaseButton, createDBPanel, mainPanel, editDBPanel, mainPanel, backToMainFromEditDBButton);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
