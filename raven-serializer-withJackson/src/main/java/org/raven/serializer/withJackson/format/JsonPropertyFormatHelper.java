package org.raven.serializer.withJackson.format;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import org.raven.commons.data.MemberFormatUtils;
import org.raven.commons.data.annotation.Contract;
import org.raven.commons.data.annotation.Member;
import org.raven.commons.util.StringUtils;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018/1/8
 */
public class JsonPropertyFormatHelper {

    /**
     * @param method      method
     * @param defaultName defaultName
     * @return String
     */
    public static String format(AnnotatedMember method, String defaultName) {

        if (method == null) {
            return defaultName;
        }

        Member dataMember = method.getAnnotation(Member.class);
        if (dataMember != null && !StringUtils.isBlank(dataMember.value())) {
            return dataMember.value();
        }

        Contract contract = method.getAnnotation(Contract.class);
        if (contract == null) {
            contract = method.getDeclaringClass().getAnnotation(Contract.class);
        }
        if (contract != null) {
            return MemberFormatUtils.namingFormat(defaultName, contract.formatType());
        }
        return defaultName;

    }

    /**
     * @param field       field
     * @param defaultName defaultName
     * @return String
     */
    public static String format(AnnotatedField field, String defaultName) {

        if (field == null)
            return defaultName;

        Member dataMember = field.getAnnotation(Member.class);
        if (dataMember != null && !StringUtils.isBlank(dataMember.value())) {
            return dataMember.value();
        }

        Contract contract = field.getAnnotation(Contract.class);
        if (contract == null) {
            contract = field.getDeclaringClass().getAnnotation(Contract.class);
        }
        if (contract != null) {
            return MemberFormatUtils.namingFormat(defaultName, contract.formatType());
        }
        return defaultName;

    }
}
