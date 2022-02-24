package top.qiudb.third.message.service;

import java.util.Map;

public interface MailService {
    /**
     * 发送简单邮件
     *
     * @param email   收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String email, String subject, String content);

    /**
     * 发送HTML邮件
     *
     * @param email   收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendHtmlMail(String email, String subject, String content);

    /**
     * 发送带附件的邮件
     *
     * @param email    收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件
     */
    void sendAttachmentsMail(String email, String subject, String content, String filePath);

    /**
     * 发送带模板的邮件
     *
     * @param email    收件人
     * @param subject  主题
     * @param template 模板
     * @param param     模板参数
     */
    void sendTemplateMail(String email, String subject, String template, Map<String, Object> param);

    /**
     * 发送邮箱验证码
     *
     * @param email    收件人
     */
    void sendEmailCode(String email);
}
