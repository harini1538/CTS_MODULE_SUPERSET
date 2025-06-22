public class Logger {
    // Step 1: Private static instance of the same class
    private static Logger instance;

    // Step 2: Private constructor to prevent instantiation
    private Logger() {
        System.out.println("Logger initialized.");
    }

    // Step 3: Public static method to provide global access point
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();  // Lazy initialization
        }
        return instance;
    }

    // Sample logging method
    public void log(String message) {
        System.out.println("[LOG]: " + message);
    }
}
