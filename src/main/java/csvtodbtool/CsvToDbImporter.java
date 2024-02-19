package csvtodbtool;

import tools.HelperTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CsvToDbImporter extends JFrame{

    private JButton selectFileButton;
    private JPanel jPanel;
    private JButton importFileButton;
    private JTextArea textArea1;
    private JPanel jPanelButtons;

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
        importFileButton.setVisible(false);

        selectFile();

        sampleMethod();

    }
    
    private void selectFile(){
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int option = fileChooser.showOpenDialog(CsvToDbImporter.this);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    textArea1.setText("Selected: " + file.getName());
                    HelperTool.ezPrint("path: " + file.getAbsolutePath());
                    importFileButton.setVisible(true);
                }else{
                    textArea1.setText("Open command canceled");
                    importFileButton.setVisible(false);
                }
            }
        });
    }

    private void sampleMethod(){
        HelperTool.ezPrint("To Try Only.");
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
