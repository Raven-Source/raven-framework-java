package org.raven.commons.data;

        import org.raven.commons.util.StringUtil;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.6.14
 */
public interface Description {

    default String getDesc() {
        return StringUtil.EMPTY;
    }

}
