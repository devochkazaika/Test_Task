package org.cwt.task.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DateConverterTest {

    @Test
    public void testParseDateValid() {
        String validDateStr = "2025-03-12T14:30:00";

        LocalDateTime result = DateConverter.parseDate(validDateStr);

        assertNotNull(result);
        assertEquals(2025, result.getYear());
        assertEquals(3, result.getMonthValue());
        assertEquals(12, result.getDayOfMonth());
        assertEquals(14, result.getHour());
        assertEquals(30, result.getMinute());
    }

    @Test
    public void testParseDateEmptyString() {
        LocalDateTime result = DateConverter.parseDate("");

        assertNull(result);
    }

    @Test
    public void testParseDateNull() {
        LocalDateTime result = DateConverter.parseDate(null);

        assertNull(result);
    }

    @Test
    public void testParseDateInvalidFormat() {
        String invalidDateStr = "2025-03-12 14:30:00";

        assertThrows(java.time.format.DateTimeParseException.class, () -> DateConverter.parseDate(invalidDateStr));
    }
}
