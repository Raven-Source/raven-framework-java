package org.raven.serializer.withJackson;

import lombok.Getter;
import lombok.Setter;
import org.raven.commons.util.StringUtils;

import java.util.Arrays;
import java.util.TimeZone;

import static org.raven.commons.constant.DateFormatStringConstant.*;

/**
 *
 */
@Getter
@Setter
public class SerializerSetting {

    /**
     *
     */
    private String dateFormatString;

    /**
     *
     */
    private String[] deserializeDateFormatString;

    /**
     *
     */
    private TimeZone timeZone;

    /**
     * @return SerializerSetting
     */
    public static SerializerSetting getDefault() {

        SerializerSetting setting = new SerializerSetting();
        setting.setDateFormatString(ISO_OFFSET_DATE_TIME);
        setting.setDeserializeDateFormatString(new String[]{
            ISO_OFFSET_DATE_TIME,   //ISO_OFFSET
            ISO_INSTANT,
            ISO_INSTANT_SECOND,
            DATE_TIME,
            DATE_TIME_SECOND
        });

        String timezone = System.getProperty("user.timezone");
        if (StringUtils.isBlank(timezone)) {
            timezone = "UTC";
        }
        setting.setTimeZone(TimeZone.getTimeZone(timezone));

        return setting;
    }

    @Override
    public String toString() {
        return "SerializerSetting{" +
            "dateFormatString='" + dateFormatString + '\'' +
            ", deserializeDateFormatString=" + Arrays.toString(deserializeDateFormatString) +
            ", timeZone=" + timeZone +
            '}';
    }
}
