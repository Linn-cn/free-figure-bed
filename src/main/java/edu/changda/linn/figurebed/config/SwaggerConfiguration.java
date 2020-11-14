
package edu.changda.linn.figurebed.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.List;


/**
 * API文档配置
 * @author Linn-cn
 * @date 2020/8/31
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfiguration {


    @Bean(value = "defaultApi")
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.changda.linn.figurebed.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("GitHub图床API文档")
                .description("GitHub图床API文档")
                .termsOfServiceUrl("https://github.com/Linn-cn/")
                .contact(new Contact("Linn-cn", "https://github.com/Linn-cn", "Linn_cn@126.com"))
                .version("1.0")
                .build();
    }


}
