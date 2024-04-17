package logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomLog {

    private static final Logger logger = LogManager.getRootLogger();

    public static void info(String message) {
        logger.info(message);
    }

    public static void info(String message, String ...args) {
        logger.info(String.format(message, args));
    }
    public static void error(String message) {
        logger.error(message);
    }
}
