package org.raven.serializer.withJackson;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import org.raven.commons.data.annotation.Ignore;
import org.raven.commons.data.annotation.Member;

/**
 * @author yi.liang
 * date by 2018/1/8
 * @since JDK1.8
 */
public class AnnotationIntrospectorWarp extends JacksonAnnotationIntrospector {

    @Override
    protected boolean _isIgnorable(Annotated a) {
        return _findAnnotation(a, Ignore.class) != null
            || super._isIgnorable(a);
    }

    @Override
    public Integer findPropertyIndex(Annotated a) {
        Member member = _findAnnotation(a, Member.class);
        if (member != null) {
            int ix = member.index();
            if (ix != Member.INDEX_UNKNOWN) {
                return Integer.valueOf(ix);
            }
        }
        return super.findPropertyIndex(a);
    }
}
