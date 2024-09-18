package com.example.kinocms_admin.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@Slf4j
public class LogUtil {
    public static void logPageInfo(Page<?> page, String action) {
        log.info("{} - Found {} elements", action, page.getTotalElements());
        log.debug("Returning Page with {} elements", page.getTotalElements());
    }
    public static void logSizeInfo(String item, int size) {
        log.info("Found {}: {}",item,size);
    }
}
