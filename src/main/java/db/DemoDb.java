package db;

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

    private void createAttenuationTestTable() throws SQLException {
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

    enum csvToDbTable{
        attenuation_test
        , csv_file
    }
}
