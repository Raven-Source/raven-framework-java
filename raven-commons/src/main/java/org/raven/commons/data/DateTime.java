package org.raven.commons.data;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Setter;
import org.raven.commons.constant.DateFormatString;
import org.raven.commons.util.Args;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Setter
@EqualsAndHashCode
public final class DateTime implements Cloneable, Comparable<DateTime> {

    public static final int MONDAY = 1;

    public static final int TUESDAY = 2;

    public static final int WEDNESDAY = 3;

    public static final int THURSDAY = 4;

    public static final int FRIDAY = 5;

    public static final int SATURDAY = 6;

    public static final int SUNDAY = 7;

    public static final int JANUARY = 1;

    public static final int FEBRUARY = 2;

    public static final int MARCH = 3;

    public static final int APRIL = 4;

    public static final int MAY = 5;

    public static final int JUNE = 6;

    public static final int JULY = 7;

    public static final int AUGUST = 8;

    public static final int SEPTEMBER = 9;

    public static final int OCTOBER = 10;

    public static final int NOVEMBER = 11;

    public static final int DECEMBER = 12;

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone(DEFAULT_ZONE_ID);

    private final Calendar calendar;

    private DateTime() {
        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setLenient(false);
        calendar.setTimeZone(DEFAULT_TIME_ZONE);
    }

    private DateTime(@NonNull Date date) {
        this();
        calendar.setTime(date);
    }

    private DateTime(long millis) {
        this();
        calendar.setTimeInMillis(millis);
    }

    public static DateTime of(@NonNull Date date) {
        return new DateTime(date);
    }

    public static DateTime of(long millisecond) {
        return new DateTime(millisecond);
    }

    /**
     * @param year       the year, eg:2021
     * @param month      the month-of-year, from 1 to 12
     * @param dayOfMonth the day-of-month, from 1 to 31
     * @return
     */
    public static DateTime of(int year, int month, int dayOfMonth) {
        return today().setYears(year)
                .setMonths(month)
                .setDays(dayOfMonth);
    }

    /**
     * @param year       the year, eg:2021
     * @param month      the month-of-year, from 1 to 12
     * @param dayOfMonth the day-of-month, from 1 to 31
     * @param hour       the hour-of-day, from 0 to 23
     * @param minute     the minute-of-hour,  from 0 to 59
     * @param second     the second-of-minute,  from 0 to 59
     * @return
     */
    public static DateTime of(int year, int month, int dayOfMonth, int hour, int minute, int second) {
        return today().setYears(year)
                .setMonths(month)
                .setDays(dayOfMonth)
                .setHours(hour)
                .setMinutes(minute)
                .setSeconds(second);
    }


    /**
     * @param year        the year, eg:2021
     * @param month       the month-of-year, from 1 to 12
     * @param dayOfMonth  the day-of-month, from 1 to 31
     * @param hour        the hour-of-day, from 0 to 23
     * @param minute      the minute-of-hour,  from 0 to 59
     * @param second      the second-of-minute,  from 0 to 59
     * @param millisecond the millisecond-of-second,  from 0 to 59
     * @return
     */
    public static DateTime of(int year, int month, int dayOfMonth, int hour, int minute, int second, int millisecond) {
        return today().setYears(year)
                .setMonths(month)
                .setDays(dayOfMonth)
                .setHours(hour)
                .setMinutes(minute)
                .setSeconds(second)
                .setMilliseconds(millisecond);
    }

    public static DateTime now() {
        return of(new Date());
    }

    public static DateTime today() {
        return today(LocalTime.MIN);
    }

    public static DateTime today(LocalTime time) {
        return of(
                toDate(LocalDate.now(), time)
        );
    }

    public static DateTime daysAgo(int amount) {
        return today().addDays(-amount);
    }

    public static DateTime weeksAgo(int amount) {
        return today().addWeeks(-amount);
    }

    public static DateTime monthsAgo(int amount) {
        return today().addMonths(-amount);
    }

    public static DateTime yearsAgo(int amount) {
        return today().addYears(-amount);
    }

    public static int compareTo(@NonNull DateTime o1, @NonNull DateTime o2) {
        return o1.calendar.compareTo(o2.calendar);
    }

    public DateTime addYears(int amount) {
        add(Calendar.YEAR, amount);
        return this;
    }

    public DateTime addMonths(int amount) {
        add(Calendar.MONTH, amount);
        return this;
    }

    public DateTime addWeeks(int amount) {
        addDays(amount * 7);
        return this;
    }

    public DateTime addDays(int amount) {
        add(Calendar.DATE, amount);
        return this;
    }

    public DateTime addHours(int amount) {
        add(Calendar.HOUR_OF_DAY, amount);
        return this;
    }

    public DateTime addMinutes(int amount) {
        add(Calendar.MINUTE, amount);
        return this;
    }

    public DateTime addSeconds(int amount) {
        add(Calendar.SECOND, amount);
        return this;
    }

    public DateTime addMilliseconds(int amount) {
        add(Calendar.MILLISECOND, amount);
        return this;
    }

    private void add(int calendarField, int amount) {
        calendar.add(calendarField, amount);
    }

    /**
     * @param amount the year, eg:2021
     * @return
     */
    public DateTime setYears(int amount) {
        set(Calendar.YEAR, amount);
        return this;
    }

    /**
     * @param amount the month-of-year, from 1 to 12
     * @return
     */
    public DateTime setMonths(int amount) {
        Args.check(amount >= 1 && amount <= 12, "the month-of-year, from 1 to 12");
        set(Calendar.MONTH, --amount);
        return this;
    }

    /**
     * @param amount the day-of-month, from 1 to 31
     * @return
     */
    public DateTime setDays(int amount) {
        Args.check(amount >= 1 && amount <= 31, "the day-of-month, from 1 to 31");
        set(Calendar.DATE, amount);
        return this;
    }

    /**
     * @param amount the day-of-week, from 1 to 7
     * @return
     * @see DateTime#MONDAY
     * @see DateTime#TUESDAY
     * @see DateTime#WEDNESDAY
     * @see DateTime#THURSDAY
     * @see DateTime#FRIDAY
     * @see DateTime#SATURDAY
     * @see DateTime#SUNDAY
     */
    public DateTime setDaysOfWeek(int amount) {
        Args.check(amount >= 1 && amount <= 7, "the day-of-week, from 1 to 7");
        amount = ++amount < 8 ? amount : 1;
        set(Calendar.DAY_OF_WEEK, amount);
        return this;
    }

    /**
     * @param amount the hour-of-day, from 0 to 23
     * @return
     */
    public DateTime setHours(int amount) {
        Args.check(amount >= 0 && amount <= 23, "the hour-of-day, from 0 to 23");
        set(Calendar.HOUR_OF_DAY, amount);
        return this;
    }

    /**
     * @param amount the minute-of-hour, from 0 to 59
     * @return
     */
    public DateTime setMinutes(int amount) {
        Args.check(amount >= 0 && amount <= 59, "the minute-of-hour, from 0 to 59");
        set(Calendar.MINUTE, amount);
        return this;
    }

    /**
     * @param amount the second-of-minute, from 0 to 59
     * @return
     */
    public DateTime setSeconds(int amount) {
        Args.check(amount >= 0 && amount <= 59, "the second-of-minute, from 0 to 59");
        set(Calendar.SECOND, amount);
        return this;
    }

    /**
     * @param amount the millisecond-of-second, from 0 to 999
     * @return
     */
    public DateTime setMilliseconds(int amount) {
        Args.check(amount >= 0 && amount <= 999, "the millisecond-of-second, from 0 to 999");
        set(Calendar.MILLISECOND, amount);
        return this;
    }

    public DateTime setTime(LocalTime localTime) {
        Date date = toDate(
                LocalDate.of(getYear(), getMonth(), getDay()),
                localTime
        );

        calendar.setTime(date);
        LocalDate a = LocalDate.now(), b = LocalDate.now();
        return this;
    }

    private void set(int calendarField, int amount) {
        calendar.set(calendarField, amount);
    }

    /**
     * @return the year, from MIN_YEAR to MAX_YEAR
     */
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    public int getDay() {
        return calendar.get(Calendar.DATE);
    }

    public int getDayOfYear() {
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public int getDayOfWeek() {
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return week == 0 ? 7 : week;
    }

    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }

    public int getSecond() {
        return calendar.get(Calendar.SECOND);
    }

    public int getMillisecond() {
        return calendar.get(Calendar.MILLISECOND);
    }

//    public Calendar toCalendar() {
//        final Calendar c = Calendar.getInstance();
//        c.setLenient(false);
//        c.setFirstDayOfWeek(Calendar.SUNDAY);
//        c.setTime(date);
//        return c;
//    }

    public void setTimeZone(TimeZone value) {
        calendar.setTimeZone(value);
    }

    public Date toDate() {
        return calendar.getTime();
    }

    public String format() {
        return format(DateFormatString.ISO_OFFSET_DATE_TIME);
    }

    /**
     * @return format string
     */
    public String format(@NonNull String pattern) {

        ZoneId zoneId = calendar.getTimeZone().toZoneId();
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(pattern)
                .withZone(zoneId);

        ZonedDateTime zonedDateTime = calendar.toInstant().atZone(zoneId);

        return formatter.format(zonedDateTime);
    }

    @Override
    public String toString() {
        return calendar.getTime().toString();
    }

    @Override
    public int compareTo(@NonNull DateTime o) {
        return calendar.compareTo(o.calendar);
    }

    @Override
    protected DateTime clone() throws CloneNotSupportedException {
        return (DateTime) super.clone();
    }

    private static Date toDate(LocalDate localDate, LocalTime localTime) {

        if (localDate == null || localTime == null)
            return null;

        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return toDate(localDateTime);
    }

    private static Date toDate(LocalDateTime localDateTime) {

        if (localDateTime == null)
            return null;

        Instant instant = localDateTime.atZone(DEFAULT_ZONE_ID).toInstant();
        return Date.from(instant);
    }
}
