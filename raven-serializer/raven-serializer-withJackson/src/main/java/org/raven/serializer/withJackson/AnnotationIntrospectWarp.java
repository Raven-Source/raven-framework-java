package org.raven.serializer.withJackson;

import org.raven.commons.data.annotation.Ignore;
import org.raven.commons.data.annotation.Member;
import tools.jackson.databind.cfg.MapperConfig;
import tools.jackson.databind.introspect.Annotated;
import tools.jackson.databind.introspect.JacksonAnnotationIntrospector;

/**
 * @author yi.liang
 * date by 2018/1/8
 * @since JDK1.8
 */
public class AnnotationIntrospectWarp extends JacksonAnnotationIntrospector {

    @Override
    protected boolean _isIgnorable(Annotated a) {
        return _findAnnotation(a, Ignore.class) != null
            || super._isIgnorable(a);
    }

    @Override
    public Integer findPropertyIndex(MapperConfig<?> config, Annotated a) {
        Member member = _findAnnotation(a, Member.class);
        if (member != null) {
            int ix = member.index();
            if (ix != Member.INDEX_UNKNOWN) {
                return ix;
            }
        }
        return super.findPropertyIndex(config, a);
    }
}
