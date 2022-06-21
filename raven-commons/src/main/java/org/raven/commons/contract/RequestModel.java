package org.raven.commons.contract;

import lombok.Data;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
@Data
public class RequestModel implements Request<Extension> {

    private Extension extension;

    /**
     *
     */
    public RequestModel() {
        extension = new Extension();
    }

}
