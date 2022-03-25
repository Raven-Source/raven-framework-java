package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.datatype.jsr310.deser.JavaTimeDeserializerModule;
import org.raven.serializer.withJackson.SerializerSetting;

import java.time.*;

import static org.raven.commons.constant.DateFormatStringConstant.*;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2021.08.22 20:09
 */
public class JavaTimeSerializerModule extends JavaTimeDeserializerModule {

    public JavaTimeSerializerModule(SerializerSetting setting) {
        super(setting);

//        this.addSerializer(Instant.class, InstantSerializer.INSTANCE);
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter(ISO_LOCAL_DATE_TIME)));
        this.addSerializer(LocalDate.class, new LocalDateSerializer(dateTimeFormatter(ISO_DATE)));
        this.addSerializer(LocalTime.class, new LocalTimeSerializer(dateTimeFormatter(ISO_LOCAL_TIME)));
        this.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer(
            OffsetDateTimeSerializer.INSTANCE, null, dateTimeFormatter(ISO_OFFSET_DATE_TIME)
        ));
        this.addSerializer(OffsetTime.class, new OffsetTimeSerializer(
            OffsetTimeSerializer.INSTANCE, null, dateTimeFormatter(ISO_OFFSET_TIME)
        ));
        this.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(dateTimeFormatter(ISO_ZONED_DATE_TIME)));
//        this.addKeySerializer(ZonedDateTime.class, ZonedDateTimeKeySerializer.INSTANCE);

    }

}
