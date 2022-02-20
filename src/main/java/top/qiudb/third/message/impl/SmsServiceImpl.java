package top.qiudb.third.message.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.qiudb.common.exception.Asserts;
import top.qiudb.third.message.SmsService;
import top.qiudb.third.redis.RedisService;
import top.qiudb.util.StringTools;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    RedisService redisService;

    @Value("${sms.aliyun.accessKeyId}")
    private String accessKeyId;

    @Value("${sms.aliyun.accessKeySecret}")
    private String accessSecret;

    @Value("${sms.aliyun.templateCode}")
    private String templateCode;

    @Value("${sms.aliyun.signName}")
    private String signName;

    @Value("${redis.key.prefix.authCode}")
    private String keyPrefix;

    @Value("${redis.key.expire.authCode}")
    private Long keyExpire;

    @Override
    public void sendSms(String phone) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> param = new HashMap<>(2);
        String randCode = StringTools.getRandCode();
        String authCodeKey = keyPrefix + phone;
        redisService.set(authCodeKey, randCode, keyExpire);
        Asserts.checkTrue(redisService.hasKey(authCodeKey), "短信验证码发送失败");
        param.put("code", randCode);
        log.info("验证码为：{}", randCode);
        try {
            request.putQueryParameter("TemplateParam", mapper.writeValueAsString(param));
            CommonResponse response = client.getCommonResponse(request);
            log.info("发送短信：{}", response.getData());
            if (!response.getHttpResponse().isSuccess()) {
                Asserts.fail("短信验证码发送失败");
            }
        } catch (ClientException | JsonProcessingException e) {
            log.error("短信发送失败", e);
            Asserts.fail("短信验证码发送失败", e);
        }
    }
}