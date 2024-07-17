package co.edu.iudigital.helpmeiud.services.impl;

import co.edu.iudigital.helpmeiud.services.interfaces.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Boolean sendMail(String message, String email, String emailSubject) {
        boolean sent = false;
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom("noreply@iudigital.edu.co");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(emailSubject);
            mimeMessageHelper.setText(message);

            mailSender.send(mimeMessage);
            sent = true;
            log.info("Email sent successfully");
        } catch (MessagingException e) {
            log.error("Error while sending email {}", e.getMessage());
        }

        return sent;
    }
}
