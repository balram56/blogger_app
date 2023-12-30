package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //http://localhost:8080/swagger-ui/index.html
    //direct on chrome
    public static final String AUTHORIZATION_hEADER="Authorization";

    private ApiKey apiKey(){
        return new ApiKey("JWT",AUTHORIZATION_hEADER, "header" );
    }

    private List<SecurityContext> securityContexts(){
        return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());

    }

    private List<SecurityReference>  sf(){
        AuthorizationScope scope = new AuthorizationScope("global","accessEverything");
        return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[]{scope}));

    }


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContexts())
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    // Additional configuration methods can go here

    private ApiInfo getInfo() {
        // Set your API information here (title, description, version, etc.)
        return new ApiInfo("Blogging Application : Backend Api","This project is developed by self","1.0","Terms of Service",
                new Contact("Balram", "http://learncodewithdurgesh.com","bkp5693@gmail.com"),"license of APIS","API license URl" ,

                Collections.emptyList());
    }
}
