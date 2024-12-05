package lk.ac.iit.Mihin.CLI;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    // Synchronized method to ensure atomic logging
    public static synchronized void log(String message) {
        System.out.println(getTimestamp() + " " + message);
    }

    private static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        return "[" + sdf.format(new Date()) + "]";
    }
}
