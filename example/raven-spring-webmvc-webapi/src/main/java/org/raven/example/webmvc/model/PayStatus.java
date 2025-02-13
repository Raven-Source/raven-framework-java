package org.raven.example.webmvc.model;

import org.raven.commons.data.NumberType;
import org.raven.commons.data.annotation.Values;

public class PayStatus extends NumberType<Integer> {

    private PayStatus(Integer value) {
        super(value);
    }

    /**
     * 处理完成
     */
    public final static PayStatus Finish = new PayStatus(3);

    /**
     * 处理失败
     */
    public final static PayStatus Fail = new PayStatus(4);

//    @Create
//    private static StatusType valueOf(Integer i) {
//        return new StatusType(i, "");
//    }

    @Values
    public static PayStatus[] values() {
        return new PayStatus[]{Finish, Fail};
    }

    public static PayStatus valueOf(int value) {
        for (PayStatus status : values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return null;
    }
}
