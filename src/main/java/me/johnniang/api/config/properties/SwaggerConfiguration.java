package me.johnniang.api.config.properties;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Type;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * Swagger configuration.
 *
 * @author johnniang
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    private final List<ResponseMessage> globalResponses = Arrays.asList(
            new ResponseMessageBuilder().code(200).message("Success").build(),
            new ResponseMessageBuilder().code(400).message("Bad request").build(),
            new ResponseMessageBuilder().code(401).message("Unauthorized").build(),
            new ResponseMessageBuilder().code(403).message("Forbidden").build(),
            new ResponseMessageBuilder().code(404).message("Not found").build(),
            new ResponseMessageBuilder().code(500).message("Internal server error").build());

    @Bean
    public Docket eventV1Api() {
        return buildApiDocket("me.johnniang.api.v1.events",
                "me.johnniang.api.controller.v1.event",
                "/api/v1/events/**");
    }

    @Bean
    SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId("test-app-client-id")
                .clientSecret("test-app-client-secret")
                .realm("test-app-realm")
                .appName("test-app")
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .build();
    }

    private Docket buildApiDocket(@NonNull String groupName, @NonNull String basePackage, @NonNull String antPattern) {
        Assert.hasText(groupName, "Group name must not be blank");
        Assert.hasText(basePackage, "Base package must not be blank");
        Assert.hasText(antPattern, "Ant pattern must not be blank");

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.ant(antPattern))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Arrays.asList(apiKeys()))
                .securityContexts(Arrays.asList(securityContext()))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalResponses)
                .globalResponseMessage(RequestMethod.POST, globalResponses)
                .globalResponseMessage(RequestMethod.DELETE, globalResponses)
                .globalResponseMessage(RequestMethod.PUT, globalResponses)
                .directModelSubstitute(Temporal.class, String.class);
    }

    private ApiKey apiKeys() {
        return new ApiKey("TOKEN ACCESS", HttpHeaders.AUTHORIZATION, In.HEADER.name());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/api/v1/.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("global", "accessEverything")};
        return Arrays.asList(new SecurityReference("TOKEN ACCESS", authorizationScopes));
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Johnniang API documentation")
                .description("Documentation for johniang api")
                .version("v0.1.1")
                .termsOfServiceUrl("http://api.johnniang.me")
                .contact(new Contact("johnniang", "https:johnniang.me/", "johnniang@riseup.net"))
                .build();
    }

    @Bean
    public AlternateTypeRuleConvention pageableConvention(final TypeResolver resolver) {
        return new AlternateTypeRuleConvention() {
            @Override
            public int getOrder() {
                return Ordered.LOWEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules() {
                return Arrays.asList(
                        newRule(resolver.resolve(Pageable.class), resolver.resolve(pageableMixin())),
                        newRule(resolver.resolve(Sort.class), resolver.resolve(sortMixin())));
            }
        };
    }

    private Type emptyMixin(Class<?> clazz) {
        Assert.notNull(clazz, "class type must not be null");

        return new AlternateTypeBuilder()
                .fullyQualifiedClassName(String.format("%s.generated.%s", clazz.getPackage().getName(), clazz.getSimpleName()))
                .withProperties(Collections.emptyList())
                .build();
    }

    private Type sortMixin() {
        return new AlternateTypeBuilder()
                .fullyQualifiedClassName(String.format("%s.generated.%s", Sort.class.getPackage().getName(), Sort.class.getSimpleName()))
                .withProperties(Arrays.asList(property(String[].class, "sort")))
                .build();
    }

    private Type pageableMixin() {
        return new AlternateTypeBuilder()
                .fullyQualifiedClassName(String.format("%s.generated.%s", Pageable.class.getPackage().getName(), Pageable.class.getSimpleName()))
                .withProperties(Arrays.asList(property(Integer.class, "page"), property(Integer.class, "size"), property(String[].class, "sort")))
                .build();
    }

    private AlternateTypePropertyBuilder property(Class<?> type, String name) {
        return new AlternateTypePropertyBuilder()
                .withName(name)
                .withType(type)
                .withCanRead(true)
                .withCanWrite(true);
    }

}
