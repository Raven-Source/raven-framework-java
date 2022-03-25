package raven.serializer;

import java.util.Date;
import java.util.List;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.msgpack.annotation.Message;

@Getter
@Setter
@ToString
@Message
public class User {

    @Protobuf
    private int id;

    @Protobuf
    private String name;

    private Date time;

    @Protobuf
    private List<Integer> list;

    @Protobuf
    private long a;

    private Date date2;

    @Protobuf
    private boolean gender;
}
