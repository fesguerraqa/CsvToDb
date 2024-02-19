package csvtodbtool;

import db.AttenuationTest;
import tools.HelperTool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String csvLine = "";
        String token = ",";
        String headerCheck = AttenuationTest.attenTestParam.run_time.toString();
        try
        {
            // /Users/jjesguerramba2023/IdeaProjects/TestAuto/CsvToDb/artifacts/csv/TestSessionCaptures.csv
            BufferedReader br = new BufferedReader(new FileReader("artifacts/csv/TestSessionCaptures.csv"));
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
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}