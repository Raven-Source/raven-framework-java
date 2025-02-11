package org.raven.api.codegen;

/**
 * date 2018/9/3
 *
 * @author liangyi
 */
public enum ModelType {

    EnumModel,
    InteriorModel,
    ServiceModel,
//    RequestModel,
//    ResponseModel,
    ;


    public static ModelType fromString(String value) {
        for (ModelType item : ModelType.values()) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }
}
