package org.raven.serializer.withJackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.raven.commons.data.MemberFormatType;
import org.raven.commons.data.annotation.Contract;

import java.util.Date;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@Contract(formatType = MemberFormatType.PascalCase)
@Data
public class User {

    private int id;
    private String name;
    private Date time;
    private List<Integer> list;
    private long a;
    private Date date2;

    private Gender gender = Gender.man;

    private Platform platform = Platform.WX;

}
