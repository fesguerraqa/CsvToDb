package db;

import tools.HelperTool;

import java.math.BigDecimal;
import java.sql.*;

public class DemoDb {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/csv_to_db";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PWD = "mysqlt3st!";

    public Connection dbConnection;
    public PreparedStatement statement;

    private void connectDb() throws SQLException {
        dbConnection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PWD);
    }

    private void disconnectDb() throws SQLException {
        dbConnection.close();
    }

    public void runBasicQuery() throws SQLException {
        connectDb();

        String query = "SELECT * FROM " + csvToDbTable.attenuation_test;
        statement = dbConnection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        prettyPrintRs(result);

        disconnectDb();
    }

    /**
     * Helper method to empty attenuation_test table.
     * @throws SQLException
     */
    private void emptyAttenuationTestTable() throws SQLException {
        connectDb();

        statement = dbConnection.prepareStatement("DELETE FROM " + csvToDbTable.attenuation_test);
        statement.execute();

        disconnectDb();
    }
    public void createCsvFileTable() throws SQLException {

        connectDb();

        String createTable = "CREATE TABLE " + csvToDbTable.csv_file
                + "(" + CsvFile.csvFileParam.import_time + " BIGINT NOT NULL"
                + ", " + CsvFile.csvFileParam.filename + " VARCHAR(255) NOT NULL"
                + ", " + CsvFile.csvFileParam.filepath + " VARCHAR(255) NOT NULL"
                + ", " + CsvFile.csvFileParam.md5sum + " VARCHAR(255) NOT NULL"
                + ")";

        statement = dbConnection.prepareStatement(createTable);
        statement.execute();

        disconnectDb();
    }

    /**
     * Helper method to create the attenuation_test table.
     * @throws SQLException
     */
    public void createAttenuationTestTable() throws SQLException {
        connectDb();

        String createTable = "CREATE TABLE " + csvToDbTable.attenuation_test
                + "(" + AttenuationTest.attenTestParam.run_time + " INTEGER NOT NULL"
                + ", " + AttenuationTest.attenTestParam.test_bench_id + " INTEGER NOT NULL"
                + ", " + AttenuationTest.attenTestParam.serial_number + " INTEGER NOT NULL"
                + ", " + AttenuationTest.attenTestParam.tx_power + " FLOAT NOT NULL"
                + ", " + AttenuationTest.attenTestParam.rx_power + " FLOAT NOT NULL"
                + ", " + AttenuationTest.attenTestParam.ceiling_pass + " FLOAT NOT NULL"
                + ", " + AttenuationTest.attenTestParam.floor_pass + " FLOAT NOT NULL"
                + ", " + AttenuationTest.attenTestParam.test_status + " VARCHAR(255) NOT NULL"
                + ")";

        statement = dbConnection.prepareStatement(createTable);
        statement.execute();

        disconnectDb();
    }

    public void clearAttenuationTestTable() throws SQLException {

        clearTable(csvToDbTable.attenuation_test.toString());
    }

    public void clearCsvFileTable() throws SQLException {

        clearTable(csvToDbTable.csv_file.toString());
    }

    private void clearTable(String table) throws SQLException {

        connectDb();
        String clearTable = "DELETE from " + table;

        statement = dbConnection.prepareStatement(clearTable);
        statement.execute();

        disconnectDb();
    }

    public void insertAttenuationTest(AttenuationTest at) throws SQLException {

        connectDb();

        String query = "INSERT INTO " + csvToDbTable.attenuation_test + "("
                + AttenuationTest.attenTestParam.run_time
                + ", " + AttenuationTest.attenTestParam.test_bench_id
                + ", " + AttenuationTest.attenTestParam.serial_number
                + ", " + AttenuationTest.attenTestParam.tx_power
                + ", " + AttenuationTest.attenTestParam.rx_power
                + ", " + AttenuationTest.attenTestParam.ceiling_pass
                + ", " + AttenuationTest.attenTestParam.floor_pass
                + ", " + AttenuationTest.attenTestParam.test_status
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?)";

        statement = dbConnection.prepareStatement(query);
        statement.setInt(1, at.getRunTime());
        statement.setInt(2, at.getTestBench());
        statement.setInt(3, at.getSerialNumber());
        statement.setFloat(4, at.getTxPower());
        statement.setFloat(5, at.getRxPower());
        statement.setFloat(6, at.getCeilingPass());
        statement.setFloat(7, at.getFloorPass());
        statement.setString(8, at.getTestStatus());
        statement.execute();

        disconnectDb();
    }

    private void prettyPrintRs(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        int colCount = metaData.getColumnCount();

        while (rs.next()) {
            for (int i = 1; i <= colCount; i++) {
                //if (i > 1) System.out.print(",  ");
                String colValue = rs.getString(i);
                System.out.print(metaData.getColumnName(i) + ": " + colValue + ". ");
            }
            System.out.println("");
        }
    }

    public void insertCsvFileData(CsvFile cf) throws SQLException {
        connectDb();

        String query = "INSERT INTO " + csvToDbTable.csv_file + "("
                + CsvFile.csvFileParam.import_time
                + ", " + CsvFile.csvFileParam.filename
                + ", " + CsvFile.csvFileParam.filepath
                + ", " + CsvFile.csvFileParam.md5sum
                + ")"
                + "VALUES (?,?,?,?)";

        statement = dbConnection.prepareStatement(query);
        statement.setBigDecimal(1, cf.getImportTime());
        statement.setString(2, cf.getFilename());
        statement.setString(3, cf.getFilename());
        statement.setString(4, cf.getMd5sum());
        statement.execute();

        disconnectDb();
    }

    public BigDecimal doesMd5sumExist(String md5sum) throws SQLException {
        connectDb();

        String query = "SELECT * FROM " + csvToDbTable.csv_file
                + " WHERE " + CsvFile.csvFileParam.md5sum +  "=" + "\"" + md5sum + "\"";

        HelperTool.ezPrint("SQL: " + query);
        statement = dbConnection.prepareStatement(query);
        ResultSet result = statement.executeQuery();

        boolean hasResults = result.next();

        BigDecimal tempTime = CsvFile.secretKey;

        if (hasResults) {
            tempTime = result.getBigDecimal(CsvFile.csvFileParam.import_time.toString());
        }

        disconnectDb();

        return tempTime;

    }

    public enum csvToDbTable{
        attenuation_test
        , csv_file
    }
}
