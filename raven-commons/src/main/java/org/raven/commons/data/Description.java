package org.raven.commons.data;

import org.raven.commons.util.StringUtils;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.6.14
 */
public interface Description {

    String DESC = "desc";

    default String getDesc() {
        return StringUtils.EMPTY;
    }

}
