package org.raven.commons.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.raven.commons.util.DateTimeUtils;

import java.text.MessageFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DateTimeUtilsTest {

    @Test
    public void test()
            throws Exception {

        Date now = new Date();

        log.info(DateTimeUtils.format(now, DateTimeUtils.DATE_MONTH_WEEK_FORMAT));

//        log.info(DateTimeUtils.formatDateMonth(now));
//        log.info(DateTimeUtils.parseDateMonth("202005").toString());
        log.info(DateTimeUtils.format(now));
        log.info(DateTimeUtils.format(now, TimeZone.getTimeZone("UTC")));
        log.info(DateTimeUtils.format(now, TimeZone.getTimeZone("America/Los_Angeles")));
        log.info(DateTimeUtils.format(now, "yyyy-MM-dd HH:mm:ss"));
        log.info(DateTimeUtils.format(now, "yyyy-MM-dd HH:mm:ss"), TimeZone.getTimeZone("America/Los_Angeles"));
        log.info(DateTimeUtils.format(now, "yyyy-MM-'W'ww"));
        log.info(DateTimeUtils.format(now, "YYYY-'W'ww-D"));

        log.info(DateTimeUtils.format(now, "YYYYMMW"));

        log.info(DateTimeFormatter.ISO_WEEK_DATE.toString());

    }

    private static final int THREAD_COUNT = 100;
    private static final int ITERATIONS = 1000;

    @Test
    public void testDateTimeUtilsMultiThreaded() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        List<String> results = new ArrayList<>();
        Date date = DateTimeUtils.parse("2020-01-31 12:21:11.123");
        String expectedFormattedDate = DateTimeUtils.format(date);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(() -> {
                try {
                    for (int j = 0; j < ITERATIONS; j++) {
                        String formattedDate = DateTimeUtils.format(date);
                        synchronized (results) {
                            results.add(formattedDate);
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        for (String result : results) {
            Assert.assertEquals(expectedFormattedDate, result, "2020-01-31T12:21:11.123+08:00");
        }
    }

    @Test
    public void formatTest() throws Exception {

        String[] dateStrings = {
                "2025-02-10T14:30:45.123+08:00", // 带时区
                "2025-02-10T14:30:45.123+0800",  // 带时区
                "2025-02-10T14:30:45.123Z",      // UTC 格式
                "2025-02-10T14:30:45+08:00",     // 带时区
                "2025-02-10T14:30:45.123",       // 无时区
                "2025-02-10T14:30:45",           // 无时区
                "2025-02-10 14:30:45.123",       // 无时区
                "2025-02-10 14:30:45"            // 无时区
        };

        for (String dateString : dateStrings) {
            Date date = DateTimeUtils.parse(dateString);
            Assert.assertNotNull(date);
            System.out.println("Parsed Date: " + date);
        }

        // 2020-01-31 12:21:11.123
        Date date = DateTimeUtils.parse("2020-01-31 12:21:11.123");
        Assert.assertNotNull(date);

        String str = DateTimeUtils.format(date);
        Assert.assertNotNull(str);
        log.info(str);
        Assert.assertEquals(str, "2020-01-31T12:21:11.123+08:00");

//        str = DateUtil.formatTime14(date);
//        log.info(str);
//        Assert.assertEquals(str, "2020-01-31 12:21:11");

        ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, 1, 31, 12, 21, 11, 123000000, ZoneId.of("Asia/Shanghai"));
        str = DateTimeUtils.format(zonedDateTime);
        log.info(str);
        Assert.assertEquals(str, "2020-01-31T12:21:11.123+08:00");

    }

    @Test
    public void timeZoneTest() {

        for (Map.Entry<String, String> stringStringEntry : ZoneId.SHORT_IDS.entrySet()) {

            ZoneId zoneId = ZoneId.of(stringStringEntry.getValue());
            ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);

            log.info(MessageFormat.format("{0} : {1}", zoneId.getId(), zonedDateTime.toLocalDateTime()));
        }

        ZoneId zoneId = ZoneId.of("+00:00");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        log.info(MessageFormat.format("{0} : {1}", zoneId.getId(), zonedDateTime.toLocalDateTime()));

    }

    @Test
    public void testRegex () {

        String[] matchString = {
                "2021-02-12T05:10:18",
                "2021-02-12T05:10:18.900",
                "2021-02-12T05:10:18Z",
                "2021-02-12T05:10:18+08:00",
        };

        String[] nonMatchString = {
                "2021-02-12 05:10:18", // 不匹配
                "2021-02-12 05:10:18.900", // 不匹配
                "2021-02-12 05:10:18.900+08:00", // 不匹配
        };

        Pattern pattern = DateTimeUtils.ISO_TIME_PATTERN;
        for (String str : matchString) {
            Matcher matcher = pattern.matcher(str);
            System.out.println(str + " : " + (matcher.matches() ? "匹配" : "不匹配"));
            Assert.assertTrue(matcher.matches());
        }

        for (String str : nonMatchString) {
            Matcher matcher = pattern.matcher(str);
            System.out.println(str + " : " + (matcher.matches() ? "匹配" : "不匹配"));
            Assert.assertFalse(matcher.matches());
        }

    }
}
