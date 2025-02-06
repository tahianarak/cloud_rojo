package com.crypto.service.firebaseSync;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDateTime getDateString(String s)
    {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME);

        // Convertir en LocalDateTime (sans la partie timezone)
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        return localDateTime;
    }
}
