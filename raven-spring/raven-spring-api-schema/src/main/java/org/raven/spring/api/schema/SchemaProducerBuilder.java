package org.raven.spring.api.schema;

import org.raven.spring.api.schema.spi.ConstraintProvide;
import org.raven.spring.api.schema.spi.JavadocProvide;
import org.raven.spring.api.schema.spi.WebHandlerProvide;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * date 2018/9/4
 *
 * @author liangyi
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class SchemaProducerBuilder {

    private String loadPackageRoot;

    private WebHandlerProvide webHandlerInfoProvide;
    private JavadocProvide javadocProvide;
    private ConstraintProvide constraintProvide;

    private Set<Class<? extends Annotation>> serviceFindByAnnotationSet;
    private Set<Class<? extends Annotation>> operationFilterByAnnotationSet;
    private Set<Class<? extends Annotation>> operationIgnoreByAnnotationSet;
    private Set<Class<? extends Annotation>> memberIgnoreByAnnotationSet;
    private Set<Class<? extends Annotation>> paramFilterByAnnotationSet;
    //    private Class<?> responseModelClass;
    private Class<?> enumInterface;
    private Set<String> excludeClassSet;
    private String version;

    public SchemaProducer build() {

        return new SchemaProducer(
                this.loadPackageRoot,
                this.webHandlerInfoProvide,
                this.javadocProvide,
                this.constraintProvide,
                this.serviceFindByAnnotationSet,
                this.operationFilterByAnnotationSet,
                this.operationIgnoreByAnnotationSet,
                this.memberIgnoreByAnnotationSet,
                this.paramFilterByAnnotationSet,
//                this.requestModelClass,
//                this.responseModelClass,
                this.enumInterface,
                this.excludeClassSet,
                this.version
        );

    }
}