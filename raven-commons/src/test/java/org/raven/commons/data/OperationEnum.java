package org.raven.commons.data;


public enum OperationEnum implements StringType, Description {

    Expression("exps","表达式"),

    EQ("eq", "等于"),
    NE("ne", "不等于"),
    GT("gt", "大于"),
    GTE("gte", "大于等于"),
    LT("lt", "小于"),
    LTE("lte", "小于等于"),
    IN("in", "包含"),
    NIN("nin", "不包含"),
    Empty("empty", "为空"),
    NotEmpty("not_empty", "不为空")
    ;

    private String value;

    private String description;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return description;
    }

    OperationEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static OperationEnum fromValue(String value) {

        for (OperationEnum e : values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }

        return null;
    }

}
