package org.raven.api.codegen;

import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.MutableDescribable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangyi
 * @date 2018/9/3
 */
@Getter
@Setter
public class ModelDescribe extends AbstractDescribe implements MutableDescribable {

//    private String responseClassName;

    private String genericTypeName;

    private String extend;

    private String implement;

    private List<MemberDescribe> members = new ArrayList<>();

    private String enumType;

    private String enumTypeConvert;

    private boolean enumIsString;

    private List<EnumDescribe> enums;

    private String description;

}
