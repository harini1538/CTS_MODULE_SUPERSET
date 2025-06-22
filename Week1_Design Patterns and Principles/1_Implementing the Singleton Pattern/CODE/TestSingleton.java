public class TestSingleton {
    public static void main(String[] args) {
        // Getting logger instance for the first time
        Logger logger1 = Logger.getInstance();
        logger1.log("This is the first log message.");

        // Getting logger instance again
        Logger logger2 = Logger.getInstance();
        logger2.log("This is the second log message.");

        // Verifying that both logger1 and logger2 refer to the same instance
        if (logger1 == logger2) {
            System.out.println("Both logger1 and logger2 refer to the same instance.");
        } else {
            System.out.println("Different instances exist. Singleton failed.");
        }
    }
}
