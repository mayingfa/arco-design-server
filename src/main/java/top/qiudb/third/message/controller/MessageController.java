package top.qiudb.third.message.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.qiudb.common.domain.CommonResult;
import top.qiudb.third.message.dto.MailCodeParam;
import top.qiudb.third.message.dto.PhoneCodeParam;
import top.qiudb.third.message.service.MailService;
import top.qiudb.third.message.service.SmsService;

@RestController
@Api(tags = "第三方消息服务")
@RequestMapping("/third/message/")
public class MessageController {
    @Autowired
    SmsService smsService;

    @Autowired
    MailService mailService;

    @ApiOperation(value = "发送手机验证码")
    @PostMapping(value = "/phone-code")
    public CommonResult<String> sendPhoneCode(@Validated @RequestBody PhoneCodeParam param) {
        smsService.sendSms(param.getPhone());
        return CommonResult.success("短信验证码发送成功");
    }

    @ApiOperation(value = "发送邮箱验证码")
    @PostMapping(value = "/email-code")
    public CommonResult<String> sendMailCode(@Validated @RequestBody MailCodeParam param) {
        mailService.sendEmailCode(param.getEmail());
        return CommonResult.success("邮箱验证码发送成功");
    }
}
