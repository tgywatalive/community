package com.newcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/*
* 邮件工具类
* */
@Component
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Autowired
    private JavaMailSender mailSender; // JavaMailSender是Spring Email的核心组件，负责发送邮件

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage(); // MimeMessage用于封装邮件的相关信息
            MimeMessageHelper helper = new MimeMessageHelper(message); // MimeMessageHelper用于辅助构建MimeMessage对象
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // 不写true默认text格式发送邮件
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送邮件失败："+ e.getMessage());
        }
    }
}
