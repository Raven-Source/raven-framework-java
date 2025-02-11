package org.raven.api.codegen;

public enum Language {

    Java,

    TypeScript,

    ;

    public static Language fromString(String value) {
        for (Language item : Language.values()) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }

}
