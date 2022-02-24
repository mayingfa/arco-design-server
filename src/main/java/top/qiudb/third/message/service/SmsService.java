package top.qiudb.third.message.service;

public interface SmsService {
    /**
     * 发送短信验证码
     * @param phone 手机号
     */
    void sendSms(String phone);
}
