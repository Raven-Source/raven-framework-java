package org.raven.api.codegen;

/**
 * @author liangyi
 * @date 2018/9/3
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
