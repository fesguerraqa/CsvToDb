package tools.gui;

import db.DemoDb;
import tools.HelperTool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DbTool extends JFrame{
    private JButton bttnClearTable;
    private JButton bttnCreateTable;
    private JComboBox cboxCreateTable;
    private JComboBox cboxClearTable;
    private JPanel dbPanel;

    public static void main(String[] arg){
        new DbTool();
    }

    private void populateComboBoxes(){

        String[] tables = {DemoDb.csvToDbTable.attenuation_test.toString(), DemoDb.csvToDbTable.csv_file.toString()};

        cboxClearTable.setModel(new DefaultComboBoxModel(tables));
        cboxCreateTable.setModel(new DefaultComboBoxModel(tables));
    }

    private void createTable(){
        bttnCreateTable.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String targetTable = cboxCreateTable.getSelectedItem().toString();
                 HelperTool.ezPrint("What was Selected: " + targetTable);

                 DemoDb dd = new DemoDb();

                 try {

                     if (targetTable.equals(DemoDb.csvToDbTable.attenuation_test.toString())) {
                         dd.createAttenuationTestTable();
                     }
                     else if (targetTable.equals(DemoDb.csvToDbTable.csv_file.toString())) {
                         dd.createCsvFileTable();
                     }
                     else {
                         HelperTool.ezPrint("NOT DOING ANYTHING");
                     }
                 }
                 catch (SQLException ex) {
                     throw new RuntimeException(ex);
                 }
             }
         }
        );

    }

    private void clearTable(){
        bttnClearTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String targetTable = cboxClearTable.getSelectedItem().toString();
                HelperTool.ezPrint("What was Selected: " + targetTable);

                DemoDb dd = new DemoDb();

                try {

                    if (targetTable.equals(DemoDb.csvToDbTable.attenuation_test.toString())) {
                            dd.clearAttenuationTestTable();
                    }
                    else if (targetTable.equals(DemoDb.csvToDbTable.csv_file.toString())) {
                            dd.clearCsvFileTable();
                    }
                    else {
                        HelperTool.ezPrint("NOT DOING ANYTHING");
                    }
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        );
    }

    public DbTool(){

        populateComboBoxes();
        setTitle("DB Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(dbPanel);
        //createUI(frame);
        setSize(560, 200);
        setLocationRelativeTo(null);
        setVisible(true);

        clearTable();
        createTable();
    }

}
