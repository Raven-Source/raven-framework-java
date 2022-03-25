package org.raven.serializer.withJackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.MemberFormatType;
import org.raven.commons.data.annotation.Contract;
import org.raven.commons.data.annotation.Member;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@Contract(formatType = MemberFormatType.PascalCase)
@Data
public class Paper {

    private ColorType color;

    @Member("tlt")
    private String title;

    private String desc;

}
