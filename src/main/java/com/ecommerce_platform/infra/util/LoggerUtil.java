package com.ecommerce_platform.infra.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);
    private static final String LOG_FILE_PATH = "src/main/resources/ecommerce_actions.log";

    public static void logAction(String message) {
        String logEntry = LocalDateTime.now() + " | " + message;
        logger.info(logEntry);
        writeToFile(logEntry);
    }

    private static void writeToFile(String logEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            logger.error("Error writing log to file", e);
        }
    }
}