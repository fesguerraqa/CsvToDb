package db;

import tools.HelperTool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CsvFile {

    private BigDecimal importTime;
    private String filename;
    private String filepath;
    private String md5sum;

    /**
     * Will use this value as a key to determine if a CsvFile has already been processed.
     */
    public static final BigDecimal secretKey = new BigDecimal(9999);

    public CsvFile(BigDecimal importTime, String filename, String filepath) throws NoSuchAlgorithmException, IOException {
        this.importTime = importTime;
        this.filename = filename;
        this.filepath = filepath;
        this.md5sum = generateMd5sum();
    }

    public boolean doesMd5Exist() throws SQLException {

        DemoDb dd = new DemoDb();

        // Instead of asking for a boolean, we ask for the existing import time if the CSV File has already
        // been processed.
        BigDecimal returnedImportTime = dd.doesMd5sumExist(this.md5sum);

        if (returnedImportTime.equals(secretKey)) {
            return false;
        }

        // Assign the retrieved import time from the DB to this instance.
        this.importTime = returnedImportTime;
        return true;
    }

    /**
     * Gets the MD5SUM of the CSV File to be stored in the DB.
     * @return File's MD5SUM
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private String generateMd5sum() throws NoSuchAlgorithmException, IOException {
        Path filePath = Path.of(this.filepath);

        byte[] data = Files.readAllBytes(Paths.get(filePath.toUri()));
        byte[] hash = MessageDigest.getInstance("MD5").digest(data);
        this.md5sum = new BigInteger(1, hash).toString(16);

        return this.md5sum;
    }

    public BigDecimal getImportTime(){
        return this.importTime;
    }

    public String getFilename(){
        return this.filename;
    }

    public String getFilepath(){
        return this.filepath;
    }

    public void insertMe() throws SQLException {

        DemoDb db = new DemoDb();
        readContentsOfCsvFile();
        db.insertCsvFileData(this);
    }

    private void readContentsOfCsvFile() {
        String csvLine = "";
        String token = ",";
        String headerCheck = AttenuationTest.attenTestParam.run_time.toString();

        AttenuationTest at;
        try {

            BufferedReader br = new BufferedReader(new FileReader(getFilepath()));

            while ((csvLine = br.readLine()) != null) {
                String[] attenuationTestLine = csvLine.split(token);

                if (attenuationTestLine[0].equals(headerCheck)) {
                    continue; // ignore the line that has the header row in the CSV
                } else {

                    at = new AttenuationTest(attenuationTestLine);
                    at.insertMe();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMd5sum() {
        return this.md5sum;
    }

    /**
     * Helper printing tool for debugging.
     */
    private void printMe(){
        HelperTool.ezPrint("CSV File: "
        + "importTime=" + this.importTime
        + ", Filename=" + this.filename
        + ", Filepath=" + this.filepath
        + ", MD5SUM=" + this.md5sum
        );
    }

    enum csvFileParam{
        import_time
        , filename
        , filepath
        , md5sum
    }
}
