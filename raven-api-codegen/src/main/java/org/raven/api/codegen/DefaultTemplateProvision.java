package org.raven.api.codegen;

import java.io.InputStream;

public class DefaultTemplateProvision implements TemplateProvision {

    private final String path;

    public DefaultTemplateProvision(final String path) {
        this.path = path;
    }

    @Override
    public InputStream getTemplate(AbstractDescribe modelDescribe, Language language) {

        String tempName = modelDescribe.getModelType().toString() + "Temp.ftl";

        return getClass().getResourceAsStream(
                path +
                        "/" +
                        language.toString().toLowerCase() +
                        "/" +
                        tempName
        );

    }

//    public static void main(String[] args) {
//        String path = Paths.get("/template")
//                .resolve(Language.Java.name().toLowerCase())
//                .resolve(ModelType.EnumModel.name().toString())
//                .toString();
//
//        System.out.println(path);
//    }

}
