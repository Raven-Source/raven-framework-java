package org.raven.api.codegen;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.raven.commons.util.CollectionUtils;
import org.raven.commons.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CodeTemplateProvision extends DefaultTemplateProvision implements TemplateProvision {

    private final Map<String, byte[]> templateCache;

    public CodeTemplateProvision(final ApiConfig apiConfig) {
        super(apiConfig.getDefaultTemplateRoot());

        this.templateCache = new HashMap<>();
        initTemplateConfig(apiConfig);

    }


    @Override
    public InputStream getTemplate(AbstractDescribe modelDescribe, Language language) {

        String key = getTemplateKey(modelDescribe.modelType, language);
        byte[] buff = templateCache.get(key);
        if (buff != null) {
            return new ByteArrayInputStream(buff);
        } else {

            try (InputStream inputStream = super.getTemplate(modelDescribe, language)) {

                if (inputStream != null) {
                    buff = addTemplateCache(modelDescribe.modelType, language, inputStream);
                    if (buff != null) {
                        return new ByteArrayInputStream(buff);
                    }
                }

            } catch (Exception ex) {
                log.error("ge template error, modelType: " + modelDescribe.getModelType().name(), ex);
            }

        }

        throw new RuntimeException("getTemplate error");

    }

    private void initTemplateConfig(final ApiConfig apiConfig) {


        if (CollectionUtils.isNotEmpty(apiConfig.getTemplates())) {
            for (TemplateConfig templateConfig : apiConfig.getTemplates()) {

                String templatePath = templateConfig.getPath();

                if (StringUtils.isNotBlank(templatePath)) {

                    try (InputStream inputStream = this.getClass().getResourceAsStream(templatePath)) {
                        if (inputStream != null) {
                            addTemplateCache(
                                    ModelType.fromString(templateConfig.getModelType()),
                                    Language.fromString(apiConfig.getLanguage()),
                                    inputStream
                            );
                        } else {
                            log.error("template is empty, path: " + templatePath);
                        }
                    } catch (Exception ex) {
                        log.error("ge template error, path: " + templatePath, ex);
                    }
                }

            }
        }
    }

    private byte[] addTemplateCache(ModelType modelType, Language language, InputStream inputStream) {

        String key = getTemplateKey(
                modelType,
                language
        );
        try {

            byte[] result = IOUtils.readStreamAsByteArray(inputStream);
            templateCache.put(key, result);
            return result;
        } catch (Exception ex) {
            log.error("add template error", ex);
        }

        return null;
    }


    private String getTemplateKey(ModelType modelType, Language language) {
        return language.name() + "_" + modelType.name();
    }

}
