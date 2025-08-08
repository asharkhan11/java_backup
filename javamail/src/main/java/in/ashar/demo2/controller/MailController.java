package in.ashar.demo2.controller;

import in.ashar.demo2.dto.MessageDto;
import in.ashar.demo2.exception.IncompleteDataException;
import in.ashar.demo2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendMail(@RequestBody MessageDto message){
        if(message.getTo().isBlank() || message.getSubject().isBlank() || message.getBody().isBlank()){
            throw new IncompleteDataException("data must contain To, Subject and Body");
        }

        return emailService.sendMail(message);
    }
}
