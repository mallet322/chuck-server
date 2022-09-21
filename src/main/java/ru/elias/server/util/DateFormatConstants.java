package ru.elias.server.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateFormatConstants {

    public static final ZoneId MOSCOW_ZONE_ID = ZoneId.of("Europe/Moscow");

    public static final String DATE_PATTERN = "dd.MM.yyyy";

    public static final String DATE_HYPHEN_PATTERN = "yyyy-MM-dd";

    public static final String DATE_PATTERN_SHORT_YEAR = "dd.MM.yy";

    public static final String TIME_PATTERN = "HH:mm";

    public static final String TIME_SEC_PATTERN = "HH:mm:ss";

    public static final String MONTH = "LLLL";

    public static final String YEAR = "yyyy";

    public static final String MONTH_YEAR = MONTH + " " + YEAR;

    public static final String DATE_TIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    public static final String DATE_TIME_SEC_PATTERN = DATE_PATTERN + " " + TIME_SEC_PATTERN;

    public static final String KAFKA_TS_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";

    public static final String KAFKA_OPERATION_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static final DateTimeFormatter DATE_HYPHEN_FORMATTER = DateTimeFormatter.ofPattern(DATE_HYPHEN_PATTERN);

    public static final DateTimeFormatter DATE_FORMATTER_SHORT_YEAR =
            DateTimeFormatter.ofPattern(DATE_PATTERN_SHORT_YEAR);

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static final DateTimeFormatter DATE_TIME_SEC_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_SEC_PATTERN);

    public static final DateTimeFormatter MONTH_FORMATTER =
            DateTimeFormatter.ofPattern(MONTH).withLocale(new Locale("ru"));

    public static final DateTimeFormatter MONTH_YEAR_FORMATTER =
            DateTimeFormatter.ofPattern(MONTH_YEAR).withLocale(new Locale("ru"));

    public static final DateTimeFormatter KAFKA_TS_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(KAFKA_TS_DATE_TIME_PATTERN);

    public static final DateTimeFormatter KAFKA_OPERATION_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(KAFKA_OPERATION_DATE_TIME_PATTERN);

    public static final ZoneId SHIFT_TIME_ZONE_ID = ZoneOffset.UTC;

    public static final ZoneId DEFAULT_TIME_ZONE_ID = ZoneId.systemDefault();

    public static final ZoneOffset SHIFT_TIME_ZONE_OFFSET = ZoneId.of("UTC").getRules().getOffset(Instant.now());

}

