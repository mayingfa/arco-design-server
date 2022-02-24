package top.qiudb.third.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.qiudb.third.message.service.MailService;
import top.qiudb.third.message.service.SmsService;

/**
 * 发送短信测试
 */
@SpringBootTest
class SmsServiceTest {
    @Autowired
    SmsService smsService;

    @Autowired
    MailService mailService;

    @Test
    void sendSms() {
        smsService.sendSms("15233088662");
    }

    @Test
    void sendMail() {
        mailService.sendEmailCode("1325554003@qq.com");
    }
}
