package tr.com.hvlsn.covid.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by ogyilmaz on 4/23/2021 1:37 PM
 */
public class DateConverter {
    private DateConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";

    public static LocalDate toDefaultLocalDate(String dateString) {
        return toLocalDate(dateString, DEFAULT_DATE_FORMAT);
    }

    public static LocalDate toLocalDate(String dateString, String format) {
        return dateString != null && !dateString.isEmpty() ? LocalDate.parse(dateString, DateTimeFormatter.ofPattern(format)) : null;
    }

}
