package org.raven.commons.data;

import org.raven.commons.contract.Interval;
import org.raven.commons.util.Args;

import java.time.LocalTime;
import java.util.Date;

public class DateInterval extends Interval<Date> {

    public DateInterval(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public static DateInterval of(Date start, Date end) {
        return new DateInterval(start, end);
    }

    public static DateInterval last7Days() {

        return daysAgo(7);
    }

    public static DateInterval last15Days() {

        return daysAgo(15);
    }

    public static DateInterval last30Days() {

        return daysAgo(30);
    }

    public static DateInterval lastDay() {

        return DateInterval.of(
                DateTime.today().addDays(-1).toDate(),
                DateTime.today(LocalTime.MAX).addDays(-1).toDate()
        );
    }

    public static DateInterval lastWeek() {

        DateTime today = DateTime.today().addDays(-7);
        if (today.getDayOfWeek() == DateTime.SUNDAY) {
            today.addDays(-1);
        }

        return DateInterval.of(
                today.setDaysOfWeek(DateTime.MONDAY).toDate(),
                today.setDaysOfWeek(DateTime.SATURDAY).addDays(1).setTime(LocalTime.MAX).toDate()
        );
    }

    public static DateInterval lastMonth() {

        return DateInterval.of(
                DateTime.today().setDays(1).addMonths(-1).toDate(),
                DateTime.today(LocalTime.MAX).setDays(1).addDays(-1).toDate()
        );
    }

    public static DateInterval lastYear() {

        return DateInterval.of(
                DateTime.today().setDays(1).setMonths(1).addYears(-1).toDate(),
                DateTime.today(LocalTime.MAX).setDays(31).setMonths(12).addYears(-1).toDate()
        );
    }

    public static DateInterval thisDay() {

        return DateInterval.of(
                DateTime.today().toDate(),
                DateTime.today(LocalTime.MAX).toDate()
        );
    }

    public static DateInterval thisWeek() {

        DateTime today = DateTime.today();
        if (today.getDayOfWeek() == DateTime.SUNDAY) {
            today.addDays(-1);
        }

        return DateInterval.of(
                today.setDaysOfWeek(DateTime.MONDAY).toDate(),
                today.setDaysOfWeek(DateTime.SATURDAY).addDays(1).setTime(LocalTime.MAX).toDate()
        );
    }

    public static DateInterval thisMonth() {

        return DateInterval.of(
                DateTime.today().setDays(1).toDate(),
                DateTime.today(LocalTime.MAX).setDays(1).addMonths(1).addDays(-1).toDate()
        );
    }

    public static DateInterval thisYear() {

        return DateInterval.of(
                DateTime.today().setDays(1).setMonths(1).toDate(),
                DateTime.today(LocalTime.MAX).setDays(31).setMonths(12).toDate()
        );
    }

    public static DateInterval daysAgo(int amount) {

        Args.check(amount >= 1, "the amount, from 1 to Integer.Max");

        return DateInterval.of(
                DateTime.today().addDays(-amount).toDate(),
                DateTime.today(LocalTime.MAX).addDays(-1).toDate()
        );
    }

    public static DateInterval weeksAgo(int amount) {

        Args.check(amount >= 1, "the amount, from 1 to Integer.Max");

        return DateInterval.of(
                DateTime.today().addWeeks(-amount).toDate(),
                DateTime.today(LocalTime.MAX).addDays(-1).toDate()
        );
    }

    public static DateInterval monthsAgo(int amount) {

        Args.check(amount >= 1, "the amount, from 1 to Integer.Max");

        return DateInterval.of(
                DateTime.today().addMonths(-amount).toDate(),
                DateTime.today(LocalTime.MAX).addDays(-1).toDate()
        );
    }

    public static DateInterval yearsAgo(int amount) {

        Args.check(amount >= 1, "the amount, from 1 to Integer.Max");

        return DateInterval.of(
                DateTime.today().addYears(-amount).toDate(),
                DateTime.today(LocalTime.MAX).addDays(-1).toDate()
        );
    }

    public static DateInterval daysAgoRange(int starAmount, int endAmount) {

        Args.check(starAmount >= 1 && endAmount >= 1, "the amount, from 1 to Integer.Max");

        return DateInterval.of(
                DateTime.today().addDays(-starAmount).toDate(),
                DateTime.today(LocalTime.MAX).addDays(-endAmount).toDate()
        );
    }

//    public static DateInterval weeksAgoRange(int starAmount, int endAmount) {
//
//        Args.check(starAmount >= 1 && endAmount >= 1, "the amount, from 1 to Integer.Max");
//
//        return DateInterval.of(
//                DateTime.today().addWeeks(-starAmount).toDate(),
//                DateTime.today(LocalTime.MAX).addWeeks(-endAmount).addDays(-1).toDate()
//        );
//    }
//
//    public static DateInterval monthsAgoRange(int starAmount, int endAmount) {
//
//    }
//
//    public static DateInterval yearsAgoRange(int starAmount, int endAmount) {
//
//    }

}
