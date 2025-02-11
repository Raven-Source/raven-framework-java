package org.raven.spring.api.schema.spi;

import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.MutableDescribable;

@Getter
@Setter
public class MemberMutableDescribable implements MutableDescribable {

    private String description;

}
