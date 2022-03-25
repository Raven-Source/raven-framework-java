package raven.serializer;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.msgpack.annotation.Index;
import org.msgpack.annotation.Message;
import org.raven.commons.data.annotation.Member;

@Getter
@Setter
@ToString
@Message
public class Mall {

    @Index(0)
    @Member(index = 0)
    @Protobuf
    private long id;

    @Index(1)
    @Member(index = 1)
    @Protobuf
    private String name;

    @Index(2)
    @Member(index = 2)
    private java.util.Date date;

    @Index(3)
    @Member(index = 3)
    @Protobuf
    private long groupId;

    @Index(4)
    @Member(index = 4)
    @Protobuf
    private String AAAAAAAAAA;

    @Index(5)
    @Member(index = 5)
    @Protobuf
    private String BBBBBBBBBB;

    @Index(6)
    @Member(index = 6)
    @Protobuf
    private String CCCCCCCCCC;

    @Index(7)
    @Member(index = 7)
    @Protobuf
    private String D;

    @Index(8)
    @Member(index = 8)
    @Protobuf
    private String EEEEEEEEEE;

    @Index(9)
    @Member(index = 9)
    @Protobuf
    private String F;

    @Index(10)
    @Member(index = 10)
    @Protobuf
    private String g;

    @Index(11)
    @Member(index = 11)
    @Protobuf
    private String HHHHHHHHHH;

    @Index(12)
    @Member(index = 12)
    @Protobuf
    private String I;

    @Index(13)
    @Member(index = 13)
    @Protobuf
    private String J;

    @Index(14)
    @Member(index = 14)
    @Protobuf(fieldType = FieldType.OBJECT)
    private User user;

}
