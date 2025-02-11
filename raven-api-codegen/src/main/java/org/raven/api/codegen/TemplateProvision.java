package org.raven.api.codegen;

import java.io.InputStream;

public interface TemplateProvision {

    InputStream getTemplate(AbstractDescribe modelDescribe, Language language);

}
