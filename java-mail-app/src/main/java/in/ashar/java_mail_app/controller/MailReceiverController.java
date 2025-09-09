package in.ashar.java_mail_app.controller;

import in.ashar.java_mail_app.dto.MessageResponse;
import in.ashar.java_mail_app.service.MailReceiverService;
import in.ashar.java_mail_app.utility.MailFolders;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/mail/read")
public class MailReceiverController {

    private final MailReceiverService mailReceiverService;

    public MailReceiverController(MailReceiverService mailReceiverService) {
        this.mailReceiverService = mailReceiverService;
    }

    @GetMapping("/recent/{folder}/{count}")
    public List<MessageResponse> readRecentMessages(@PathVariable("count") int count, @PathVariable("folder") MailFolders folder) throws MessagingException {
        return mailReceiverService.readRecentMessages(count, folder);
    }

    @GetMapping("/last/{folder}/{count}")
    public List<MessageResponse> readLastMessages(@PathVariable("count") int count, @PathVariable("folder") MailFolders folder) throws MessagingException {
        return mailReceiverService.readLastMessages(count, folder);
    }


    @GetMapping("/recent/{folder}/{count}/unseen")
    public List<MessageResponse> readRecentUnseenMessages(@PathVariable("count") int count, @PathVariable("folder") MailFolders folder) throws MessagingException {
        return mailReceiverService.readRecentUnseenMessages(count, folder);
    }

    @GetMapping("/last/{folder}/{count}/unseen")
    public List<MessageResponse> readLastUnseenMessages(@PathVariable("count") int count, @PathVariable("folder") MailFolders folder) throws MessagingException {
        return mailReceiverService.readLastUnseenMessages(count, folder);
    }

    @GetMapping("/{folder}/date")
    public List<MessageResponse> readMessagesByDate(@PathVariable("folder") MailFolders folder, @RequestParam("date") Date date) throws MessagingException {

        return mailReceiverService.readMessagesByDate(folder,date);
    }

    @GetMapping("/{folder}/date/range")
    public List<MessageResponse> readMessagesByDate(@PathVariable("folder") MailFolders folder, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate) throws MessagingException {

        return mailReceiverService.readMessagesByDate(folder,startDate,endDate);
    }

    @GetMapping("/{folder}/from")
    public List<MessageResponse> readMessagesByReceivedFrom(@PathVariable("folder") MailFolders folder, @RequestParam String email) throws MessagingException {

        return mailReceiverService.readMessagesByReceivedFrom(folder,email);
    }

    @GetMapping("/{folder}/recipient")
    public List<MessageResponse> readMessagesByRecipient(@PathVariable("folder") MailFolders folder, @RequestParam String email) throws MessagingException {

        return mailReceiverService.readMessagesByRecipient(folder,email);
    }

    @GetMapping("/{folder}/subject")
    public List<MessageResponse> readMessagesBySubject(@PathVariable("folder") MailFolders folder, @RequestParam String subject) throws MessagingException {

        return mailReceiverService.readMessagesBySubject(folder,subject);
    }

}
