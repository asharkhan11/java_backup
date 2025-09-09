package in.ashar.java_mail_app.controller;

import in.ashar.java_mail_app.service.MailSenderService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/mail/send")
@Slf4j
public class MailSenderController {

    private final MailSenderService mailSenderService;

    public MailSenderController(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/simple")
    public ResponseEntity<String> SendMail(@RequestParam("to") List<String> to,
                                                 @RequestParam(value = "cc", required = false) List<String> cc,
                                                 @RequestParam(value = "bcc", required = false) List<String> bcc,
                                                 @RequestParam("subject") String subject,
                                                 @RequestParam("body") String body)
    {
        log.info("sending simple mail ");
        return mailSenderService.SendMail(to, cc, bcc, subject, body);
    }


    @PostMapping("/simple-with-attachment")
    public ResponseEntity<String> sendMailWithAttachment(@RequestParam("to") List<String> to,
                                                               @RequestParam(value = "cc", required = false) List<String> cc,
                                                               @RequestParam(value = "bcc", required = false) List<String> bcc,
                                                               @RequestParam("subject") String subject,
                                                               @RequestParam("body") String body,
                                                               @RequestParam("files") List<MultipartFile> attachments) throws MessagingException
    {
        return mailSenderService.sendMailWithAttachment(to, cc, bcc, subject, body, attachments);
    }


    @PostMapping("/html")
    public ResponseEntity<String> sendMailAsHTML(@RequestParam("to") List<String> to,
                                               @RequestParam(value = "cc", required = false) List<String> cc,
                                               @RequestParam(value = "bcc", required = false) List<String> bcc,
                                               @RequestParam("subject") String subject,
                                               @RequestParam("htmlBody") String htmlBody,
                                               @RequestParam(value = "files", required = false) List<MultipartFile> attachments) throws MessagingException
    {

        return mailSenderService.sendMailAsHTML(to, cc, bcc, subject, htmlBody, attachments);
    }


    @PostMapping("/inline")
    public ResponseEntity<String> sendMailWithInlineImages(@RequestParam("to") List<String> to,
                                                  @RequestParam(value = "cc", required = false) List<String> cc,
                                                  @RequestParam(value = "bcc", required = false) List<String> bcc,
                                                  @RequestParam("subject") String subject,
                                                  @RequestParam("htmlBody") String htmlBody,
                                                  @RequestParam(value = "files") List<MultipartFile> attachments) throws MessagingException, IOException
    {

        return mailSenderService.sendMailWithInlineImages(to, cc, bcc, subject, htmlBody, attachments);
    }



    /// //////////////////////////// ASYNC /////////////////////////////////////////

    @PostMapping("/async/simple")
    public ResponseEntity<String> sendAsyncMail(@RequestParam("to") List<String> to,
                                                      @RequestParam(value = "cc", required = false) List<String> cc,
                                                      @RequestParam(value = "bcc", required = false) List<String> bcc,
                                                      @RequestParam("subject") String subject,
                                                      @RequestParam("body") String body) throws InterruptedException
    {
        mailSenderService.sendAsyncMail(to, cc, bcc, subject, body);
        Thread.sleep(500);
        return ResponseEntity.ok("will be send shortly");
    }


    @PostMapping("/async/simple-with-attachment")
    public ResponseEntity<String> sendAsyncMailWithAttachment(@RequestParam("to") List<String> to,
                                                                    @RequestParam(value = "cc", required = false) List<String> cc,
                                                                    @RequestParam(value = "bcc", required = false) List<String> bcc,
                                                                    @RequestParam("subject") String subject,
                                                                    @RequestParam("body") String body,
                                                                    @RequestParam("files") List<MultipartFile> attachments) throws MessagingException, IOException, InterruptedException
    {
        mailSenderService.sendAsyncMailWithAttachment(to, cc, bcc, subject, body, attachments);
        Thread.sleep(500);
        return ResponseEntity.ok("will be send shortly");
    }


    @PostMapping("/async/html")
    public ResponseEntity<String> sendAsyncMailAsHTML(@RequestParam("to") List<String> to,
                                                    @RequestParam(value = "cc", required = false) List<String> cc,
                                                    @RequestParam(value = "bcc", required = false) List<String> bcc,
                                                    @RequestParam("subject") String subject,
                                                    @RequestParam("htmlBody") String htmlBody,
                                                    @RequestParam(value = "files", required = false) List<MultipartFile> attachments) throws MessagingException, IOException, InterruptedException
    {

        mailSenderService.sendAsyncMailAsHTML(to, cc, bcc, subject, htmlBody, attachments);
        Thread.sleep(500);
        return ResponseEntity.ok("will be send shortly");
    }


    @PostMapping("/async/inline")
    public ResponseEntity<String> sendAsyncMailWithInlineImages(@RequestParam("to") List<String> to,
                                                       @RequestParam(value = "cc", required = false) List<String> cc,
                                                       @RequestParam(value = "bcc", required = false) List<String> bcc,
                                                       @RequestParam("subject") String subject,
                                                       @RequestParam("htmlBody") String htmlBody,
                                                       @RequestParam(value = "files") List<MultipartFile> attachments) throws MessagingException, IOException, InterruptedException
    {

        mailSenderService.sendAsyncMailWithInlineImages(to, cc, bcc, subject, htmlBody, attachments);
        Thread.sleep(500);
        return ResponseEntity.ok("will be send shortly");
    }
}
