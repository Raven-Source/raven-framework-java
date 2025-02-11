package org.raven.api.codegen;

import freemarker.template.TemplateException;
import org.dom4j.DocumentException;

import java.io.IOException;

public interface CodeBuilder {

    void loadStructure(String xmlContent) throws DocumentException;

    SchemaParser getSchemaParser();

    void render() throws IOException, TemplateException;
}
