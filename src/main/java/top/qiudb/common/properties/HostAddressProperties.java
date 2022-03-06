package top.qiudb.common.properties;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;

/**
 * 项目地址
 */
@Data
@Component
public class HostAddressProperties {
    @Value("${server.port}")
    private int port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    /**
     * 系统地址
     */
    private String hostAddress;

    @SneakyThrows
    @PostConstruct
    private void init(){
        String ipAddress = InetAddress.getLocalHost().getHostAddress();
        hostAddress = String.format("http://%s:%s%s/", ipAddress, port, contextPath);
    }
}
