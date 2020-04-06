package com.github.elibracha.samples.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
        String[] packagedToMatch = {"com.github.elibracha.samples.controllers"};
        GroupedOpenApi openApi = GroupedOpenApi.builder().setGroup("v1").pathsToMatch(paths).packagesToScan(packagedToMatch).addOpenApiCustomiser(openApiCustomiser).build();
        return openApi;
    }

    @Bean
    public GroupedOpenApi tempOpenApi2(@Qualifier("openApbCustomizerV2") OpenApiCustomiser openApiCustomiser) {
        String[] paths = {"/v2/**"};
        String[] packagedToMatch = {"com.github.elibracha.samples.controllers"};
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

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().openapi("3.0.2")
                .components(new Components().addSecuritySchemes("api_key",
                        new SecurityScheme().type(SecurityScheme.Type.APIKEY).name("api_key").in(SecurityScheme.In.HEADER))
                        .addSecuritySchemes("petstore_auth", new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(new OAuthFlows()
                                .implicit(new OAuthFlow().authorizationUrl("https://petstore3.swagger.io/oauth/authorize")
                                        .scopes(new Scopes().addString("write:pets", "modify pets in your account").addString("read:pets", "read your pets"))))));
    }
}