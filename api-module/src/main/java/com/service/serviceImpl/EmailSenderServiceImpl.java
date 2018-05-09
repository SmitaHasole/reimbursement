package com.service.serviceImpl;

import com.persistence.entity.Reimbursement;
import com.persistence.entity.UserData;
import com.service.EmailSenderService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * created by Smita Hasole on 02-05-2018
 */
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private JavaMailSender javaMailSender;

    private Configuration freemarkerConfiguration;
    private UserService userService;
    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender,
                                  Configuration freemarkerConfiguration, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.freemarkerConfiguration = freemarkerConfiguration;
        this.userService = userService;
    }

    @Override
    public void sendMail(String msg, Reimbursement reimbursement) {
        Map<String, String> detailsMap = new HashMap<>();
        UserData userData = userService.getUserByReimbursement(reimbursement);
        detailsMap.put("username", userData.getFName() + " " + userData.getLName());
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        freemarkerConfiguration.setClassForTemplateLoading(this.getClass(),"/email-templates");
        Template template = null;
        try {
            template = freemarkerConfiguration.getTemplate("report-generated.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, detailsMap);
            helper.setTo(userData.getEmailId());
            helper.setText(text, true);
            helper.setSubject("mail sent");
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
