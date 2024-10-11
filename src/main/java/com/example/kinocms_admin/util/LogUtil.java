package com.example.kinocms_admin.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

@Slf4j
public class LogUtil {
    public static void logPageInfo(Page<?> page, String action) {
        log.info("{} - Found {} elements", action, page.getTotalElements());
        log.debug("Returning Page with {} elements", page.getTotalElements());
    }

    public static void logSizeInfo(String item, int size) {
        log.info("Found {}: {}", item, size);
    }

    public static void logSaveInfo(String item, String attribute, Object attributeValue) {
        log.info("{} with {}: {} successfully saved", item, attribute, attributeValue);
    }

    public static void logSaveInfo(String item, Long id) {
        log.info("{} with id: {} successfully saved", item, id);
    }

    public static void logDeleteInfo(String item, String attribute, Object attributeValue) {
        if (attributeValue == null) {
            log.warn("{} is not exist with value: {}", item, null);
        } else {
            log.info("{} with {}: {} deleted", item, attribute, attributeValue);
        }
    }

    public static void logDeleteAllInfo(String item, String attribute) {
        log.info("{} successfully deleted by {}", item, attribute);
    }

    public static void logGetNotification(String item, String attribute, Object attributeValue) {
        log.info("Fetching {} with {}: {}", item, attribute, attributeValue);
    }

    public static void logGetNotification(String item, String attribute1, String attribute2,
                                          Object attributeValue1, Object attributeValue2) {
        log.info("Fetching {} with {}: {} & {}: {}", item, attribute1, attributeValue1, attribute2, attributeValue2);
    }

    public static void logGetAllNotification(String item) {
        log.info("Fetching all {}", item);
    }

    public static void logGetAllNotification(String item, String attribute, Object attributeValue) {
        log.info("Fetching all {} with {}: {}", item, attribute, attributeValue);
    }

    public static void logGetAllNotification(String item, Pageable pageable) {
        log.info("Fetching all {} with pageable", item);
    }

    public static void logSaveNotification(String item, String attribute, Object attributeValue) {
        if (attributeValue == null) {
            logSavingNewItem(item);
        } else {
            log.info("Saving {} with {}: {}", item, attribute, attributeValue);
        }
    }

    public static void logSaveNotification(String item, String attribute1, String attribute2,
                                           Object attributeValue1, Object attributeValue2, Long id) {
        if (id == null) {
            logSavingNewItem(item);
        } else {
            log.info("Saving {} with {}: {} & {}: {}", item, attribute1, attributeValue1, attribute2, attributeValue2);
        }
    }

    public static void logDeleteNotification(String item, String attribute, Object attributeValue) {
        log.info("Deleting {} with {}: {}", item, attribute, attributeValue);
    }

    public static void logDeleteAllNotification(String item) {
        log.info("Deleting all {}", item);
    }

    public static void logDeleteAllNotification(String item, String attribute, Object attributeValue) {
        log.info("Deleting all {} with {}: {}", item, attribute, attributeValue);
    }

    public static void logGetInfo(String item, String attribute1, String attribute2,
                                  Object attributeValue1, Object attributeValue2,
                                  boolean exist) {
        if (exist) {
            log.info("{} with {}: {} & {}:{} was found", item, attribute1, attributeValue1.getClass(), attribute2, attributeValue2);
        } else {
            log.warn("{} wasn't found with {}: {} & {}:{}", item, attribute1, attributeValue1.getClass(), attribute2, attributeValue2);
        }
    }

    public static void logGetInfo(String item, String attribute, Object attributeValue, boolean exist) {
        if (exist) {
            log.info("{} with {}: {} was found", item, attribute, attributeValue);
        } else {
            log.warn("{} wasn't found with {}: {}", item, attribute, attributeValue);
        }
    }

    public static void logSaveAllNotifications(String item) {
        log.info("Saving all {}", item);
    }

    public static void logSaveAllInfo(String item) {
        log.info("All {} were successfully saved", item);
    }

    public static void logItemNull(String item) {
        log.warn("Received null {} object, returning empty set", item);
    }

    public static void logUpdateNotifications(String item, String attribute, Object attributeValue) {
        log.info("Updating {} with {}: {}", item, attribute, attributeValue);
    }

    public static void logUpdateInfo(String item, String attribute, Object attributeValue) {
        log.info("{} with {}: {} successfully updated", item, attribute, attributeValue);
    }

    public static void logErrorSavingFiles(IOException e) {
        log.error("Error saving files in directories", e);
    }

    private static void logSavingNewItem(String item) {
        log.info("Saving new {}", item);
    }

    public static void logClearMarksNotification(String item) {
        log.info("Clearing marks in {}", item);
    }
    public static void logClearMarksInfo(String item) {
        log.info("Marks were cleared in {}", item);
    }
}
