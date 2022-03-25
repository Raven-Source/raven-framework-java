package org.raven.serializer.withJackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.10.22 18:06
 */
public class MultiFormatDateModule extends SimpleModule {

    public MultiFormatDateModule(SerializerSetting setting) {
        super("MultiFormatDateModule", PackageVersion.VERSION);

        this.addDeserializer(Date.class, new MultiFormatDateDeserializer(setting.getDeserializeDateFormatString()));
    }
}
