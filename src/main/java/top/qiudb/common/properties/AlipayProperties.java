package top.qiudb.common.properties;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 支付宝支付的参数配置
 */
@Data
@ConfigurationProperties(prefix = "alipay")
public class AlipayProperties {
    /**
     * 支付宝gatewayUrl
     */
    private String gatewayUrl;

    /**
     * 商户应用id
     */
    private String appid;

    /**
     * RSA私钥，用于对商户请求报文加签
     */
    private String privateKey;

    /**
     * 支付宝RSA公钥，用于验签支付宝应答
     */
    private String alipayPublicKey;

    /**
     * 签名类型
     */
    private String signType = "RSA2";

    /**
     * 格式
     */
    private String format = "json";

    /**
     * 编码
     */
    private String charset = "UTF-8";

    /**
     * 同步地址
     */
    private String returnUrl;

    /**
     * 异步地址
     */
    private String notifyUrl;

    /**
     * 重定向地址
     */
    private String redirectUrl;

    /**
     * 最大查询次数
     */
    private static int maxQueryRetry = 5;

    /**
     * 查询间隔（毫秒）
     */
    private static long queryDuration = 5000;

    /**
     * 最大撤销次数
     */
    private static int maxCancelRetry = 3;

    /**
     * 撤销间隔（毫秒）
     */
    private static long cancelDuration = 3000;

    private AlipayProperties() {}

    public String description() {
        return "\n支付宝沙箱配置信息：{\n" +
                "   支付宝网关: " + gatewayUrl + "\n" +
                "   appid: " + appid + "\n" +
                "   商户RSA私钥: " + getKeyDescription(privateKey) + "\n" +
                "   支付宝RSA公钥: " + getKeyDescription(alipayPublicKey) + "\n" +
                "   签名类型: " + signType + "\n" +
                "   查询重试次数: " + maxQueryRetry + "\n" +
                "   查询间隔(毫秒): " + queryDuration + "\n" +
                "   撤销尝试次数: " + maxCancelRetry + "\n" +
                "   撤销重试间隔(毫秒): " + cancelDuration + "\n" +
                "}";
    }

    private String getKeyDescription(String key) {
        int showLength = 6;
        if (!StringUtils.isEmpty(key) && key.length() > showLength) {
            return key.substring(0, showLength) + "******" + key.substring(key.length() - showLength);
        }
        return null;
    }
}
