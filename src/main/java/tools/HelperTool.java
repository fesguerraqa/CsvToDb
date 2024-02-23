package tools;

import java.util.Date;

public class HelperTool {

    public static void ezPrint(String s){
        System.out.println(s);
    }

    /**
     * Helper Tool process unix time into String Date format
     * @param testInUnixTime
     * @return
     */
    public static String prettyPrint(long testInUnixTime){

        Date date = new java.util.Date(testInUnixTime);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

        return sdf.format(date);
    }
}
