package csvtodbtool;

import db.AttenuationTest;
import db.CsvFile;
import db.DemoDb;
import tools.HelperTool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

/**
 * NOTHING to look at here. Used this class to mostly experiment. Please look at CsvToDbImporter.java for the main form.
 */
public class Main {

    private static void timeStampGet(){

        System.out.println("DATE: " + System.currentTimeMillis());
    }

    public static void main(String[] args) {
        String csvLine = "";
        String token = ",";
        String headerCheck = AttenuationTest.attenTestParam.run_time.toString();
        try
        {

            timeStampGet();



            // /Users/jjesguerramba2023/IdeaProjects/TestAuto/CsvToDb/artifacts/csv/TestSessionCaptures.csv
            String fileName1 = "artifacts/csv/TestSessionCaptures.csv";

//            CsvFile cf = new CsvFile(9000, "filename",fileName1);
//            System.out.println("MD5SUM: " + cf.getMd5sum());

            BufferedReader br = new BufferedReader(new FileReader(fileName1));


            while ((csvLine = br.readLine()) != null)
            {
                String[] attenuationTestLine = csvLine.split(token);

                // if statement to ignore first line that has column headers.
                if(attenuationTestLine[0].equals(headerCheck)){
                    HelperTool.ezPrint("Found Header Line. Skipping!!!" + attenuationTestLine[0]);
                    continue;
                }
                else{
                    AttenuationTest at = new AttenuationTest(attenuationTestLine);
                    at.insertMe();
//                    DemoDb dd = new DemoDb();
//                    dd.insertAttenuationTest(at);
                    //dd.runBasicQuery();
//                    dd.createAttenuationTestTable();

                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
    }
}