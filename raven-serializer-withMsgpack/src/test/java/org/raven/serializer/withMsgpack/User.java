package org.raven.serializer.withMsgpack;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.msgpack.annotation.Index;
import org.msgpack.annotation.Message;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Message
public class User {

    @Index(2)
    private int id;

    @Index(1)
    private String name;

    @Index(3)
    private Date time;

    @Index(4)
    private List<Integer> list;
}
