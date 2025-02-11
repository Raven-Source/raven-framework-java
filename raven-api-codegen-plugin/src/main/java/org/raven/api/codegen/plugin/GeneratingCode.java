package org.raven.api.codegen.plugin;

import org.raven.api.codegen.CodeBuilder;
import org.raven.api.codegen.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.raven.commons.util.Assert;
import org.raven.commons.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Mojo(name = "generating-code")
@Slf4j
public class GeneratingCode extends AbstractMojo {

    @Parameter
    private List<ApiConfig> apis;

//    @Parameter(defaultValue = "false")
//    private boolean enableDebug = false;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    /**
     * 插件入口类
     */
    @Override
    public void execute() throws MojoExecutionException {

        LoggerFactorySetup.initializeMavenLoggerFactory(getLog());

        for (ApiConfig apiConfig : apis) {

            if (apiConfig.isEnable()) {

                verify(apiConfig);

                log.info("process api: " + apiConfig.getName());
                CodeBuilder codeBuilder = CodeBuilderFactor.createCodeBuilder(apiConfig);
                try {
                    Assert.notNull(codeBuilder, "codeBuilder is null");
                    log.info("start loadStructure");
                    loadStructure(codeBuilder, apiConfig);
                    log.info("start codeBuilder render");
                    codeBuilder.render();
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        }

    }

    private void verify(ApiConfig apiConfig) {

        Assert.hasText(apiConfig.getName(), "name is empty");

        Assert.hasText(apiConfig.getLanguage(), "language is empty");

        Assert.notNull(Language.fromString(apiConfig.getLanguage()), "language is wrong");

        Assert.isTrue(StringUtils.isNotBlank(apiConfig.getUrl()) || StringUtils.isNotBlank(apiConfig.getSystemPath())
                , "url or path is empty");

        if (StringUtils.isBlank(apiConfig.getOutputRoot())) {
            apiConfig.setOutputRoot(Paths.get(
                    project.getBasedir().getAbsolutePath(),
                    "generated-sources",
                    apiConfig.getLanguage().toLowerCase(),
                    apiConfig.getName()
            ).toString());
        } else {
            apiConfig.setOutputRoot(Paths.get(
                    apiConfig.getOutputRoot(),
                    apiConfig.getName()
            ).toString());
        }
    }

    private void loadStructure(CodeBuilder codeBuilder, ApiConfig apiConfig) throws Exception {

        String apiStructureXml = null;

        if (!StringUtils.isBlank(apiConfig.getUrl())) {

            log.info("apiStructure url: " + apiConfig.getUrl());
            apiStructureXml = HttpUtils.get(apiConfig.getUrl());
        } else if (!StringUtils.isBlank(apiConfig.getSystemPath())) {

            String filePath = Paths.get(apiConfig.getSystemPath())
                    .toAbsolutePath()
                    .toString();

            log.info("apiStructure systemPath: " + filePath);
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
                apiStructureXml = bufferedReader.lines().collect(Collectors.joining("\n"));

            } catch (Exception ex) {

                log.error(ex.getMessage(), ex);
            }

        }

        Assert.hasText(apiStructureXml, "apiStructureXml is empty");

        if (log.isDebugEnabled()) {
            log.info("structure xml:\r\n" + apiStructureXml);
        }

        codeBuilder.loadStructure(apiStructureXml);

    }

}
