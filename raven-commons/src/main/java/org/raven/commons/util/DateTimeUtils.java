package org.raven.commons.util;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.raven.commons.constant.DateFormatString.*;

@Slf4j
public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static final Pattern TIMEZONE_PATTERN = Pattern.compile(".*([\\+\\-]\\d{2}:?\\d{2}|Z).*");
    public static final Pattern ISO_TIME_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?(Z|[+-]\\d{2}:\\d{2})?$");

    public static final DateTimeFormatter ISO_DATE_FORMAT = DateTimeFormatter.ofPattern(ISO_OFFSET_DATE_TIME);
    public static final DateTimeFormatter DATE_MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");
    public static final DateTimeFormatter DATE_MONTH_WEEK_FORMAT = DateTimeFormatter.ofPattern("yyyyMMW");
    public static final DateTimeFormatter DATE_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter DATE_HOUR_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd HH");
    public static final DateTimeFormatter DATE_TIME_FORMAT = ISO_DATE_FORMAT;

    public static final DateTimeFormatter DESERIALIZE_ZONE_DATE_FORMAT = buildDateTimeFormatter(
            ISO_OFFSET_DATE_TIME
            , ISO_OFFSET_DATE_TIME_SECOND
            , RFC822_DATE_TIME
            , RFC822_DATE_TIME_SECOND);

    public static final DateTimeFormatter DESERIALIZE_DATE_FORMAT = buildDateTimeFormatter(
            ISO_LOCAL_DATE_TIME
            , ISO_LOCAL_DATE_TIME_SECOND
            , ISO_LOCAL_DATE);

    public static final DateTimeFormatter DESERIALIZE_NON_ISO_DATE_FORMAT = buildDateTimeFormatter(
            NON_ISO_LOCAL_DATE_TIME
            , NON_ISO_LOCAL_DATE_TIME_SECOND);


    public static final ZoneId defaultZoneId = ZoneId.systemDefault();
    public static final TimeZone defaultTimeZone = TimeZone.getTimeZone(defaultZoneId);

    public static DateTimeFormatter buildDateTimeFormatter(String... patterns) {

        String pattern = Arrays.stream(patterns)
                .map(p -> "[" + p + "]")
                .collect(Collectors.joining());

        return DateTimeFormatter.ofPattern(pattern);
    }


    /**
     * @param date date
     * @return format string
     */
    public static String format(Date date) {

        if (date == null)
            return null;

        Instant instant = date.toInstant();
        return format(instant.atZone(defaultZoneId));
    }


    /**
     * @param date      date
     * @param formatter DateTimeFormatter
     * @return format string
     */
    public static String format(Date date, @NonNull DateTimeFormatter formatter) {

        if (date == null)
            return null;

        Instant instant = date.toInstant();
        return format(instant.atZone(defaultZoneId), formatter);
    }

    /**
     * @param date    date
     * @param pattern pattern
     * @return format string
     */
    public static String format(Date date, @NonNull String pattern) {
        return format(date, pattern, defaultTimeZone);
    }

    /**
     * @param date     date
     * @param timeZone timeZone
     * @return format string
     */
    public static String format(Date date, @NonNull TimeZone timeZone) {
        return format(date, ISO_OFFSET_DATE_TIME, timeZone);
    }

    /**
     * @param date     date
     * @param pattern  pattern
     * @param timeZone timeZone
     * @return format string
     */
    public static String format(Date date, @NonNull String pattern, @NonNull TimeZone timeZone) {

        if (date == null)
            return null;

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(pattern)
                .withZone(timeZone.toZoneId());
        Instant instant = date.toInstant();
        return format(instant.atZone(defaultZoneId), formatter);
    }

    /**
     * @param date zoned dateTime
     * @return format string
     */
    public static String format(ZonedDateTime date) {
        if (date == null)
            return null;
        return format(date, DATE_TIME_FORMAT);
    }

    /**
     * @param date      zoned dateTime
     * @param formatter DateTimeFormatter
     * @return format string
     */
    public static String format(ZonedDateTime date, @NonNull DateTimeFormatter formatter) {
        if (date == null)
            return null;
        return date.format(formatter);
    }

    /**
     * @param dateString date string
     * @return the date
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static Date parse(String dateString) {

        try {
            long time = Long.parseLong(dateString);
            return new Date(time);
        } catch (Exception ignored) {
        }

        boolean hasTimeZone = TIMEZONE_PATTERN.matcher(dateString).matches();

        ZonedDateTime zonedDateTime;

        if (hasTimeZone) {
            // 使用 ZonedDateTime 解析
            zonedDateTime = ZonedDateTime.parse(dateString, DESERIALIZE_ZONE_DATE_FORMAT);
        } else {

            boolean iosTime = ISO_TIME_PATTERN.matcher(dateString).matches();
            // 使用 LocalDateTime 解析（无时区，需要手动补充默认时区）
            LocalDateTime localDateTime;
            if (iosTime) {
                localDateTime = LocalDateTime.parse(dateString, DESERIALIZE_DATE_FORMAT);
            } else {
                localDateTime = LocalDateTime.parse(dateString, DESERIALIZE_NON_ISO_DATE_FORMAT);
            }
            zonedDateTime = localDateTime.atZone(defaultZoneId);
        }

        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * @param dateString         date string
     * @param dateTimeFormatters DateTimeFormatter
     * @return the date
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static Date parse(String dateString, DateTimeFormatter... dateTimeFormatters) {

        try {
            long time = Long.parseLong(dateString);
            return new Date(time);
        } catch (Exception ignored) {
        }

        boolean hasTimeZone = TIMEZONE_PATTERN.matcher(dateString).matches();
        boolean iosTime = ISO_TIME_PATTERN.matcher(dateString).matches();

        ZonedDateTime zonedDateTime;

        for (DateTimeFormatter formatter : dateTimeFormatters) {

            try {

                if (hasTimeZone) {
                    // 使用 ZonedDateTime 解析
                    zonedDateTime = ZonedDateTime.parse(dateString, formatter);
                } else {
                    // 使用 LocalDateTime 解析（无时区，需要手动补充默认时区）
                    LocalDateTime localDateTime;
                    if (iosTime) {
                        localDateTime = LocalDateTime.parse(dateString, DESERIALIZE_DATE_FORMAT);
                    } else {
                        localDateTime = LocalDateTime.parse(dateString, DESERIALIZE_NON_ISO_DATE_FORMAT);
                    }
                    zonedDateTime = localDateTime.atZone(defaultZoneId);
                }

                return Date.from(zonedDateTime.toInstant());

            } catch (DateTimeParseException ignore) {
            }

        }

        throw new DateTimeParseException(
                String.format("not a valid date string: %s", dateString)
                , dateString
                , 0);

    }

    /**
     * @param localDateTime localDateTime
     * @return the date
     */
    public static Date toDate(LocalDateTime localDateTime) {

        if (localDateTime == null)
            return null;

        Instant instant = localDateTime.atZone(defaultZoneId).toInstant();
        return Date.from(instant);
    }

    /**
     * @param localDate localDate
     * @return the date
     */
    public static Date toDate(LocalDate localDate) {

        if (localDate == null)
            return null;

        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.MIN);
        return toDate(localDateTime);

    }

    /**
     * @param localDate localDate
     * @param localTime localTime
     * @return the date
     */
    public static Date toDate(LocalDate localDate, LocalTime localTime) {

        if (localDate == null || localTime == null)
            return null;

        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return toDate(localDateTime);
    }

    public static Date today(LocalTime time) {
        return toDate(LocalDate.now(), time);
    }

    public static Date today() {
        return toDate(LocalDate.now(), LocalTime.MIN);
    }

}
