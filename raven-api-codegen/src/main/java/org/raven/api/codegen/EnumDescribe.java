package org.raven.api.codegen;

import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.MutableDescribable;


@Getter
@Setter
public class EnumDescribe implements MutableDescribable {

//    private String typeConvert;

    private String name;
    private String value;

    private String description;
}
