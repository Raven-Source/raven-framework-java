package org.raven.spring.api.schema.config;

import org.raven.commons.util.ClassUtils;
import org.raven.spring.api.schema.spi.JavadocProvide;
import org.raven.spring.api.schema.spi.MemberMutableDescribable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

@Configuration
@ConditionalOnClass({Api.class, ApiOperation.class})
public class Swagger2JavadocConfiguration {

    @Bean
    @ConditionalOnMissingBean(JavadocProvide.class)
    public JavadocProvide javadocProvide() {
        return new Swagger2JavadocProvide();
    }

    public static class Swagger2JavadocProvide implements JavadocProvide {

        @Override
        public MemberMutableDescribable getMethodDeclaration(Class<?> clazz, Method method) {

            ApiOperation operation = MethodUtils.getAnnotation(method, ApiOperation.class, true, false);

            MemberMutableDescribable memberDeclaration = buildMemberDescribable(operation);

            if (memberDeclaration != null) {
                return memberDeclaration;
            }

            ApiModelProperty schema = MethodUtils.getAnnotation(method, ApiModelProperty.class, true, false);

            return buildMemberDescribable(schema);
        }

        @Override
        public MemberMutableDescribable getFieldDeclaration(Class<?> clazz, Field field) {

            ApiModelProperty schema = field.getAnnotation(ApiModelProperty.class);

            return buildMemberDescribable(schema);
        }

        @Override
        public MemberMutableDescribable getTypeDeclaration(Class<?> clazz) {

            Api tag = ClassUtils.getAnnotation(clazz, Api.class, true);
            MemberMutableDescribable memberDeclaration = buildMemberDescribable(tag);
            if (memberDeclaration != null) {
                return memberDeclaration;
            }

            ApiModelProperty schema = clazz.getAnnotation(ApiModelProperty.class);

            return buildMemberDescribable(schema);
        }

        private MemberMutableDescribable buildMemberDescribable(ApiModelProperty property) {

            if (property != null) {

                MemberMutableDescribable memberDeclaration = new MemberMutableDescribable();
                memberDeclaration.setDescription(
                        String.join(" "
                                , Objects.toString(property.value(), "")
                                , Objects.toString(property.notes(), "")
                        )
                );

                return memberDeclaration;
            }

            return null;
        }

        private MemberMutableDescribable buildMemberDescribable(ApiOperation operation) {

            if (operation != null) {

                MemberMutableDescribable memberDeclaration = new MemberMutableDescribable();
                memberDeclaration.setDescription(
                        String.join(" "
                                , Objects.toString(operation.value(), "")
                                , Objects.toString(operation.notes(), "")
                        )
                );

                return memberDeclaration;
            }

            return null;
        }

        private MemberMutableDescribable buildMemberDescribable(Api api) {

            if (api != null) {

                MemberMutableDescribable memberDeclaration = new MemberMutableDescribable();
                memberDeclaration.setDescription(
                        String.join(" "
                                , Objects.toString(api.value(), "")
                                , Objects.toString(api.description(), "")
                        )
                );

                return memberDeclaration;
            }

            return null;
        }
    }
}
