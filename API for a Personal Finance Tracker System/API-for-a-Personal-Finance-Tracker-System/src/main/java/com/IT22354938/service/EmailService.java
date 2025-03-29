package com.IT22354938.service;



import com.IT22354938.dto.EmailRequest;
import jakarta.mail.AuthenticationFailedException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendEmail(EmailRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("pereraps1234@gmail.com");
            helper.setTo(request.to());
            helper.setSubject(request.subject());
            helper.setText(request.body(), true);

            mailSender.send(message);
            return "Email Sent Successfully!";
        } catch (AuthenticationFailedException e) {
            return "Email Authentication Failed. Please check your credentials.";
        } catch (MailException e) {
            return "Email Sending Error: " + e.getMessage();
        } catch (Exception e) {
            return "Unexpected Error: " + e.getMessage();
        }
    }
}
