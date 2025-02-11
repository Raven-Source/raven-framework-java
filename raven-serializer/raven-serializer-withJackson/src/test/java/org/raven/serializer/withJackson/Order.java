package org.raven.serializer.withJackson;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2021.03.01 23:28
 */
@Data
public class Order {
    private BigDecimal id;

    private LocalTime localTime;

    private List<LocalTime> localTimes;
}
