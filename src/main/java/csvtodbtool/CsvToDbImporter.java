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
    private JPanel jPanel;
    private JButton bttnImportFile;
    private JTextArea txtAreaTool;
    private JPanel jPanelButtons;

    private String targetFilename;
    private String targetFilePath;

    private CsvFile targetCsv;

    private final String preferredStartLocation = "/Users/jjesguerramba2023/IdeaProjects/TestAuto/CsvToDb/";

    public static void main(String[] args) {
        new CsvToDbImporter();
        //createWindow();
    }

    public CsvToDbImporter(){
        setTitle("Store Attenuation Test Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(jPanel);
        //createUI(frame);
        setSize(560, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        bttnImportFile.setVisible(false);

        selectFile();

        importSelectedFile();

    }
    
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
                    HelperTool.ezPrint("path: " + file.getAbsolutePath());
                    targetFilePath = file.getAbsolutePath();
                    bttnImportFile.setVisible(true);

                    long unixTime =  System.currentTimeMillis();

                    try {
                        targetCsv = new CsvFile(BigDecimal.valueOf(unixTime) , targetFilename, targetFilePath);
                    } catch (NoSuchAlgorithmException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }else{
                    txtAreaTool.setText("Open command canceled");
                    bttnImportFile.setVisible(false);
                }
            }
        });
    }

    private void importSelectedFile(){
        bttnImportFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelperTool.ezPrint("To Try Only.");


                try {

                    boolean exists = targetCsv.doesMd5Exist();
                    HelperTool.ezPrint("Does " + targetCsv.getMd5sum() + " Exist in DB: " + exists);
                    if (!exists){
                      targetCsv.insertMe();
                    }
                    else{
                        String failedToImport = "IMPORT CANCELLED\n\n"
                                + "FILE: " + targetCsv.getFilename() + " has been imported back in "
                                + targetCsv.getImportTime();
                        txtAreaTool.setText(failedToImport);
                    }


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

//                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//                int option = fileChooser.showOpenDialog(CsvToDbImporter.this);
//                if(option == JFileChooser.APPROVE_OPTION){
//                    File file = fileChooser.getSelectedFile();
//                    textArea1.setText("Selected: " + file.getName());
//                    targetFilename = file.getName();
//                    HelperTool.ezPrint("path: " + file.getAbsolutePath());
//                    targetFilePath = file.getAbsolutePath();
//                    importFileButton.setVisible(true);
//                }else{
//                    textArea1.setText("Open command canceled");
//                    importFileButton.setVisible(false);
//                }
            }
        });
    }

    private static void createWindow() {
        JFrame frame = new JFrame("Swing Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //createUI(frame);
        frame.setSize(560, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

//    private void createUI(final JFrame frame){
//        jPanel = new JPanel();
//        LayoutManager layout = new FlowLayout();
//        jPanel.setLayout(layout);
//
//        button1 = new JButton("Click Me!");
//        final JLabel label = new JLabel();
//
//        button1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//                int option = fileChooser.showOpenDialog(frame);
//                if(option == JFileChooser.APPROVE_OPTION){
//                    File file = fileChooser.getSelectedFile();
//                    label.setText("Selected: " + file.getName());
//                    HelperTool.ezPrint("path: " + file.getAbsolutePath());
//                }else{
//                    label.setText("Open command canceled");
//                }
//            }
//        });
//
//        jPanel.add(button1);
//        jPanel.add(label);
//        frame.getContentPane().add(jPanel, BorderLayout.CENTER);
//    }
}
