package top.qiudb.third.message.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import top.qiudb.common.exception.Asserts;
import top.qiudb.third.message.service.MailService;
import top.qiudb.third.redis.RedisService;
import top.qiudb.util.StringTools;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisService redisService;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.nickname}")
    private String nickname;

    @Value("${redis.key.prefix.authCode}")
    private String keyPrefix;

    @Value("${redis.key.expire.authCode}")
    private Long keyExpire;

    @Override
    public void sendSimpleMail(String email, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(nickname + '<' + from + '>');
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    public void sendHtmlMail(String email, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            messageHelper.setFrom(nickname + '<' + from + '>');
            mailSender.send(message);
        } catch (Exception e) {
            Asserts.fail("HTML邮件发送失败", e);
        }
    }

    @Override
    public void sendAttachmentsMail(String email, String subject, String content, String filePath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            messageHelper.setFrom(nickname + '<' + from + '>');
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            messageHelper.addAttachment(fileName, file);
            mailSender.send(message);
        } catch (Exception e) {
            Asserts.fail("附件邮件发送失败", e);
        }
    }

    @Override
    public void sendTemplateMail(String email, String subject, String template,
                                 Map<String, Object> param) {
        sendTemplateMail(email, subject, template, param, "模板邮件发送失败");
    }

    @Override
    public void sendEmailCode(String email) {
        Map<String, Object> param = new HashMap<>(2);
        String authCode = StringTools.getRandCode();
        String authCodeKey = keyPrefix + email;
        redisService.set(authCodeKey, authCode, keyExpire);
        Asserts.checkTrue(redisService.hasKey(authCodeKey), "邮箱验证码发送失败");
        param.put("email", email);
        param.put("code", authCode);
        sendTemplateMail(email, "操作验证码", "code", param, "邮箱验证码发送失败");
        log.info("验证码为：{}", authCode);
        log.info("发送邮箱：{}，状态：成功", email);
    }

    /**
     * 发送带模板的邮件
     *
     * @param email        收件人
     * @param subject      主题
     * @param template     模板
     * @param param        模板参数
     * @param errorMessage 错误信息
     */
    private void sendTemplateMail(String email, String subject, String template,
                                  Map<String, Object> param, String errorMessage) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setFrom(nickname + '<' + from + '>');
            Context context = new Context();
            context.setVariables(param);
            String emailContent = templateEngine.process(template, context);
            messageHelper.setText(emailContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            Asserts.fail(errorMessage, e);
        }
    }


}