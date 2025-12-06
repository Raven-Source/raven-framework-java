package org.raven.commons.data;

import java.util.Date;

/**
 * @author yanfeng
 * date 2021.07.06 20:41
 */
public interface UpdateTimer {
    Date getUpdateTime();

    void setUpdateTime(Date dateTime);
}
