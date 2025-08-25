package in.ashar.email_sender.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendSimpleMail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);

        return "success";
    }

    public String sendSimpleMailWithAttachment(String to, String subject, String body, List<MultipartFile> attachments) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        for (MultipartFile attachment : attachments) {
            helper.addAttachment(attachment.getOriginalFilename(), attachment);
        }

        javaMailSender.send(message);

        return "success";
    }


    public String sendHtmlMail(String to, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        System.out.println(attachments);

        if (attachments != null && !attachments.isEmpty()) {
            for (MultipartFile attachment : attachments) {
                helper.addAttachment(attachment.getOriginalFilename(), attachment);
            }
        }

        javaMailSender.send(message);

        return "success";
    }

    public String sendInlineImage(String to, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        if (attachments != null) {
            for (MultipartFile attachment : attachments) {
//                if (attachment.getContentType() != null && attachment.getContentType().contains("image")) {
//                    if (htmlBody.contains("{cid}")){
//                        String cid = UUID.randomUUID().toString();
//                        helper.addInline(cid,attachment,attachment.getContentType());
//                        htmlBody = htmlBody.replace("{cid}",cid);
//                        System.out.println("if blcok");
//                    }
//                    else{
//                        helper.addAttachment(attachment.getOriginalFilename(), attachment);
//                        System.out.println("first else");
//                    }
//                }
//                else {
//                    helper.addAttachment(attachment.getOriginalFilename(), attachment);
//                    System.out.println("second else");
//                }
                if(htmlBody.contains("{cid}")) {
                    String cid = UUID.randomUUID().toString();
                    helper.addInline(cid, attachment, attachment.getContentType());
                    htmlBody = htmlBody.replaceFirst("\\{cid\\}", cid);
                }else{
                    helper.addAttachment(attachment.getOriginalFilename(), attachment);
                }
            }
        }

        helper.setText(htmlBody, true);

        javaMailSender.send(message);

        return "success";
    }



    /// //////////////////// ASYNC /////////////////////////////////

    @Async
    public void sendSimpleMailAsync(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);

    }

    @Async
    public void sendSimpleMailWithAttachmentAsync(String to, String subject, String body, List<MultipartFile> attachments) throws MessagingException, IOException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        for (MultipartFile attachment : attachments) {
            helper.addAttachment(attachment.getOriginalFilename(), new ByteArrayResource(attachment.getBytes()));
        }

        javaMailSender.send(message);

    }


    @Async
    public void sendHtmlMailAsync(String to, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        if (attachments != null && !attachments.isEmpty()) {
            for (MultipartFile attachment : attachments) {
                helper.addAttachment(attachment.getOriginalFilename(), new ByteArrayResource(attachment.getBytes()));
            }
        }

        javaMailSender.send(message);
    }

    @Async
    public void sendInlineImageAsync(String to, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        if (attachments != null) {
            for (MultipartFile attachment : attachments) {
                if(htmlBody.contains("{cid}")) {
                    String cid = UUID.randomUUID().toString();
                    helper.addInline(cid, new ByteArrayResource(attachment.getBytes()), attachment.getContentType());
                    htmlBody = htmlBody.replaceFirst("\\{cid\\}", cid);
                }else{
                    helper.addAttachment(attachment.getOriginalFilename(), new ByteArrayResource(attachment.getBytes()));
                }
            }
        }

        helper.setText(htmlBody, true);

        javaMailSender.send(message);

    }

}
