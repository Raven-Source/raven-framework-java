package org.raven.spring.api.schema.config;

import org.raven.commons.util.ClassUtils;
import org.raven.spring.api.schema.spi.JavadocProvide;
import org.raven.spring.api.schema.spi.MemberMutableDescribable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

@Configuration
public class Swagger3JavadocConfiguration {

    @Bean
    @Primary
    @ConditionalOnMissingBean(JavadocProvide.class)
    @ConditionalOnClass({Operation.class, Schema.class})
    public JavadocProvide javadocProvide() {
        return new Swagger3JavadocProvide();
    }

    public static class Swagger3JavadocProvide implements JavadocProvide {

        @Override
        public MemberMutableDescribable getMethodDeclaration(Class<?> clazz, Method method) {

            Operation operation = MethodUtils.getAnnotation(method, Operation.class, true, false);

            MemberMutableDescribable memberDeclaration = buildMemberDescribable(operation);

            if (memberDeclaration != null) {
                return memberDeclaration;
            }

            Schema schema = MethodUtils.getAnnotation(method, Schema.class, true, false);

            return buildMemberDescribable(schema);
        }

        @Override
        public MemberMutableDescribable getFieldDeclaration(Class<?> clazz, Field field) {

            Schema schema = field.getAnnotation(Schema.class);

            return buildMemberDescribable(schema);
        }

        @Override
        public MemberMutableDescribable getTypeDeclaration(Class<?> clazz) {

            Tag tag = ClassUtils.getAnnotation(clazz, Tag.class, true);
            MemberMutableDescribable memberDeclaration = buildMemberDescribable(tag);
            if (memberDeclaration != null) {
                return memberDeclaration;
            }

            Schema schema = clazz.getAnnotation(Schema.class);

            return buildMemberDescribable(schema);
        }

        private MemberMutableDescribable buildMemberDescribable(Schema schema) {

            if (schema != null) {

                MemberMutableDescribable memberDeclaration = new MemberMutableDescribable();
                memberDeclaration.setDescription(
                        String.join(" "
                                , Objects.toString(schema.title(), "")
                                , Objects.toString(schema.description(), "")
                        )
                );

                return memberDeclaration;
            }

            return null;
        }

        private MemberMutableDescribable buildMemberDescribable(Operation operation) {

            if (operation != null) {

                MemberMutableDescribable memberDeclaration = new MemberMutableDescribable();
                memberDeclaration.setDescription(
                        String.join(" "
                                , Objects.toString(operation.description(), "")
                                , Objects.toString(operation.summary(), "")
                        )
                );

                return memberDeclaration;
            }

            return null;
        }

        private MemberMutableDescribable buildMemberDescribable(Tag tag) {

            if (tag != null) {

                MemberMutableDescribable memberDeclaration = new MemberMutableDescribable();
                memberDeclaration.setDescription(
                        String.join(" "
                                , Objects.toString(tag.name(), "")
                                , Objects.toString(tag.description(), "")
                        )
                );

                return memberDeclaration;
            }

            return null;
        }
    }

}
