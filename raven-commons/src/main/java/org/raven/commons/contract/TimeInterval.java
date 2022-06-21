package org.raven.commons.contract;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * @author yanfeng
 * date 2021.07.12 10:48
 */
@Data
@FieldNameConstants
public class TimeInterval<T extends Comparable<T>> {

    private T start;
    private T end;

}
