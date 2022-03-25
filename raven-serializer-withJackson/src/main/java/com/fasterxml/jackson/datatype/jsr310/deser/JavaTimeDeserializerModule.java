package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import org.raven.serializer.withJackson.SerializerSetting;

import java.time.*;
import java.time.format.DateTimeFormatter;

import static org.raven.commons.constant.DateFormatStringConstant.*;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2021.08.22 19:52
 */
public abstract class JavaTimeDeserializerModule extends SimpleModule {

    public JavaTimeDeserializerModule(SerializerSetting setting) {
        super(PackageVersion.VERSION);

//        this.addDeserializer(Instant.class, InstantDeserializer.INSTANT.withDateFormat(dateTimeFormatter(ISO_INSTANT)));
        this.addDeserializer(OffsetDateTime.class, InstantDeserializer.OFFSET_DATE_TIME.withDateFormat(dateTimeFormatter(ISO_OFFSET_DATE_TIME)));
        this.addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME.withDateFormat(dateTimeFormatter(ISO_OFFSET_DATE_TIME)));
        this.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE.withDateFormat(dateTimeFormatter(ISO_LOCAL_DATE_TIME)));
        this.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE.withDateFormat(dateTimeFormatter(ISO_LOCAL_DATE)));
        this.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE.withDateFormat(dateTimeFormatter(ISO_LOCAL_TIME)));
        this.addDeserializer(OffsetTime.class, OffsetTimeDeserializer.INSTANCE.withDateFormat(dateTimeFormatter(ISO_OFFSET_TIME)));
//        this.addKeyDeserializer(LocalDateTime.class, LocalDateTimeKeyDeserializer.INSTANCE);
//        this.addKeyDeserializer(LocalDate.class, LocalDateKeyDeserializer.INSTANCE);
//        this.addKeyDeserializer(LocalTime.class, LocalTimeKeyDeserializer.INSTANCE);
//        this.addKeyDeserializer(OffsetDateTime.class, OffsetDateTimeKeyDeserializer.INSTANCE);
//        this.addKeyDeserializer(OffsetTime.class, OffsetTimeKeyDeserializer.INSTANCE);
//        this.addKeyDeserializer(ZonedDateTime.class, ZonedDateTimeKeyDeserializer.INSTANCE);
//        this.addKeyDeserializer(ZoneOffset.class, ZoneOffsetKeyDeserializer.INSTANCE);

    }

    protected DateTimeFormatter dateTimeFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }
}
