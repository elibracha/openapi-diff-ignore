package org.openapi.diff.ignore.samples.config;

import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi tempOpenApi1(@Qualifier("openApbCustomizerV1") OpenApiCustomiser openApiCustomiser) {
        String[] paths = {"/v1/**"};
        String[] packagedToMatch = {"org.openapi.diff.ignore.samples.controllers"};
        GroupedOpenApi openApi = GroupedOpenApi.builder().setGroup("v1").pathsToMatch(paths).packagesToScan(packagedToMatch).addOpenApiCustomiser(openApiCustomiser).build();
        return openApi;
    }

    @Bean
    public GroupedOpenApi tempOpenApi2(@Qualifier("openApbCustomizerV2") OpenApiCustomiser openApiCustomiser) {
        String[] paths = {"/v2/**"};
        String[] packagedToMatch = {"org.openapi.diff.ignore.samples.controllers"};
        GroupedOpenApi openApi = GroupedOpenApi.builder().setGroup("v2").pathsToMatch(paths).packagesToScan(packagedToMatch).addOpenApiCustomiser(openApiCustomiser).build();
        return openApi;
    }

    @Bean
    public OpenApiCustomiser openApbCustomizerV1() {
        return openApi -> {
            openApi.paths(removeSubstringVersionFromPath(openApi.getPaths()));
            openApi.info(new Info().title("OpenAPI PetStore - OpenAPI 3.0 - Version 1.0")
                    .version("v1").description("PetStore Api version 1.0"));
        };
    }

    @Bean
    public OpenApiCustomiser openApbCustomizerV2() {
        return openApi -> {
            openApi.paths(removeSubstringVersionFromPath(openApi.getPaths()));
            openApi.info(new Info().title("OpenAPI PetStore - OpenAPI 3.0 - Version 2.0")
                    .version("v2").description("PetStore Api version 2.0"));
        };
    }

    public Paths removeSubstringVersionFromPath(Paths openApiPaths) {
        Paths paths = new Paths();
        for (Map.Entry<String, PathItem> entry : openApiPaths.entrySet()) {
            int cut = entry.getKey().indexOf("/", entry.getKey().indexOf("/") + 1);
            paths.put(entry.getKey().substring(cut), entry.getValue());
        }
        return paths;
    }
}