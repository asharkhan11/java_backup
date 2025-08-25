package in.ashar.email_sender.controller;

import in.ashar.email_sender.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mail/send")
public class SendMail {

    private final MailService mailService;

    SendMail(MailService mailService){
        this.mailService = mailService;
    }

    @PostMapping("/simple")
    public ResponseEntity<String> sendSimpleMail(@RequestParam("to") String to,
                                                 @RequestParam("subject") String subject,
                                                 @RequestParam("body") String body)
    {
        return ResponseEntity.ok(mailService.sendSimpleMail(to, subject, body));
    }


    @PostMapping("/simple-with-attachment")
    public ResponseEntity<String> sendSimpleMailWithAttachment(@RequestParam("to") String to,
                                                         @RequestParam("subject") String subject,
                                                         @RequestParam("body") String body,
                                                         @RequestParam("files") List<MultipartFile> attachments) throws MessagingException {
        return ResponseEntity.ok(mailService.sendSimpleMailWithAttachment(to, subject, body, attachments));
    }


    @PostMapping("/html")
    public ResponseEntity<String> sendHtmlMail(@RequestParam("to") String to,
                                               @RequestParam("subject") String subject,
                                               @RequestParam("htmlBody") String htmlBody,
                                               @RequestParam(value = "files", required = false) List<MultipartFile> attachments) throws MessagingException
    {

        return ResponseEntity.ok(mailService.sendHtmlMail(to, subject, htmlBody, attachments));
    }


    @PostMapping("/inline")
    public ResponseEntity<String> sendInlineImage(@RequestParam("to") String to,
                                               @RequestParam("subject") String subject,
                                               @RequestParam("htmlBody") String htmlBody,
                                               @RequestParam(value = "files") List<MultipartFile> attachments) throws MessagingException, IOException
    {
        return ResponseEntity.ok(mailService.sendInlineImage(to, subject, htmlBody, attachments));
    }



    /// //////////////////////////// ASYNC /////////////////////////////////////////

    @PostMapping("/async/simple")
    public ResponseEntity<String> sendSimpleMailAsync(@RequestParam("to") String to,
                                                 @RequestParam("subject") String subject,
                                                 @RequestParam("body") String body)
    {
        mailService.sendSimpleMailAsync(to, subject, body);
        return ResponseEntity.ok("will be send shortly");
    }


    @PostMapping("/async/simple-with-attachment")
    public ResponseEntity<String> sendSimpleMailWithAttachmentAsync(@RequestParam("to") String to,
                                                               @RequestParam("subject") String subject,
                                                               @RequestParam("body") String body,
                                                               @RequestParam("files") List<MultipartFile> attachments) throws MessagingException, IOException {
        mailService.sendSimpleMailWithAttachmentAsync(to, subject, body, attachments);
        return ResponseEntity.ok("will be send shortly");
    }


    @PostMapping("/async/html")
    public ResponseEntity<String> sendHtmlMailAsync(@RequestParam("to") String to,
                                               @RequestParam("subject") String subject,
                                               @RequestParam("htmlBody") String htmlBody,
                                               @RequestParam(value = "files", required = false) List<MultipartFile> attachments) throws MessagingException, IOException {

        mailService.sendHtmlMailAsync(to, subject, htmlBody, attachments);
        return ResponseEntity.ok("will be send shortly");
    }


    @PostMapping("/async/inline")
    public ResponseEntity<String> sendInlineImageAsync(@RequestParam("to") String to,
                                                  @RequestParam("subject") String subject,
                                                  @RequestParam("htmlBody") String htmlBody,
                                                  @RequestParam(value = "files") List<MultipartFile> attachments) throws MessagingException, IOException
    {

        mailService.sendInlineImageAsync(to, subject, htmlBody, attachments);
        return ResponseEntity.ok("will be send shortly");
    }



}
