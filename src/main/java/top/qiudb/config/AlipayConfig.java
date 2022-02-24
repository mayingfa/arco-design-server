package top.qiudb.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.qiudb.common.properties.AlipayProperties;

import javax.annotation.Resource;

/**
 * 支付宝配置
 */
@Configuration
@EnableConfigurationProperties(AlipayProperties.class)
public class AlipayConfig {

    @Resource
    private AlipayProperties properties;

    /**
     * 支付宝客户端 alipay-sdk-java
     *
     * @return {@link AlipayClient}
     */
    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(properties.getGatewayUrl(),
                properties.getAppid(),
                properties.getPrivateKey(),
                properties.getFormat(),
                properties.getCharset(),
                properties.getAlipayPublicKey(),
                properties.getSignType());
    }
}
