package top.qiudb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.qiudb.common.properties.UploadProperties;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Configuration
@EnableConfigurationProperties(UploadProperties.class)
public class WebConfig implements WebMvcConfigurer {
    private static final String WINDOWS_OS = "win";

    @Autowired
    private UploadProperties properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String realPathDir = new File(properties.getPath()).getAbsolutePath() + File.separator;
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/resources/");

        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith(WINDOWS_OS)) {
            registry.addResourceHandler("/upload/**")
                    .addResourceLocations("file:" + realPathDir);
        } else {
            registry.addResourceHandler("/upload/**")
                    .addResourceLocations("file://" + realPathDir);
        }

        registry.addResourceHandler("doc.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 解决controller返回字符串中文乱码问题
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
    }
}