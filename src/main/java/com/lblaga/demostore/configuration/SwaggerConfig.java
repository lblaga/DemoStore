package com.lblaga.demostore.configuration;

import com.lblaga.demostore.model.OrderItem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Configures Swagger.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Contact DEFAULT_CONTACT = new Contact(
            "Levente Blága", "", "blagalevi@gmail.com");

    private static ApiInfo API_INFO = new ApiInfo(
            "Demo Store",
            "Demo Spring Boot 2.0 using JPA.",
            "0.1",
            "",
            DEFAULT_CONTACT,
            "",
            "",
            new ArrayList<>());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Collections.singletonList("application/json"));

    @Bean
    public Docket productApi() {
        ResponseMessage r200 = new ResponseMessage(200, "OK", null, Collections.emptyMap(), Collections.emptyList());
        ResponseMessage r201 = new ResponseMessage(201, "Created", null, Collections.emptyMap(), Collections.emptyList());
        ResponseMessage r400 = new ResponseMessage(400, "Bad Request", null, Collections.emptyMap(), Collections.emptyList());
        ResponseMessage r404 = new ResponseMessage(404, "Not Found", null, Collections.emptyMap(), Collections.emptyList());
        ResponseMessage r500 = new ResponseMessage(500, "Internal Server Error", null, Collections.emptyMap(), Collections.emptyList());

        return new Docket(DocumentationType.SWAGGER_2)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .apiInfo(API_INFO)
                .globalResponseMessage(RequestMethod.POST, Stream.of(r200, r201, r400, r404, r500).collect(Collectors.toList()))
                .globalResponseMessage(RequestMethod.GET, Stream.of(r200, r400, r404, r500).collect(Collectors.toList()))
                .globalResponseMessage(RequestMethod.PUT, Stream.of(r200, r400, r404, r500).collect(Collectors.toList()))
                .globalResponseMessage(RequestMethod.DELETE, Stream.of(r200, r400, r404, r500).collect(Collectors.toList()))
                .groupName("Demo Store API")
                .forCodeGeneration(true)
                .ignoredParameterTypes(OrderItem.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lblaga.demostore.controller"))
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .displayRequestDuration(true)
                .validatorUrl("")
                .operationsSorter(OperationsSorter.METHOD)
                .build();
    }
}
