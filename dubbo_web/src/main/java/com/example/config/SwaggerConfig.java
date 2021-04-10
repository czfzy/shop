package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //.apiInfo(groupApiInfo()) //文档信息
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller")) //扫描接口
                .paths(PathSelectors.any())
                .build();
    }

    //配置文档信息
    private ApiInfo groupApiInfo() {
        return new ApiInfoBuilder()
                .title("swagger-bootstrap-ui~~~")
                .description("<div style='font-size:14px;color:red;'>swagger-bootstrap-ui-demo RESTful APIs</div>")
                .termsOfServiceUrl("http://www.xx.com/")
                //.contact("group@qq.com")
                .version("1.0")
                .build();
    }

    //配置文档信息-kuang
    private ApiInfo apiInfo() {
        Contact contact = new Contact("联系人", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱");
        return new ApiInfo(
                "Swagger学习", //标题
                "学习演示如何配置Swagger", //描述
                "v1.0", //版本
                "http://terms.service.url/组织链接", //组织链接
                contact, //联系人信息
                "Apach 2.0 许可", //许可
                "许可链接", //许可连接
                new ArrayList<>() //扩展
        );
    }
}
