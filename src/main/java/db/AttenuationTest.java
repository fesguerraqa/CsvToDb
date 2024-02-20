package db;

import tools.HelperTool;

import java.sql.SQLException;

public class AttenuationTest {

    private int runTime;
    private int testBench;
    private int serialNumber;
    private float txPower;
    private float rxPower;
    private float ceilingPass;
    private float floorPass;
    private String testStatus;

    public AttenuationTest(String[] test){
        setRunTime(Integer.parseInt(test[0]));
        setTestBench(Integer.parseInt(test[1]));
        setSerialNumber(Integer.parseInt(test[2]));
        setTxPower(Float.parseFloat(test[3]));
        setRxPower(Float.parseFloat(test[4]));
        setCeilingPass(Float.parseFloat(test[5]));
        setFloorPass(Float.parseFloat(test[6]));
        setTestStatus(test[7]);
    }

    private void printMe(){
        HelperTool.ezPrint("Attenuation Test: "
                + "RunTime: " + this.runTime
                + ", TestBench: " + this.testBench
                + ", SerialNumber: " + this.serialNumber
                + ", TxPower: " + this.txPower
                + ", RxPower: " + this.rxPower
                + ", CeilingPass: " + this.ceilingPass
                + ", FloorPass: " + this.floorPass
                + ", TestStatus: " + this.testStatus
                );
    }

    private void setRunTime(int runTime){
        this.runTime = runTime;
    }

    public int getRunTime(){
        return this.runTime;
    }

    private void setTestBench(int testBench){
        this.testBench =  testBench;
    }

    public int getTestBench(){
        return this.testBench;
    }

    private void setSerialNumber(int serialNum){
        this.serialNumber =  serialNum;
    }

    public int getSerialNumber(){
        return this.serialNumber;
    }

    private void setTxPower(float txPower){
        this.txPower =  txPower;
    }

    public float getTxPower(){
        return this.txPower;
    }

    private void setRxPower(float rxPower){
        this.rxPower =  rxPower;
    }

    public float getRxPower(){
        return this.rxPower;
    }

    private void setCeilingPass(float ceiling){
        this.ceilingPass =  ceiling;
    }

    public float getCeilingPass(){
        return this.ceilingPass;
    }

    private void setFloorPass(float floor){
        this.floorPass =  floor;
    }

    public float getFloorPass(){
        return this.floorPass;
    }

    private void setTestStatus(String status){
        this.testStatus = status;
    }

    public String getTestStatus(){
        return this.testStatus;
    }

    public void insertMe() throws SQLException {

        DemoDb db = new DemoDb();
        db.insertAttenuationTest(this);
    }


    public enum attenTestParam{
        run_time
        , test_bench_id
        , serial_number
        , tx_power
        , rx_power
        , ceiling_pass
        , floor_pass
        , test_status
    }
}
