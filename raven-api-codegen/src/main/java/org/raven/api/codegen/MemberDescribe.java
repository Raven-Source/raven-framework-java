package org.raven.api.codegen;

import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.MutableDescribable;

/**
 * @author liangyi
 * @date 2018/9/3
 */
@Getter
@Setter
public class MemberDescribe implements MutableDescribable {

    private String type;

    private String filed;

    private String property;

    private boolean array;

    private String description;

    private ConstraintDescribe constraint;

}
