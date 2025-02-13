package org.raven.springfox.swagger2;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.raven.commons.data.Describable;
import org.raven.commons.data.SerializableType;
import org.raven.commons.data.SerializableTypeUtils;
import org.raven.commons.util.StringUtils;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ModelSpecificationBuilder;
import springfox.documentation.builders.PropertySpecificationBuilder;
import springfox.documentation.schema.Annotations;
import springfox.documentation.schema.ScalarTypes;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.schema.ApiModelProperties;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@SuppressWarnings("deprecation")
public class SerializableTypePropertyPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public void apply(ModelPropertyContext context) {

        if (context.getBeanPropertyDefinition().isPresent()) {
            BeanPropertyDefinition beanPropertyDefinition = context.getBeanPropertyDefinition().get();
            if (beanPropertyDefinition.getField() != null) {

                //获取当前字段的类型
                final Class<?> fieldType = beanPropertyDefinition.getField().getRawType();

                //为枚举字段设置注释
                descForSerializableTypeFields(context, fieldType);
            }
        }


    }

    /**
     * 为枚举字段设置注释
     */
    private void descForSerializableTypeFields(ModelPropertyContext context, Class fieldType) {

        if (!SerializableType.class.isAssignableFrom(fieldType)) {
            return;
        }

        Optional<ApiModelProperty> annotation = Optional.empty();

        // 找到 @ApiModelProperty 注解修饰的枚举类
        if (context.getAnnotatedElement().isPresent()) {
            annotation = annotation.isPresent() ? annotation
                    : ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get());
        }
        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = annotation.isPresent() ? annotation
                    : Annotations.findPropertyAnnotation(context.getBeanPropertyDefinition().get(), ApiModelProperty.class);
        }

        //没有@ApiModelProperty 或者 notes 属性没有值，直接返回
        if (!annotation.isPresent()) {
            return;
        }


        Object[] subItemRecords = null;
        if (Enum.class.isAssignableFrom(fieldType)) {
            subItemRecords = fieldType.getEnumConstants();
        }
        if (null == subItemRecords) {
            return;
        }

        PropertySpecificationBuilder builder = context.getSpecificationBuilder();

        final List<String> displayValues = new ArrayList<>();
        final List<String> enumerationValues = new ArrayList<>();
        for (Object x : subItemRecords) {
            if (Objects.isNull(x)) {
                continue;
            }
            String value;
            String display;
            if (x instanceof SerializableType) {
                display = value = ((SerializableType<?>) x).getValue().toString();
                if (x instanceof Describable) {
                    display = value + " - " + ((Describable) x).getDescription();
                }
            } else {
                display = value = x.toString();
            }

            enumerationValues.add(value);
            displayValues.add(display);
        }

        Class<?> valueType = SerializableTypeUtils.getGenericType(fieldType);
        final ResolvedType resolvedType = context.getResolver().resolve(valueType);

        String joinText = String.join("; ", displayValues);
        try {
            // 拿到字段上原先的描述
            Field mField = PropertySpecificationBuilder.class.getDeclaredField("description");
            mField.setAccessible(true);
            // context 中的 builder 对象保存了字段的信息
            Object description = mField.get(builder);
            if (description != null && StringUtils.isNotBlank(description.toString())) {
                joinText = description + " (" + joinText + ")";
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }


        builder.enumerationFacet(x -> x.allowedValues(new AllowableListValues(enumerationValues, valueType.getSimpleName())))
                .description(joinText)
                .type(
                        new ModelSpecificationBuilder().scalarModel(
                                ScalarTypes.builtInScalarType(valueType).orElse(null)
                        ).build())
        ;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
