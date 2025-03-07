package org.raven.api.codegen.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * date 2018/8/28
 *
 * @author liangyi
 */
public abstract class FreeMarkerUtils {

    public static String render(Template template, Object data)
            throws IOException, TemplateException {

        StringWriter sw = new StringWriter();
        template.process(data, sw);

        return sw.toString();

    }

    public static String render(InputStream inputStream, String templateName, Object data)
            throws IOException, TemplateException {

        Template template = getTemplate(inputStream, templateName);

        return render(template, data);

    }

    public static Template getTemplate(InputStream inputStream, String templateName)
            throws IOException {

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
//        cfg.setTemplateLoader(templateLoader);
//        cfg.setTemplateLoader(new FileTemplateLoader(new File(root)));
        //cfg.setDirectoryForTemplateLoading(new File(templatePath));
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.name());
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        return new Template(templateName, new InputStreamReader(inputStream, StandardCharsets.UTF_8), cfg);

    }

}
