package csvtodbtool;

import db.CsvFile;
import tools.HelperTool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CsvToDbImporter extends JFrame{

    private JButton bttnSelectFile;
    private JPanel jPanelTxtArea;
    private JButton bttnImportFile;
    private JTextArea txtAreaTool;
    private JPanel jPanelButtons;

    private String targetFilename;
    private String targetFilePath;

    private CsvFile targetCsv;

    private final String preferredStartLocation = "/Users/jjesguerramba2023/IdeaProjects/TestAuto/CsvToDb/";

    public static void main(String[] args) {
        new CsvToDbImporter();
    }

    public CsvToDbImporter() {

        setTitle("Store Attenuation Test Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(jPanelTxtArea);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        bttnImportFile.setVisible(false);

        selectFile();

        importSelectedFile();
    }

    /**
     * This handles the selecting of the CSV file that contains the results of the test.
     */
    private void selectFile(){
        bttnSelectFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser(preferredStartLocation);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int option = fileChooser.showOpenDialog(CsvToDbImporter.this);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    txtAreaTool.setText("Selected: " + file.getName());
                    targetFilename = file.getName();
                    targetFilePath = file.getAbsolutePath();
                    bttnImportFile.setVisible(true);

                    long unixTime =  System.currentTimeMillis();

                    try {
                        targetCsv = new CsvFile(BigDecimal.valueOf(unixTime) , targetFilename, targetFilePath);
                    } catch (NoSuchAlgorithmException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    txtAreaTool.setText("Please choose CSV File");
                    bttnImportFile.setVisible(false);
                }
            }
        });
    }

    /**
     * This handles the importing of the CSV File. It runs a check first if the CSV file has already been imported.
     * It checks this via checking the DB if the file's MD5SUM already exist.
     */
    private void importSelectedFile(){
        bttnImportFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    boolean exists = targetCsv.doesMd5Exist();
                    if (!exists){
                        // Current file has not been processed yet.
                        targetCsv.insertMe();

                        String successfulImport = "Successfully Imported: " + targetCsv.getFilename() + ".";
                        txtAreaTool.setText(successfulImport);
                    }
                    else{

                        // TODO: CHANGE import time that is unix based to more human readable
                        String failedToImport = "IMPORT CANCELLED!\n\n"
                                + "FILE: " + targetCsv.getFilename() + ".\n\nHas been imported back in "
                                + HelperTool.prettyPrint(Long.parseLong( targetCsv.getImportTime().toString())) + ".";

                        txtAreaTool.setText(failedToImport);
                    }
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}