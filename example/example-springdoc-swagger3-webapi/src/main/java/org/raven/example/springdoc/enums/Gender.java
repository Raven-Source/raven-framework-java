package org.raven.example.springdoc.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.raven.commons.data.Describable;
import org.raven.commons.data.GenericUtils;
import org.raven.commons.data.NumberType;
import org.raven.commons.data.ValueType;
import org.raven.commons.data.annotation.Create;
import org.raven.commons.data.annotation.Values;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.04.23 01:00
 */
@Schema(title = "性别")
@Getter
public class Gender implements ValueType<Long>, Describable {

    private final Long value;
    private final String description;

    private Gender(Long value, String description) {
        this.value = value;
        this.description = description;
    }

    @Schema(title = "男")
    public final static Gender x = new Gender(10L, "男");

    @Schema(title = "女")
    public final static Gender y = new Gender(20L, "女");


    @Create
    private static Gender valueOf(Long value) {
        for (Gender gender : values()) {
            if (gender.equalsValue(value)) {
                return gender;
            }
        }
        return new Gender(value, "");
    }

    @Values
    public static Gender[] values() {
        return new Gender[]{x, y};
    }

    public static void main(String[] args) {

        Type type = Gender.class.getGenericSuperclass();

        System.out.println(GenericUtils.getInterfacesGenericTypes(Gender.class, NumberType.class)[0]);


        for (Field declaredField : Gender.class.getDeclaredFields()) {
            System.out.println(declaredField.getName());
            System.out.println(declaredField.getType().getName());
        }


    }
}
