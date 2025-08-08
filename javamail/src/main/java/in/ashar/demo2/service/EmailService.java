package in.ashar.demo2.service;

import in.ashar.demo2.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendMail(MessageDto message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(message.getTo());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getBody());

        javaMailSender.send(mailMessage);
        return "mail sent";
    }
}
