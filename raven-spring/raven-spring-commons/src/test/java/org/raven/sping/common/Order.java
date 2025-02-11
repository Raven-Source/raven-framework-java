package org.raven.sping.common;

import lombok.Data;
import org.junit.platform.commons.util.StringUtils;
import org.raven.commons.util.Lists;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static void main(String[] args) {

        System.out.println(
                String.join(System.lineSeparator(),
                        Lists.newArrayList("", "aa").stream().filter(StringUtils::isNotBlank).collect(Collectors.toList())
                )
        );
    }
}
