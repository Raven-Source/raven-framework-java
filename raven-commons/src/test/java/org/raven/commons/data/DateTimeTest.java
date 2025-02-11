package org.raven.commons.data;

import org.raven.commons.data.DateInterval;
import org.raven.commons.data.DateTime;
import org.raven.commons.data.TimeBucketInterval;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.raven.commons.enums.DownSampling;
import org.raven.commons.util.DateTimeUtils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Slf4j
public class DateTimeTest {

    @Test
    public void test() {

        LocalDateTime time = LocalDateTime.now();

        int year = time.getYear();
        int month = time.getMonthValue();
        int day = time.getDayOfMonth();
        DayOfWeek dayOfWeek = time.getDayOfWeek();
        Date start = DateTime.today().toDate();
        Date end = null;

        log.info(String.format("now time: %s.%s.%s dayOfWeek:%s", year, month, day, dayOfWeek.getValue()));
        Assert.assertEquals(start,
                new Date(year - 1900, month - 1, day)
        );

        DateTime dateTime = DateTime.now();
        start = dateTime.toDate();
        log.info(DateTimeUtils.format(start));
        log.info("year: " + dateTime.getYear());
        log.info("month: " + dateTime.getMonth());
        log.info("day: " + dateTime.getDay());

        log.info("dayOfWeek: " + dateTime.getDayOfWeek());
        log.info("hour: " + dateTime.getHour());
        log.info("minute: " + dateTime.getMinute());
        log.info("second: " + dateTime.getSecond());
        log.info("millisecond: " + dateTime.getMillisecond());


        Date date = DateTimeUtils.parse("2024-03-24T22:08:33.608+08:00");
        dateTime = DateTime.of(date);

        log.info("test date time" + DateTimeUtils.format(dateTime.toDate()));
        log.info("year: " + dateTime.getYear());
        log.info("month: " + dateTime.getMonth());
        log.info("day: " + dateTime.getDay());

        log.info("dayOfWeek: " + dateTime.getDayOfWeek());
        log.info("hour: " + dateTime.getHour());
        log.info("minute: " + dateTime.getMinute());
        log.info("second: " + dateTime.getSecond());
        log.info("millisecond: " + dateTime.getMillisecond());

        Assert.assertEquals(dateTime.getYear(), 2024);
        Assert.assertEquals(dateTime.getMonth(), 3);
        Assert.assertEquals(dateTime.getDay(), 24);
        Assert.assertEquals(dateTime.getHour(), 22);
        Assert.assertEquals(dateTime.getMinute(), 8);
        Assert.assertEquals(dateTime.getSecond(), 33);
        Assert.assertEquals(dateTime.getMillisecond(), 608);

        dateTime = DateTime.of(date).addWeeks(1);
        log.info("addWeeks 1: " + DateTimeUtils.format(dateTime.toDate()));

        dateTime = DateTime.of(date).addWeeks(-1);
        log.info("addWeeks -1: " + DateTimeUtils.format(dateTime.toDate()));


        start = DateTime.today(LocalTime.MAX).toDate();
        log.info("today LocalTime.MAX: ");
        log.info(DateTimeUtils.format(start));

        end = DateTime.today().addDays(1).toDate();
        log.info("today addDays 1: ");
        log.info(DateTimeUtils.format(end));

        Assert.assertTrue(start.getTime() <
                end.getTime()
        );

        log.info("lastMonth: ");
        start = DateTime.today().setDays(1).addMonths(-1).toDate();
        end = DateTime.today(LocalTime.MAX).setDays(1).addDays(-1).toDate();

        log.info(DateTimeUtils.format(start));
        log.info(DateTimeUtils.format(end));

        log.info("lastYear: ");
        start = DateTime.today().setDays(1).setMonths(1).addYears(-1).toDate();
        end = DateTime.today(LocalTime.MAX).setDays(31).setMonths(12).addYears(-1).toDate();

        log.info(DateTimeUtils.format(start));
        log.info(DateTimeUtils.format(end));

        log.info("2023/12/31: ");
        start = DateTime.today(LocalTime.MAX).setYears(2024).setDays(1).setMonths(1).addDays(-1).toDate();

        log.info(DateTimeUtils.format(start));

        dateTime = DateTime.of(DateTimeUtils.parse("2024-03-31T22:08:33.608+08:00"));
        dateTime.addMonths(-1);

        log.info(DateTimeUtils.format(dateTime.toDate()));
        Assert.assertEquals(dateTime.toDate().getTime(),
                DateTimeUtils.parse("2024-02-29T22:08:33.608+08:00").getTime());

        Assert.assertEquals(dateTime.addYears(-1).toDate().getTime(),
                DateTimeUtils.parse("2023-02-28T22:08:33.608+08:00").getTime());

    }

    @Test
    public void dateIntervalTest() {

        DateInterval interval = null;

        interval = DateInterval.last7Days();
        log.info("last7Days:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.last15Days();
        log.info("last15days:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.last30Days();
        log.info("last30days:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.lastDay();
        log.info("lastDay:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.lastWeek();
        log.info("lastWeek:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.lastMonth();
        log.info("lastMonth:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.lastYear();
        log.info("lastYear:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.thisYear();
        log.info("thisYear:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.thisWeek();
        log.info("thisWeek:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.thisMonth();
        log.info("thisMonth:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.thisDay();
        log.info("thisDay:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.daysAgo(1);
        log.info("daysAgo 1:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.daysAgo(3);
        log.info("daysAgo 3:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.weeksAgo(1);
        log.info("weeksAgo 1:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.weeksAgo(3);
        log.info("weeksAgo 3:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.monthsAgo(1);
        log.info("monthsAgo 1:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.monthsAgo(3);
        log.info("monthsAgo 3:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

        interval = DateInterval.yearsAgo(1);
        log.info("yearsAgo 1:");
        log.info(DateTimeUtils.format(interval.getStart()));
        log.info(DateTimeUtils.format(interval.getEnd()));

    }

    @Test
    public void timeBucketIntervalTest() {

        DateInterval interval = DateInterval.last15Days();
        TimeBucketInterval timeBucketInterval = new TimeBucketInterval(interval, DownSampling.Day);

        log.info("start: " + timeBucketInterval.getStart());
        log.info("end: " + timeBucketInterval.getEnd());


    }
}
