package lk.ac.iit.Mihin.CLI;

public class Logger {
    // Synchronized method to ensure atomic logging
    public static synchronized void log(String message) {
        System.out.println(message);
    }
}
