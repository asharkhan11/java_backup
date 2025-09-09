package in.ashar.email_sender.controller;

import in.ashar.email_sender.dto.MessageResponse;
import in.ashar.email_sender.service.ReceiveMailService;
import in.ashar.email_sender.utility.MailFolders;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mail/read")
public class ReceiveMailController {

    private final ReceiveMailService receiveMailService;

    ReceiveMailController(ReceiveMailService receiveMailService){
        this.receiveMailService = receiveMailService;
    }


    @GetMapping("/{folder}/{count}")
    public List<MessageResponse> readRecentMessagesFrom(@PathVariable("count") int count, @PathVariable("folder") MailFolders folder) throws MessagingException, IOException {
        return receiveMailService.readRecentMessages(count, folder);
    }


    @GetMapping("/{folder}/{count}/unseen")
    public List<MessageResponse> readRecentUnseenMessages(@PathVariable("count") int count, @PathVariable("folder") MailFolders folder) throws MessagingException, IOException {
        return receiveMailService.readRecentUnseenMessages(count, folder);
    }

    @GetMapping("/{folder}/date")
    public List<MessageResponse> readMessagesByDate(@PathVariable("folder") MailFolders folder, @RequestParam("date") Date date) throws MessagingException, IOException {

        return receiveMailService.readMessagesByDate(folder,date);
    }

    @GetMapping("/{folder}/from")
    public List<MessageResponse> readMessagesByReceivedFrom(@PathVariable("folder") MailFolders folder, @RequestParam String email) throws MessagingException, IOException {

        return receiveMailService.readMessagesByReceivedFrom(folder,email);
    }

}
