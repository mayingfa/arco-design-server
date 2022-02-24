package top.qiudb.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;


@Slf4j
@Configuration
@EnableOpenApi
@EnableKnife4j
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 是否启用swagger文档
     */
    @Value("${swagger.enable}")
    private boolean enable;

    @Value("${server.port}")
    private int port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${swagger.name}")
    private String name;

    @Value("${swagger.url}")
    private String url;

    @Value("${swagger.email}")
    private String email;

    @Bean
    @SneakyThrows
    public Docket createRestApi() {
        String ipAddress = InetAddress.getLocalHost().getHostAddress();
        Docket docket = new Docket(DocumentationType.OAS_30)
                .enable(enable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.qiudb"))
                .paths(PathSelectors.any())
                .build();
        log.info("项目接口文档地址: http://{}:{}{}/doc.html", ipAddress, port, contextPath);
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("后端开发模板")
                .description("基于Springboot、MyBatis-plus的后端开发模板")
                .termsOfServiceUrl(url)
                .contact(new Contact(name, url, email))
                .version("1.0")
                .build();
    }
}
