package top.qiudb.third.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 发送短信测试
 */
@SpringBootTest
class SmsServiceTest {
    @Autowired
    SmsService smsService;

    @Test
    void sendSms() {
        smsService.sendSms("15233088662");
    }
}
