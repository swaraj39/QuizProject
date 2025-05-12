package com.pack.demo.EmailService;

import com.pack.demo.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
@Component
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    // public String sendMail(int code, UserModel userModel) {
    // try {
    // SimpleMailMessage mailMessage = new SimpleMailMessage();
    // mailMessage.setFrom(sender);
    // mailMessage.setTo(userModel.getEmail());
    // mailMessage.setText("Dear Customer,/n" + userModel.getName()
    // + "/nNice you to see here please/nYour One Time Password is " + "{" + code +
    // "}"
    // + " for To SRG Quizzz/nDo not share with anyone/nThank You");
    // mailMessage.setSubject("SRGQuizzz");
    // javaMailSender.send(mailMessage);
    // return "successfull";
    // } catch (Exception e) {
    // System.out.println(e);
    // return "not sent";
    // }
    // }

    // }

    public String sendMail(int code, UserModel userModel) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(sender);
            System.out.println(userModel.getEmail());
            helper.setTo(userModel.getEmail());
            helper.setSubject("🚀 Your OTP for Quiz Shedder 🎉");
            String htmlMsg = "<html>" +
                    "<body style='font-family: Arial, sans-serif;'>" +
                    "<h2 style='color: #2E86C1;'>Dear " + userModel.getName() + ", 👋</h2>" +
                    "<p>Welcome to <strong>SRG Quizzz</strong>! We're excited to have you on board.</p>" +
                    "<p>🎯 <strong>Your One-Time Password (OTP) is:</strong></p>" +
                    "<div style='font-size: 24px; color: #D35400; font-weight: bold; padding: 10px; border: 2px dashed #D35400; display: inline-block;'>"
                    +
                    code +
                    "</div>" +
                    "<p style='margin-top: 20px;'>🔒 <i>Please do not share this OTP with anyone. <br> Valid for 30 min </i></p>"
                    +
                    "<p>Thanks & Regards,<br><strong>Team SRG Quizzz</strong></p>" +
                    "<img src='https://cdn-icons-png.flaticon.com/512/3135/3135715.png' alt='Quiz Icon' width='100' style='margin-top: 10px;'/>"
                    +
                    "</body></html>";

            helper.setText(htmlMsg, true); // true indicates HTML

            javaMailSender.send(mimeMessage);
            return "successful";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "not sent";
        }
    }

    public String sendMail(String name, String email, String message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(email);
            helper.setTo(sender);
            helper.setSubject("Contact Form Submission from " + name);
            String htmlMsg = "<html>" +
                    "<body style='font-family: Arial, sans-serif;'>" +
                    "<h2 style='color: #2E86C1;'>Dear " + name + ", 👋</h2>" +
                    "<p>Thank you for reaching out to us!</p>" +
                    "<p>Your message:</p>" +
                    "<blockquote style='border-left: 4px solid #D35400; padding-left: 10px;'>" +
                    message +
                    "</blockquote>" +
                    "<p>We will get back to you shortly.</p>" +
                    "<p>Thanks & Regards,<br><strong>Team SRG Quizzz</strong></p>" +
                    "</body></html>";

            helper.setText(htmlMsg, true); // true indicates HTML

            javaMailSender.send(mimeMessage);
            return "successful";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "not sent";
        }
    }
}
