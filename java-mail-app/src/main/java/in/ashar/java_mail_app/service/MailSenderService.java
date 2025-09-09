package in.ashar.java_mail_app.service;

import in.ashar.java_mail_app.exception.ErrorWhileSendingMail;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
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
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    public MailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /// //////////////////////// SYNCHRONIZED  //////////////////////////////

    public ResponseEntity<String> SendMail(List<String> to, List<String> cc, List<String> bcc, String subject, String body) {
        return sendSimpleMail(to, cc, bcc, subject, body);
    }

    public ResponseEntity<String> sendMailWithAttachment(List<String> to, List<String> cc, List<String> bcc, String subject, String body, List<MultipartFile> attachments) throws MessagingException {
        return sendSimpleMailWithAttachment(to, cc, bcc, subject, body, attachments);
    }

    public ResponseEntity<String> sendMailAsHTML(List<String> to, List<String> cc, List<String> bcc, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException {
        return sendHtmlMail(to, cc, bcc, subject, htmlBody, attachments);
    }

    public ResponseEntity<String> sendMailWithInlineImages(List<String> to, List<String> cc, List<String> bcc, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException, IOException {
        return sendInlineImage(to, cc, bcc, subject, htmlBody, attachments);
    }

    /// //////////////////////////////  ASYNC /////////////////////////////////////////

    @Async
    public void sendAsyncMail(List<String> to, List<String> cc, List<String> bcc, String subject, String body) {
        sendSimpleMail(to, cc, bcc, subject, body);
    }

    @Async
    public void sendAsyncMailWithAttachment(List<String> to, List<String> cc, List<String> bcc, String subject, String body, List<MultipartFile> attachments) throws MessagingException, IOException {
        sendSimpleMailWithAttachment(to, cc, bcc, subject, body, attachments);
    }

    @Async
    public void sendAsyncMailAsHTML(List<String> to, List<String> cc, List<String> bcc, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException, IOException {
        sendHtmlMail(to, cc, bcc, subject, htmlBody, attachments);
    }

    @Async
    public void sendAsyncMailWithInlineImages(List<String> to, List<String> cc, List<String> bcc, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException, IOException {
        sendInlineImage(to, cc, bcc, subject, htmlBody, attachments);
    }


    /// /////////////////// Service Methods ///////////////////////////////


    private ResponseEntity<String> sendSimpleMail(List<String> to, List<String> cc, List<String> bcc, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to.toArray(new String[0]));

        if (cc != null && !cc.isEmpty()) message.setCc(cc.toArray(new String[0]));
        if (bcc != null && !bcc.isEmpty()) message.setBcc(bcc.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(body);

        return executeMail(message);
    }

    private ResponseEntity<String> sendSimpleMailWithAttachment(List<String> to, List<String> cc, List<String> bcc, String subject, String body, List<MultipartFile> attachments) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to.toArray(new String[0]));
        if (cc != null && !cc.isEmpty()) helper.setCc(cc.toArray(new String[0]));
        if (bcc != null && !bcc.isEmpty()) helper.setBcc(bcc.toArray(new String[0]));
        helper.setSubject(subject);
        helper.setText(body);

        for (MultipartFile attachment : attachments) {
            helper.addAttachment(attachment.getOriginalFilename(), attachment);
        }

        return executeMail(message);
    }


    private ResponseEntity<String> sendHtmlMail(List<String> to, List<String> cc, List<String> bcc, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to.toArray(new String[0]));
        if (cc != null && !cc.isEmpty()) helper.setCc(cc.toArray(new String[0]));
        if (bcc != null && !bcc.isEmpty()) helper.setBcc(bcc.toArray(new String[0]));
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        System.out.println(attachments);

        if (attachments != null && !attachments.isEmpty()) {
            for (MultipartFile attachment : attachments) {
                helper.addAttachment(attachment.getOriginalFilename(), attachment);
            }
        }

        return executeMail(message);
    }

    private ResponseEntity<String> sendInlineImage(List<String> to, List<String> cc, List<String> bcc, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(to.toArray(new String[0]));
        if (cc != null && !cc.isEmpty()) helper.setCc(cc.toArray(new String[0]));
        if (bcc != null && !bcc.isEmpty()) helper.setBcc(bcc.toArray(new String[0]));
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        if (attachments != null) {
            for (MultipartFile attachment : attachments) {
                if (htmlBody.contains("{cid}")) {
                    String cid = UUID.randomUUID().toString();
                    helper.addInline(cid, attachment, attachment.getContentType());
                    htmlBody = htmlBody.replaceFirst("\\{cid\\}", cid);
                } else {
                    helper.addAttachment(attachment.getOriginalFilename(), attachment);
                }
            }
        }

        helper.setText(htmlBody, true);

        return executeMail(message);
    }


    /// //////////////////  HELPER Methods ////////////////////////

    private <T> ResponseEntity<String> executeMail(T message) {
        try {
            if (message instanceof SimpleMailMessage simpleMsg) {
                javaMailSender.send(simpleMsg);
            } else if (message instanceof MimeMessage mimeMsg) {
                javaMailSender.send(mimeMsg);
            } else {
                throw new IllegalArgumentException("Unsupported message type");
            }
            return ResponseEntity.ok("success");
        } catch (MailException e) {
            throw new ErrorWhileSendingMail(e.getMessage());
        }
    }



    /// //////////////////// ASYNC /////////////////////////////////

//    @Async
//    public void sendSimpleMailAsync(List<String> to, List<String> cc, List<String> bcc, String subject, String body) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setTo(to.toArray(new String[0]));
//        if(!cc.isEmpty()) message.setCc(cc.toArray(new String[0]));
//        if(!bcc.isEmpty()) message.setBcc(bcc.toArray(new String[0]));
//        message.setSubject(subject);
//        message.setText(body);
//
//        javaMailSender.send(message);
//
//    }
//
//    @Async
//    public void sendSimpleMailWithAttachmentAsync(
//            List<String> to, List<String> cc, List<String> bcc, String subject, String body, List<MultipartFile> attachments
//    ) throws MessagingException, IOException {
//
//        List<byte[]> list = new ArrayList<>();
//        List<String> fileNames = new ArrayList<>();
//
//        for(MultipartFile attachment : attachments){
//            list.add(attachment.getBytes());
//            fileNames.add(attachment.getOriginalFilename());
//        }
//
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to.toArray(new String[0]));
//        if(!cc.isEmpty()) helper.setCc(cc.toArray(new String[0]));
//        if(!bcc.isEmpty()) helper.setBcc(bcc.toArray(new String[0]));
//        helper.setSubject(subject);
//        helper.setText(body);
//
//
//
//        for(int i=0; i<list.size(); i++){
//            helper.addAttachment(fileNames.get(i), new ByteArrayResource(list.get(i)));
//        }
//
//        javaMailSender.send(message);
//    }
//
//
//
//    @Async
//    public void sendHtmlMailAsync(List<String> to, List<String> cc, List<String> bcc, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException, IOException {
//
//        List<byte[]> list = new ArrayList<>();
//        List<String> fileNames = new ArrayList<>();
//
//        for(MultipartFile attachment : attachments){
//            list.add(attachment.getBytes());
//            fileNames.add(attachment.getOriginalFilename());
//        }
//
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to.toArray(new String[0]));
//        if(!cc.isEmpty()) helper.setCc(cc.toArray(new String[0]));
//        if(!bcc.isEmpty()) helper.setBcc(bcc.toArray(new String[0]));
//        helper.setSubject(subject);
//        helper.setText(htmlBody, true);
//
//        for(int i=0; i<list.size(); i++){
//            helper.addAttachment(fileNames.get(i), new ByteArrayResource(list.get(i)));
//        }
//
//        javaMailSender.send(message);
//    }
//
//    @Async
//    public void sendInlineImageAsync(List<String> to, List<String> cc, List<String> bcc, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException, IOException {
//
//        List<byte[]> list = new ArrayList<>();
//        List<String> fileNames = new ArrayList<>();
//        List<String> contentType = new ArrayList<>();
//
//        for(MultipartFile attachment : attachments){
//            list.add(attachment.getBytes());
//            fileNames.add(attachment.getOriginalFilename());
//            contentType.add(attachment.getContentType());
//        }
//
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
//
//        helper.setTo(to.toArray(new String[0]));
//        if(!cc.isEmpty()) helper.setCc(cc.toArray(new String[0]));
//        if(!bcc.isEmpty()) helper.setBcc(bcc.toArray(new String[0]));
//        helper.setSubject(subject);
//        helper.setText(htmlBody, true);
//
//
//        for(int i=0; i<list.size(); i++){
//            if(htmlBody.contains("{cid}")) {
//                String cid = UUID.randomUUID().toString();
//                helper.addInline(cid, new ByteArrayResource(list.get(i)), contentType.get(i));
//                htmlBody = htmlBody.replaceFirst("\\{cid\\}", cid);
//            }else{
//                helper.addAttachment(fileNames.get(i), new ByteArrayResource(list.get(i)));
//            }
//        }
//
//        helper.setText(htmlBody, true);
//
//        javaMailSender.send(message);
//
//    }
}
