package db;

import tools.HelperTool;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CsvFile {

    private BigDecimal importTime;
    private String filename;
    private String filepath;
    private String md5sum;

    public static final BigDecimal secretKey = new BigDecimal(9999);

    public CsvFile(BigDecimal importTime, String filename, String filepath) throws NoSuchAlgorithmException, IOException {
        this.importTime = importTime;
        this.filename = filename;
        this.filepath = filepath;
        this.md5sum = generateMd5sum();
    }

    public boolean doesMd5Exist() throws SQLException {

        DemoDb dd = new DemoDb();

        BigDecimal returnedImportTime = dd.doesMd5sumExist(this.md5sum);
        HelperTool.ezPrint("Search Result:" + returnedImportTime);

        if (returnedImportTime.equals(secretKey)) {
            return false;
        }
        this.importTime = returnedImportTime;
        return true;

    }

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
        db.insertCsvFileData(this);
    }

    public java.lang.String getMd5sum() {
        return this.md5sum;
    }

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
