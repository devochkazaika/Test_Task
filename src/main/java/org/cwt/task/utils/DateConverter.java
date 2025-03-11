package org.cwt.task.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter {
    public static LocalDateTime parseDate(String dateStr) {
        return (dateStr != null && !dateStr.isEmpty()) ?
                LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
    }
}
